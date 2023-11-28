package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;

public class ApiAssertionLogMessageHelper {
    public static void createAndAppendAssertionLogMessage(ApiLogMessage apiLogMessage, String message, LogMessage.Status status){
        AssertionLogMessage assertionLogMessage = new AssertionLogMessage(LogLevel.INFO, message);
        assertionLogMessage.setStatus(status);
        assertionLogMessage.setActual(""); //TODO
        assertionLogMessage.setExpected(""); // TODO
        apiLogMessage.addAssertion(assertionLogMessage);
        // TODO: assertionLogMessage.setFeatureId()
        // TODO: assertionLogMessage.setAbstractScenarioId()
        // TODO: assertionLogMessage.setScenarioId()
    };
}
