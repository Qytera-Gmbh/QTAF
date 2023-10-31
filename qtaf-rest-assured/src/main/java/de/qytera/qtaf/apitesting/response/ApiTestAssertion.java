package de.qytera.qtaf.apitesting.response;

import io.restassured.response.ValidatableResponse;

@FunctionalInterface
public interface ApiTestAssertion {
    void apply(ValidatableResponse res);
}
