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

@TestFeature(name = "POST Request Tests", description = "Check the post request methods")
public class PostTest extends QtafTestNGContext implements ApiTest {
    String url = "https://reqres.in";
    @Test(testName = "test postRequest() with wrong mediaFile-> PASSED")
    public void testPostRequest_serverReceivesAnUnexpectedMediaFileType_PASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/api/users")),
                postRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.POST,
                415
        );
    }

    @Test(testName = "test postRequest() with right mediaFile-> PASSED")
    public void testPostRequestPASSED() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "leader");

        JSONObject jsonObject = new JSONObject(jsonMap);
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/api/users"),
                        body(jsonObject),
                        contentType(ContentType.JSON)
                        //json(jsonObject)
                ),
                postRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.POST,
                201
        );
    }

    @Test(testName = "test postRequest() with wrong path but no assertion -> PASSED")
    public void testPostRequestPASSEDWrongPathNoAssertionsPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/user/1")),
                postRequest(),
                List.of()
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.POST,
                415
        );
    }

    @Test(testName = "test postRequest() no path -> PENDING")
    public void testPostRequestNoPathPASSED() {
        try {
            apiTest(
                    this,
                    List.of(),
                    postRequest(),
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
                ApiLogMessage.Action.RequestType.POST,
                0
        );
    }
}