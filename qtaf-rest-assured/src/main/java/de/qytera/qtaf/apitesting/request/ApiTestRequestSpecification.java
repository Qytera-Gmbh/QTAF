package de.qytera.qtaf.apitesting.request;

import io.restassured.specification.RequestSpecification;

@FunctionalInterface
public interface ApiTestRequestSpecification {
    public void apply(RequestSpecification req);
}
