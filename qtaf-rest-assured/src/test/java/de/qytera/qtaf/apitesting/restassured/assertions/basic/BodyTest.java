package de.qytera.qtaf.apitesting.restassured.assertions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;
import static org.hamcrest.Matchers.*;

@TestFeature(name = "Body Assertion Tests", description = "Check the body assertion methods")
public class BodyTest extends QtafTestNGContext implements ApiTest {
    String url = "https://fakerestapi.azurewebsites.net/";
    @Test(testName = "Test body(path, hasKey()) -> PASSED")
    public void testBodyHasKey() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/api/v1/Books/{id}"),
                        pathParam("id", 1)
                ),
                getRequest(),
                List.of(
                        body("$", hasKey("id"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "LogMessage",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                "GET",
                200
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "$: " + hasKey("id"),
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test body(path, hasKey()) -> PASSED")
    public void testBodyMultipleHasKey() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/api/v1/Books/{id}"),
                        pathParam("id", 1)
                ),
                getRequest(),
                List.of(
                        body("$", hasKey("id")),
                        body("$", hasKey("title"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "LogMessage",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                2,
                "GET",
                200
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "$: " + hasKey("id"),
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "$: " + hasKey("title"),
                LogMessage.Status.PASSED
        );
    }

    @Test(testName = "Test body() with multiple matchers -> PASSED")
    public void testBodyMultipleMatchers() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/api/v1/Books/{id}"),
                        pathParam("id", 1)
                ),
                getRequest(),
                List.of(
                        body("$", hasKey("id")),
                        body("$", hasKey("title")),
                        body("title" , equalTo("Book 1")),
                        body("pageCount" , lessThan(1000))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "LogMessage",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                4,
                "GET",
                200
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "$: " + hasKey("id"),
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "$: " + hasKey("title"),
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(2),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "title: " + equalTo("Book 1"),
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(3),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "pageCount: " + lessThan(1000),
                LogMessage.Status.PASSED
        );
    }
}
