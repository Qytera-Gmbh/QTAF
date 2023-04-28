package de.qytera.qtaf.xray.dto.request.xray;

import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestExecutionInfoEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Xray import request DTO. This class represents the JSON structure that Xray API expects when importing tests.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat">Xray Server REST documentation</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results#UsingXrayJSONformattoimportexecutionresults-JSONformat">Xray Cloud REST documentation</a>
 */
@Getter
@Setter
public class ImportExecutionResultsRequestDto {
    /**
     * The test execution key where to import the execution results.
     */
    private String testExecutionKey;
    /**
     * The info object for creating new Test Execution issues.
     */
    private XrayTestExecutionInfoEntity info;
    /**
     * The Test Run result details.
     */
    private List<XrayTestEntity> tests;

}
