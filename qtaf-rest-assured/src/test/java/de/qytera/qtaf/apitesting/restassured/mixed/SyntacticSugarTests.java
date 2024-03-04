package de.qytera.qtaf.apitesting.restassured.mixed;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.*;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.getAssertionMessagesFormApiLogMessage;

@TestFeature(name = "Status Code Assertion Tests", description = "Check the status code assertion methods")
public class SyntacticSugarTests extends QtafTestNGContext implements ApiTest {

    private static final String url = "https://jsonplaceholder.typicode.com";
    @Test(testName = "Test statusCodeIs(200) -> expect a logMessage that indicates PASSED")
    public void testStatusCodeIsPASSED() {
        apiTest(
                this,
                specifyRequest(
                        baseUri(url),
                        basePath("/users/1")
                ),
                getRequest(),
                specifyAssertions(
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
}
