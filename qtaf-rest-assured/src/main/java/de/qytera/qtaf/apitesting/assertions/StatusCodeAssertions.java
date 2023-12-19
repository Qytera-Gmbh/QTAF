package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public interface StatusCodeAssertions {
    default ApiAssertion statusCodeIs(int code) {

        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs assertion",
                    code
            );
            res.statusCode(code);
        };

    }

    default ApiAssertion statusCodeIsNot(int code) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot assertion",
                    "not " + code
            );
            res.statusCode(Matchers.not(code));
        };
    }

    default ApiAssertion statusCodeIs1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs1xx assertion",
                    "1xx"
            );

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(100),
                    Matchers.lessThan(200)
            ));


        };
    }

    default ApiAssertion statusCodeIsNot1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot1xx assertion",
                    "not 1xx"
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(100),
                    Matchers.greaterThanOrEqualTo(200)
            ));
        };
    }

    default ApiAssertion statusCodeIs2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs2xx assertion",
                    "2xx"
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(200),
                    Matchers.lessThan(300)
            ));
        };
    }

    default ApiAssertion statusCodeIsNot2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot2xx assertion",
                    "not 2xx"
            );

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(200),
                    Matchers.greaterThanOrEqualTo(300)
            ));
        };
    }

    default ApiAssertion statusCodeIs3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs3xx assertion",
                    "3xx"
            );

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(300),
                    Matchers.lessThan(400)
            ));
        };
    }

    default ApiAssertion statusCodeIsNot3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot3xx assertion",
                    "not 3xx"
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(300),
                    Matchers.greaterThanOrEqualTo(400)
            ));
        };
    }

    default ApiAssertion statusCodeIs4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs4xx assertion",
                    "4xx"
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(400),
                    Matchers.lessThan(500)
            ));
        };
    }

    default ApiAssertion statusCodeIsNot4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot4xx assertion",
                    "not 4xx"
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(400),
                    Matchers.greaterThanOrEqualTo(500)
            ));
        };
    }

    default ApiAssertion statusCodeIs5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIs5xx assertion",
                    "5xx"
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(500),
                    Matchers.lessThan(600)
            ));
        };
    }

    default ApiAssertion statusCodeIsNot5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendStatusCodeAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot5xx assertion",
                    "not 5xx"
            );

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(500),
                    Matchers.greaterThanOrEqualTo(600)
            ));
        };
    }
}


