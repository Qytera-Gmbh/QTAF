package de.qytera.qtaf.apitesting.request;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.specification.RequestSpecification;

@FunctionalInterface
public interface ApiTestRequestSpecification {
    public void apply(RequestSpecification req, ApiLogMessage logMessage);
}
