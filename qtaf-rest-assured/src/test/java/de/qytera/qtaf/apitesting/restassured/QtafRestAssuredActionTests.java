package de.qytera.qtaf.apitesting.restassured;

import com.google.gson.JsonObject;
import de.qytera.qtaf.apitesting.Api;
import de.qytera.qtaf.apitesting.action.ApiActions;
import de.qytera.qtaf.apitesting.request.RequestSpecifications;
import de.qytera.qtaf.apitesting.response.ResponseAssertions;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
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
    String uriString = "/users/1";
    String expectedResponseBody = "{\n  \"id\": 1,\n  \"name\": \"Leanne Graham\",\n  \"username\": \"Bret\",\n  \"email\": \"Sincere@april.biz\",\n  \"address\": {\n    \"street\": \"Kulas Light\",\n    \"suite\": \"Apt. 556\",\n    \"city\": \"Gwenborough\",\n    \"zipcode\": \"92998-3874\",\n    \"geo\": {\n      \"lat\": \"-37.3159\",\n      \"lng\": \"81.1496\"\n    }\n  },\n  \"phone\": \"1-770-736-8031 x56442\",\n  \"website\": \"hildegard.org\",\n  \"company\": {\n    \"name\": \"Romaguera-Crona\",\n    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n    \"bs\": \"harness real-time e-markets\"\n  }\n}";

    @Test
    public void getReqeustsUriTests() throws URISyntaxException {
        URI uri = new URI(uriString);
        Api.test(
                this,
                List.of(
                        baseUri(urlString)
                ),
                getRequest(uri),
                List.of(
                        body(Matchers.hasToString(expectedResponseBody)
                ))
        );
    }

    @Test
    public void getReqeustsUrlTests() throws MalformedURLException {

        URL url = new URL(urlString+ uriString);
        Api.test(
                this,
                List.of(),
                getRequest(url),
                List.of(
                        body(Matchers.hasToString(expectedResponseBody)
                        ))
        );
    }


    @Test
    public void QtafApiTeststatusCode() {
        Api.test(
                this,
                List.of(baseUri(urlString)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0)
                )
        );
    }
}
