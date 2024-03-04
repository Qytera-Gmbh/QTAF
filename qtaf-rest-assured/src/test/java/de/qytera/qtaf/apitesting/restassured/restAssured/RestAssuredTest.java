package de.qytera.qtaf.apitesting.restassured.restAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void multiplePathParamsRestAssuredTest() {

        String basePath = "/{param1}{param3}";

        String param1 = "posts";
        String param2 = "/";
        String param3 = "/1";
        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("param1", param1);
        // pathParamMap.put("param2", param2);

        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .basePath(basePath)
                .pathParams(pathParamMap)
                .pathParam("param3", param3)
                .when()
                .get()
                .then()
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

    @Test
    public void multipleBodyRestAssuredTest() {
        given()
                .baseUri("https://reqres.in/")
                .basePath("api/users/2")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
                .body("{\"name\":\"bob\",\"job\":\"leader\"}")
        .when()
                .put()
        .then()
                .body(Matchers.containsString("{\"name\":\"bob\",\"job\":\"leader\","));
    }

    @Test
    public void bodyAssertionRestAssuredTest() {
        given()
            .baseUri("https://fakerestapi.azurewebsites.net")
            .basePath("api/v1/Books/{id}")
            .pathParam("id", 1)
            .noContentType()
            .contentType(ContentType.JSON)
            .contentType(ContentType.TEXT)
        .when()
            .get()
        .then()
            .body("$", Matchers.hasKey("id"))
            .statusCode(200);
    }

    @Test
    public void testMultipleContentTypesRestAssuredTest() {
        given()
                .baseUri("https://reqres.in/")
                .basePath("api/users/2")
                .contentType(ContentType.JSON)
                .contentType(ContentType.TEXT)
                .noContentType()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
        .when()
                .put()
                .then()
                .body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
                .statusCode(200);
    }
    @Test
    public void testHeaderRestAssuredTest() {
        given()
            .baseUri("https://reqres.in/")
            .basePath("api/users/2")
            .header("Content-Type", ContentType.JSON, ContentType.JSON)
            .header("Content-Type", ContentType.TEXT)
            .header("Content-Type", ContentType.JSON)
            .header("Content-Type", ContentType.TEXT)
            .header("Content-Type", ContentType.JSON)
            .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
        .when()
            .put()
        .then()
            .body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
            .statusCode(200);
    }
    @Test
    public void testHeader2RestAssuredTest() {
        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/booking1.json");
        given()
            .baseUri("https://restful-booker.herokuapp.com")
            .basePath("/booking/1")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
            .body(file)
        .when()
            .put()
        .then()
            .statusCode(200);
    }
}
