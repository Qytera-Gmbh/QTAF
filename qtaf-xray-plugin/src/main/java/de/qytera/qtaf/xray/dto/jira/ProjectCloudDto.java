package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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
    private ProjectInsight insight;
    /**
     * Whether the project is private.
     */
    private Boolean isPrivate;
    /**
     * The issue type hierarchy for the project.
     */
    private Hierarchy issueTypeHierarchy;
    /**
     * The project landing page info.
     */
    private LandingPageInfo landingPageInfo;
    /**
     * User permissions on the project
     */
    private ProjectPermissions permissions;
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

    /**
     * Insights about a project.
     */
    @Data
    public static class ProjectInsight {
        /**
         * The last issue update time.
         */
        private String lastIssueUpdateTime;
        /**
         * Total issue count.
         */
        private Integer totalIssueCount;
    }

    /**
     * The issue type hierarchy for a project.
     */
    @Data
    public static class Hierarchy {
        /**
         * Details about the hierarchy level.
         */
        private List<SimplifiedHierarchyLevel> levels = new ArrayList<>();

        @Data
        public static class SimplifiedHierarchyLevel {
            /**
             * The hierarchy level's number.
             */
            private Integer hierarchyLevelNumber;
            /**
             * The issue types available in this hierarchy level.
             */
            private List<Integer> issueTypeIds = new ArrayList<>();
            /**
             * The level of this item in the hierarchy.
             */
            private Integer level;
            /**
             * The name of this hierarchy level.
             */
            private String name;
        }

    }

    /**
     * A project's landing page info.
     */
    @Data
    public static class LandingPageInfo {
        /**
         * Landing page attributes.
         */
        private JsonObject attributes;
        /**
         * Landing page board ID.
         */
        private Integer boardId;
        /**
         * Landing page board name.
         */
        private String boardName;
        /**
         * Landing page project key.
         */
        private String projectKey;
        /**
         * Landing page project type
         */
        private String projectType;
        /**
         * Landing page queue category.
         */
        private String queueCategory;
        /**
         * Landing page queue ID.
         */
        private Integer queueId;
        /**
         * Whether the project is a simple board.
         */
        private Boolean simpleBoard;
        /**
         * Whether the project has been simplified.
         */
        private Boolean simplified;
        /**
         * Landing page URL.
         */
        private String url;
    }

    /**
     * User permissions on a project.
     */
    @Data
    public static class ProjectPermissions {
        /**
         * Whether the logged user can edit the project.
         */
        private Boolean canEdit;
    }

}
