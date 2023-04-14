package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Getter
@Setter
public class XrayTestEntity {
    /**
     * The test issue key.
     */
    private String testKey;
    /**
     * The testInfo element.
     */
    private XrayTestInfoEntity testInfo;
    /**
     * The start date for the test run.
     */
    private String start;
    /**
     * The finish date for the test run.
     */
    private String finish;
    /**
     * The comment for the test run.
     */
    private String comment;
    /**
     * The user id who executed the test run.
     */
    private String executedBy;
    /**
     * The user id for the assignee of the test run.
     */
    private String assignee;
    /**
     * The test run status (PASS, FAIL, EXECUTING, TO DO, custom statuses ...).
     */
    @NonNull
    private String status;
    /**
     * The step results.
     */
    private List<XrayManualTestStepResultEntity> steps = new ArrayList<>();
    /**
     * The example results for BDD tests (link).
     */
    private List<String> examples = new ArrayList<>();
    /**
     * The iteration containing data-driven test results.
     */
    private List<XrayIterationResultEntity> iterations = new ArrayList<>();
    /**
     * An array of defect issue keys to associate with the test run.
     */
    private List<String> defects = new ArrayList<>();
    /**
     * An array of evidence items of the test run.
     */
    private List<XrayEvidenceItemEntity> evidence = new ArrayList<>();
    /**
     * An array of custom fields for the test run.
     */
    private List<XrayCustomFieldEntity> customFields = new ArrayList<>();

    public XrayTestEntity(@NonNull TestScenarioLogCollection.Status status) {
        this.status = XrayStatusHelper.statusToText(status);
    }

    /**
     * Set the status of the test.
     *
     * @param status the test status
     */
    public void setStatus(@NonNull TestScenarioLogCollection.Status status) {
        this.status = XrayStatusHelper.statusToText(status);
    }

}
