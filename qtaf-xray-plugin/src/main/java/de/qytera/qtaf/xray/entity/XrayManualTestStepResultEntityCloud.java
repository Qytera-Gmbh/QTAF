package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray manual test step result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayManualTestStepResultEntityCloud extends XrayManualTestStepResultEntity {
    /**
     * An array of evidence items of the test run.
     */
    private List<XrayEvidenceItemEntity> evidence = new ArrayList<>();

    public XrayManualTestStepResultEntityCloud(@NonNull StepInformationLogMessage.Status status) {
        super(status);
    }

    @Override
    public void addEvidenceIfPresent(XrayEvidenceItemEntity evidence) {
        if (evidence != null) {
            this.evidence.add(evidence);
        }
    }

}
