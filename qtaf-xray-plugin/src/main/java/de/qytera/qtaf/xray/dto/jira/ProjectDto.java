package de.qytera.qtaf.xray.dto.jira;

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
public abstract class ProjectDto<U extends UserDto<?, ?>, I extends IssueTypeDto, V extends VersionDto> {
    /**
     * Expand options that include additional project details in the response.
     */
    private String expand;
    /**
     * The URL of the project details.
     */
    private String self;
    /**
     * The ID of the project.
     */
    private String id;
    /**
     * The key of the project.
     */
    private String key;
    /**
     * A brief description of the project.
     */
    private String description;
    /**
     * The username of the project lead.
     */
    private U lead;
    /**
     * List of the components contained in the project.
     */
    private List<ComponentDto<U>> components = new ArrayList<>();
    /**
     * List of the issue types available in the project.
     */
    private List<I> issueTypes = new ArrayList<>();
    /**
     * A link to information about this project, such as project documentation.
     */
    private String url;
    /**
     * An email address associated with the project.
     */
    private String email;
    /**
     * The default assignee when creating issues for this project.
     */
    private AssigneeType assigneeType;
    /**
     * The versions defined in the project.
     */
    private List<V> versions = new ArrayList<>();
    /**
     * The name of the project.
     */
    private String name;
    /**
     * The name and self URL for each role defined in the project.
     */
    private JsonObject roles;
    /**
     * The URLs of the project's avatars.
     */
    private JsonObject avatarUrls;
    /**
     * The category the project belongs to.
     */
    private ProjectCategoryDto projectCategory;
    /**
     * The <a href="https://support.atlassian.com/jira-work-management/docs/what-is-the-jira-family-of-applications/#Jiraapplicationsoverview-Productfeaturesandprojecttypes">project type</a>
     * of the project.
     */
    private String projectTypeKey;
    /**
     * Whether the project is archived.
     */
    private Boolean archived;

    /**
     * The default assignee when creating issues for this project.
     */
    public enum AssigneeType {
        /**
         * The project's lead user.
         */
        PROJECT_LEAD,
        /**
         * No user.
         */
        UNASSIGNED
    }

}
