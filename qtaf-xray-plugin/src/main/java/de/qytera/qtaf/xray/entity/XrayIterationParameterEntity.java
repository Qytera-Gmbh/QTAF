package de.qytera.qtaf.xray.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Xray test iteration parameter entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
public class XrayIterationParameterEntity {
    /**
     * The parameter name.
     */
    private String name;
    /**
     * The parameter value.
     */
    private String value;
}
