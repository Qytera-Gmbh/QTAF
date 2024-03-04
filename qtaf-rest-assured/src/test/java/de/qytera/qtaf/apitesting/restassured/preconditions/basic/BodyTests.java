package de.qytera.qtaf.apitesting.restassured.preconditions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

import org.hamcrest.Matchers.*;

@TestFeature(name = "Body Precondition Tests", description = "Check the body precondition methods")
public class BodyTests extends QtafTestNGContext implements ApiTest {

    @Test(testName = "test body(String body) -> PASSED")
    public void testBodyStringPASSED() {
        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON),
                        body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
                ),
                putRequest(),
                List.of(
                        body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.PUT,
                200
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}");
    }


    @Test(testName = "test body(Object object) -> PASSED")
    public void testBodyObjectPASSED() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "leader");
        JSONObject jsonObject = new JSONObject(jsonMap);
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON),
                        body(jsonObject)
                ),
                putRequest(),
                List.of(
                        body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.PUT,
                200
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}");
    }
    @Test(testName = "test body(File file) -> PASSED")
    public void testBodyFilePASSED() {
        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");



        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON),
                        body(file)
                ),
                putRequest(),
                List.of(
                       body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.PUT,
                200
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "Filepath: src\\test\\java\\de\\qytera\\qtaf\\apitesting\\restassured\\preconditions\\basic\\assets\\user1.json");
    }


    // The body should be set once.
    // If multiple methods are called that try to set a body then its expected
    // that the body gets reset according to the last call.

    @Test(testName = "test multiple body preconditions -> PASSED")
    public void testMultipleBodyMethods1PASSED() {

        String bodyString = "{\"name\":\"morpheus\",\"job\":\"leader\"}";

        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "leader");
        JSONObject jsonObject = new JSONObject(jsonMap);

        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON),
                        body(file),
                        body(jsonObject),
                        body(bodyString),
                        body(bodyString),
                        body(file),
                        body(jsonObject)
                ),
                putRequest(),
                List.of(
                        body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.PUT,
                200
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                bodyString
        );
    }

    @Test(testName = "test multiple body preconditions -> PASSED")
    public void testMultipleBodyMethods2PASSED() {

        String bodyString = "{\"name\":\"morpheus\",\"job\":\"leader\"}";

        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "leader");
        JSONObject jsonObject = new JSONObject(jsonMap);

        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        body(bodyString),
                        contentType(ContentType.JSON),
                        body(file),
                        body(jsonObject),
                        body(bodyString),
                        body(jsonObject),
                        body(file)
                ),
                putRequest(),
                List.of(
                        body(Matchers.containsString("{\"name\":\"morpheus\",\"job\":\"leader\","))
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                ApiLogMessage.Action.RequestType.PUT,
                200
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "Filepath: src\\test\\java\\de\\qytera\\qtaf\\apitesting\\restassured\\preconditions\\basic\\assets\\user1.json"
        );
    }
}
