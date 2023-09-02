package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response when retrieving Jira cloud project versions.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-versions/#api-rest-api-3-project-projectidorkey-versions-get">Get project versions (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/project-getProjectVersions">Get project versions (Jira Server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VersionCloudDto extends VersionDto {
    /**
     * If the expand option {@code issuesstatus} is used, returns the count of issues in this version for each of the
     * status categories <i>to do</i>, <i>in progress</i>, <i>done</i>, and <i>unmapped</i>. The unmapped property
     * contains a count of issues with a status other than <i>to do</i>, <i>in progress</i>, and <i>done</i>.
     */
    private VersionIssueStatus issuesStatusForFixVersion;

    /**
     * Issue version statuses.
     */
    @Data
    public static class VersionIssueStatus {
        /**
         * Count of issues with status <i>done</i>.
         */
        private Integer done;
        /**
         * Count of issues with status in <i>progress</i>.
         */
        private Integer inProgress;
        /**
         * Count of issues with status <i>to do</i>.
         */
        private Integer toDo;
        /**
         * Count of issues with a status other than <i>to do</i>, <i>in progress</i>, and <i>done</i>.
         */
        private Integer unmapped;
        /**
         * Extra properties of any type may be provided to this object.
         */
        private JsonObject any;
        /**
         * The release date of the version. Expressed in ISO 8601 format (yyyy-mm-dd). Optional when creating or
         * updating a version.
         */
        private String releaseDate;
        /**
         * The start date of the version. Expressed in ISO 8601 format (yyyy-mm-dd). Optional when creating or updating
         * a version.
         */
        private String startDate;
    }

}
