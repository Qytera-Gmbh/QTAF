package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import static de.qytera.qtaf.apitesting.response.AssertionTypes.Type.STATUSCODE;

public interface StatusCodeAssertions {
    default ApiTestAssertion statusCodeIs(int code) {

        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIs assertion",
                    LogMessage.Status.PASSED,
                    code,
                    STATUSCODE
            );
            res.statusCode(code);
        };

    }

    default ApiTestAssertion statusCodeIsNot(int code) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot assertion",
                    LogMessage.Status.PASSED,
                    "not" + code,
                    STATUSCODE
            );
            res.statusCode(Matchers.not(code));
        };
    }

    default ApiTestAssertion statusCodeIs1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIs1xx assertion",
                    LogMessage.Status.PASSED,
                    "1xx",
                    STATUSCODE
            );

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(100),
                    Matchers.lessThan(200)
            ));


        };
    }

    default ApiTestAssertion statusCodeIsNot1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot1xx assertion",
                    LogMessage.Status.PASSED,
                    "not 1xx",
                    STATUSCODE
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(100),
                    Matchers.greaterThanOrEqualTo(200)
            ));
        };
    }

    default ApiTestAssertion statusCodeIs2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIs2xx assertion",
                    LogMessage.Status.PASSED,
                    "2xx",
                    STATUSCODE
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(200),
                    Matchers.lessThan(300)
            ));
        };
    }

    default ApiTestAssertion statusCodeIsNot2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot2xx assertion",
                    LogMessage.Status.PASSED,
                    "not 2xx",
                    STATUSCODE
            );

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(200),
                    Matchers.greaterThanOrEqualTo(300)
            ));
        };
    }

    default ApiTestAssertion statusCodeIs3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIs3xx assertion",
                    LogMessage.Status.PASSED,
                    "3xx",
                    STATUSCODE
            );

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(300),
                    Matchers.lessThan(400)
            ));
        };
    }

    default ApiTestAssertion statusCodeIsNot3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot3xx assertion",
                    LogMessage.Status.PASSED,
                    "not 3xx",
                    STATUSCODE
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(300),
                    Matchers.greaterThanOrEqualTo(400)
            ));
        };
    }

    default ApiTestAssertion statusCodeIs4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIs4xx assertion",
                    LogMessage.Status.PASSED,
                    "4xx",
                    STATUSCODE
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(400),
                    Matchers.lessThan(500)
            ));
        };
    }

    default ApiTestAssertion statusCodeIsNot4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot4xx assertion",
                    LogMessage.Status.PASSED,
                    "not 4xx",
                    STATUSCODE
            );


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(400),
                    Matchers.greaterThanOrEqualTo(500)
            ));
        };
    }

    default ApiTestAssertion statusCodeIs5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIs5xx assertion",
                    LogMessage.Status.PASSED,
                    "5xx",
                    STATUSCODE
            );


            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(500),
                    Matchers.lessThan(600)
            ));
        };
    }

    default ApiTestAssertion statusCodeIsNot5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeIsNot5xx assertion",
                    LogMessage.Status.PASSED,
                    "not 5xx",
                    STATUSCODE
            );

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(500),
                    Matchers.greaterThanOrEqualTo(600)
            ));
        };
    }
}


