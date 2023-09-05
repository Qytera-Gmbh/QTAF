package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The issue type hierarchy for a project.
 */
@Data
public class HierarchyDto {
    /**
     * Details about the hierarchy level.
     */
    private List<SimplifiedHierarchyLevelDto> levels = new ArrayList<>();
}
