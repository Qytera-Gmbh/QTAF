package de.qytera.qtaf.xray.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test step entity
 */
public class XrayTestStepEntity {
    private Status status;
    private String comment;
    private List<XrayEvidenceEntity> evidences = new ArrayList<>();
    private List<XrayDefectEntity> defects = new ArrayList<>();

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
    public XrayTestStepEntity setStatus(Status status) {
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
    public XrayTestStepEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Get evidences
     *
     * @return evidences Evidences
     */
    public List<XrayEvidenceEntity> getEvidences() {
        return evidences;
    }

    /**
     * Set evidences
     *
     * @param evidences Evidences
     * @return this
     */
    public XrayTestStepEntity setEvidences(List<XrayEvidenceEntity> evidences) {
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
    public XrayTestStepEntity setDefects(List<XrayDefectEntity> defects) {
        this.defects = defects;
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
