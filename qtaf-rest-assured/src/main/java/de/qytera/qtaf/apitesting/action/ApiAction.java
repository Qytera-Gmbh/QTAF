package de.qytera.qtaf.apitesting.action;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@FunctionalInterface
public interface ApiAction {
    Response perform(RequestSpecification req);
}
