package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The project details of the project an item has scope in.
 */
@Data
public class ProjectDetailsDto {
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
        return ProjectType.valueOf(this.projectTypeKey.toUpperCase());
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
