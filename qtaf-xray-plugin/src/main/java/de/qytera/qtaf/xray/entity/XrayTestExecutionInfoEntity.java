package de.qytera.qtaf.xray.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test execution info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private List<String> testEnvironments = new ArrayList<>();

    /**
     * Add a test environment to the list of test environments. The provided environment string is modified such that
     * it will be displayed correctly in Xray (e.g. whitespace will be removed).
     *
     * @param environment a test environment
     */
    public void addTestEnvironment(String environment) {
        // Xray interprets environments containing whitespace as distinct environments.
        testEnvironments.add(environment.replaceAll("\\s+", "-").toLowerCase());
    }

}
