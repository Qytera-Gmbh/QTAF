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

    /**
     * Create a new {@link XrayTestInfoEntityCloud} with the given summary, project key and test type.
     *
     * @param summary    the summary
     * @param projectKey the project key
     * @param type       the test type
     */
    public XrayTestInfoEntityCloud(String summary, String projectKey, String type) {
        super(summary, projectKey);
        this.type = type;
    }

}
