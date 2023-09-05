package de.qytera.qtaf.xray.dto.jira;


import lombok.Data;

/**
 * User permissions on a project.
 */
@Data
public class ProjectPermissionsDto {
    /**
     * Whether the logged user can edit the project.
     */
    private Boolean canEdit;
}
