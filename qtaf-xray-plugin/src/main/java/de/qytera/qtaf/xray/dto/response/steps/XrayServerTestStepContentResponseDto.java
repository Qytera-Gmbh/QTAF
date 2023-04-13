package de.qytera.qtaf.xray.dto.response.steps;

import lombok.Getter;
import lombok.Setter;

/**
 * Xray Server test step responses contain specific content responses split into raw and rendered.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Test+Steps+-+REST">Xray Server documentation</a>
 */
@Getter
@Setter
public class XrayServerTestStepContentResponseDto {

    private String raw;
    private String rendered;

}
