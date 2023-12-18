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

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "responseTimeShouldBeLessThanXMilliseconds",
                    LogMessage.Status.PASSED,
                    matcher,
                    AssertionTypes.Type.TIME
            );

            res.time(matcher);
        };
    }
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(long duration) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendAssertionLogMessage(
                    logMessage,
                    "responseTimeShouldBeLessThanXMilliseconds",
                    LogMessage.Status.PASSED,
                    duration,
                    AssertionTypes.Type.TIME
            );

            res.time(Matchers.lessThan(duration));
        };
    }
}
