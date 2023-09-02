package de.qytera.qtaf.xray.repository.jira;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.IssueUpdateDto;
import de.qytera.qtaf.xray.dto.jira.StatusDto;
import de.qytera.qtaf.xray.dto.jira.TransitionDto;
import de.qytera.qtaf.xray.dto.jira.TransitionsMetaDto;
import de.qytera.qtaf.xray.dto.request.issues.AdditionalField;
import de.qytera.qtaf.xray.dto.request.issues.JiraIssueSearchRequestDto;
import de.qytera.qtaf.xray.dto.response.issues.JiraIssueResponseDto;
import de.qytera.qtaf.xray.dto.response.issues.JiraIssueSearchResponseDto;
import de.qytera.qtaf.xray.dto.jira.ApplicationRoleDto;
import de.qytera.qtaf.xray.dto.jira.GroupDto;
import de.qytera.qtaf.xray.dto.jira.UserDto;
import de.qytera.qtaf.xray.dto.request.jira.issues.AdditionalField;
import de.qytera.qtaf.xray.dto.request.jira.issues.JiraIssueSearchRequestDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueResponseDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueSearchResponseDto;
import jakarta.ws.rs.client.Entity;
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
    public List<JiraIssueResponseDto> search(
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
            try (Response response = WebService.post(request, Entity.json(dto))) {
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
     * Assigns an issue to a user.
     *
     * @param issueIdOrKey the ID or key of the issue to be assigned
     * @param user         the user to assign the issue to
     * @return true if it was successfully assigned, otherwise false
     * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-issueidorkey-assignee-put">Assign issue (Jira Cloud)</a>
     * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-assign">Assign (Jira Server)</a>
     */
    public <R extends ApplicationRoleDto, G extends GroupDto, U extends UserDto<R, G>> boolean assign(
            String issueIdOrKey, U user
    ) throws URISyntaxException, MissingConfigurationValueException {
        RequestBuilder request = WebService.buildRequest(getAssignURI(issueIdOrKey));
        request.getBuilder()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, getJiraAuthorizationHeaderValue());
        try (Response response = WebService.put(request, Entity.json(user))) {
            String responseData = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String reason = String.format(
                        "%d %s: %s",
                        response.getStatus(),
                        response.getStatusInfo().getReasonPhrase(),
                        responseData
                );
                QtafFactory.getLogger().error(
                        String.format(
                                "[QTAF Xray Plugin] Failed to assign issue '%s' to user '%s': %s",
                                issueIdOrKey,
                                user,
                                reason
                        )
                );
                return false;
            }
            return true;
        }
    }

    private static URI getAssignURI(String issueIdOrKey) throws URISyntaxException {
        if (XrayConfigHelper.isXrayCloudService()) {
            return new URI(String.format("%s/rest/api/3/issue/%s/assignee", XrayConfigHelper.getJiraUrl(), issueIdOrKey));
        }
        return new URI(String.format("%s/rest/api/2/issue/%s/assignee", XrayConfigHelper.getJiraUrl(), issueIdOrKey));
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
        List<JiraIssueResponseDto> issues = search(
                testIssueKeys,
                AdditionalField.ISSUE_KEY,
                AdditionalField.ID
        );
        issues.forEach(issue -> issueIds.put(issue.getKey(), issue.getId()));
        return issueIds;
    }

    /**
     * Performs an issue transition and, if the transition has a screen, updates the fields from the transition screen.
     *
     * @param issueIdOrKey the ID or key of the issue
     * @param body         the request body
     * @return whether the transition was successful
     * @throws MissingConfigurationValueException if necessary configuration values are missing
     * @throws URISyntaxException                 if the URI for transitioning the issue cannot be constructed
     * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-issueidorkey-transitions-post">Transition issue (Jira Cloud)</a>
     * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Do transition (Jira Server)</a>
     */
    public boolean transitionIssue(String issueIdOrKey, IssueUpdateDto body) throws MissingConfigurationValueException, URISyntaxException {
        RequestBuilder request = WebService.buildRequest(getURITransitionIssue(issueIdOrKey));
        request.getBuilder()
                .header(HttpHeaders.AUTHORIZATION, getJiraAuthorizationHeaderValue())
                .accept(MediaType.APPLICATION_JSON_TYPE);
        try (Response response = WebService.post(request, GsonFactory.getInstance().toJsonTree(body))) {
            String responseJson = response.readEntity(String.class);
            if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
                QtafFactory.getLogger().info(
                        String.format(
                                "[QTAF Xray Plugin] Successfully transitioned issue %s to status %s.",
                                issueIdOrKey,
                                body.getTransition()
                        )
                );
                return true;
            } else {
                QtafFactory.getLogger().error(
                        String.format(
                                "[QTAF Xray Plugin] Failed to transition issue %s to status %s: %s",
                                issueIdOrKey,
                                body.getTransition(),
                                responseJson
                        )
                );
            }
        }
        return false;
    }

    /**
     * Performs an issue transition to the provided status.
     * <p>
     * Before the request is made, the issue's possible transitions are queried. The actual follow-up transition request
     * is only made if the possible transitions contain a transition belonging to the provided status.
     *
     * @param issueIdOrKey the ID or key of the issue
     * @param statusName   the target status to transition to
     * @return whether the transition was successful
     * @throws MissingConfigurationValueException if necessary configuration values are missing
     * @throws URISyntaxException                 if the URI for transitioning the issue cannot be constructed
     * @see JiraIssueRepository#transitionIssue(String, IssueUpdateDto)
     */
    public boolean transitionIssue(String issueIdOrKey, String statusName) throws MissingConfigurationValueException, URISyntaxException {
        List<TransitionDto> transitions = getIssueTransitions(issueIdOrKey);
        for (TransitionDto transition : transitions) {
            StatusDto status = transition.getTo();
            if (status != null && status.getName() != null && status.getName().equalsIgnoreCase(statusName)) {
                IssueUpdateDto dto = new IssueUpdateDto();
                dto.setTransition(transition);
                return transitionIssue(issueIdOrKey, dto);
            }
        }
        QtafFactory.getLogger().error(
                String.format(
                        "[QTAF Xray Plugin] Failed to transition issue %s to status %s: %s. Possible transitions: %s",
                        issueIdOrKey,
                        statusName,
                        "The workflow prohibits the transition or it does not exist",
                        transitions.stream()
                                .map(TransitionDto::getTo)
                                .filter(Objects::nonNull)
                                .map(StatusDto::getName)
                                .collect(Collectors.joining(","))
                )
        );
        return false;
    }

    private static URI getURITransitionIssue(String issueIdOrKey) throws URISyntaxException {
        String endpoint = String.format(
                "%s/rest/api/2/issue/%s/transitions",
                XrayConfigHelper.getJiraUrl(),
                issueIdOrKey
        );
        return new URI(endpoint);
    }

    /**
     * Get a list of the transitions possible for this issue by the current user, along with fields that are required
     * and their types.
     * <p>
     * Note, if a request is made for a transition that does not exist or cannot be performed on the issue, given its
     * status, the response will return any empty transitions list.
     *
     * @param issueIdOrKey the ID or key of the issue
     * @return a list of transitions possible for the issue
     * @throws MissingConfigurationValueException if necessary configuration values are missing
     * @throws URISyntaxException                 if the URI for transitioning the issue cannot be constructed
     * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-issueidorkey-transitions-get">Get transitions (Jira Cloud)</a>
     * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-getTransitions">Get transitions (Jira Server)</a>
     */
    public List<TransitionDto> getIssueTransitions(String issueIdOrKey) throws MissingConfigurationValueException, URISyntaxException {
        RequestBuilder request = WebService.buildRequest(getURITransitionIssue(issueIdOrKey));
        request.getBuilder()
                .header(HttpHeaders.AUTHORIZATION, getJiraAuthorizationHeaderValue())
                .accept(MediaType.APPLICATION_JSON_TYPE);
        try (Response response = WebService.get(request)) {
            String responseJson = response.readEntity(String.class);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                TransitionsMetaDto dto = GsonFactory.getInstance().fromJson(responseJson, TransitionsMetaDto.class);
                return dto.getTransitions();
            } else {
                QtafFactory.getLogger().error(
                        String.format(
                                "[QTAF Xray Plugin] Failed to get transitions for issue %s: %s",
                                issueIdOrKey,
                                responseJson
                        )
                );
            }
        }
        return Collections.emptyList();
    }

}
