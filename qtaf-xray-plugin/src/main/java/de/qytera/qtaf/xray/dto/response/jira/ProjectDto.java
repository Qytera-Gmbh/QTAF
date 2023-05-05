package de.qytera.qtaf.xray.dto.response.jira;

import com.google.gson.JsonObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The response when retrieving project data for a specific Jira project.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-projects/#api-rest-api-3-project-projectidorkey-get">Get project (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/project-getProject">Get project (Jira Server)</a>
 */
@Data
public class ProjectDto {

    private String expand;
    private String self;
    private String id;
    private String key;
    private String description;
    private UserDto lead;
    private List<ComponentDto> components = new ArrayList<>();
    private List<IssueTypeDto> issueTypes = new ArrayList<>();
    private String url;
    private String email;
    private AssigneeType assigneeType;
    private List<VersionDto> versions = new ArrayList<>();
    private String name;
    private JsonObject roles;
    private JsonObject avatarUrls;
    private List<String> projectKeys = new ArrayList<>();
    private ProjectCategoryDto projectCategory;
    private String projectTypeKey;
    private Boolean archived;

    public enum AssigneeType {
        PROJECT_LEAD,
        UNASSIGNED
    }

}
