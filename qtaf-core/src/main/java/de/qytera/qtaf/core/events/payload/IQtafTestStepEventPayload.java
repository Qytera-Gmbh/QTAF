package de.qytera.qtaf.core.events.payload;

import de.qytera.qtaf.core.log.model.message.LogMessage;

/**
 * Interface for step event payload objects.
 */
public interface IQtafTestStepEventPayload {
    /**
     * Get scenario ID.
     *
     * @return scenario DI
     */
    String getScenarioId();

    /**
     * Get log message.
     *
     * @return log message
     */
    LogMessage getLogMessage();
}
