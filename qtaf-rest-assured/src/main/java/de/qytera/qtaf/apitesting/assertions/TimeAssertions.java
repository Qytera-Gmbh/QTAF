package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

/**
 * Interface for classes that provide methods for checking the response time.
 */
public interface TimeAssertions {
    /**
     * Validate that the response time (in milliseconds) is less than max expected duration.
     *
     * @param maxExpectedDuration response time should be less than this value
     * @return lambda
     */

    default ApiAssertion responseTimeShouldBeLessThanXMilliseconds(long maxExpectedDuration) {
        return (ValidatableResponse res, ApiLogMessage logMessage) -> {
            ApiAssertionLogMessageHelper.createAndAppendTimeAssertionLogMessage(
                    logMessage,
                    "responseTimeShouldBeLessThanXMilliseconds",
                    maxExpectedDuration,
                    AssertionLogMessageType.ASSERT_EQUALS
            );
            res.time(Matchers.lessThan(maxExpectedDuration));
        };
    }
}
