package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents single group of status which has similar positions in terms of representing in workflows.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/statuscategory">Jira REST API - GET status categories</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class StatusCategoryDto {

    private int id;
    private String key;
    private String colorName;
    private String name;

}
