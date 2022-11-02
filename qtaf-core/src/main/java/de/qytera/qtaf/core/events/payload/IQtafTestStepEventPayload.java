package de.qytera.qtaf.core.events.payload;

import de.qytera.qtaf.core.log.model.message.LogMessage;

/**
 * Interface for step event payload objects
 */
public interface IQtafTestStepEventPayload {
    String getScenarioId();
    LogMessage getLogMessage();
}
