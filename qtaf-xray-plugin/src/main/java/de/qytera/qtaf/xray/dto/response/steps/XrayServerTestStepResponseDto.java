package de.qytera.qtaf.xray.dto.response.steps;

import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;

/**
 * An Xray Server test step response.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Test+Steps+-+REST">Xray Server documentation</a>
 */
@Getter
@Setter
public class XrayServerTestStepResponseDto implements XrayTestStepResponseDto {

    private int id;
    private int index;
    private XrayServerTestStepContentResponseDto step;
    private XrayServerTestStepContentResponseDto data;
    private XrayServerTestStepContentResponseDto result;
    private JsonArray attachments;

}
