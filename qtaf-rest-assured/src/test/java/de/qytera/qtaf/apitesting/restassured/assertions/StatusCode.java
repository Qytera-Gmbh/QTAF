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

    private static final String url = "https://jsonplaceholder.typicode.com";


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

    @Test(testName = "Test statusCodeIs2xx() -> expect a logMessage that indicates FAILED")
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

    // testStatusCodeIs3xxPASSED <- missing because the api does not return a 300 code

    @Test(testName = "Test statusCodeIs3xx() -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIs3xxFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIs3xx()
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
                "3xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test statusCodeIsNot3xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsNot3xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot3xx()
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
                "3xx",
                LogMessage.Status.PASSED
        );
    }


    // testStatusCodeIsNot3xxFAILED <- missing because the api does not return a 300 code

    @Test(testName = "Test statusCodeIs4xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIs4xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs4xx()
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
                AssertionLogMessageType.ASSERT_EQUALS,
                404,
                "4xx",
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test statusCodeIs4xx() -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIs4xxFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIs4xx()
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
                "4xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test statusCodeIsNot4xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsNot4xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot4xx()
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
                "4xx",
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test statusCodeIsNot4xx() -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIsNot4xxFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIsNot4xx()
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
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                404,
                "4xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    // testStatusCodeIs5xxPASSED <- missing because the api does not return a 500 code

    @Test(testName = "Test statusCodeIs5xx() -> expect a logMessage that indicates FAILED")
    public void testStatusCodeIs5xxFAILED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs5xx()
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
                "5xx",
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test statusCodeIsNot5xx() -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsNot5xxPASSED() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIsNot5xx()
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
                "5xx",
                LogMessage.Status.PASSED
        );
    }

    // testStatusCodeIs5xxPASSED <- missing because the api does not return a 300 code

    @Test(testName = "Tests multiple status code methods (GET 200) -> expects a logMessage that indicates PASSED")
    public void testStatusCodeIs200CombinedPASSED() {
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
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                7,
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
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                "2xx",
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(2),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                200,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(3),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                201,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(4),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "3xx",
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(5),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "4xx",
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(6),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "5xx",
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Tests multiple status code methods (GET 404) -> expects a logMessage that indicates FAILED")
    public void testStatusCodeIs404() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
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
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                7,
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
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "2xx",
                LogMessage.Status.FAILED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(2),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "3xx",
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(3),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                "4xx",
                LogMessage.Status.FAILED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(4),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                401,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(5),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                404,
                LogMessage.Status.FAILED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(6),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                "5xx",
                LogMessage.Status.PASSED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }
}
