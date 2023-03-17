package de.qytera.qtaf.xray.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test step entity
 */
public class XrayManualTestStepResultEntity {
    /**
     * Step status
     */
    private Status status;

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
     * Get status
     *
     * @return status Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set status
     *
     * @param status Status
     * @return this
     */
    public XrayManualTestStepResultEntity setStatus(Status status) {
        this.status = status;
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

    public enum Status {
        PASS,   // for xray server
        PASSED, // for xray cloud
        FAIL,   // for xray server
        FAILED, // for xray cloud
        ABORTED,
    }
}
