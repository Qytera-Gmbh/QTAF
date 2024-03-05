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

@TestFeature(name = "DELETE Request Tests", description = "Check the delete request methods")
public class DeleteTest extends QtafTestNGContext implements ApiTest {

    String url = "https://jsonplaceholder.typicode.com";
    @Test(testName = "test deleteRequest() -> PASSED")
    public void testDeleteRequestPASSED() {

        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/users/1")),
                deleteRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.DELETE,
                200
        );
    }

    @Test(testName = "test deleteRequest() with wrong path but no assertion -> PASSED")
    public void testDeleteRequestWrongPathNoAssertionsPASSED() {

        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/user/1")),
                deleteRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.DELETE,
                404
        );
    }

    @Test(testName = "test deleteRequest() 204 -> PASSED")
    public void testDeleteRequest204PASSED() {

        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("/api/users/2")
                ),
                deleteRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.DELETE,
                204
        );
    }

    @Test(testName = "test deleteRequest() not path -> PENDING")
    public void testDeleteRequestNoPathPASSED() {

        try {
            apiTest(
                    this,
                    List.of(),
                    deleteRequest(),
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
                ApiLogMessage.Action.RequestType.DELETE,
                0
        );
    }
}