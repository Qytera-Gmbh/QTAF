package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Xray test iteration result entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayIterationResultEntityCloud extends XrayIterationResultEntity {
    /**
     * The iteration name.
     */
    private String name;
    /**
     * The log for the iteration.
     */
    private String log;
    /**
     * A duration for the iteration.
     */
    private String duration;

    public XrayIterationResultEntityCloud(@NonNull TestScenarioLogCollection.Status status) {
        super(status);
    }

}
