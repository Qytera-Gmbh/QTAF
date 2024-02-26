package de.qytera.qtaf.apitesting.restassured.restAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
public class RestAssuredTest {
    String url = "https://jsonplaceholder.typicode.com";
    @Test
    public void basicRestAssuredTest() {

        given()
                .baseUri("https://jsonplaceholder.typicode.com")
        .when()
                .get(url + "/user/1")
        .then()
                .statusCode(404);
    }

    @Test
    public void debugRestAssuredTest() {

        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get(url + "/users/1")
                .then()
                    .time(Matchers.lessThan(0L))
                    .statusCode(200);
                    //.time(Matchers.lessThan(10001L))
                    //.statusCode(404);

        /*
                        responseTimeShouldBeLessThanXMilliseconds(0),
                        statusCodeIs(200),
                        responseTimeShouldBeLessThanXMilliseconds(10001),
                        statusCodeIsNot(404)
         */
    }
}
