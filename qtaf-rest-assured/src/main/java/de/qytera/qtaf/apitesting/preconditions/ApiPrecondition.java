package de.qytera.qtaf.apitesting.preconditions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.specification.RequestSpecification;

/**
 * Functional interface which is used for handling the preconditions.
 */
@FunctionalInterface
public interface ApiPrecondition {
    public void apply(RequestSpecification req, ApiLogMessage logMessage);
}
