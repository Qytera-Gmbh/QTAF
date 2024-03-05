package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;

public interface BodyAssertions {

    /**
     * Validate that the response body conforms to one or more Hamcrest matchers. E.g.
     *
     * @param matcher The hamcrest matcher the response body must match.
     * @return lambda
     */
    default ApiAssertion body(Matcher<?> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendBodyAssertionLogMessage(
                    logMessage,
                    "body Assertion",
                    matcher.toString(),
                    AssertionLogMessageType.ASSERT_EQUALS
            );

            res.body(matcher);
        };
    }

    /**
     * Validate that the JSON or XML response body conforms to one Hamcrest matcher.
     * Note that if the response body type is not of type application/xml or application/json you cannot use this verification.
     *
     * @param path The body path matcher – The hamcrest matcher that must response body must match.
     * @param matcher The hamcrest matcher the response body must match.
     * @return lambda
     */
    default ApiAssertion body(String path, Matcher<?> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendBodyAssertionLogMessage(
                    logMessage,
                    "body Assertion",
                    path + ": " + matcher.toString(),
                    AssertionLogMessageType.ASSERT_EQUALS
            );

            res.body(path, matcher);
        };
    }
}
