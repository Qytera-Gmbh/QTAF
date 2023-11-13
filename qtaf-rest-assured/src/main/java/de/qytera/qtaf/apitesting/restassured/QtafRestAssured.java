package de.qytera.qtaf.apitesting.restassured;

import io.restassured.RestAssured;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;

import java.awt.*;
import java.util.Map;

public class QtafRestAssured extends RestAssured {
    public static QtafRequestSpecification given() {
        System.out.println("given");
        RequestSpecification given = RestAssured.given();
        boolean isInstanceRequestSpecification = given instanceof RequestSpecification;
        boolean isInstanceRequestSpecificationImpl = given instanceof RequestSpecificationImpl;

        return (QtafRequestSpecification) RestAssured.given();
        // assert given instanceof RequestSpecificationImpl;
        // return new QtafRequestSpecification((RequestSpecificationImpl) given);
    }

    public static Response get(String path, Map<String, ?> pathParams) {
        System.out.println("QTAF get()");
        return RestAssured.get(path, pathParams);
    }

    public static QtafResponse get(String path, Object... pathParams) {
        System.out.println("QTAF get()");
        return (QtafResponse) RestAssured.get(path, pathParams);
    }

    public static RequestSender when() {
        return RestAssured.when();
    }



    /*
    public QtafRequestSpecification when() {

        System.out.println("when");
        return (QtafRequestSpecification) super.when();
    }
    */
}
