package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public interface TimeAssertions {
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(Matcher<Long> matcher) {
        return (ValidatableResponse res) -> {
            res.time(matcher);
        };
    }
    default ApiTestAssertion responseTimeShouldBeLessThanXMilliseconds(long duration) {
        return (ValidatableResponse res) -> {
            res.time(Matchers.lessThan(duration));
        };
    }
}
