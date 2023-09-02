package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

@Data
public class ScopeDto {
    /**
     * The project the item has scope in.
     */
    private ProjectDetailsDto project;
    /**
     * The type of scope.
     */
    private Type type;

    /**
     * The type of scope.
     */
    public enum Type {
        PROJECT,
        TEMPLATE
    }

}
