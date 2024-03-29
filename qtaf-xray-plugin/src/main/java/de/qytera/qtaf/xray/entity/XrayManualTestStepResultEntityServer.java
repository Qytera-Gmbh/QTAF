package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray manual test step result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
public class XrayManualTestStepResultEntityServer extends XrayManualTestStepResultEntity {
    /**
     * An array of evidence items of the test run.
     */
    private final List<XrayEvidenceItemEntity> evidences = new ArrayList<>();

    /**
     * Create a new {@link XrayManualTestStepResultEntityServer} with the given step status.
     *
     * @param status the status
     */
    public XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status status) {
        super(status);
    }

    @Override
    public void addEvidenceIfPresent(XrayEvidenceItemEntity evidence) {
        if (evidence != null) {
            this.evidences.add(evidence);
        }
    }

    @Override
    public List<XrayEvidenceItemEntity> getAllEvidence() {
        return evidences;
    }

}
