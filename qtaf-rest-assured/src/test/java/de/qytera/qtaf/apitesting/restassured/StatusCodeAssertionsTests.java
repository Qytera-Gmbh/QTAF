package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

/**
 * This class contains tests for the status code assertions of QTAF API Tests
 */
public class StatusCodeAssertionsTests extends QtafTestNGContext implements ApiTest {
    private static String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "Test GET 200 Route")
    public void testStatusCodeIs200() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIs2xx(),
                        statusCodeIs(200),
                        statusCodeIsNot(201),
                        statusCodeIsNot3xx(),
                        statusCodeIsNot4xx(),
                        statusCodeIsNot5xx()
                )
        );
    }

    @Test(testName = "Test GET 404 Route")
    public void testStatusCodeIs404() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/xxx"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIsNot2xx(),
                        statusCodeIsNot3xx(),
                        statusCodeIs4xx(),
                        statusCodeIsNot(401),
                        statusCodeIs(404),
                        statusCodeIsNot5xx()
                )
        );
    }

}
