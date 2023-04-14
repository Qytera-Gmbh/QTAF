package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray manual test step result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Getter
@Setter
public abstract class XrayManualTestStepResultEntity {
    /**
     * The status for the test step (PASSED, FAILED, EXECUTING, TO DO, custom statuses ...).
     */
    private final String status;
    /**
     * The comment for the step result.
     */
    private String comment;
    /**
     * An array of defect issue keys to associate with the test run.
     */
    private List<String> defects = new ArrayList<>();
    /**
     * The actual result field for the step result.
     */
    private String actualResult;

    protected XrayManualTestStepResultEntity(StepInformationLogMessage.Status status) {
        this.status = XrayStatusHelper.statusToText(status);
    }

    /**
     * Add an evidence item to the list of step evidences. If {@code evidence} is {@code null}, the current list of
     * evidences remains unchanged.
     *
     * @param evidence the evidence to add
     */
    public abstract void addEvidenceIfPresent(XrayEvidenceItemEntity evidence);

    /**
     * Retrieve a list of all evidence items attached to this step result.
     *
     * @return the list of evidence
     */
    public abstract List<XrayEvidenceItemEntity> getAllEvidence();

}
