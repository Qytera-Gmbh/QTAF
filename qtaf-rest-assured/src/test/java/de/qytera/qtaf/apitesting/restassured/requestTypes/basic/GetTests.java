package de.qytera.qtaf.apitesting.restassured.requestTypes.basic;

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

@TestFeature(name = "Body Assertion Tests", description = "Check the body assertion methods")
public class GetTests extends QtafTestNGContext implements ApiTest {
    String url = "https://jsonplaceholder.typicode.com";
    @Test(testName = "test getRequest() -> PASSED")
    public void testGetRequestPASSED() {

        apiTest(
                this,
                List.of(
                        baseUri(url + "/users/1")
                ),
                getRequest(),
                List.of()
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
    }

    @Test(testName = "test getRequest() -> FAILED")
    public void testGetRequestFAILED() {
        apiTest(
                this,
                List.of(
                        baseUri(url + "/user/1")
                ),
                getRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.GET,
                404
        );
    }

    @Test(testName = "test getRequest(uri) -> PASSED")
    public void testGetRequestUriPASSED() {

        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest(),
                List.of()
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
    }
}
