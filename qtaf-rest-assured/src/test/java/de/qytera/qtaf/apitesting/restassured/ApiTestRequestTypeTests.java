package de.qytera.qtaf.apitesting.restassured;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

import de.qytera.qtaf.apitesting.ApiTest;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.Header;
import org.hamcrest.Matchers;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class ApiTestRequestTypeTests extends QtafTestNGContext implements ApiTest {

    String urlString = "https://jsonplaceholder.typicode.com";

    @Test
    public void quickTest() {

        apiTest(
                this,
                List.of(
                        baseUri(urlString + uriStringHead)
                ),
                headRequest(),
                List.of()
        );
    }




    // ========== HEAD ==========
    String uriStringHead = "/users/1";
    @Test
    public void headRequestPassedTest() {
        TestSuiteLogCollection testSuiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        testSuiteLogCollection.getLogDirectory();

        apiTest(
                this,
                List.of(
                        baseUri(urlString + uriStringHead)
                ),
                headRequest(),
                List.of()
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.HEAD,
                200
        );
    }
    /*
    @Test
    public void headRequestFailedTest() {
        TestSuiteLogCollection testSuiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        testSuiteLogCollection.getLogDirectory();

        Api.test(
                this,
                List.of(
                        baseUri(urlString + uriStringHead)
                ),
                headRequest(),
                List.of(
                        statusCodeIs(202)
                )
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.FAILED,
                ApiLogMessage.Action.RequestType.HEAD
        );
    }
     */

    @Test
    public void headRequestUriTest() throws URISyntaxException {
        Header header = new Header("Content-type", "application/json; charset=UTF-8");
        URI uri = new URI(urlString + uriStringHead);

        apiTest(
                this,
                List.of(),
                headRequest(uri),
                List.of()
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.HEAD,
                200
        );
    }

    @Test
    public void headRequestUrlTest() throws MalformedURLException {
        Header header = new Header("Content-type", "application/json; charset=UTF-8");

        URL url = new URL(urlString + uriStringHead);

        apiTest(
                this,
                List.of(),
                headRequest(url),
                List.of()
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.HEAD,
                200
        );
    }


    @Test
    public void headRequestPathParamsTest() {
        Header header = new Header("Content-type", "application/json; charset=UTF-8");


        apiTest(
                this,
                List.of(),
                headRequest(urlString + "/{type}/{id}", "users", "1"),
                List.of()
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.HEAD,
                200
        );
    }

    // ========== OPTION ==========
    String uriStringOption = "/users/1";
    @Test
    public void optionRequestsTest() {
        apiTest(
            this,
            List.of(
                    baseUri(urlString)
            ),
            optionsRequest(),
            List.of()
        );
        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.OPTIONS,
                204
        );
    }

    @Test
    public void optionRequestsUriTest() throws URISyntaxException {
        URI uri = new URI(urlString + uriStringOption);
        apiTest(
                this,
                List.of(),
                optionsRequest(uri),
                List.of()
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.OPTIONS,
                204
        );
    }
    @Test
    public void optionRequestsUrlTest() throws MalformedURLException {
        URL url = new URL(urlString + uriStringOption);
        apiTest(
                this,
                List.of(),
                optionsRequest(url),
                List.of()
        );

        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.OPTIONS,
                204
        );
    }


    // ========== GET ==========

    String uriStringGet = "/users/1";
    String expectedResponseBodyGet = "{\n  \"id\": 1,\n  \"name\": \"Leanne Graham\",\n  \"username\": \"Bret\",\n  \"email\": \"Sincere@april.biz\",\n  \"address\": {\n    \"street\": \"Kulas Light\",\n    \"suite\": \"Apt. 556\",\n    \"city\": \"Gwenborough\",\n    \"zipcode\": \"92998-3874\",\n    \"geo\": {\n      \"lat\": \"-37.3159\",\n      \"lng\": \"81.1496\"\n    }\n  },\n  \"phone\": \"1-770-736-8031 x56442\",\n  \"website\": \"hildegard.org\",\n  \"company\": {\n    \"name\": \"Romaguera-Crona\",\n    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n    \"bs\": \"harness real-time e-markets\"\n  }\n}";

    @Test
    public void getRequestsTest() {
        apiTest(
                this,
                List.of(
                        baseUri(urlString)
                ),
                getRequest(uriStringGet),
                List.of()
        );
        ApiLogMessage filledOutApiLogMessage = getLogCollection().getLogMessages(ApiLogMessage.class).get(0);
        checkApiLogMessage(
                filledOutApiLogMessage,
                LogMessage.Status.PASSED,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
    }

    @Test
    public void getReqeustsUriTest() throws URISyntaxException {
        URI uri = new URI(uriStringGet);
        apiTest(
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

        URL url = new URL(urlString + uriStringGet);
        apiTest(
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
        apiTest(
                this,
                List.of(),
                getRequest(urlString + "/{type}/{id}", "users", "1"),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                        ))
        );
    }

    @Test @Ignore
    public void getRequestPathParamsWithtBasePathTest() {
        apiTest(
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
        apiTest(
                this,
                List.of(basePath(urlString)),
                getRequest(urlString + "/{type}/{id}", "users", "1"),
                List.of(
                        body(Matchers.hasToString(expectedResponseBodyGet)
                        ))
        );
    }

    // ========== POST ==========

    String uriStringPostEndpoint = "/posts";
    /*
    @Test
    public void postRequestUriTest() throws URISyntaxException {

     */

        //TODO: POST not working
        /*
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("title", "foo");
        requestBody.addProperty("body", "bar");
        requestBody.addProperty("userId", 1);

         */
    /*
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


    }*/
    /*
    @Test
    public void postRequestUrlTest() throws MalformedURLException {
        // TODO
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

    @Test
    public void postRequestPathParamsTest() throws MalformedURLException {
        // TODO
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

    // ========== PUT ==========

    @Test
    public void putRequestTest() {
        // TODO
    }

    @Test
    public void putRequestUriTest() {
        // TODO
    }

    @Test
    public void putRequestUrlTest() {
        // TODO
    }

    @Test
    public void putRequestPathParamsTest() {
        // TODO
    }

    // ========== DELETE ==========

    @Test
    public void deleteRequestTest() {
        // TODO
    }

    @Test
    public void deleteRequestUriTest() {
        // TODO
    }

    @Test
    public void deleteRequestUrlTest() {
        // TODO
    }

    @Test
    public void deleteRequestPathParamsTest() {
        // TODO
    }

    */

    // ========== HELPER ==========

    public void checkApiLogMessage(
            ApiLogMessage filledOutApiLogMessage,
            LogMessage.Status apiLogMessageStatus,
            ApiLogMessage.Action.RequestType requestType,
            int statusCode
        )
    {
        assertEquals(filledOutApiLogMessage.getStatus(), apiLogMessageStatus);
        assertEquals(filledOutApiLogMessage.getAction().getRequestType(), requestType);
        assertEquals(filledOutApiLogMessage.getResponse().getStatusCode(), statusCode);
    }
}
