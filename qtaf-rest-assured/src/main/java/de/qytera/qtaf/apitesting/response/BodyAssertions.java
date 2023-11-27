package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;

public interface BodyAssertions {

    default ApiTestAssertion body(Matcher<?> matcher) {
        return (ValidatableResponse res) -> res.body(matcher);
    }


    default ApiTestAssertion body(String s, Matcher<?> matcher) {
        return (ValidatableResponse res) -> res.body(s, matcher);
    }

}
