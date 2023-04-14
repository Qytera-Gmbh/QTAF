package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Xray test step entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
public class XrayTestStepEntity {
    /**
     * The step action - native field.
     */
    @NonNull
    private String action;
    /**
     * The step data - native field.
     */
    private String data;
    /**
     * The step expected result - native field.
     */
    private String result;
}
