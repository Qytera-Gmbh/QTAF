package de.qytera.qtaf.xray.dto.response.steps;

import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;

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
