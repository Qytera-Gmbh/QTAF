package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

/**
 * The DTO describing project category details for a specific Jira project.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-categories/#api-rest-api-3-projectcategory-id-get">Get project category by ID (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/projectCategory-getProjectCategoryById">Get project category by id (Jira Server)</a>
 */
@Data
public class ProjectCategoryDto {
    /**
     * The URL of the project category.
     */
    private String self;
    /**
     * The ID of the project category.
     */
    private String id;
    /**
     * The name of the project category. Required on create, optional on update.
     */
    private String name;
    /**
     * The description of the project category.
     */
    private String description;

}
