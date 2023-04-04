package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.xray.config.XrayStatusHelper;

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
     * The test iteration status, e.g. {@code "PASSED"} or {@code "FAIL"}.
     */
    private String status;

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
     * Get the status text of the iteration status (e.g. {@code "FAIL"} or {@code "SUCCESS"}).
     *
     * @return the status text
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the test iteration. These are identical to the general test statuses.
     *
     * @param status the iteration status
     * @return this
     */
    public XrayTestIterationResultEntity setStatus(TestScenarioLogCollection.Status status) {
        this.status = XrayStatusHelper.statusToText(status);
        return this;
    }
}
