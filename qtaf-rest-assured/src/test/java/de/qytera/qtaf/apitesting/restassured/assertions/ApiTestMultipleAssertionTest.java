package de.qytera.qtaf.apitesting.restassured.assertions;

import de.qytera.qtaf.apitesting.ApiTest;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

public class ApiTestMultipleAssertionTest extends QtafTestNGContext implements ApiTest {

    String url = "https://jsonplaceholder.typicode.com";

    @Test
    public void test1() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(404)
                )
        );
    }

    @Test
    public void test2() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIsNot(0),
                        statusCodeIsNot(5)
                )
        );
    }

    @Test
    public void test3() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0),
                        statusCodeIs(404)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(this));
        List<AssertionLogMessage> assertionLogMessages = getAssertionMessagesFormApiLogMessage(latestApiLogMessage);
        assertEquals(assertionLogMessages.size(), 2);
        apiAssertionMessageFitsTo(
                "",
                assertionLogMessages.get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                false,
                404,
                0,
                LogMessage.Status.FAILED);
        apiAssertionMessageFitsTo(
                "",
                assertionLogMessages.get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                true,
                404,
                404,
                LogMessage.Status.PASSED);
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);

    }
}
