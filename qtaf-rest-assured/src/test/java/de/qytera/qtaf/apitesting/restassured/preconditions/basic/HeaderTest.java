package de.qytera.qtaf.apitesting.restassured.preconditions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.*;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Header Precondition Tests", description = "Check the header precondition methods")
public class HeaderTest extends QtafTestNGContext implements ApiTest {

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
                "PUT",
                200
        );
        apiLogMessageRequestBodyFitsTo(
                "",
                latestApiLogMessage,
                "{\"name\":\"morpheus\",\"job\":\"leader\"}"
        );
        String expectedContentTypes = ContentType.JSON.toString();
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
                "PUT",
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
        String expectedContentTypes = "text/plain; charset=ISO-8859-1";
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
                "PUT",
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
        String expectedContentTypes = ContentType.JSON.toString();

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
                "PUT",
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
        String expectedContentTypes = ContentType.JSON.toString();
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
                        contentType("charset=ISO-8859-1"),
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
                "PUT",
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
        String expectedContentTypes = "application/json";

        apiLogMessageContentTypeFitsTo(
                "",
                latestApiLogMessage,
                expectedContentTypes
        );
    }

    // ====== Headers ======

    @Test(testName = "GIVEN: one header with header(String, String) -> WHEN: POST-request -> THEN: successful request and header is set to logMessage as expected")
    public void testHeaderPASSED() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "leader");

        JSONObject jsonObject = new JSONObject(jsonMap);
        apiTest(
                this,
                specifyRequest(
                        baseUri("https://reqres.in"),
                        basePath("/api/users"),
                        body(jsonObject),
                        header("Content-Type", "application/json")
                ),
                postRequest(),
                specifyAssertions(
                        statusCodeIs(201)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                "POST",
                201
        );
        Header contentTypeHeader = new Header("Content-Type", "application/json");
        Header acceptHeader = new Header("Accept", "*/*");
        Headers headers = new Headers(acceptHeader, contentTypeHeader);

        apiLogMessageHeadersFitsTo(
                "",
                latestApiLogMessage,
                headers
        );
    }

    @Test(testName = "GIVEN: multiple headers with header(String, String) -> WHEN: PUT-request -> THEN: successful request and headers get append to logMessage as expected")
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
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                "PUT",
                200
        );
        Header contentTypeHeader = new Header("Content-Type", "application/json");
        Header acceptHeader = new Header("Accept", "application/json");
        Header authHeader = new Header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=");

        Headers headers = new Headers(acceptHeader, authHeader, contentTypeHeader);
        apiLogMessageHeadersFitsTo(
                "",
                latestApiLogMessage,
                headers
        );
    }

    @Test(testName = "GIVEN: one header with header(Header header) -> WHEN: POST-request -> THEN: successful request and header is set to logMessage as expected")
    public void testHeader2PASSED() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", "morpheus");
        jsonMap.put("job", "leader");

        JSONObject jsonObject = new JSONObject(jsonMap);

        Header contentTypeHeader = new Header("Content-Type", "application/json");
        apiTest(
                this,
                specifyRequest(
                        baseUri("https://reqres.in"),
                        basePath("/api/users"),
                        body(jsonObject),
                        header(contentTypeHeader)
                ),
                postRequest(),
                specifyAssertions(
                        statusCodeIs(201)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                "POST",
                201
        );
        Header acceptHeader = new Header("Accept", "*/*");
        Headers headers = new Headers(acceptHeader, contentTypeHeader);

        apiLogMessageHeadersFitsTo(
                "",
                latestApiLogMessage,
                headers
        );
    }

    @Test(testName = "GIVEN: multiple headers with header(Header header) -> WHEN: PUT-request -> THEN: successful request and headers get append to logMessage as expected")
    public void testMultipleHeader2PASSED() {
        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/booking1.json");
        Header contentTypeHeader = new Header("Content-Type", "application/json");
        Header acceptHeader = new Header("Accept", "application/json");
        Header authHeader = new Header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=");
        apiTest(
                this,
                List.of(
                        baseUri("https://restful-booker.herokuapp.com"),
                        basePath("/booking/1"),
                        header(contentTypeHeader),
                        header(acceptHeader),
                        header(authHeader),
                        body(file)
                ),
                putRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                1,
                "PUT",
                200
        );
        Headers headers = new Headers(acceptHeader, authHeader, contentTypeHeader);
        apiLogMessageHeadersFitsTo(
                "",
                latestApiLogMessage,
                headers
        );
    }

    // ====== Cookies ======

    @Test(testName = "GIVEN: cookie provided with header() -> WHEN: correct request -> THEN: cookie got set in log header")
    public void testCookiesUsingCookieObjectsPrecondition() {
        Cookie cookie1 = (new Cookie.Builder("bar-foo", "foo_bar")).build();
        Cookie cookie2 = (new Cookie.Builder("foo-bar", "bar_foo")).build();

        String url = "https://jsonplaceholder.typicode.com";

        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/albums/1"),
                        header("Cookie", "bar-foo=foo_bar; foo-bar=bar_foo")
                        //cookie(cookie1),
                        //cookie(cookie2)
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );
        Header cookieHeader = new Header("Cookie", "bar-foo=foo_bar; foo-bar=bar_foo");
        Header acceptHeader = new Header("Accept", "*/*");
        Headers headers = new Headers(cookieHeader, acceptHeader);

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageHeadersFitsTo(
                "",
                latestApiLogMessage,
                headers
        );

        // Check the values of cookies 'bar-foo' and 'foo-bar'
        Assert.assertEquals(result.getReq().getHeaders().getValue("Cookie"), "bar-foo=foo_bar; foo-bar=bar_foo");

        // The cookie 'xxx' should not exist
        Assert.assertNull(result.getReq().getCookies().get("xxx"));
    }
}
