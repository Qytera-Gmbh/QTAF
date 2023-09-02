package de.qytera.qtaf.xray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray JSON format</a>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class XrayTestInfoEntityCloud extends XrayTestInfoEntity {
    /**
     * The test type (e.g. Manual, Cucumber, Generic).
     */
    private final String type;

    public XrayTestInfoEntityCloud(String summary, String projectKey, String type) {
        super(summary, projectKey);
        this.type = type;
    }

}
