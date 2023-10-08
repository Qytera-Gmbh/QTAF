package de.qytera.qtaf.xray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
public abstract class XrayTestInfoEntity {
    /**
     * The summary for the test issue.
     */
    protected String summary;
    /**
     * The project key where the test issue will be created.
     */
    protected String projectKey;
    /**
     * An array of requirement issue keys to associate with the test.
     */
    protected List<String> requirementKeys = new ArrayList<>();
    /**
     * The test issue labels.
     */
    protected List<String> labels = new ArrayList<>();
    /**
     * An array of test steps (for Manual tests).
     */
    protected List<XrayTestStepEntity> steps = new ArrayList<>();
    /**
     * The BDD scenario.
     */
    protected String scenario;
    /**
     * The generic test definition.
     */
    protected String definition;
}
