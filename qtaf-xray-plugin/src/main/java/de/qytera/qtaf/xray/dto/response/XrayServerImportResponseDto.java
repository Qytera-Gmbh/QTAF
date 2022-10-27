package de.qytera.qtaf.xray.dto.response;

/**
 * Response object that is returns form Xray Server when uploading execution
 */
public class XrayServerImportResponseDto extends XrayImportResponseDto {
    private XrayTestExecIssueEntity testExecIssue;

    /**
     * Get testExecIssue
     *
     * @return testExecIssue
     */
    public XrayTestExecIssueEntity getTestExecIssue() {
        return testExecIssue;
    }

    /**
     * Set testExecIssue
     *
     * @param testExecIssue TestExecIssue
     * @return this
     */
    public XrayServerImportResponseDto setTestExecIssue(XrayTestExecIssueEntity testExecIssue) {
        this.testExecIssue = testExecIssue;
        return this;
    }
}
