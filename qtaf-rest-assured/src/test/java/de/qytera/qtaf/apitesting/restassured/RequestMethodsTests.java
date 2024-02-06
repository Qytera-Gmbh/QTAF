package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

public class RequestMethodsTests extends QtafTestNGContext implements ApiTest {

    private static String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "Test GET Route")
    public void testGetRequest() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/albums/1"),
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

    @Test(testName = "Test POST Route")
    public void testPostRequest() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        body("""
                              {"userId": "1", "title": "Lorem Ipsum"}
                                """)
                ),
                postRequest("/albums"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIs2xx(),
                        statusCodeIsNot(200),
                        statusCodeIs(201),
                        statusCodeIsNot3xx(),
                        statusCodeIsNot4xx(),
                        statusCodeIsNot5xx()
                )
        );
    }

    @Test(testName = "Test POST Route with invalid JSON")
    public void testPostRequestWithInvalidJSON() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        header("content-type", "application/json"),
                        body("""
                              {"userId": , "title": "Lorem Ipsum"}
                                """)
                ),
                postRequest("/albums"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIsNot2xx(),
                        statusCodeIsNot(201),
                        statusCodeIsNot3xx(),
                        statusCodeIsNot4xx(),
                        statusCodeIs5xx(),
                        statusCodeIs(500)
                )
        );
    }

    @Test(testName = "Test PUT Route")
    public void testPutRequest() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        body("""
                              {"userId": "1", "title": "Lorem Ipsum"}
                                """)
                ),
                putRequest("/albums/1"),
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

    @Test(testName = "Test DELETE Route")
    public void testDeleteRequest() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url)
                ),
                deleteRequest("/albums/1"),
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
}