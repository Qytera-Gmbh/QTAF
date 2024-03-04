package de.qytera.qtaf.apitesting.restassured.preconditions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Header Precondition Tests", description = "Check the header precondition methods")
public class HeaderTests extends QtafTestNGContext implements ApiTest {

    // ====== ContentType ======
    @Test(testName = "test contentType(ContentType) -> PASSED")
    public void testContentTypePASSED() {
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
                "{\"name\":\"morpheus\",\"job\":\"leader\"}"
        );
        ArrayList<String> expectedContentTypes = new ArrayList<>();
        expectedContentTypes.add(ContentType.JSON.toString());
        apiLogMessageContentTypeFitsTo(
                "",
                latestApiLogMessage,
                expectedContentTypes
        );
    }

    @Test(testName = "test contentType(ContentType) wrong contentType -> FAILED")
    public void testContentTypeFAILED() {
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.TEXT),
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
                LogMessage.Status.FAILED,
                1,
                ApiLogMessage.Action.RequestType.PUT,
                200
        );
        apiAssertionMessageFitsTo(
                "",
                latestApiLogMessage.getAssertions().get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "a string containing \"{\\\"name\\\":\\\"morpheus\\\",\\\"job\\\":\\\"leader\\\",\"",
                LogMessage.Status.FAILED
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}"
        );
        ArrayList<String> expectedContentTypes = new ArrayList<>();
        expectedContentTypes.add(ContentType.TEXT.toString());
        apiLogMessageContentTypeFitsTo(
                "",
                latestApiLogMessage,
                expectedContentTypes
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "test contentType(ContentType) with multiple contentTypes (first correct second incorrect than remove them and add the correct one) -> PASSED")
    public void testMultipleContentTypes1PASSED() {
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON),
                        contentType(ContentType.TEXT),
                        removeContentType(),
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
        apiAssertionMessageFitsTo(
                "",
                latestApiLogMessage.getAssertions().get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "a string containing \"{\\\"name\\\":\\\"morpheus\\\",\\\"job\\\":\\\"leader\\\",\"",
                LogMessage.Status.PASSED
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}"
        );
        ArrayList<String> expectedContentTypes = new ArrayList<>();
        expectedContentTypes.add(ContentType.JSON.toString());
        apiLogMessageContentTypeFitsTo(
                "",
                latestApiLogMessage,
                expectedContentTypes
        );
    }

    @Test(testName = "test contentType(String contentType) -> PASSED")
    public void testMultipleContentTypeStringPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON.toString()),
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
        apiAssertionMessageFitsTo(
                "",
                latestApiLogMessage.getAssertions().get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "a string containing \"{\\\"name\\\":\\\"morpheus\\\",\\\"job\\\":\\\"leader\\\",\"",
                LogMessage.Status.PASSED
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}"
        );
        ArrayList<String> expectedContentTypes = new ArrayList<>();
        expectedContentTypes.add(ContentType.JSON.toString());
        apiLogMessageContentTypeFitsTo(
                "",
                latestApiLogMessage,
                expectedContentTypes
        );
    }
    @Test(testName = "test contentType(String contentType) with multiple times the same correct contentType -> PASSED")
    public void testMultipleContentTypeMixedPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in/"),
                        basePath("api/users/2"),
                        contentType(ContentType.JSON.toString()),
                        contentType(ContentType.JSON),
                        contentType(ContentType.JSON.toString()),
                        contentType(ContentType.JSON.toString()),
                        contentType(ContentType.JSON),
                        contentType(ContentType.JSON),
                        removeContentType(),
                        contentType(ContentType.JSON),
                        contentType(ContentType.JSON.toString()),
                        contentType(ContentType.JSON.toString()),
                        contentType(ContentType.JSON),
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
        apiAssertionMessageFitsTo(
                "",
                latestApiLogMessage.getAssertions().get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                latestApiLogMessage.getResponse().getBodyAsString(),
                "a string containing \"{\\\"name\\\":\\\"morpheus\\\",\\\"job\\\":\\\"leader\\\",\"",
                LogMessage.Status.PASSED
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}"
        );
        ArrayList<String> expectedContentTypes = new ArrayList<>();
        expectedContentTypes.add(ContentType.JSON.toString());
        expectedContentTypes.add(ContentType.JSON.toString());
        expectedContentTypes.add(ContentType.JSON.toString());
        expectedContentTypes.add(ContentType.JSON.toString());
        expectedContentTypes.add(ContentType.JSON.toString());
        apiLogMessageContentTypeFitsTo(
                "",
                latestApiLogMessage,
                expectedContentTypes
        );
    }

    // ====== Headers ======

    @Test(testName = "test header(String, String) with multiple header values -> PASSED")
    public void testMultipleHeaderPASSED() {
        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/booking1.json");
        apiTest(
                this,
                List.of(
                        baseUri("https://restful-booker.herokuapp.com"),
                        basePath("/booking/1"),
                        header("Content-Type", "application/json"),
                        header("Accept", "application/json"),
                        header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM="),
                        body(file)
                ),
                putRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );
    }
}
