package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test iteration result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
public abstract class XrayIterationResultEntity {
    /**
     * An array of parameters along with their values.
     */
    private List<XrayIterationParameterEntity> parameters = new ArrayList<>();
    /**
     * The status for the iteration (PASS, FAIL, EXECUTING, TO DO, custom statuses ...).
     */
    @NonNull
    private String status;
    /**
     * An array of step results (for Manual tests).
     */
    private List<XrayManualTestStepResultEntity> steps = new ArrayList<>();

    protected XrayIterationResultEntity(@NonNull TestScenarioLogCollection.Status status) {
        this.status = XrayStatusHelper.statusToText(status);
    }

}
