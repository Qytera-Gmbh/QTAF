package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * The response when retrieving Jira cloud issue type details.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-types/#api-rest-api-3-issuetype-id-get">Get issue type (Jira Cloud)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueTypeCloudDto extends IssueTypeDto {
    /**
     * Unique ID for next-gen projects.
     */
    private String entityId;
    /**
     * Hierarchy level of the issue type.
     */
    private Integer hierarchyLevel;
    /**
     * Details of the next-gen projects the issue type is available in.
     */
    private ScopeDto scope;

    @Data
    public static class ScopeDto {
        /**
         * The project the item has scope in.
         */
        private ProjectDetailsDto project;
        /**
         * The type of scope.
         */
        private Type type;

        /**
         * The project details of the project an item has scope in.
         */
        @Data
        public static class ProjectDetailsDto {
            /**
             * The URLs of the project's avatars.
             */
            private JsonObject avatarUrls;
            /**
             * The ID of the project.
             */
            private String id;
            /**
             * The key of the project.
             */
            private String key;
            /**
             * The name of the project.
             */
            private String name;
            /**
             * The category the project belongs to.
             */
            private ProjectCategoryDto projectCategory;
            /**
             * The project type of the project.
             */
            private String projectTypeKey;
            private String self;
            private Boolean simplified;

            /**
             * Set the project type.
             *
             * @param projectType the project type
             */
            public void setProjectTypeKey(ProjectType projectType) {
                this.projectTypeKey = projectType.text;
            }

            /**
             * Get the project type.
             *
             * @return the project type or null if not set
             */
            public ProjectType getProjectTypeKey() {
                if (this.projectTypeKey == null) {
                    return null;
                }
                return ProjectType.valueOf(this.projectTypeKey);
            }

            /**
             * The project types available in Jira.
             *
             * @see <a href="https://confluence.atlassian.com/adminjiraserver/jira-applications-and-project-types-overview-938846805.html">Jira project types</a>
             */
            @RequiredArgsConstructor
            public enum ProjectType {
                SOFTWARE("software"),
                SERVICE_DESK("service_desk"),
                BUSINESS("business");
                public final String text;
            }

        }

        /**
         * The type of scope.
         */
        public enum Type {
            PROJECT,
            TEMPLATE
        }

    }

}
