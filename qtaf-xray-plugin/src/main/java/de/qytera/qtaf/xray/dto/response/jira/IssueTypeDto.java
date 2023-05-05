package de.qytera.qtaf.xray.dto.response.jira;

import lombok.Data;

/**
 * The response when retrieving project components.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-types/#api-rest-api-3-issuetype-id-get">Get issue type (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issuetype-getIssueType">Get issue type (Jira Server)</a>
 */
@Data
public class IssueTypeDto {

    private String self;
    private String id;
    private String description;
    private String iconUrl;
    private String name;
    private final Boolean subtask;
    private int avatarId;

}
