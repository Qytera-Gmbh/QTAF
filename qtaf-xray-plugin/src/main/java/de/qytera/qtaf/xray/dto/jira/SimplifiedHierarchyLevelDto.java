package de.qytera.qtaf.xray.dto.jira;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The issue type hierarchy level.
 */
@Data
public class SimplifiedHierarchyLevelDto {
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
