package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * This resource represents application roles. Use it to get details of an application role or all application roles.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-application-roles/#api-rest-api-3-applicationrole-key-get">Get application role (Jira cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/applicationrole-get">Get application role (Jira server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationRoleCloudDto extends ApplicationRoleDto {
    /**
     * The groups that are granted default access for this application role.
     */
    private List<GroupCloudDto> defaultGroupDetails = new ArrayList<>();
    /**
     * The groups associated with the application role.
     */
    private List<GroupCloudDto> groupDetails = new ArrayList<>();
}
