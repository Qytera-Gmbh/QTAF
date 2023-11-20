package de.qytera.qtaf.apitesting.restassured;


import com.google.gson.JsonObject;
import de.qytera.qtaf.apitesting.Api;
import de.qytera.qtaf.apitesting.action.ApiActions;
import de.qytera.qtaf.apitesting.request.RequestSpecifications;
import de.qytera.qtaf.apitesting.response.ResponseAssertions;

import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QtafRestAssuredTest extends QtafTestNGContext implements RequestSpecifications, ApiActions, ResponseAssertions {

    String url = "https://jsonplaceholder.typicode.com";

    @Test
    public void QtafApiTestPutMethode() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("Testheader", "Headertest");

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("title", "foo");
        requestBody.addProperty("body", "bar");
        requestBody.addProperty("userId", 1);

        JSONObject body = new JSONObject();
        body.put("title", "foo");
        body.put("body", "bar");
        body.put("userId", 0);


        ExtractableResponse<Response> response = Api.test(
                this,
                List.of(headers(headers),
                        baseUri(url),
                        // basePath("/posts/{id}"),
                        // pathParam(params),
                        json(body)
                        ),
                putRequest("/posts/1"),
                // putRequest(),
                List.of(statusCodeIs(200))
        ).getRes();

        System.out.println(response.asString());

    }

    @Test
    public void QtafApiTestPutMethode2() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("Testheader", "Headertest");

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("title", "foo");
        requestBody.addProperty("body", "bar");
        requestBody.addProperty("userId", 1);

        JSONObject body = new JSONObject();
        body.put("title", "foo");
        body.put("body", "bar");
        body.put("userId", 0);


        QueryableRequestSpecification req = Api.test(
                this,
                List.of(headers(headers),
                        baseUri(url),
                        basePath("/posts/{id}"),
                        pathParam(params),
                        json(body)
                ),
                // putRequest(),
                putRequest("/posts/1"),
                List.of(statusCodeIs(200))
        ).getReq();

        System.out.println(req.getBasePath());
    }

    @Test
    public void QtafApiTestStatusCodeAndResponseTime() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(404),
                        responseTimeShouldBeLessThanXMilliseconds(2000)
                )
        );
    }

    @Test
    public void QtafApiTestStatusCodeXx() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeShouldIs4xx(),
                        statusCodeShouldIsNot2xx()
                )
        );
    }

    @Test
    public void QtafApiTestgetUserOne() {
        ExtractableResponse<Response> response = Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeShouldIsNot4xx()
                )
        ).getRes();

        System.out.println(response.asString());
        System.out.println(response.statusCode());
    }

    @Test
    public void QtafApiTestResponse() {
        ExtractableResponse<Response> response = Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeShouldIs4xx(),
                        statusCodeShouldIsNot2xx()
                )
        ).getRes();
        response.body();

        System.out.println(response.asString());
    }
}
