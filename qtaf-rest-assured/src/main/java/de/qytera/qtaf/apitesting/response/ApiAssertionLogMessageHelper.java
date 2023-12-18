package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import static de.qytera.qtaf.apitesting.response.AssertionTypes.Type.*;

public class ApiAssertionLogMessageHelper {
    public static void createAndAppendAssertionLogMessage(ApiLogMessage apiLogMessage, String message, LogMessage.Status status, Object expectedValue, Object actualValue ){
        AssertionLogMessage assertionLogMessage = new AssertionLogMessage(LogLevel.INFO, message);
        assertionLogMessage.setStatus(status);
        assertionLogMessage.setExpected(expectedValue);
        assertionLogMessage.setActual(actualValue);

        // assertionLogMessage.setExpected(expected); TODO
        // assertionLogMessage.setActual(actual); TODO
        apiLogMessage.addAssertion(assertionLogMessage);
        // TODO: assertionLogMessage.setFeatureId()
        // TODO: assertionLogMessage.setAbstractScenarioId()
        // TODO: assertionLogMessage.setScenarioId()
    };
    /*
    public static void computeAcualValue(LogMessage logMessage, LogMessage.Status status, ExtractableResponse<Response> response, String message){
        List<AssertionLogMessage> assertionLogMessages = logMessage.getAssertions();

        for (AssertionLogMessage assertionLogMessage : assertionLogMessages) {
            assertionLogMessage.setStatus(status);
            if (message != null){
                assertionLogMessage.setMessage(message);
            }
            if (assertionLogMessage.actual() == STATUSCODE) {
                assertionLogMessage.setActual(response.statusCode());
            }
            if (assertionLogMessage.actual() == BODY) {
                assertionLogMessage.setActual(response.body());
            }
            if (assertionLogMessage.actual() == TIME) {
                assertionLogMessage.setActual(response.time());
            }
        }
    }

     */

    public static void computeAcualValue(AssertionLogMessage assertionLogMessage, LogMessage.Status status, ExtractableResponse<Response> response, String message){

        assertionLogMessage.setStatus(status);
        if (message != null){
            assertionLogMessage.setMessage(message);
        }
        if (assertionLogMessage.actual() == STATUSCODE) {
            assertionLogMessage.setActual(response.statusCode());
        }
        if (assertionLogMessage.actual() == BODY) {
            assertionLogMessage.setActual(response.body());
        }
        if (assertionLogMessage.actual() == TIME) {
            assertionLogMessage.setActual(response.time());
        }
    }
}
