package de.qytera.qtaf.xray.dto.response.jira;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The response when retrieving user data.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-users/#api-rest-api-3-user-get">Get user (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/user-getUser">Get user (Jira Server)</a>
 */
@Data
public class UserDto {

    private String self;
    private String key;
    private String name;
    private String emailAddress;
    private JsonObject avatarUrls;
    private String displayName;
    private final Boolean active;
    private Boolean deleted;
    private String timeZone;
    private String locale;
    private SimpleListWrapperDto groups;
    private SimpleListWrapperDto applicationRoles;
    private String expand;

    @Data
    public static class SimpleListWrapperDto {
        private int size;
        @SerializedName("max-results")
        private int maxResults;
        private List<GroupDto> items = new ArrayList<>();
    }

    @Data
    public static class GroupDto {
        private String name;
        private String self;
    }

}
