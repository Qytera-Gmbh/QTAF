package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public interface StatusCodeAssertions {
    @Step(name="statusCodeIs", description = "Check that status code matches a given status code")
    default ApiTestAssertion statusCodeIs(int code) {

        return (ValidatableResponse res) -> {
            res.statusCode(code);
        };

    }

    @Step(name="statusCodeIsNot", description = "Check that status code does not match a given status code")
    default ApiTestAssertion statusCodeIsNot(int code) {
        return (ValidatableResponse res) -> res.statusCode(Matchers.not(code));
    }

    @Step(name="statusCodeIs1xx", description = "Check that status code indicates an informational response")
    default ApiTestAssertion statusCodeShouldIs1xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(100),
                        Matchers.lessThan(200)
                ));
    }

    @Step(name="statusCodeIsNot1xx", description = "Check that status code does not indicate an informational response")
    default ApiTestAssertion statusCodeShouldIsNot1xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(100),
                        Matchers.greaterThanOrEqualTo(200)
                ));
    }

    @Step(name="statusCodeIs2xx", description = "Check that status code indicates a successful response")
    default ApiTestAssertion statusCodeShouldIs2xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(200),
                        Matchers.lessThan(300)
                ));
    }

    @Step(name="statusCodeIsNot2xx", description = "Check that status code does not indicate a successful response")
    default ApiTestAssertion statusCodeShouldIsNot2xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(200),
                        Matchers.greaterThanOrEqualTo(300)
                ));
    }

    @Step(name="statusCodeIs3xx", description = "Check that status code indicates a redirection response")
    default ApiTestAssertion statusCodeShouldIs3xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(300),
                        Matchers.lessThan(400)
                ));
    }

    @Step(name="statusCodeIsNot3xx", description = "Check that status code does not indicate a redirection response")
    default ApiTestAssertion statusCodeShouldIsNot3xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(300),
                        Matchers.greaterThanOrEqualTo(400)
                ));
    }

    @Step(name="statusCodeIs4xx", description = "Check that status code indicates a client error")
    default ApiTestAssertion statusCodeShouldIs4xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(400),
                        Matchers.lessThan(500)
                ));
    }

    @Step(name="statusCodeIsNot4xx", description = "Check that status code does not indicate a client error")
    default ApiTestAssertion statusCodeShouldIsNot4xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(400),
                        Matchers.greaterThanOrEqualTo(500)
                ));
    }

    @Step(name="statusCodeIs5xx", description = "Check that status code indicates a server error")
    default ApiTestAssertion statusCodeShouldIs5xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(500),
                        Matchers.lessThan(600)
                ));
    }

    @Step(name="statusCodeIsNot5xx", description = "Check that status code does not indicate a server error")
    default ApiTestAssertion statusCodeShouldIsNot5xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(500),
                        Matchers.greaterThanOrEqualTo(600)
                ));
    }
}
