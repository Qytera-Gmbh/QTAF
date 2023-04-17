package de.qytera.qtaf.xray.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayRestPaths;
import de.qytera.qtaf.xray.dto.request.graphql.GraphQLRequestDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayCloudTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.entity.XrayAuthCredentials;
import de.qytera.qtaf.xray.events.QtafXrayEvents;
import de.qytera.qtaf.xray.log.XrayAuthenticationErrorLog;

import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Xray API Rest Client
 */
public class XrayCloudService extends AbstractXrayService {

    /**
     * Singleton instance.
     */
    private static final XrayCloudService INSTANCE = new XrayCloudService();

    /**
     * Returns the singleton instance of the {@code XrayCloudService}.
     *
     * @return the instance
     */
    public static XrayCloudService getInstance() {
        if (INSTANCE.xrayAuthCredentials == null) {
            String clientId = XrayConfigHelper.getAuthenticationXrayClientId();
            if (clientId == null) {
                throw new MissingConfigurationValueException(
                        XrayConfigHelper.AUTHENTICATION_XRAY_CLIENT_ID,
                        QtafFactory.getConfiguration()
                );
            }
            String clientSecret = XrayConfigHelper.getAuthenticationXrayClientSecret();
            if (clientSecret == null) {
                throw new MissingConfigurationValueException(
                        XrayConfigHelper.AUTHENTICATION_XRAY_CLIENT_SECRET,
                        QtafFactory.getConfiguration()
                );
            }
            INSTANCE.setAuthCredentials(new XrayAuthCredentials(clientId, clientSecret));
        }
        return INSTANCE;
    }

    private XrayCloudService() {
        // Hide constructor.
    }

    /**
     * Xray Cloud authentication endpoint.
     */
    private static final String PATH_AUTHENTICATION = "/authenticate";

    /**
     * Xray Cloud GraphQL endpoint.
     */
    private static final String PATH_GRAPH_QL = "/graphql";

    /**
     * The maximum number of queryable issues when querying Xray using JQL in GraphQL requests.
     *
     * @see <a href="https://xray.cloud.getxray.app/doc/graphql/gettests.doc.html">GraphQL documentation</a>
     */
    private static final int GRAPH_QL_QUERY_ISSUES_LIMIT = 100;

    /**
     * Xray authentication credentials
     */
    private XrayAuthCredentials xrayAuthCredentials;

    @Override
    public String getXrayURL() {
        return XrayRestPaths.XRAY_CLOUD_API_V2;
    }

    @Override
    public String getImportPath() {
        return "/import/execution";
    }

    @Override
    public String getIssueSearchPath() {
        return "/rest/api/3/search";
    }

