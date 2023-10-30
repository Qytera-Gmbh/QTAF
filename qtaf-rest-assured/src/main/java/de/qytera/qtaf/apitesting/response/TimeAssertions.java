package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public interface TimeAssertions {
    @Step(name="responseTimeAssertion", description = "Check that the response time matches a given criteria")
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(Matcher<Long> matcher) {
        return (ValidatableResponse res) -> {
            res.time(matcher);
        };
    }

    @Step(name="responseTimeShouldBeLessThanXMilliseconds", description = "Check that the response time is less than a given value")
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(long duration) {
        return (ValidatableResponse res) -> {
            res.time(Matchers.lessThan(duration));
        };
    }
}
