package de.qytera.qtaf.apitesting.restassured;

import com.google.gson.JsonObject;
import de.qytera.qtaf.apitesting.Api;
import de.qytera.qtaf.apitesting.action.ApiActions;
import de.qytera.qtaf.apitesting.request.RequestSpecifications;
import de.qytera.qtaf.apitesting.response.ResponseAssertions;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import org.asynchttpclient.uri.Uri;
import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QtafRestAssuredActionTests  extends QtafTestNGContext implements RequestSpecifications, ApiActions, ResponseAssertions {

    String urlString = "https://jsonplaceholder.typicode.com";
    String uriStringGetEndpoint = "/users/1";
    String uriStringPostEndpoint = "/posts";
    String expectedResponseBodyGet = "{\n  \"id\": 1,\n  \"name\": \"Leanne Graham\",\n  \"username\": \"Bret\",\n  \"email\": \"Sincere@april.biz\",\n  \"address\": {\n    \"street\": \"Kulas Light\",\n    \"suite\": \"Apt. 556\",\n    \"city\": \"Gwenborough\",\n    \"zipcode\": \"92998-3874\",\n    \"geo\": {\n      \"lat\": \"-37.3159\",\n      \"lng\": \"81.1496\"\n    }\n  },\n  \"phone\": \"1-770-736-8031 x56442\",\n  \"website\": \"hildegard.org\",\n  \"company\": {\n    \"name\": \"Romaguera-Crona\",\n    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n    \"bs\": \"harness real-time e-markets\"\n  }\n}";

    @Test
    public void getReqeustsUriTest() throws URISyntaxException {
        URI uri = new URI(uriStringGetEndpoint);
        Api.test(
                this,
                List.of(
                        baseUri(urlString)

                ),
                getRequest(uri),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                ))
        );
    }

    @Test
    public void getReqeustsUrlTest() throws MalformedURLException {

        URL url = new URL(urlString + uriStringGetEndpoint);
        Api.test(
                this,
                List.of(),
                getRequest(url),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                        ))
        );
    }
    @Test
    public void getRequestPathParamsWithoutBasePathTest() {
        Api.test(
                this,
                List.of(),
                getRequest(urlString + "/{type}/{id}", "users", "1"),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                        ))
        );
    }

    @Test
    public void getRequestPathParamsWithtBasePathTest() {
        Api.test(
                this,
                List.of(basePath(urlString)),
                getRequest("/{type}/{id}", "users", "1"),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                        ))
        );
    }

    @Test
    public void getRequestPathParamsWithtBasePathAndFullPathTest() {
        Api.test(
                this,
                List.of(basePath(urlString)),
                getRequest(urlString + "/{type}/{id}", "users", "1"),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                        ))
        );
    }

    @Test
    public void postRequestUriTest() throws URISyntaxException {

        //TODO: POST not working
        /*
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("title", "foo");
        requestBody.addProperty("body", "bar");
        requestBody.addProperty("userId", 1);

         */

        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");

        String jsonString = "{\"title\" : \"foo\",\"body\" : \"bar\"}";

        Header header = new Header("Content-type", "application/jason; charset=UTF-8");

        URI uri = new URI(uriStringPostEndpoint);
        Api.test(
                this,
                List.of(basePath(urlString),
                        header(header),
                        // body(requestBody.toJSONString()),
                        // json(requestBody)
                        contentType(ContentType.JSON)
                        //body(jsonString)
                ),
                postRequest(uri),
                List.of(
                        // body(Matchers.hasToString(expectedResponseBody))
                        )
        );
    }

    @Test
    public void postRequestUrlTest() throws MalformedURLException {
        URL url = new URL(urlString+uriStringPostEndpoint);
        Header header = new Header("Content-type", "application/json; charset=UTF-8");
        Api.test(
                this,
                List.of(header(header)),
                postRequest(url),
                List.of(
                        // body(Matchers.hasToString(expectedResponseBodyGet))
                        )
        );
    }
}
