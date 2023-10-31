package de.qytera.qtaf.apitesting.restassured;

import static de.qytera.qtaf.apitesting.restassured.QtafRestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;


public class QtafRestAssuredTest {
    String url = "https://jsonplaceholder.typicode.com";
    @Test
    public void qtafRestAsseuredGetTest() {
        String requestUrl = "/users/1";
        System.out.println(get(url + requestUrl).body().asString());
        get(url + requestUrl);
    }
    @Test
    public void basicQtafRestAssuredTest() {

        /*
        String requestUrl = "/users/1";
        System.out.println(get(url + requestUrl).body().asString());
        get(url + requestUrl);

        int statusCode = get(url + requestUrl).statusCode();
         */

        when().
                get(url + "/user/1").
        then().
                statusCode(404);

        /*
        Response response = get(url + requestUrl);

        int statusCode = response.statusCode();

        System.out.println(statusCode);
        when().
                get(url + "/user/1").
                then().
                statusCode(404);

         */
    }
}
