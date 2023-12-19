package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;

public interface BodyAssertions {

    default ApiAssertion body(Matcher<?> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendBodyAssertionLogMessage(
                    logMessage,
                    "body Assertion",
                    matcher
            );

            res.body(matcher);
        };
    }


    default ApiAssertion body(String s, Matcher<?> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendBodyAssertionLogMessage(
                    logMessage,
                    "body Assertion",
                    matcher
            );

            res.body(s, matcher);
        };
    }

}
