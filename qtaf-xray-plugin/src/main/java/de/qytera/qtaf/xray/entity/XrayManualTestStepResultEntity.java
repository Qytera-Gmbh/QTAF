package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.config.XrayStatusHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test step entity
 */
public class XrayManualTestStepResultEntity {
    /**
     * The concrete test step status, e.g. {@code "PASSED"} or {@code "FAIL"}.
     * Depends on the Xray instance configuration.
     */
    private String status;

    /**
     * Step comment
     */
    private String comment;

    /**
     * Step evidences
     */
    private List<XrayEvidenceItemEntity> evidences = new ArrayList<>();

    /**
     * Step defects
     */
    private List<XrayDefectEntity> defects = new ArrayList<>();

    /**
     * Actual step result
     */
    private String actualResult;

    /**
     * Returns the concrete test step status, e.g. {@code "PASSED"} or {@code "FAIL"}.
     * Depends on the Xray instance configuration.
     *
     * @return the status text
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the test step.
     *
     * @param status the test step status
     * @return this
     */
    public XrayManualTestStepResultEntity setStatus(StepInformationLogMessage.Status status) {
        this.status = XrayStatusHelper.statusToText(status);
        return this;
    }

    /**
     * Get comment
     *
     * @return comment Comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     *
     * @param comment Comment
     * @return this
     */
    public XrayManualTestStepResultEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Get evidences
     *
     * @return evidences Evidences
     */
    public List<XrayEvidenceItemEntity> getEvidences() {
        return evidences;
    }

    /**
     * Set evidences
     *
     * @param evidences Evidences
     * @return this
     */
    public XrayManualTestStepResultEntity setEvidences(List<XrayEvidenceItemEntity> evidences) {
        this.evidences = evidences;
        return this;
    }

    /**
     * Get defects
     *
     * @return defects Defects
     */
    public List<XrayDefectEntity> getDefects() {
        return defects;
    }

    /**
     * Set defects
     *
     * @param defects Defects
     * @return this
     */
    public XrayManualTestStepResultEntity setDefects(List<XrayDefectEntity> defects) {
        this.defects = defects;
        return this;
    }

    /**
     * Get actualResult
     *
     * @return actualResult
     */
    public String getActualResult() {
        return actualResult;
    }

    /**
     * Set actualResult
     *
     * @param actualResult ActualResult
     * @return this
     */
    public XrayManualTestStepResultEntity setActualResult(String actualResult) {
        this.actualResult = actualResult;
        return this;
    }

}