    private Map<String, XrayTestStepResponseDto[]> parseTestStepResponse(String request, ClientResponse response) {
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            String reason = String.format(
                    "%d %s",
                    response.getStatus(),
                    response.getStatusInfo().getReasonPhrase()
            );
            String message = String.format("Failed to get test steps for issues using query '%s': %s", request, reason);
            logger.error(message);
            errorLogs.addErrorLog(new IllegalStateException(message));
            return Collections.emptyMap();
        }
        XrayCloudTestStepResponseDto stepResponse = GsonFactory.getInstance().fromJson(
                response.getEntity(String.class),
                XrayCloudTestStepResponseDto.class
        );
        if (stepResponse.hasErrors()) {
            String message = String.format(
                    "Failed to get test steps for issues using query '%s': %s",
                    request,
                    stepResponse.errorReason()
            );
            logger.error(message);
            errorLogs.addErrorLog(new IllegalStateException(message));
            return Collections.emptyMap();
        }
        Map<String, XrayTestStepResponseDto[]> stepsByIssue = new HashMap<>();
        Arrays.stream(stepResponse.getData().getGetTests().getResults())
                .forEach(r -> stepsByIssue.put(r.getIssueId(), r.getSteps()));
        return stepsByIssue;
    }

    private Map<String, XrayTestStepResponseDto[]> convertIssueIdsToIssueKeys(
            Map<String, XrayTestStepResponseDto[]> stepsByIssueId,
            Collection<String> testIssueKeys
    ) {
        Map<String, String> issueIds = getIssueIds(testIssueKeys);
        Map<String, String> testKeysByIssueId = issueIds.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<String, XrayTestStepResponseDto[]> stepsByIssueKey = new HashMap<>(stepsByIssueId.size());
        stepsByIssueId.forEach((id, steps) -> stepsByIssueKey.put(testKeysByIssueId.get(id), steps));
        return stepsByIssueKey;
    }

    @Override
    public Map<String, XrayTestStepResponseDto[]> getTestSteps(Collection<String> testIssueKeys) {
        // Retrieve issue data of all issues we're interested in through a GraphQL query.
        // See: https://xray.cloud.getxray.app/doc/graphql/gettests.doc.html
        String query = """
                query ($jql: String!, $limit: Int!) {
                  getTests(jql: $jql, limit: $limit) {
                    total
                    limit
                    results {
                      issueId
                      testType {
                        name
                        kind
                      }
                      steps {
                        id
                        data
                        action
                        result
                      }
                    }
                  }
                }
                """;
        // For big test sets, we need to send multiple queries, since JQL queries that would return more than
        // GRAPH_QL_QUERY_ISSUES_LIMIT issues result in error responses (see link above).
        Map<String, XrayTestStepResponseDto[]> stepsByIssue = new HashMap<>();
        for (int n = 0; n < testIssueKeys.size(); n = n + GRAPH_QL_QUERY_ISSUES_LIMIT) {
            List<String> subset = testIssueKeys.stream()
                    .skip(n)
                    .limit(GRAPH_QL_QUERY_ISSUES_LIMIT)
                    .toList();
            String jql = String.format("issue in (%s)", String.join(",", subset));
            WebResource webResource = client.resource(getXrayURL() + PATH_GRAPH_QL);
            QtafXrayEvents.webResourceAvailable.onNext(webResource);
            GraphQLRequestDto request = new GraphQLRequestDto();
            request.setQuery(query);
            request.addVariable("jql", jql);
            request.addVariable("limit", GRAPH_QL_QUERY_ISSUES_LIMIT);
            String json = GsonFactory.getInstance().toJson(request);
            ClientResponse response = webResource
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", authorizationHeaderXray())
                    .post(ClientResponse.class, json);
            stepsByIssue.putAll(parseTestStepResponse(json, response));
        }
        // Unfortunately, Xray only issue IDs instead of issue keys. Therefore, we need this extra step now.
        return convertIssueIdsToIssueKeys(stepsByIssue, testIssueKeys);
    }

    /**
     * Set authentication credentials
     *
     * @param xrayAuthCredentials Authentication credentials
     */
    public void setAuthCredentials(XrayAuthCredentials xrayAuthCredentials) {
        this.xrayAuthCredentials = xrayAuthCredentials;
    }

    /**
     * Authenticate against Xray API
     *
     * @return Bearer token
     */
    public String authorizationHeaderXray() {
        // Check if bearer token is available, if not try to get one by clientId and clientSecret
        if (jwtToken == null) {
            // Build HTTP Request
            WebResource webResource = client.resource(getXrayURL() + PATH_AUTHENTICATION);

            // Build HTTP Request Payload
            Gson gson = GsonFactory.getInstance();
            String json = gson.toJson(xrayAuthCredentials);

            // Send HTTP Request
            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, json);

            QtafXrayEvents.authenticationResponseAvailable.onNext(response);

            // Check response
            if (response.getStatus() != 200) {
                String text = response.getEntity(String.class);
                QtafXrayEvents.authenticationSuccess.onNext(false);
                ErrorLog authErrorLog = new XrayAuthenticationErrorLog(new Exception(text))
                        .setErrorMessage(response.getStatusInfo().getReasonPhrase())
                        .setStatusCode(response.getStatus());
                errorLogs.addErrorLog(authErrorLog);
            } else {
                QtafXrayEvents.authenticationSuccess.onNext(true);
            }

            jwtToken = gson.fromJson(response.getEntity(String.class), String.class);
        }

        return String.format("Bearer %s", jwtToken);
    }

    @Override
    public String authorizationHeaderJira() {
        String username = XrayConfigHelper.getAuthenticationJiraUsername();
        if (username == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_JIRA_USERNAME,
                    QtafFactory.getConfiguration()
            );
        }
        String apiToken = XrayConfigHelper.getAuthenticationJiraAPIToken();
        if (apiToken == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_JIRA_API_TOKEN,
                    QtafFactory.getConfiguration());
        }
        String encoded = Base64Helper.encode(String.format("%s:%s", username, apiToken));
        return String.format("Basic %s", encoded);
    }

}
