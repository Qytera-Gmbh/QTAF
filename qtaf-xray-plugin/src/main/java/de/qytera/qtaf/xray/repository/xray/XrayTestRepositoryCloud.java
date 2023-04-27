package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.dto.request.graphql.GraphQLRequestDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayCloudTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class XrayTestRepositoryCloud implements XrayTestRepository {

    /**
     * The maximum number of queryable issues when querying Xray using JQL in GraphQL requests.
     *
     * @see <a href="https://xray.cloud.getxray.app/doc/graphql/gettests.doc.html">GraphQL documentation</a>
     */
    private static final int GRAPH_QL_QUERY_ISSUES_LIMIT = 100;

    @Override
    public Map<String, XrayTestStepResponseDto[]> getTestSteps(Collection<String> testIssueKeys) throws URISyntaxException, MissingConfigurationValueException {
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
            GraphQLRequestDto dto = new GraphQLRequestDto();
            dto.setQuery(query);
            dto.addVariable("jql", jql);
            dto.addVariable("limit", GRAPH_QL_QUERY_ISSUES_LIMIT);
            RequestBuilder request = WebService.buildRequest(getURIGraphQL());
            request.getBuilder()
                    .header(HttpHeaders.AUTHORIZATION, getXrayAuthorizationHeaderValue())
                    .accept(MediaType.APPLICATION_JSON_TYPE);
            String requestData = GsonFactory.getInstance().toJson(dto);
            try (Response response = WebService.post(request, GsonFactory.getInstance().toJsonTree(dto))) {
                String responseData = response.readEntity(String.class);
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    stepsByIssue.putAll(parseTestStepResponse(requestData, responseData));
                } else {
                    String reason = String.format(
                            "%d %s: %s",
                            response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            responseData
                    );
                    QtafFactory.getLogger().error(
                            String.format(
                                    "[QTAF Xray Plugin] Failed to get test steps for issues using query '%s': %s",
                                    requestData,
                                    reason
                            )
                    );
                    break;
                }
            }
        }
        // Unfortunately, Xray only returns issue IDs instead of issue keys. Therefore, we need this extra step now.
        return convertIssueIdsToIssueKeys(stepsByIssue, testIssueKeys);
    }

    private URI getURIGraphQL() throws URISyntaxException {
        return new URI(getXrayURL() + "/graphql");
    }

    private Map<String, XrayTestStepResponseDto[]> parseTestStepResponse(String request, String response) {
        XrayCloudTestStepResponseDto stepResponse = GsonFactory.getInstance().fromJson(
                response,
                XrayCloudTestStepResponseDto.class
        );
        if (stepResponse.hasErrors()) {
            QtafFactory.getLogger().error(String.format(
                    "[QTAF Xray Plugin] Failed to get test steps for issues using query '%s': %s",
                    request,
                    stepResponse.errorReason()
            ));
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
    ) throws URISyntaxException, MissingConfigurationValueException {
        Map<String, String> issueIds = JiraIssueRepository.getInstance().getIssueIds(testIssueKeys);
        Map<String, String> testKeysByIssueId = issueIds.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<String, XrayTestStepResponseDto[]> stepsByIssueKey = new HashMap<>(stepsByIssueId.size());
        stepsByIssueId.forEach((id, steps) -> stepsByIssueKey.put(testKeysByIssueId.get(id), steps));
        return stepsByIssueKey;
    }


}
