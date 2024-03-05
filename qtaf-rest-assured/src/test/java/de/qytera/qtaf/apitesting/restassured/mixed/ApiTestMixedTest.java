package de.qytera.qtaf.apitesting.restassured.mixed;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import de.qytera.qtaf.apitesting.ApiTest;


import com.google.gson.JsonObject;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiTestMixedTest extends QtafTestNGContext implements ApiTest {

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


        ExtractableResponse<Response> response = apiTest(
                this,
                List.of(header("content-type", "application/json; charset=utf-8"),
                        header("Testheader", "Headertest"),
                        baseUri(url),
                        basePath("/posts/1"),
                        json(body)
                        ),
                putRequest(),
                List.of(statusCodeIs(200))
        ).getRes();

        System.out.println(response.asString());

    }

    @Test(enabled = false)
    public void QtafApiTestPutMethode2() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "1");

        Map<String, Object> headers = new HashMap<>();
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


        QueryableRequestSpecification req = apiTest(
                this,
                List.of(header("content-type", "application/json; charset=utf-8"),
                        header("Testheader", "Headertest"),
                        baseUri(url),
                        basePath("/posts/1"),
                        pathParams(params),
                        json(body)
                ),
                putRequest(),
                List.of(statusCodeIs(200))
        ).getReq();

        System.out.println(req.getBasePath());
    }

    @Test
    public void QtafApiTestStatusCodeAndResponseTime() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/user/1")),
                getRequest(),
                List.of(
                        statusCodeIs(404),
                        responseTimeShouldBeLessThanXMilliseconds(2000)
                )
        );
    }

    @Test
    public void QtafApiTestStatusCodeXx() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/user/1")),
                getRequest(),
                List.of(
                        statusCodeIs4xx(),
                        statusCodeIsNot2xx()
                )
        );
    }

    @Test
    public void QtafApiTestgetUserOne() {
        ExtractableResponse<Response> response = apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        statusCodeIsNot4xx()
                )
        ).getRes();

        System.out.println(response.asString());
        System.out.println(response.statusCode());
    }

    @Test
    public void QtafApiTestResponse() {
        ExtractableResponse<Response> response = apiTest(
                this,
                List.of(baseUri(url), basePath("/user/1")),
                getRequest(),
                List.of(
                        statusCodeIs4xx(),
                        statusCodeIsNot2xx()
                )
        ).getRes();
        response.body();

        System.out.println(response.asString());
    }

    @Test
    public void QtafApiTeststatusCodeFailed() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/user/1")),
                getRequest(),
                List.of(
                        statusCodeIs(0)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(this));
        assertEquals(latestApiLogMessage.getResponse().getStatusCode(), 404);
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test
    public void QtafApiTeststatusCodePassed() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/users/1")
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );
    }
}
