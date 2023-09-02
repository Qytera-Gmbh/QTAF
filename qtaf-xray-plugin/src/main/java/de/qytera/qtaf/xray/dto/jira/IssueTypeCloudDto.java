package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response when retrieving Jira cloud issue type details.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-types/#api-rest-api-3-issuetype-id-get">Get issue type (Jira Cloud)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueTypeCloudDto extends IssueTypeDto {
    /**
     * Unique ID for next-gen projects.
     */
    private String entityId;
    /**
     * Hierarchy level of the issue type.
     */
    private Integer hierarchyLevel;
    /**
     * Details of the next-gen projects the issue type is available in.
     */
    private ScopeDto scope;
}
