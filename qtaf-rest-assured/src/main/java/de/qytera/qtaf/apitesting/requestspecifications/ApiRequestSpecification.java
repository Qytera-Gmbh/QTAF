package de.qytera.qtaf.apitesting.requestspecifications;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.specification.RequestSpecification;

/**
 * Functional interface which is used for handling the request specification.
 */
@FunctionalInterface
public interface ApiRequestSpecification {
    // void apply(RequestSpecification req, ApiLogMessage logMessage);
    void apply(RequestSpecification req);
}
