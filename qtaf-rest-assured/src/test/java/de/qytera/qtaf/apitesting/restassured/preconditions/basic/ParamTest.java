package de.qytera.qtaf.apitesting.restassured.preconditions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.hamcrest.Matchers;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.*;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Time Assertion Tests", description = "Check the time assertion methods")
public class ParamTest extends QtafTestNGContext implements ApiTest {

    // ====== formParams ======

    @Test(testName = "GIVEN: formParam(String, String) -> WHEN: correct POST-request -> THEN: formParam in LogMessage has one entry as expected")
    public void testFormParam() {
        apiTest(
                this,
                specifyRequest(
                        baseUri("https://jsonplaceholder.typicode.com"),
                        basePath("/posts"),
                        contentType("application/x-www-form-urlencoded;charset=utf-8"),
                        header("accept", "application/json"),
                        formParam("zone", "computer")
                ),
                postRequest(),
                specifyAssertions(
                        statusCodeIs(201),
                        body(Matchers.containsString("{\n  \"zone\": \"computer\",\n  \"id\": 101\n}"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                2,
                ApiLogMessage.Action.RequestType.POST,
                201
        );
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("zone", "computer");
        apiLogMessageFormParamsFitsTo(
                "",
                latestApiLogMessage,
                formParams
        );
    }

    @Test(testName = "GIVEN: the same formParam(String, String) twice -> WHEN: correct POST-request -> THEN: formParam in LogMessage has just one entry as expected") @Ignore
    public void testDuplicatedFormParams() {
        apiTest(
                this,
                specifyRequest(
                        baseUri("https://jsonplaceholder.typicode.com"),
                        basePath("/posts"),
                        contentType("application/x-www-form-urlencoded;charset=utf-8"),
                        header("accept", "application/json"),
                        formParam("zone", "computer"),
                        formParam("zone", "computer")
                ),
                postRequest(),
                specifyAssertions(
                        statusCodeIs(201),
                        body(Matchers.containsString("{\n  \"zone\": \"computer\",\n  \"id\": 101\n}"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                2,
                ApiLogMessage.Action.RequestType.POST,
                201
        );
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("zone", "computer");
        apiLogMessageFormParamsFitsTo(
                "",
                latestApiLogMessage,
                formParams
        );
    }

    @Test(testName = "GIVEN: multiple formParam(String, String) -> WHEN: correct POST-request -> THEN: formParam in LogMessage has multiple entries as expected")
    public void testMultipleFormParam() {
        apiTest(
                this,
                specifyRequest(
                        baseUri("https://jsonplaceholder.typicode.com"),
                        basePath("/posts"),
                        contentType("application/x-www-form-urlencoded;charset=utf-8"),
                        header("accept", "application/json"),
                        formParam("zone", "computer"),
                        formParam("bob", "alice")
                ),
                postRequest(),
                specifyAssertions(
                        statusCodeIs(201),
                        body(Matchers.containsString("{\n  \"zone\": \"computer\",\n  \"bob\": \"alice\",\n  \"id\": 101\n}"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                2,
                ApiLogMessage.Action.RequestType.POST,
                201
        );
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("zone", "computer");
        formParams.put("bob", "alice");
        apiLogMessageFormParamsFitsTo(
                "",
                latestApiLogMessage,
                formParams
        );
    }

    @Test(testName = "GIVEN: multiple formParam(Map<String, Object> params) -> WHEN: correct POST-request -> THEN: formParam in LogMessage has multiple entries as expected")
    public void testMultipleFormParam2() {

        HashMap<String, Object> formParams = new HashMap<>();

        formParams.put("zone", "computer");
        formParams.put("bob", "alice");

        apiTest(
                this,
                specifyRequest(
                        baseUri("https://jsonplaceholder.typicode.com"),
                        basePath("/posts"),
                        contentType("application/x-www-form-urlencoded;charset=utf-8"),
                        header("accept", "application/json"),
                        formParams(formParams)
                ),
                postRequest(),
                specifyAssertions(
                        statusCodeIs(201),
                        body(Matchers.containsString("{\n  \"bob\": \"alice\",\n  \"zone\": \"computer\",\n  \"id\": 101\n}"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                2,
                ApiLogMessage.Action.RequestType.POST,
                201
        );

        apiLogMessageFormParamsFitsTo(
                "",
                latestApiLogMessage,
                formParams
        );
    }
}
