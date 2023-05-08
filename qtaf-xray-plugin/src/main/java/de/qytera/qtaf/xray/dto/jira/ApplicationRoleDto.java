package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This resource represents application roles. Use it to get details of an application role or all application roles.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-application-roles/#api-rest-api-3-applicationrole-key-get">Get application role (Jira cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/applicationrole-get">Get application role (Jira server)</a>
 */
@Data
public abstract class ApplicationRoleDto {
    /**
     * The key of the application role.
     */
    private String key;
    /**
     * The groups associated with the application role.
     */
    private List<String> groups = new ArrayList<>();
    /**
     * The display name of the application role.
     */
    private String name;
    /**
     * The groups that are granted default access for this application role.
     */
    private List<String> defaultGroups = new ArrayList<>();
    /**
     * Determines whether this application role should be selected by default on user creation.
     */
    private Boolean selectedByDefault;
    /**
     * The maximum count of users on your license.
     */
    private Integer numberOfSeats;
    /**
     * The count of users remaining on your license.
     */
    private Integer remainingSeats;
    /**
     * The number of users counting against your license.
     */
    private Integer userCount;
    /**
     * The <a href="https://confluence.atlassian.com/x/lRW3Ng">type of users</a> being counted against your license.
     */
    private String userCountDescription;
    /**
     * Whether the application role has unlimited seats.
     */
    private Boolean hasUnlimitedSeats;
    /**
     * Indicates if the application role belongs to Jira platform (jira-core).
     */
    private Boolean platform;
}
