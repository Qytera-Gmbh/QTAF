package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A group a Jira user can belong to.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-users/#api-rest-api-3-user-groups-get">Get user groups (Jira cloud)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupCloudDto extends GroupDto {
    /**
     * The ID of the group, which uniquely identifies the group across all Atlassian products. For example,
     * 952d12c3-5b5b-4d04-bb32-44d383afc4b2.
     */
    private String groupId;
}
