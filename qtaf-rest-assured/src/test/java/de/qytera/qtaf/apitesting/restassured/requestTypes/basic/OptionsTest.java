package de.qytera.qtaf.apitesting.restassured.requestTypes.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.ConnectException;
import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "OPTIONS Request Tests", description = "Check the options request methods")
public class OptionsTest extends QtafTestNGContext implements ApiTest {
    String url = "https://jsonplaceholder.typicode.com";
    @Test(testName = "test optionsRequest() -> PASSED")
    public void testOptionsRequestPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/users/1")),
                optionsRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.OPTIONS,
                204
        );
    }

    @Test(testName = "test optionsRequest() with wrong path but no assertion -> PASSED")
    public void testOptionsRequestPASSEDWrongPathNoAssertionsPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/user/1")),
                optionsRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.OPTIONS,
                204
        );
    }

    @Test(testName = "test optionsRequest() no path -> PENDING")
    public void testOptionsRequestNoPathPASSED() {

        try {
            apiTest(
                    this,
                    List.of(),
                    optionsRequest(),
                    List.of()
            );
        } catch (Exception e){
            Assert.assertTrue(e instanceof ConnectException);
        }

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PENDING,
                0,
                ApiLogMessage.Action.RequestType.OPTIONS,
                0
        );
    }
}