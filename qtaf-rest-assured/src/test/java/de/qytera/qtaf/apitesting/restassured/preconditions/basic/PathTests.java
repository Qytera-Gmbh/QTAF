package de.qytera.qtaf.apitesting.restassured.preconditions.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Time Assertion Tests", description = "Check the time assertion methods")
public class PathTests extends QtafTestNGContext implements ApiTest {

    private static final String urlPlaceholder = "https://jsonplaceholder.typicode.com";
    String urlReqres = "https://reqres.in";
    @Test(testName = "baseUri get just baseUri 200 -> PASSED")
    public void testBaseUriGet() {
        apiTest(
                this,
                List.of(baseUri(urlPlaceholder)),
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlPlaceholder,
                null,
                null,
                null
        );
    }

    @Test(testName = "multiple baseUrisPASSED")
    public void testMultipleBaseUrisGetPASSED() {
        apiTest(
                this,
                List.of(
                        baseUri("dummy"),
                        baseUri(urlPlaceholder)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlPlaceholder,
                null,
                null,
                null
        );
    }

    @Test(testName = "GIVEN: wrong baseUri -> WHEN: GET-request -> THEN: throw IllegalStateException and Logmessage status is PENDING")
    public void testWrongBaseUriGetPENDING() {
        try {
            apiTest(
                    this,
                    List.of(
                            baseUri("dummy")
                    ),
                    getRequest(),
                    List.of()
            );
        } catch (Exception e){
            Assert.assertTrue(e instanceof IllegalStateException);
        }

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PENDING,
                0,
                ApiLogMessage.Action.RequestType.GET,
                0
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                "dummy",
                null,
                null,
                null
        );
    }

