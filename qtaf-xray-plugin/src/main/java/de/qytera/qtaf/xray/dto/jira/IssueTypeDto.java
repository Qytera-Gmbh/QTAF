package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

/**
 * The response when retrieving issue type details.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-types/#api-rest-api-3-issuetype-id-get">Get issue type (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issuetype-getIssueType">Get issue type (Jira Server)</a>
 */
@Data
public abstract class IssueTypeDto {

    /**
     * The URL of these issue type details.
     */
    private String self;
    /**
     * The ID of the issue type.
     */
    private String id;
    /**
     * The description of the issue type.
     */
    private String description;
    /**
     * The URL of the issue type's avatar.
     */
    private String iconUrl;
    /**
     * The name of the issue type.
     */
    private String name;
    /**
     * Whether this issue type is used to create subtasks.
     */
    private Boolean subtask;
    /**
     * The ID of the issue type's avatar.
     */
    private int avatarId;

}
