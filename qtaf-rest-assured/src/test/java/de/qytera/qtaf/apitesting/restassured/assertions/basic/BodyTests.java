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
public class BodyTests extends QtafTestNGContext implements ApiTest {
    String url = "https://fakerestapi.azurewebsites.net/";
    @Test(testName = "Test statusCodeIs(200) -> expect a logMessage that indicates PASSED")
    public void testBody() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/api/v1/Books/{id}"),
                        pathParam("id", 1)
                ),
                getRequest(),
                List.of(
                        body(hasKey("id"))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "LogMessage",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiAssertionMessageFitsTo(
                "AssertionLogMessage 0",
                getAssertionMessagesFormApiLogMessage(latestApiLogMessage).get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                hasKey("id"),
                200,
                LogMessage.Status.PASSED
        );
    }
}
