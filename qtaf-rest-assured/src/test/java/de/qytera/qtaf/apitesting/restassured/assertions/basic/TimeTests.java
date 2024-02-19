package de.qytera.qtaf.apitesting.restassured.assertions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Time Assertion Tests", description = "Check the time assertion methods")
public class TimeTests extends QtafTestNGContext implements ApiTest {

    String url = "https://jsonplaceholder.typicode.com";


    @Test(testName = "Test responseTimeShouldBeLessThanXMilliseconds() -> expect a logMessage that indicates PASSED")
    public void testResponseTimeShouldBeLessThanXMillisecondsPASSED() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        responseTimeShouldBeLessThanXMilliseconds(10000)
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
                latestApiLogMessage.getResponse().getTime(),
                10000L,
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test responseTimeShouldBeLessThanXMilliseconds() -> expect a logMessage that indicates FAILED")
    public void testResponseTimeShouldBeLessThanXMillisecondsFAILED() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        responseTimeShouldBeLessThanXMilliseconds(0)
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
                latestApiLogMessage.getResponse().getTime(),
                0L,
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test responseTimeShouldBeLessThanXMilliseconds() exploration test -> expect a logMessage that indicates PASSED")
    public void testResponseTimeShouldBeLessThanXMillisecondsExploration1PASSED() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        responseTimeShouldBeLessThanXMilliseconds(10000),
                        statusCodeIs(200),
                        responseTimeShouldBeLessThanXMilliseconds(10001),
                        statusCodeIsNot(404)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                4,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getTime(),
                10000L,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                200,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(2),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getTime(),
                10001L,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(3),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                404,
                LogMessage.Status.PASSED
        );
    }
    // This test case indicates that there cloud be a bug
    @Test(testName = "Test responseTimeShouldBeLessThanXMilliseconds() exploration test -> expect a logMessage that indicates FAILED")
    public void testResponseTimeShouldBeLessThanXMillisecondsExploration1FAILED() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        responseTimeShouldBeLessThanXMilliseconds(0),
                        statusCodeIs(200),
                        responseTimeShouldBeLessThanXMilliseconds(10001),
                        statusCodeIsNot(404)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                4,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getTime(),
                0L,
                LogMessage.Status.FAILED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                200,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(2),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getTime(),
                10001L,
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(3),
                AssertionLogMessageType.ASSERT_NOT_EQUALS,
                200,
                404,
                LogMessage.Status.PASSED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test responseTimeShouldBeLessThanXMilliseconds() exploration test -> expect a logMessage that indicates PASSED")
    public void testResponseTimeShouldBeLessThanXMillisecondsExploration2FAILED() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        statusCodeIs(2001),
                        statusCodeIs(200)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                2,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                2001,
                LogMessage.Status.FAILED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                200,
                200,
                LogMessage.Status.PASSED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test responseTimeShouldBeLessThanXMilliseconds() exploration test -> expect a logMessage that indicates PASSED")
    public void testResponseTimeShouldBeLessThanXMillisecondsExploration3PASSED() {
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        responseTimeShouldBeLessThanXMilliseconds(0),
                        responseTimeShouldBeLessThanXMilliseconds(10000)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                2,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getTime(),
                0L,
                LogMessage.Status.FAILED
        );
        apiAssertionMessageFitsTo(
                "",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getTime(),
                10000L,
                LogMessage.Status.PASSED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }
}
