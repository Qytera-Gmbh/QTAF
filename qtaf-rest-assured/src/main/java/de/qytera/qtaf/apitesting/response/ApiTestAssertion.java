package de.qytera.qtaf.apitesting.response;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.response.ValidatableResponse;

@FunctionalInterface
public interface ApiTestAssertion {
    void apply(ValidatableResponse res, ApiLogMessage logMessage);
}
