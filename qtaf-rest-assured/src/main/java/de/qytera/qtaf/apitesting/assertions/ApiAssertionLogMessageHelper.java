package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import static de.qytera.qtaf.apitesting.assertions.AssertionPlaceholdersForActualValue.Type.*;

public class ApiAssertionLogMessageHelper {
    /**
     * Creates and appends an AssertionLogMessage to the provided ApiLogMessage
     * and sets relevant attributes
     *
     * @param  apiLogMessage  the ApiLogMessage the AssertionLogMessage should be appended
     * @param  message the message of the AssertionLogMessage
     * @param  expectedValue the expectedValue of the assertion
     * @param  actualValuePlaceholder The actual value is unknown before the API call is executed.
     *                                However, the information which value is to be compared is required later.
     *                                Therefore, this placeholder is set so that the actual value can be computed
     *                                based on the response of the API call.
     */
    private void createAndAppendAssertionLogMessage(@NotNull ApiLogMessage apiLogMessage, String message, Object expectedValue, AssertionPlaceholdersForActualValue.Type actualValuePlaceholder ){
        AssertionLogMessage assertionLogMessage = new AssertionLogMessage(LogLevel.INFO, message);
        assertionLogMessage.setStatus(LogMessage.Status.UNDEFINED);
        assertionLogMessage.setExpected(expectedValue);
        assertionLogMessage.setActual(actualValuePlaceholder); // The actual value is unknown before the API call is executed. However, the information which value is to be compared is required later. Therefore, this placeholder is set so that the actual value can be computed based on the response of the API call.
        assertionLogMessage.setType(AssertionLogMessageType.ASSERT_EQUALS);
        apiLogMessage.addAssertion(assertionLogMessage);
        // TODO: assertionLogMessage.setFeatureId()
        // TODO: assertionLogMessage.setAbstractScenarioId()
        // TODO: assertionLogMessage.setScenarioId()
    };


    /**
     * Meant to be used in the context of a body assertion.
     * Creates and appends an AssertionLogMessage to the provided ApiLogMessage
     * and sets relevant attributes, especially the correct placeholder
     * for the actual vale of the assertion.
     *
     * @param  apiLogMessage  the ApiLogMessage the AssertionLogMessage should be appended
     * @param  message the message of the AssertionLogMessage
     * @param  expectedValue the expectedValue of the assertion
     */
    public static void createAndAppendBodyAssertionLogMessage(ApiLogMessage apiLogMessage, String message, Object expectedValue){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(apiLogMessage, message, expectedValue, UNKNOWN_ACTUAL_VALUE_forBodyAssertion);
    };

    /**
     * Meant to be used in the context of a status code assertion.
     * Creates and appends an AssertionLogMessage to the provided ApiLogMessage
     * and sets relevant attributes, especially the correct placeholder
     * for the actual vale of the assertion.
     *
     * @param  apiLogMessage  the ApiLogMessage the AssertionLogMessage should be appended
     * @param  message the message of the AssertionLogMessage
     * @param  expectedValue the expectedValue of the assertion
     */
    public static void createAndAppendStatusCodeAssertionLogMessage(ApiLogMessage apiLogMessage, String message, Object expectedValue){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(apiLogMessage, message, expectedValue, UNKNOWN_ACTUAL_VALUE_forStatusCodeAssertion);
    };

    /**
     * Meant to be used in the context of a time assertion.
     * Creates and appends an AssertionLogMessage to the provided ApiLogMessage
     * and sets relevant attributes, especially the correct placeholder
     * for the actual vale of the assertion.
     *
     * @param  apiLogMessage  the ApiLogMessage the AssertionLogMessage should be appended
     * @param  message the message of the AssertionLogMessage
     * @param  expectedValue the expectedValue of the assertion
     */
    public static void createAndAppendTimeAssertionLogMessage(ApiLogMessage apiLogMessage, String message, Object expectedValue){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(apiLogMessage, message, expectedValue, UNKNOWN_ACTUAL_VALUE_forTimeAssertion);
    };


    /**
     * returns the actual value based on the response and the previously set placeholder
     *
     * @param  assertionLogMessage  the assertionLogMessage the actual value should be computed for
     * @param  response the response of the api call that contains the actual value
     */
    public static Object computeActualValue(AssertionLogMessage assertionLogMessage, ExtractableResponse<Response> response) {

        if (assertionLogMessage.actual() == UNKNOWN_ACTUAL_VALUE_forBodyAssertion) {
            return response.body();
        }
        if (assertionLogMessage.actual() == UNKNOWN_ACTUAL_VALUE_forStatusCodeAssertion) {
            return response.statusCode();
        }
        if (assertionLogMessage.actual() == UNKNOWN_ACTUAL_VALUE_forTimeAssertion) {
            return response.statusCode();
        }
        return "Error: The actual value for the exception message of the API test could not be computed";
    }

    private void changeMessage(AssertionLogMessage currentAssertionLogMessage, LogMessage.Status status, boolean condition, ExtractableResponse<Response> response, AssertionError error){
        currentAssertionLogMessage.setActual(computeActualValue(currentAssertionLogMessage, response));
        currentAssertionLogMessage.setStatus(status);
        currentAssertionLogMessage.setCondition(condition);
        if (error != null){
            currentAssertionLogMessage.setMessage(error.getMessage());
        }
    }

    public static void changeMessageAccordingToAssertionFailure(AssertionLogMessage currentAssertionLogMessage, ExtractableResponse<Response> response, AssertionError error){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.changeMessage(currentAssertionLogMessage, LogMessage.Status.FAILURE, false, response, error);
    }

    public static void changeMessageAccordingToAssertionPassed(AssertionLogMessage currentAssertionLogMessage, ExtractableResponse<Response> response){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.changeMessage(currentAssertionLogMessage, LogMessage.Status.PASSED, true, response, null);
    }
}
