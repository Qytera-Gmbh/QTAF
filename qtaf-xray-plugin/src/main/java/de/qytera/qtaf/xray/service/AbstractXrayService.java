package de.qytera.qtaf.xray.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.request.issues.XrayIssueSearchRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayCloudImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayServerImportResponseDto;
import de.qytera.qtaf.xray.dto.response.issues.XrayIssueSearchResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.events.QtafXrayEvents;

import javax.ws.rs.core.MediaType;
import java.util.*;

public abstract class AbstractXrayService {

    /**
     * JWT auth token that was fetched from the API
     */
    protected String jwtToken = null;

    /**
     * Jersey HTTP Client
     */
    protected final Client client = new Client();

    /**
     * The logger instance used for writing logs to stdout and stderr.
     */
    protected final Logger logger = QtafFactory.getLogger();

    /**
     * The collection used for persisting error messages.
     */
    protected final ErrorLogCollection errorLogs = ErrorLogCollection.getInstance();

    /**
     * Get Xray URL
     *
     * @return Xray URL
     */
    public abstract String getXrayURL();

    /**
     * Get Xray REST Import Path
     *
     * @return Xray REST Import Path
     */
    public abstract String getImportPath();

    /**
     * Returns the Jira instance's JQL search path.
     *
     * @return REST path for JQL queries
     */
    public abstract String getIssueSearchPath();

    /**
     * Authentication against Xray API.
     *
     * @return authorization header value
     */
    public abstract String authorizationHeaderXray();

    /**
     * Authentication against Jira API.
     *
     * @return authorization header value
     */
    public abstract String authorizationHeaderJira();

    /**
     * Get JWT token
     *
     * @return jwt token
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Set jwtToken
     *
     * @param jwtToken JwtToken
     * @return this
     */
    public AbstractXrayService setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    /**
     * Import a test execution
     *
     * @param xrayImportDTO Test execution DTO
     * @return Test execution response DTO
     */
    public XrayImportResponseDto importExecution(
            XrayImportRequestDto xrayImportDTO
    ) {
        // Create HTTP client
        WebResource webResource = client.resource(getXrayURL() + getImportPath());
        QtafXrayEvents.webResourceAvailable.onNext(webResource);

        // Create JSON string that gets uplaoded
        Gson gson = GsonFactory.getInstance();
        String json = gson.toJson(xrayImportDTO);

        // Send request
        ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getJwtToken())
                .post(ClientResponse.class, json);

        // Check response
        if (response.getStatus() == 200) {
            QtafXrayEvents.uploadStatus.onNext(true);
        } else {
            QtafXrayEvents.uploadStatus.onNext(false);
        }

        QtafXrayEvents.uploadResponseAvailable.onNext(response);

        // Create response entity
        XrayImportResponseDto responseDto = null;

        if (XrayConfigHelper.isXrayServerService()) {
            responseDto = gson.fromJson(
                    response.getEntity(String.class),
                    XrayServerImportResponseDto.class
            );
        } else if (XrayConfigHelper.isXrayCloudService()) {
            responseDto = gson.fromJson(
                    response.getEntity(String.class),
                    XrayCloudImportResponseDto.class
            );
        }

        return responseDto;
    }

    private List<XrayIssueSearchResponseDto.IssueResponseDto> searchJiraIssues(Collection<String> testIssueKeys, String... fields) {
        List<XrayIssueSearchResponseDto.IssueResponseDto> issues = new ArrayList<>();
        // Retrieve issue data of all issues we're interested in through a JQL query.
        XrayIssueSearchRequestDto dto = new XrayIssueSearchRequestDto();
        String jql = String.format("issue in (%s)", String.join(",", testIssueKeys));
        dto.setJql(jql);
        dto.setFields(fields);
        // For big test sets, we need to handle paginated results.
        // See Jira Cloud: https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-search/#api-rest-api-3-search-post
        // See Jira Server: https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/search
        int total = Integer.MAX_VALUE;
        for (int startAt = 0; issues.size() != total; startAt = issues.size()) {
            dto.setStartAt(startAt);
            WebResource webResource = client.resource(XrayConfigHelper.getJiraUrl() + getIssueSearchPath());
            QtafXrayEvents.webResourceAvailable.onNext(webResource);
            String json = GsonFactory.getInstance().toJson(dto);
            ClientResponse response = webResource
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", authorizationHeaderJira())
                    .post(ClientResponse.class, json);
            XrayIssueSearchResponseDto paginatedResult = GsonFactory.getInstance().fromJson(
                    response.getEntity(String.class),
                    XrayIssueSearchResponseDto.class
            );
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                String reason = String.format(
                        "%d %s",
                        response.getStatus(),
                        response.getStatusInfo().getReasonPhrase()
                );
                String message = String.format(
                        "Failed to search for Jira issues using search body '%s': %s",
                        json,
                        reason
                );
                logger.error(message);
                errorLogs.addErrorLog(new IllegalStateException(message));
                break;
            }
            total = paginatedResult.getTotal();
            issues.addAll(Arrays.asList(paginatedResult.getIssues()));
        }
        return issues;
    }

    /**
     * Retrieve all issue IDs for the given test issues.
     *
     * @param testIssueKeys the test issues to retrieve the IDs for
     * @return a mapping of test issue keys to their IDs
     * @see <a href="https://confluence.atlassian.com/cloudkb/how-to-identify-the-jira-issue-id-in-cloud-1167747456.html">Identifying Jira issue IDs in Jira Cloud</a>
     */
    protected Map<String, String> getIssueIds(Collection<String> testIssueKeys) {
        Map<String, String> issueIds = new HashMap<>();
        List<XrayIssueSearchResponseDto.IssueResponseDto> issues = searchJiraIssues(testIssueKeys, "key", "id");
        issues.forEach(issue -> issueIds.put(issue.getKey(), issue.getId()));
        return issueIds;
    }

    /**
     * Retrieve all test steps for the given test issues.
     *
     * @param testIssueKeys the test issues to retrieve the steps for
     * @return a mapping of test issue keys to their steps
     */
    public abstract Map<String, XrayTestStepResponseDto[]> getTestSteps(Collection<String> testIssueKeys);
}
