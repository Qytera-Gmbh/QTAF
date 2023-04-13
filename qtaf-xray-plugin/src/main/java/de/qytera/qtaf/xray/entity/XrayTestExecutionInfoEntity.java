package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Xray test execution info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayTestExecutionInfoEntity {
    /**
     * The project key where the test execution will be created.
     */
    private String project;
    /**
     * The summary for the test execution issue.
     */
    private String summary;
    /**
     * The description for the test execution issue.
     */
    private String description;
    /**
     * The version name for the Fix Version field of the test execution issue.
     */
    private String version;
    /**
     * A revision for the revision custom field.
     */
    private String revision;
    /**
     * The username for the Jira user who executed the tests.
     */
    private String user;
    /**
     * The start date for the test execution issue.
     */
    private String startDate;
    /**
     * The finish date for the test execution issue.
     */
    private String finishDate;
    /**
     * The test plan key for associating the test execution issue.
     */
    private String testPlanKey;
    /**
     * The test environments for the test execution issue.
     */
    private List<String> testEnvironments;
}