    @Test(testName = "baseUri() & basePath() post 200 -> PASSED")
    public void testBaseUriBasePathPost() {
        // de/qytera/qtaf/apitesting/restassured/preconditions/basic/PathTests.java

        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");

        String basePath = "/api/users";
        ExecutedApiTest executedApiTest = apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        contentType(ContentType.JSON),
                        body(file)
                ),
                postRequest(),
                List.of()
        );
        System.out.println("----- " + executedApiTest.getRes().body().asString());

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.POST,
                201
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                null,
                null
        );
    }

    @Test(testName = "pathParams() DELETE 204 -> PASSED")
    public void testPathParamDeletePASSED() {

        String basePath = "/api/users/{id}";
        String id = "2";

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        pathParam("id", id)
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

        Map<String, String> pathParamMap = new HashMap<>();
        pathParamMap.put("id", id);

        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                pathParamMap,
                null
        );
    }

    @Test(testName = "pathParams() DELETE 204 -> PASSED")
    public void testPathParamIntDeletePASSED() {

        String basePath = "/api/users/{id}";
        int id = 2;

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        pathParam("id", id)
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

        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("id", id);

        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                pathParamMap,
                null
        );
    }

    @Test(testName = "multiple iterations with different pathParams() DELETE 204 -> PASSED")
    public void testPathParamIterationPutPASSED() {

        String basePath = "/api/users/{id}";
        String[] ids = {"1", "2", "3"};

        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");

        for (String id : ids){
            apiTest(
                    this,
                    List.of(
                            baseUri(urlReqres),
                            basePath(basePath),
                            pathParam("id", id),
                            body(file)
                    ),
                    postRequest(),
                    List.of()
            );
        }
        for(int i = 0; i< ids.length; i++){
            ApiLogMessage apiLogMessage = getApiLogMessageAtIndexFromContext(this, i);
            apiLogMessageFitsTo(
                    "ApiLogMessage",
                    apiLogMessage,
                    LogMessage.Status.PASSED,
                    0,
                    ApiLogMessage.Action.RequestType.POST,
                    201
            );
            Map<String, String> pathParamMap = new HashMap<>();
            pathParamMap.put("id", ids[i]);
            apiLogMessageUrlPathFitsTo(
                    "",
                    apiLogMessage,
                    urlReqres,
                    basePath,
                    pathParamMap,
                    null
            );
        }
    }

    @Test(testName = "multiple pathParam()")
    public void testPathParamMultipleTimesPASSED() {

        String basePath = "/api/{param1}{param2}";
        // String basePath = "/api/{param1}";
        String param1 = "u";
        String param2 = "sers";

        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/user1.json");

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        pathParam("param1", param1),
                        pathParam("param2", param2),
                        json(),
                        body(file)
                ),
                postRequest(),
                List.of()
        );

        ApiLogMessage apiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "ApiLogMessage",
                apiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.POST,
                201
        );
        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("param1", param1);
        pathParamMap.put("param2", param2);

        apiLogMessageUrlPathFitsTo(
                "",
                apiLogMessage,
                urlReqres,
                basePath,
                pathParamMap,
                null
        );
    }

    @Test(testName = "pathParams() DELETE 204 -> PASSED")
    public void testPathParamsGetPASSED() {

        String basePath = "/api/{param1}}/{param2}";
        String param1 = "users";
        String param2 = "2";

        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("param1", param1);
        pathParamMap.put("param2", param2);

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        pathParams(pathParamMap)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                pathParamMap,
                null
        );
    }

    @Test(testName = "pathParams() with same keys in map -> PASSED")
    public void testPathParamsPutSameKeyPASSED() {

        String basePath = "/{param1}}/{param2}";

        String param1 = "api";
        String param2 = "register";
        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("param1", param1);
        pathParamMap.put("param1", param2);

        File file = new File("src/test/java/de/qytera/qtaf/apitesting/restassured/preconditions/basic/assets/registerUser.json");

        try {
            apiTest(
                    this,
                    List.of(
                            baseUri(urlReqres),
                            basePath(basePath),
                            pathParams(pathParamMap),
                            body(file)
                    ),
                    postRequest(),
                    List.of()
            );
        } catch (Exception exception){
            IllegalArgumentException checkedException = exception instanceof IllegalArgumentException ? ((IllegalArgumentException) exception) : null;
            Assert.assertNotNull(checkedException);
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
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                pathParamMap,
                null
        );
    }

    // test pathParam and Path Params in same api test
    @Test(testName = "GIVEN: a path param with a backslash -> WHEN: GET-request -> THEN status code 404")
    public void testPathParamsAndPathParamGetSameKeyPASSED() {

        String basePath = "/{param1}{param2}{param3}";

        String param1 = "posts";
        String param2 = "/";
        String param3 = "1";
        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("param1", param1);
        pathParamMap.put("param2", param2);

        apiTest(
                this,
                List.of(
                        baseUri(urlPlaceholder),
                        basePath(basePath),
                        pathParams(pathParamMap),
                        pathParam("param3", param3)
                ),
                getRequest(),
                List.of()
        );

        pathParamMap.put("param3", param3);

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.GET,
                404
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlPlaceholder,
                basePath,
                pathParamMap,
                null
        );
    }

    @Test(testName = "GIVEN: a path param map and a path param -> WHEN: GET-request -> THEN: successful request")
    public void testPathParamsAndPathParamGetPASSED() {

        String basePath = "/{param1}/{param3}";

        String param1 = "posts";
        String param2 = "/";
        String param3 = "1";
        Map<String, Object> pathParamMap = new HashMap<>();
        pathParamMap.put("param1", param1);
        //pathParamMap.put("param2", param2);

        apiTest(
                this,
                List.of(
                        baseUri(urlPlaceholder),
                        basePath(basePath),
                        pathParams(pathParamMap),
                        pathParam("param3", param3)
                ),
                getRequest(),
                List.of()
        );

        pathParamMap.put("param3", param3);

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.PASSED,
                0,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlPlaceholder,
                basePath,
                pathParamMap,
                null
        );
    }

    @Test(testName = "pathParams() with same keys in map -> PASSED")
    public void testQueryParamPASSED() {

        String basePath = "/api/users";

        String queryParamKey = "page";
        int queryParamValue = 2;
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put(queryParamKey, queryParamValue);

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        queryParam(queryParamKey , queryParamValue)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                null,
                queryParamMap
        );
    }

    @Test(testName = "pathParams() with same keys in map -> PASSED")
    public void testMultipleQueryParamPASSED() {

        String basePath = "/api/users";

        String queryParamKey1 = "page";
        int queryParamValue1 = 2;

        String queryParamKey2 = "delay";
        int queryParamValue2 = 1;

        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put(queryParamKey1, queryParamValue1);
        queryParamMap.put(queryParamKey2, queryParamValue2);

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        queryParam(queryParamKey1 , queryParamValue1),
                        queryParam(queryParamKey2 , queryParamValue2)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                null,
                queryParamMap
        );
    }

    @Test(testName = "pathParams() with same keys in map -> PASSED")
    public void testQueryParamsPASSED() {

        String basePath = "/api/users";

        String queryParamKey1 = "page";
        int queryParamValue1 = 2;

        String queryParamKey2 = "delay";
        int queryParamValue2 = 1;

        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put(queryParamKey1, queryParamValue1);
        queryParamMap.put(queryParamKey2, queryParamValue2);

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        queryParams(queryParamMap)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                null,
                queryParamMap
        );
    }

    @Test(testName = "pathParams() with same keys in map -> PASSED")
    public void testQueryParamAndParamsPASSED() {

        String basePath = "/api/users";

        String queryParamKey1 = "page";
        int queryParamValue1 = 2;

        String queryParamKey2 = "delay";
        int queryParamValue2 = 1;

        Map<String, Object> queryParamsAllMap = new HashMap<>();
        queryParamsAllMap.put(queryParamKey1, queryParamValue1);
        queryParamsAllMap.put(queryParamKey2, queryParamValue2);

        Map<String, Object> queryParamsMap = new HashMap<>();
        queryParamsMap.put(queryParamKey2, queryParamValue2);

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        queryParam(queryParamKey1, queryParamValue1),
                        queryParams(queryParamsMap)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                null,
                queryParamsAllMap
        );
    }

    @Test(testName = "pathParams() with same keys in map -> PASSED")
    public void testQueryParamsAndParamPASSED() {

        String basePath = "/api/users";

        String queryParamKey1 = "page";
        int queryParamValue1 = 2;

        String queryParamKey2 = "delay";
        int queryParamValue2 = 1;

        Map<String, Object> queryParamsAllMap = new HashMap<>();
        queryParamsAllMap.put(queryParamKey1, queryParamValue1);
        queryParamsAllMap.put(queryParamKey2, queryParamValue2);

        Map<String, Object> queryParamsMap = new HashMap<>();
        queryParamsMap.put(queryParamKey2, queryParamValue2);

        apiTest(
                this,
                List.of(
                        baseUri(urlReqres),
                        basePath(basePath),
                        queryParams(queryParamsMap),
                        queryParam(queryParamKey1, queryParamValue1)
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
                200
        );
        apiLogMessageUrlPathFitsTo(
                "",
                latestApiLogMessage,
                urlReqres,
                basePath,
                null,
                queryParamsAllMap
        );
    }
}
