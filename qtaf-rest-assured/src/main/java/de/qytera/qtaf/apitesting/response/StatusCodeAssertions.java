package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public interface StatusCodeAssertions {
    default ApiTestAssertion statusCodeIs(int code) {

        return (ValidatableResponse res) -> {
            res.statusCode(code);
        };

    }

    default ApiTestAssertion statusCodeIsNot(int code) {
        return (ValidatableResponse res) -> res.statusCode(Matchers.not(code));
    }

    default ApiTestAssertion statusCodeShouldIs1xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(100),
                        Matchers.lessThan(200)
                ));
    }

    default ApiTestAssertion statusCodeShouldIsNot1xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(100),
                        Matchers.greaterThanOrEqualTo(200)
                ));
    }

    default ApiTestAssertion statusCodeShouldIs2xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(200),
                        Matchers.lessThan(300)
                ));
    }

    default ApiTestAssertion statusCodeShouldIsNot2xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(200),
                        Matchers.greaterThanOrEqualTo(300)
                ));
    }

    default ApiTestAssertion statusCodeShouldIs3xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(300),
                        Matchers.lessThan(400)
                ));
    }

    default ApiTestAssertion statusCodeShouldIsNot3xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(300),
                        Matchers.greaterThanOrEqualTo(400)
                ));
    }

    default ApiTestAssertion statusCodeShouldIs4xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(400),
                        Matchers.lessThan(500)
                ));
    }

    default ApiTestAssertion statusCodeShouldIsNot4xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(400),
                        Matchers.greaterThanOrEqualTo(500)
                ));
    }

    default ApiTestAssertion statusCodeShouldIs5xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.allOf(
                        Matchers.greaterThanOrEqualTo(500),
                        Matchers.lessThan(600)
                ));
    }

    default ApiTestAssertion statusCodeShouldIsNot5xx() {
        return (ValidatableResponse res) ->
                res.statusCode(Matchers.anyOf(
                        Matchers.lessThan(500),
                        Matchers.greaterThanOrEqualTo(600)
                ));
    }
}
