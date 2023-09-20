package de.qytera.qtaf.xray.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray JSON format</a>
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class XrayTestInfoEntityCloud extends XrayTestInfoEntity {
    /**
     * The test type (e.g. Manual, Cucumber, Generic).
     */
    private String type;
}
