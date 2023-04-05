package de.qytera.qtaf.xray.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray Test Info entity
 *
 * @see "https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema"
 */
public class XrayTestInfoEntity {
    private String summary;
    private String description;
    private String projectKey;
    private List<String> requirementKeys = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    private String testType;
    private List<XrayTestInfoStepEntity> steps = new ArrayList<>();
    private String scenario;
    private String scenarioType;
    private String definition;


    class XrayTestInfoStepEntity {
        private String action;
        private String data;
        private String result;
    }
}
