package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Contains all data necessary to display status of an issue.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class StatusDto {

    private String statusColor;
    private String description;
    private String iconUrl;
    private String name;
    private String id;
    private StatusCategoryDto statusCategory;

}
