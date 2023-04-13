package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray manual test step result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayManualTestStepResultEntityServer extends XrayManualTestStepResultEntity {
    /**
     * An array of evidence items of the test run.
     */
    private List<XrayEvidenceItemEntity> evidences = new ArrayList<>();

    public XrayManualTestStepResultEntityServer(@NonNull String status) {
        super(status);
    }

    @Override
    public void addEvidenceIfPresent(XrayEvidenceItemEntity evidence) {
        if (evidence != null) {
            this.evidences.add(evidence);
        }
    }

}
