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
@TestFeature(name = "HEAD Request Tests", description = "Check the head request methods")
public class HeadTest extends QtafTestNGContext implements ApiTest {

    String url = "https://jsonplaceholder.typicode.com";
    @Test(testName = "test headRequest() -> PASSED")
    public void testHeadRequestPASSED() {

        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/users/1")),
                headRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                "HEAD",
                200
        );
    }

    @Test(testName = "test headRequest()with wrong path but no assertion -> PASSED")
    public void testHeadRequestPASSEDWrongPathNoAssertionsPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/user/1")),
                headRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                "HEAD",
                404
        );
    }

    @Test(testName = "test headRequest() not path -> PENDING")
    public void testHeadRequestNoPathPASSED() {

        try {
            apiTest(
                    this,
                    List.of(),
                    headRequest(),
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
                null,
                0
        );
    }
}