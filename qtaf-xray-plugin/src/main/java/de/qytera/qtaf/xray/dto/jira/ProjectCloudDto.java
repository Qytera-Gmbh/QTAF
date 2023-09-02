package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The DTO describing Jira cloud project details.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-projects/#api-rest-api-3-project-projectidorkey-get">Get project (Jira Cloud)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectCloudDto extends ProjectDto<UserCloudDto, IssueTypeCloudDto, VersionCloudDto> {
    /**
     * The user who archived the project.
     */
    private UserCloudDto archivedBy;
    /**
     * The date when the project was archived.
     */
    private String archivedDate;
    /**
     * Whether the project is marked as deleted.
     */
    private Boolean deleted;
    /**
     * The user who marked the project as deleted.
     */
    private UserCloudDto deletedBy;
    /**
     * The date when the project was marked as deleted.
     */
    private String deletedDate;
    /**
     * Whether the project is selected as a favorite.
     */
    private Boolean favourite;
    /**
     * Insights about the project.
     */
    private ProjectInsightDto insight;
    /**
     * Whether the project is private.
     */
    private Boolean isPrivate;
    /**
     * The issue type hierarchy for the project.
     */
    private HierarchyDto issueTypeHierarchy;
    /**
     * The project landing page info.
     */
    private LandingPageInfoDto landingPageInfo;
    /**
     * User permissions on the project
     */
    private ProjectPermissionsDto permissions;
    /**
     * Map of project properties.
     */
    private JsonObject properties;
    /**
     * The date when the project is deleted permanently.
     */
    private String retentionTillDate;
    /**
     * Whether the project is simplified.
     */
    private Boolean simplified;
    /**
     * The type of the project.
     */
    private String style;
    /**
     * Unique ID for next-gen projects.
     */
    private String uuid;

}
