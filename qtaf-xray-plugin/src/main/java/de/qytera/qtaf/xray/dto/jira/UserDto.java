package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * The DTO when working with Jira users.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-issueidorkey-assignee-put">Assign issue (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-assign">Assign issue (Jira Server)</a>
 */
@Data
public abstract class UserDto<R extends ApplicationRoleDto, G extends GroupDto> {
    /**
     * Whether the user is active.
     */
    private Boolean active;
    /**
     * The application roles the user is assigned to.
     */
    private SimpleListWrapperDto<R> applicationRoles;
    /**
     * The avatars of the user.
     */
    private JsonObject avatarUrls;
    /**
     * The display name of the user. Depending on the user’s privacy setting, this may return an alternative value.
     */
    private String displayName;
    /**
     * The email address of the user. Depending on the user’s privacy setting, this may be returned as null.
     */
    private String emailAddress;
    /**
     * Expand options that include additional user details in the response.
     */
    private String expand;
    /**
     * The groups that the user belongs to.
     */
    private SimpleListWrapperDto<G> groups;
    /**
     * The locale of the user. Depending on the user’s privacy setting, this may be returned as null.
     */
    private String locale;
    /**
     * The URL of the user.
     */
    private String self;
    /**
     * The time zone specified in the user's profile. Depending on the user’s privacy setting, this may be returned as null.
     */
    private String timeZone;
}
