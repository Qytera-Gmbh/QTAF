package de.qytera.qtaf.apitesting.restassured;

import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.ValidatableResponse;

public class QtafResponse extends RestAssuredResponseImpl {
    @Override
    public ValidatableResponse then() {
        return super.then();
    }
}
