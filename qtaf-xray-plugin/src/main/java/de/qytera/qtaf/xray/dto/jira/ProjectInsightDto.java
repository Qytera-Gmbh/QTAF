package de.qytera.qtaf.xray.dto.jira;


import lombok.Data;

/**
 * Insights about a project.
 */
@Data
public class ProjectInsightDto {
    /**
     * The last issue update time.
     */
    private String lastIssueUpdateTime;
    /**
     * Total issue count.
     */
    private Integer totalIssueCount;
}
