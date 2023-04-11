package de.qytera.qtaf.xray.dto.response.steps;

import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XrayCloudTestStepResponseDto implements XrayTestStepResponseDto {

    private int id;

    private String data;

    private String action;

    private String result;

    private JsonArray attachments;

}
