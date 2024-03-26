package de.qytera.qtaf.apitesting.restassured.requestTypes.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Put Request Tests", description = "Check the put request methods")
public class PutTest extends QtafTestNGContext implements ApiTest {
    String url = "https://jsonplaceholder.typicode.com";
    @Test(testName = "test putsRequest() -> PASSED")
    public void testPutRequest200PASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/users/1")),
                putRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                "PUT",
                200
        );
    }
    @Test(testName = "test putsRequest() with json -> PASSED")
    public void testPutRequestJsonPASSED() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "zion resident");
        JSONObject jsonObject = new JSONObject(jsonMap);
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("/api/users/2"),
                        contentType(ContentType.JSON),
                        body(jsonObject)),
                        // json(jsonObject)),
                putRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                "PUT",
                200
        );
    }

    @Test(testName = "test putRequest() with wrong path but no assertion -> PASSED")
    public void testPutRequestPASSEDWrongPathNoAssertions404PASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/user/1")),
                putRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                "PUT",
                404
        );
    }

    @Test(testName = "test putRequest() no path -> PENDING")
    public void testPutRequestNoPathPASSED() {
        try {
            apiTest(
                    this,
                    List.of(),
                    putRequest(),
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