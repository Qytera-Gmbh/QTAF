package de.qytera.qtaf.apitesting.restassured.assertions;


import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;
import java.util.List;

public class ApiTestAssertionTests extends QtafTestNGContext implements ApiTest {

    @Test
    public void test() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot(404)
                )
        );
    }

    String url = "https://jsonplaceholder.typicode.com";


    // ========== BODY ==========

    @Test
    public void bodyIsAssertionTestPass() {

        ExecutedApiTest executedApiTest = apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot(404)
                )
        );
        System.out.println(executedApiTest.getRes().body().asString());
    }


    // ========== STATUS CODE ==========

    @Test
    public void statusCodeIsAssertionTestFail() {
        ExecutedApiTest executedApiTest = apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0)
                )
        );
        ApiLogMessage currentApiLogMessage = getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(this));
        assertEquals(currentApiLogMessage.getResponse().getStatusCode(), 404);
        changeApiLogMessageStatusFromFailedToPassed(currentApiLogMessage);
    }

    @Test
    public void statusCodeIsAssertionTestPass() {
        ExecutedApiTest executedApiTest = apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(404)
                )
        );
    }

    @Test
    public void statusCodeIs1xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIsNot1xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIs2xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIsNot2xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIsNotAssertionTest() {
        // TODO
    }


    // ========== TIME ==========
}
