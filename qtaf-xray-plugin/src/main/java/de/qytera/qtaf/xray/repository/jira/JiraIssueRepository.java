package de.qytera.qtaf.xray.repository.jira;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.jira.issues.AdditionalField;
import de.qytera.qtaf.xray.dto.request.jira.issues.JiraIssueSearchRequestDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueResponseDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueSearchResponseDto;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * A class for interacting with Jira issues, such as searching for issues or updating existing issues' fields.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JiraIssueRepository implements JiraEndpoint {

    private static final JiraIssueRepository INSTANCE = new JiraIssueRepository();

    /**
     * Retrieve a {@link JiraIssueRepository} instance.
     *
     * @return an instance to work with
     */
    public static JiraIssueRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Searches for the given Jira issues, including any additional fields in the response if provided.
     *
     * @param testIssueKeys the Jira issues to search for
     * @param fields        additional fields to include in the response
     * @return a list of found issues
     * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-search/#api-rest-api-3-search-post">Search (Jira Cloud)</a>
     * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/search">Search (Jira Server)</a>
     */
    public List<JiraIssueResponseDto> searchJiraIssues(
            Collection<String> testIssueKeys,
            AdditionalField... fields
    ) throws URISyntaxException, MissingConfigurationValueException {
        List<JiraIssueResponseDto> issues = new ArrayList<>();
        // Retrieve issue data of all issues we're interested in through a JQL query.
        JiraIssueSearchRequestDto dto = new JiraIssueSearchRequestDto();
        String jql = String.format("issue in (%s)", String.join(",", testIssueKeys));
        dto.setJql(jql);
        dto.setFields(Arrays.stream(fields).map(AdditionalField::getText).toArray(String[]::new));
        // For big test sets, we need to handle paginated results.
        int total = Integer.MAX_VALUE;
        for (int startAt = 0; issues.size() != total; startAt = issues.size()) {
            dto.setStartAt(startAt);
            RequestBuilder request = WebService.buildRequest(getURIIssueSearchPath());
            request.getBuilder()
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .header(HttpHeaders.AUTHORIZATION, getJiraAuthorizationHeaderValue());
            try (Response response = WebService.post(request, GsonFactory.getInstance().toJsonTree(dto))) {
                String responseData = response.readEntity(String.class);
                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    String reason = String.format(
                            "%d %s: %s",
                            response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            responseData
                    );
                    QtafFactory.getLogger().error(
                            String.format(
                                    "[QTAF Xray Plugin] Failed to search for Jira issues using search body '%s': %s",
                                    GsonFactory.getInstance().toJsonTree(dto),
                                    reason
                            )
                    );
                    break;
                }
                JiraIssueSearchResponseDto paginatedResult = GsonFactory.getInstance().fromJson(
                        responseData,
                        JiraIssueSearchResponseDto.class
                );
                total = paginatedResult.getTotal();
                issues.addAll(Arrays.asList(paginatedResult.getIssues()));
            }
        }
        return issues;
    }

    private static URI getURIIssueSearchPath() throws URISyntaxException {
        if (XrayConfigHelper.isXrayCloudService()) {
            return new URI(XrayConfigHelper.getJiraUrl() + "/rest/api/3/search");
        }
        return new URI(XrayConfigHelper.getJiraUrl() + "/rest/api/2/search");
    }

    /**
     * Retrieve all issue IDs for the given test issues.
     *
     * @param testIssueKeys the test issues to retrieve the IDs for
     * @return a mapping of test issue keys to their IDs
     * @see <a href="https://confluence.atlassian.com/cloudkb/how-to-identify-the-jira-issue-id-in-cloud-1167747456.html">Identifying Jira issue IDs in Jira Cloud</a>
     */
    public Map<String, String> getIssueIds(Collection<String> testIssueKeys) throws URISyntaxException, MissingConfigurationValueException {
        Map<String, String> issueIds = new HashMap<>();
        List<JiraIssueResponseDto> issues = searchJiraIssues(
                testIssueKeys,
                AdditionalField.ISSUE_KEY,
                AdditionalField.ID
        );
        issues.forEach(issue -> issueIds.put(issue.getKey(), issue.getId()));
        return issueIds;
    }

}
