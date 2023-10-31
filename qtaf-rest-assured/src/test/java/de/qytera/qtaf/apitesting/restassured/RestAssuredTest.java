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

        String requestUrl = "/user/1";
        System.out.println(get(url + requestUrl).body().asString());

        Response response = get(url + requestUrl);

        int statusCode = response.statusCode();

        System.out.println(statusCode);
        when().
                get(url + "/user/1").
        then().
                statusCode(404);
    }
}
