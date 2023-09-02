package de.qytera.qtaf.xray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class XrayTestInfoEntityServer extends XrayTestInfoEntity {
    /**
     * The description of the test issue.
     */
    private String description;
    /**
     * The test type (e.g. Manual, Cucumber, Generic).
     */
    private final String testType;
    /**
     * The BDD scenario type (Scenario or Scenario Outline).
     */
    private String scenarioType;

    /**
     * Create a new {@link XrayTestInfoEntityServer} with the given summary, project key and test type.
     *
     * @param summary    the summary
     * @param projectKey the project key
     * @param testType   the test type
     */
    public XrayTestInfoEntityServer(String summary, String projectKey, String testType) {
        super(summary, projectKey);
        this.testType = testType;
    }

}
