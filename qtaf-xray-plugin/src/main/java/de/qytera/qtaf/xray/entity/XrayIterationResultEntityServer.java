package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Xray test iteration result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayIterationResultEntityServer extends XrayIterationResultEntity {

    // Empty because Xray Server does not add any additional or distinct fields in contrast to Xray Cloud.
    // Nonetheless, keeping the abstract super class feels like the right thing to do.

    public XrayIterationResultEntityServer(@NonNull TestScenarioLogCollection.Status status) {
        super(status);
    }

}
