package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

/**
 * Details about a project component.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-components/#api-rest-api-3-component-id-get">Get component (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/component-getComponent">Get component (Jira Server)</a>
 */
@Data
public class ComponentDto<U extends UserDto<?, ?>> {
    /**
     * The URL of the component.
     */
    private String self;
    /**
     * The unique identifier for the component.
     */
    private String id;
    /**
     * The unique name for the component in the project. Required when creating a component. Optional when updating a
     * component. The maximum length is 255 characters.
     */
    private String name;
    /**
     * The description for the component. Optional when creating or updating a component.
     */
    private String description;
    /**
     * The user details for the component's lead user.
     */
    private U lead;
    /**
     * The nominal user type used to determine the assignee for issues created with a component. See
     * {@link ComponentDto#getRealAssigneeType()} for details on how the type of the user, and hence the user,
     * assigned to issues is determined.
     */
    private AssigneeType assigneeType;
    /**
     * The details of the user associated with assigneeType, if any. See {@link ComponentDto#getRealAssignee()} for
     * details of the user assigned to issues created with this component.
     */
    private U assignee;
    /**
     * The type of the assignee that is assigned to issues created with this component, when an assignee cannot be set
     * from the {@link ComponentDto#getAssigneeType()}. For example, {@link ComponentDto#getAssigneeType()} is set to
     * {@link AssigneeType#COMPONENT_LEAD} but no component lead is set.
     * <p>
     * This property is set to one of the following values:
     * <dl>
     *     <dt>{@link AssigneeType#PROJECT_LEAD}</dt>
     *     <dd>
     *         When {@link ComponentDto#getAssigneeType()} is {@link AssigneeType#PROJECT_LEAD} and the project lead
     *         has permission to be assigned issues in the project that the component is in.
     *     </dd>
     *     <dt>{@link AssigneeType#COMPONENT_LEAD}</dt>
     *     <dd>
     *         When {@link ComponentDto#getAssigneeType()} is {@link AssigneeType#COMPONENT_LEAD} and the component
     *         lead has permission to be assigned issues in the project that the component is in.
     *     </dd>
     *     <dt>{@link AssigneeType#UNASSIGNED}</dt>
     *     <dd>
     *         When {@link ComponentDto#getAssigneeType()} is {@link AssigneeType#UNASSIGNED} and Jira is configured to
     *         allow unassigned issues.
     *     </dd>
     *     <dt>{@link AssigneeType#PROJECT_DEFAULT}</dt>
     *     <dd>
     *         When none of the preceding cases are true.
     *     </dd>
     * </dl>
     */
    private AssigneeType realAssigneeType;
    /**
     * The user assigned to issues created with this component, when {@link ComponentDto#getAssigneeType()}  does not
     * identify a valid assignee.
     */
    private U realAssignee;
    /**
     * Whether a user is associated with {@link ComponentDto#getAssigneeType()}. For example, if the type is set to
     * {@link AssigneeType#COMPONENT_LEAD}, but the component lead is not set, then false is returned.
     */
    private Boolean isAssigneeTypeValid;
    /**
     * The key of the project the component is assigned to. Required when creating a component. Can't be updated.
     */
    private String project;
    /**
     * The ID of the project the component is assigned to.
     */
    private Integer projectId;

    /**
     * The user type used to determine the assignee for issues created with a component.
     * <p>
     * Default value: {@link AssigneeType#PROJECT_DEFAULT}.
     */
    public enum AssigneeType {
        /**
         * The assignee to any issues created with this component is nominally the default assignee for the project
         * that the component is in.
         */
        PROJECT_DEFAULT,
        /**
         * The assignee to any issues created with this component is nominally the lead for the component.
         */
        COMPONENT_LEAD,
        /**
         * The assignee to any issues created with this component is nominally the lead for the project the component
         * is in.
         */
        PROJECT_LEAD,
        /**
         * An assignee is not set for issues created with this component.
         */
        UNASSIGNED
    }


}
