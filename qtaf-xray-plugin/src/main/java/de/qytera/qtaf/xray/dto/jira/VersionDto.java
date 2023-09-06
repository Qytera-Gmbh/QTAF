package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The response when retrieving project versions.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-versions/#api-rest-api-3-project-projectidorkey-versions-get">Get project versions (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/project-getProjectVersions">Get project versions (Jira Server)</a>
 */
@Data
public abstract class VersionDto {

    /**
     * Use expand to include additional information about version in the response. This parameter accepts a
     * comma-separated list. Expand options include:
     * <dl>
     *     <dt>{@code operations}</dt>
     *     <dd>Returns the list of operations available for this version.</dd>
     *     <dt>{@code issuesstatus}</dt>
     *     <dd>
     *         Returns the count of issues in this version for each of the status categories <i>to do</i>,
     *         <i>in progress</i>, <i>done</i>, and  <i>unmapped</i>. The unmapped property contains a count of issues
     *         with a status other than <i>to do</i>, <i>in progress</i>, and <i>done</i>.
     *     </dd>
     * </dl>
     */
    private String expand;
    /**
     * The URL of the version.
     */
    private String self;
    /**
     * The ID of the version.
     */
    private String id;
    /**
     * The description of the version. Optional when creating or updating a version.
     */
    private String description;
    /**
     * The unique name of the version. Required when creating a version. Optional when updating a version. The maximum
     * length is 255 characters.
     */
    private String name;
    /**
     * Indicates that the version is archived. Optional when creating or updating a version.
     */
    private Boolean archived;
    /**
     * Indicates that the version is released. If the version is released a request to release again is ignored. Not
     * applicable when creating a version. Optional when updating a version.
     */
    private Boolean released;
    /**
     * Indicates that the version is overdue.
     */
    private Boolean overdue;
    /**
     * The date on which work on this version is expected to start, expressed in the instance's Day/Month/Year format
     * date format.
     */
    private String userStartDate;
    /**
     * The date on which work on this version is expected to finish, expressed in the instance's Day/Month/Year Format
     * date format.
     */
    private String userReleaseData;
    /**
     * The ID of the project to which this version is attached. Required when creating a version. Not applicable when
     * updating a version.
     */
    private Integer projectId;
    /**
     * The URL of the self link to the version to which all unfixed issues are moved when a version is released.
     * Not applicable when creating a version. Optional when updating a version.
     */
    private String moveUnfixedIssuesTo;
    /**
     * If the expand option {@code operations} is used, returns the list of operations available for this version.
     */
    private List<SimpleLinkDto> operations = new ArrayList<>();


}
