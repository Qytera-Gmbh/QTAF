package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

/**
 * A group a Jira user can belong to.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-users/#api-rest-api-3-user-groups-get">Get user groups (Jira cloud)</a>
 */
@Data
public abstract class GroupDto {
    /**
     * The name of the group.
     */
    private String name;
    /**
     * The URL for these group details.
     */
    private String self;
}
