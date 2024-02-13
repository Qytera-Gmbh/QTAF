package de.qytera.qtaf.apitesting.restassured.assertions;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

/**
 * This class contains tests for the status code assertions of QTAF API Tests
 */
@TestFeature(name = "Status Code Assertion Tests", description = "Check the status code assertion methods")
public class StatusCode extends QtafTestNGContext implements ApiTest {

    private static String url = "https://jsonplaceholder.typicode.com";


    @Test(testName = "Test statusCodeIs(200) -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIs(200)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                200,
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test statusCodeIs(200) -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIsFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIs(201)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                201,
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }
    @Test(testName = "Test statusCodeIsNot(201) -> expect a logMessage that indicates PASSED")
    public void testNG() {
        Assert.assertNotEquals(200,200);
    }

    @Test(testName = "Test statusCodeIsNot(201) -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsNotPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot(201)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                201,
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test statusCodeIsNot(200) -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIsNotFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot(200)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                200,
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    // testStatusCodeIs1xxPASSED <- missing because the api does not return a 100 code

    @Test(testName = "Test statusCodeIs1xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIs1xxFAIL() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIs1xx()
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                "1xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test statusCodeIsNot1xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsNot1xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot1xx()
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "1xx",
                LogMessage.Status.PASSED
        );
    }

    // testStatusCodeIsNot1xxFAILED <- missing because the api does not return a 100 code

    @Test(testName = "Test statusCodeIs2xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIs2xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIs2xx()
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                "2xx",
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test statusCodeIs2xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIs2xxFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs2xx()
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                404
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                404,
                "2xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test statusCodeIsNot2xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsNot2xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIsNot2xx()
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                404
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                404,
                "2xx",
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test statusCodeIsNot2xx() -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIsNot2xxFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot2xx()
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "2xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }
















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

    @Test(testName = "Test GET 404 Route") @Ignore
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
