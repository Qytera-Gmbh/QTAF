package de.qytera.qtaf.xray.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This entity class describes a concrete execution of an abstract test scenario
 */
public class XrayTestIterationResultEntity {
    /**
     * List pf parameters that was passed to the concrete scenario execution (i.e. by a data provider)
     */
    private List<XrayTestIterationParameterEntity> parameters = new ArrayList<>();

    /**
     * List pf parameters that was passed to the concrete scenario execution (i.e. by a data provider)
     */
    private List<XrayManualTestStepResultEntity> steps = new ArrayList<>();

    /**
     * Test iteration status
     */
    private Status status;

    public enum Status {
        PASS,   // for xray server
        PASSED, // for xray cloud
        FAIL,   // for xray server
        FAILED, // for xray cloud
        ABORTED,
    }

    /**
     * Get parameters
     *
     * @return parameters
     */
    public List<XrayTestIterationParameterEntity> getParameters() {
        return parameters;
    }

    /**
     * Add a parameter entity to the iteration entity
     * @param parameter Parameter entity object
     * @return  this
     */
    public XrayTestIterationResultEntity addParameter(XrayTestIterationParameterEntity parameter) {
        parameters.add(parameter);
        return this;
    }

    /**
     * Set parameters
     *
     * @param parameters Parameters
     * @return this
     */
    public XrayTestIterationResultEntity setParameters(List<XrayTestIterationParameterEntity> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get steps
     *
     * @return steps
     */
    public List<XrayManualTestStepResultEntity> getSteps() {
        return steps;
    }

    /**
     * Add a step entity to the iteration entity
     * @param step Step entity object
     * @return  this
     */
    public XrayTestIterationResultEntity addStep(XrayManualTestStepResultEntity step) {
        steps.add(step);
        return this;
    }

    /**
     * Set steps
     *
     * @param steps Steps
     * @return this
     */
    public XrayTestIterationResultEntity setSteps(List<XrayManualTestStepResultEntity> steps) {
        this.steps = steps;
        return this;
    }

    /**
     * Get status
     *
     * @return status
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
    public XrayTestIterationResultEntity setStatus(Status status) {
        this.status = status;
        return this;
    }
}
