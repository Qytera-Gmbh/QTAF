package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public interface StatusCodeAssertions {
    /**
     * Validate that the response status code matches an integer.
     *
     * @param expectedStatusCode The expected status code.
     * @return lambda
     */
    default ApiAssertion statusCodeIs(int expectedStatusCode) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs assertion",
                    expectedStatusCode,
                    AssertionLogMessageType.ASSERT_EQUALS
            );
            res.statusCode(expectedStatusCode);
        };
    }

    /**
     * Validate that the response status code does not match an integer.
     *
     * @param unexpectedStatusCode The status code that is not expected to be found.
     * @return lambda
     */
    default ApiAssertion statusCodeIsNot(int unexpectedStatusCode) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot assertion",
                    unexpectedStatusCode,
                    AssertionLogMessageType.ASSERT_NOT_EQUALS
            );
            res.statusCode(Matchers.not(unexpectedStatusCode));
        };
    }

    /**
     * Validate that the response status is in the interval [100, 200).
     * This means: 100 less/equal status code less 200
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIs1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs1xx assertion",
                    "1xx",
                    AssertionLogMessageType.ASSERT_EQUALS
            );
            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(100),
                    Matchers.lessThan(200)
            ));
        };
    }
    /**
     * Validate that the response status is not in the interval [100, 200).
     * This means: (status code less 100) || (200 less/equal status code)
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIsNot1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot1xx assertion",
                    "1xx",
                    AssertionLogMessageType.ASSERT_NOT_EQUALS
            );
            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(100),
                    Matchers.greaterThanOrEqualTo(200)
            ));
        };
    }

    /**
     * Validate that the response status is in the interval [200, 300).
     * This means: 200 less/equal status code less 300
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIs2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs2xx assertion",
                    "2xx",
                    AssertionLogMessageType.ASSERT_EQUALS
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(200),
                    Matchers.lessThan(300)
            ));
        };
    }

    /**
     * Validate that the response status is not in the interval [200, 300).
     * This means: (status code less 200) || (300 less/equal status code)
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIsNot2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot2xx assertion",
                    "2xx",
                    AssertionLogMessageType.ASSERT_NOT_EQUALS
            );

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(200),
                    Matchers.greaterThanOrEqualTo(300)
            ));
        };
    }

    /**
     * Validate that the response status is in the interval [300, 400).
     * This means: 300 less/equal status code less 400
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIs3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs3xx assertion",
                    "3xx",
                    AssertionLogMessageType.ASSERT_EQUALS
            );

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(300),
                    Matchers.lessThan(400)
            ));
        };
    }

    /**
     * Validate that the response status is not in the interval [300, 400).
     * This means: (status code less 300) || (400 less/equal status code)
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIsNot3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot3xx assertion",
                    "3xx",
                    AssertionLogMessageType.ASSERT_NOT_EQUALS
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(300),
                    Matchers.greaterThanOrEqualTo(400)
            ));
        };
    }

    /**
     * Validate that the response status is in the interval [400, 500).
     * This means: 400 less/equal status code less 500
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIs4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs4xx assertion",
                    "4xx",
                    AssertionLogMessageType.ASSERT_EQUALS
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(400),
                    Matchers.lessThan(500)
            ));
        };
    }

    /**
     * Validate that the response status is not in the interval [400, 500).
     * This means: (status code less 400) || (500 less/equal status code)
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIsNot4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot4xx assertion",
                    "4xx",
                    AssertionLogMessageType.ASSERT_NOT_EQUALS
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(400),
                    Matchers.greaterThanOrEqualTo(500)
            ));
        };
    }

    /**
     * Validate that the response status is in the interval [500, 600).
     * This means: 500 less/equal status code less 600
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIs5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs5xx assertion",
                    "5xx",
                    AssertionLogMessageType.ASSERT_EQUALS
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(500),
                    Matchers.lessThan(600)
            ));
        };
    }

    /**
     * Validate that the response status is not in the interval [500, 600).
     * This means: (status code less 500) || (600 less/equal status code)
     *
     * @return lambda
     */
    default ApiAssertion statusCodeIsNot5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot5xx assertion",
                    "5xx",
                    AssertionLogMessageType.ASSERT_NOT_EQUALS
            );

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(500),
                    Matchers.greaterThanOrEqualTo(600)
            ));
        };
    }
}


