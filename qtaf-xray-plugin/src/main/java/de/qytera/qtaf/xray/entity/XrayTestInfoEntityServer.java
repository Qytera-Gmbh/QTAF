package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
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

    public XrayTestInfoEntityServer(String summary, String projectKey, String testType) {
        super(summary, projectKey);
        this.testType = testType;
    }

}
