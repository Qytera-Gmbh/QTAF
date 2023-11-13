package de.qytera.qtaf.apitesting.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;
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
}
