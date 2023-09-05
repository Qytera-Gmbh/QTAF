package de.qytera.qtaf.core.events.payload;

import de.qytera.qtaf.core.log.model.message.LogMessage;

/**
 * Step event payload object.
 */
public class QtafTestStepEventPayload implements IQtafTestStepEventPayload {
    /**
     * ID of the scenario the step belongs to.
     */
    protected String scenarioId;

    /**
     * Log message of the executed step.
     */
    protected LogMessage logMessage;

    /**
     * Get scenarioId.
     *
     * @return scenarioId
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Set scenarioId.
     *
     * @param scenarioId ScenarioId
     * @return this
     */
    public QtafTestStepEventPayload setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
        return this;
    }

    /**
     * Get logMessage.
     *
     * @return logMessage
     */
    @Override
    public LogMessage getLogMessage() {
        return logMessage;
    }

    /**
     * Set logMessage.
     *
     * @param logMessage LogMessage
     * @return this
     */
    public QtafTestStepEventPayload setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
        return this;
    }
}
