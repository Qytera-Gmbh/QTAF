package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public interface StatusCodeAssertions {
    default ApiTestAssertion statusCodeIs(int code) {

        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            res.statusCode(code);

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage, "statusCodeIs assertion", LogMessage.Status.PASSED);
        };

    }

    default ApiTestAssertion statusCodeIsNot(int code) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            res.statusCode(Matchers.not(code));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage, "statusCodeIsNot assertion", LogMessage.Status.PASSED);
        };
    }

    default ApiTestAssertion statusCodeShouldIs1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(100),
                    Matchers.lessThan(200)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage, "statusCodeShouldIs1xx assertion", LogMessage.Status.PASSED);
        };
    }

    default ApiTestAssertion statusCodeShouldIsNot1xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(100),
                    Matchers.greaterThanOrEqualTo(200)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage, "statusCodeShouldIsNot1xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIs2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(200),
                    Matchers.lessThan(300)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIs2xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIsNot2xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {


            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(200),
                    Matchers.greaterThanOrEqualTo(300)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIsNot2xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIs3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(300),
                    Matchers.lessThan(400)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIs3xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIsNot3xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(300),
                    Matchers.greaterThanOrEqualTo(400)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIsNot3xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIs4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(400),
                    Matchers.lessThan(500)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIs4xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIsNot4xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(400),
                    Matchers.greaterThanOrEqualTo(500)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIsNot4xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIs5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.statusCode(Matchers.allOf(
                    Matchers.greaterThanOrEqualTo(500),
                    Matchers.lessThan(600)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIs5xx assertion", LogMessage.Status.PASSED );
        };
    }

    default ApiTestAssertion statusCodeShouldIsNot5xx() {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            res.statusCode(Matchers.anyOf(
                    Matchers.lessThan(500),
                    Matchers.greaterThanOrEqualTo(600)
            ));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(logMessage,"statusCodeShouldIsNot5xx assertion", LogMessage.Status.PASSED );
        };
    }
}


