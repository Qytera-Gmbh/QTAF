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


/**
 * This class contains logic for handling API assertion log messages.
 * It is intended to reduce the redundancy of reusable code sections.
 * In addition, the class is intended to provide developers with an interface
 * for better handling of the API assertion log messages,
 * in particular to simplify the somewhat unclear behaviour of the implementation for calculating the "actual value".
 */
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
        assertionLogMessage.setActual(actualValuePlaceholder); // The actual value is unknown before the API call is executed.
        // However, the information which value is to be compared is required later.
        // Therefore, this placeholder is set so that the actual value can be computed based on the response of the API call.
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
        return "Error: The actual value of the assertion message of the API test could not be calculated due to an unknown error.";
    }

    /**
     * Updates the attributes of an AssertionLogMessage object, including its actual value, status, condition, and message.
     * This method provides the basis for further methods that are intended to change AssertionLogMessages depending on whether the assertion has succeeded or failed.
     * (also see changeMessageAccordingToAssertionFailure(), changeMessageAccordingToAssertionPassed() )
     *
     * @param currentAssertionLogMessage The AssertionLogMessage object to be updated.
     * @param status                     The new status to set for the AssertionLogMessage.
     * @param condition                  The new condition to set for the AssertionLogMessage.
     * @param response                   The ExtractableResponse containing the response data.
     * @param error                      The AssertionError object representing an error (optional, can be null).
     */
    private void changeMessage(AssertionLogMessage currentAssertionLogMessage, LogMessage.Status status, boolean condition, ExtractableResponse<Response> response, AssertionError error){
        currentAssertionLogMessage.setActual(computeActualValue(currentAssertionLogMessage, response));
        currentAssertionLogMessage.setStatus(status);
        currentAssertionLogMessage.setCondition(condition);
        if (error != null){
            currentAssertionLogMessage.setMessage(error.getMessage());
        }
    }

    /**
     * Updates the attributes of an AssertionLogMessage object, including its actual value, status, condition, and message according to a failed assertion.
     *
     * @param currentAssertionLogMessage The AssertionLogMessage object to be updated.
     * @param response                   The ExtractableResponse containing the response data.
     * @param error                      The AssertionError object representing an error (optional, can be null).
     */
    public static void changeMessageAccordingToAssertionFailure(AssertionLogMessage currentAssertionLogMessage, ExtractableResponse<Response> response, AssertionError error){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.changeMessage(currentAssertionLogMessage, LogMessage.Status.FAILURE, false, response, error);
    }

    /**
     * Updates the attributes of an AssertionLogMessage object, including its actual value, status, condition, and message according to a passed assertion.
     *
     * @param currentAssertionLogMessage The AssertionLogMessage object to be updated.
     * @param response                   The ExtractableResponse containing the response data.
     */
    public static void changeMessageAccordingToAssertionPassed(AssertionLogMessage currentAssertionLogMessage, ExtractableResponse<Response> response){
        ApiAssertionLogMessageHelper apiAssertionLogMessageHelper = new ApiAssertionLogMessageHelper();
        apiAssertionLogMessageHelper.changeMessage(currentAssertionLogMessage, LogMessage.Status.PASSED, true, response, null);
    }
}
