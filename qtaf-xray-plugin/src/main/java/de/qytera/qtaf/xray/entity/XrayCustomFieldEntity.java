package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Xray custom field entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Getter
@Setter
public class XrayCustomFieldEntity {
    /**
     * The test run custom field ID.
     */
    private String id;
    /**
     * The test run custom field value.
     */
    private String value;
}
