package de.qytera.qtaf.xray.dto.response.issues;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

/**
 * The response when searching for specific issues via JQL queries.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-search/#api-rest-api-3-search-get-responses">Issue search (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/search">Issue search (Jira Server)</a>
 */
@Getter
@Setter
public class XrayIssueSearchResponseDto {

    private Integer total;

    private IssueResponseDto[] issues;

    @Getter
    @Setter
    public static class IssueResponseDto {
        private String id;
        private String key;
        private JsonObject fields;
    }
}
