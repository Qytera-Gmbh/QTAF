package de.qytera.qtaf.cucumber.log.model.message;

import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;

import java.util.UUID;

/**
 * Cucumber log message
 */
public class CucumberStepLogMessage extends StepInformationLogMessage {

    /**
     * Hash code of message
     */
    UUID id;

    /**
     * Cucumber scenario ID
     */
    String scenarioId;

    /**
     * Internal cucumber step index
     */
    int stepPosition;

    /**
     * Flag that indicates if log message was already dispatched
     */
    transient boolean dispatched = false;

    /**
     * Constructor
     *
     * @param methodName step name
     * @param message    log message
     */
    public CucumberStepLogMessage(String methodName, String message) {
        super(methodName, message);
    }

    /**
     * Constructor
     *
     * @param methodName step name
     * @param message    log message
     */
    public CucumberStepLogMessage(UUID id, String methodName, String message) {
        super(methodName, message);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CucumberStepLogMessage)) {
            return false;
        }

        CucumberStepLogMessage m = (CucumberStepLogMessage) o;

        if (m == this) {
            return true;
        }

        return this.id.equals(m.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * Get id
     *
     * @return id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id Id
     * @return this
     */
    public CucumberStepLogMessage setId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get scenarioId
     *
     * @return scenarioId
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Set scenarioId
     *
     * @param scenarioId ScenarioId
     * @return this
     */
    public CucumberStepLogMessage setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
        return this;
    }

    /**
     * Get stepIndex
     *
     * @return stepIndex
     */
    public int getStepPosition() {
        return stepPosition;
    }

    /**
     * Set stepIndex
     *
     * @param stepPosition StepIndex
     * @return this
     */
    public CucumberStepLogMessage setStepPosition(int stepPosition) {
        this.stepPosition = stepPosition;
        return this;
    }

    /**
     * Get dispatched
     *
     * @return dispatched
     */
    public boolean isDispatched() {
        return dispatched;
    }

    /**
     * Set dispatched
     *
     * @param dispatched Dispatched
     * @return this
     */
    public CucumberStepLogMessage setDispatched(boolean dispatched) {
        this.dispatched = dispatched;
        return this;
    }
}
