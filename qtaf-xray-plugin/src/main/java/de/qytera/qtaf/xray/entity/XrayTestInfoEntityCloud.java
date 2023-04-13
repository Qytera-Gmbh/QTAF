package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayTestInfoEntityCloud extends XrayTestInfoEntity {
    /**
     * The test type (e.g. Manual, Cucumber, Generic).
     */
    @NonNull
    private String type;

    public XrayTestInfoEntityCloud(@NonNull String summary, @NonNull String projectKey, @NonNull String type) {
        super(summary, projectKey);
        this.type = type;
    }

}
