package de.qytera.qtaf.xray.dto.response.jira;

import lombok.Data;

/**
 * The response when retrieving project category data for a specific Jira project.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-categories/#api-rest-api-3-projectcategory-id-get">Get project category by ID (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/projectCategory-getProjectCategoryById">Get project category by id (Jira Server)</a>
 */
@Data
public class ProjectCategoryDto {

    private String self;
    private String id;
    private String name;
    private String description;

}
