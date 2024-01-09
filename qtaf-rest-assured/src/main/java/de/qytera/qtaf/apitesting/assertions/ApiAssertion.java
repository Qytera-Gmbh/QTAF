package de.qytera.qtaf.apitesting.assertions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.response.ValidatableResponse;

/**
 * Functional interface which is used for handling the assertions.
 */

@FunctionalInterface
public interface ApiAssertion {
    void apply(ValidatableResponse res, ApiLogMessage logMessage);
}
