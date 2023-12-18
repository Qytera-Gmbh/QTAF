package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;

public interface BodyAssertions {

    default ApiTestAssertion body(Matcher<?> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.body(matcher);

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "body Assertion",
                    LogMessage.Status.PASSED,
                    matcher,
                    AssertionTypes.Type.BODY);
        };
    }


    default ApiTestAssertion body(String s, Matcher<?> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.body(s, matcher);

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "body Assertion",
                    LogMessage.Status.PASSED,
                    matcher,
                    AssertionTypes.Type.BODY );
        };
    }

}
