package de.qytera.qtaf.apitesting.requesttypes;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Functional interface which is used for handling the api request types.
 */
@FunctionalInterface
public interface ApiRequestType {
    // Response perform(RequestSpecification req, ApiLogMessage logMessage);
    Response perform(RequestSpecification req, ApiLogMessage logMessage);
}
