package de.qytera.qtaf.apitesting.preconditions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.specification.RequestSpecification;

@FunctionalInterface
public interface ApiPrecondition {
    public void apply(RequestSpecification req, ApiLogMessage logMessage);
}
