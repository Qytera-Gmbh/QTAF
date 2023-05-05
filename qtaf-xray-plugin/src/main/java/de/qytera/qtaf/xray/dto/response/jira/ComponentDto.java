package de.qytera.qtaf.xray.dto.response.jira;

import lombok.Data;

/**
 * The response when retrieving project components.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-components/#api-rest-api-3-component-id-get">Get component (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/component-getComponent">Get component (Jira Server)</a>
 */
@Data
public class ComponentDto {

    private String self;
    private String id;
    private String name;
    private String description;
    private UserDto lead;
    private String leadUserName;
    private AssigneeType assigneeType;
    private UserDto assignee;
    private AssigneeType realAssigneeType;
    private UserDto realAssignee;
    private final Boolean isAssigneeTypeValid;
    private String project;
    private int projectId;
    private Boolean archived;
    private Boolean deleted;

    public enum AssigneeType {
        PROJECT_DEFAULT,
        COMPONENT_LEAD,
        PROJECT_LEAD,
        UNASSIGNED
    }


}
