package de.qytera.qtaf.xray.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Xray test entity
 */
public class XrayTestEntity {
    /**
     * testKey
     */
    private String testKey;

    /**
     * start
     */
    private Date start;

    /**
     * finish
     */
    private Date finish;

    /**
     * comment
     */
    private String comment;

    /**
     * status
     */
    private Status status;

    /**
     * Scenario evidences
     */
    private List<XrayEvidenceEntity> evidences = new ArrayList<>();

    /**
     * step logs
     */
    private final List<XrayTestStepEntity> steps = new ArrayList<>();

    /**
     * Get testKey
     *
     * @return testKey TestKey
     */
    public String getTestKey() {
        return testKey;
    }

    /**
     * Set testKey
     *
     * @param testKey TestKey
     * @return this
     */
    public XrayTestEntity setTestKey(String testKey) {
        this.testKey = testKey;
        return this;
    }

    /**
     * Get start
     *
     * @return start Start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Set start
     *
     * @param start Start
     * @return this
     */
    public XrayTestEntity setStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Get finish
     *
     * @return finish Finish
     */
    public Date getFinish() {
        return finish;
    }

    /**
     * Set finish
     *
     * @param finish Finish
     * @return this
     */
    public XrayTestEntity setFinish(Date finish) {
        this.finish = finish;
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
    public XrayTestEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

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
    public XrayTestEntity setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Add Test Step
     *
     * @param xrayTestStepEntity  step
     * @return              this
     */
    public XrayTestEntity addXrayStep(XrayTestStepEntity xrayTestStepEntity) {
        this.steps.add(xrayTestStepEntity);
        return this;
    }

    /**
     * Add Evidence Entity
     *
     * @param xrayEvidenceEntity  Evidence entity
     * @return              this
     */
    public XrayTestEntity addXrayEvidenceEntity(XrayEvidenceEntity xrayEvidenceEntity) {
        this.evidences.add(xrayEvidenceEntity);
        return this;
    }

    /**
     * Status enum
     */
    public enum Status {
        PASSED,
        FAILED
    }
}
