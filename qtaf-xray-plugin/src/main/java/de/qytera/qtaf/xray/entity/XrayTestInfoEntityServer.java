package de.qytera.qtaf.xray.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class XrayTestInfoEntityServer extends XrayTestInfoEntity {
    /**
     * The description of the test issue.
     */
    private String description;
    /**
     * The test type (e.g. Manual, Cucumber, Generic).
     */
    private String testType;
    /**
     * The BDD scenario type (Scenario or Scenario Outline).
     */
    private String scenarioType;
}
