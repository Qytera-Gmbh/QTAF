package de.qytera.qtaf.apitesting.action;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@FunctionalInterface
public interface ApiAction {
    // Response perform(RequestSpecification req, ApiLogMessage logMessage);
    Response perform(RequestSpecification req, ApiLogMessage logMessage);
}
