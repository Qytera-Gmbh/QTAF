package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;


public interface TimeAssertions {
    default ApiAssertion responseTimeShouldBeLessThanXMilliseconds(Matcher<Long> matcher) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendTimeAssertionLogMessage(
                    logMessage,
                    "responseTimeShouldBeLessThanXMilliseconds",
                    matcher
            );

            res.time(matcher);
        };
    }
    default ApiAssertion responseTimeShouldBeLessThanXMilliseconds(long duration) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {

            ApiAssertionLogMessageHelper.createAndAppendTimeAssertionLogMessage(
                    logMessage,
                    "responseTimeShouldBeLessThanXMilliseconds",
                    duration
            );

            res.time(Matchers.lessThan(duration));
        };
    }
}
