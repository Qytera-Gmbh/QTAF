package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public interface TimeAssertions {
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(Matcher<Long> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.time(matcher);

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeShouldIs5xx assertion",
                    LogMessage.Status.PASSED
            );
        };
    }
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(long duration) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            res.time(Matchers.lessThan(duration));

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "statusCodeShouldIs5xx assertion",
                    LogMessage.Status.PASSED
            );
        };
    }
}
