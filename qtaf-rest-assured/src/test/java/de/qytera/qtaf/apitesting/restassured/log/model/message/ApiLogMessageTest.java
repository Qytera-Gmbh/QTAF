package de.qytera.qtaf.apitesting.restassured.log.model.message;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.log.model.LogLevel;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Tests for the ApiLogMessage class
 */
public class ApiLogMessageTest {
    /**
     * Test if a log message will be built without exceptions
     */
    @Test
    public void testBuildMessage() {
        ApiLogMessage message = new ApiLogMessage(LogLevel.INFO, "message");
        ApiLogMessage.Request request = new ApiLogMessage.Request();
        request.setRequestMethod("GET");
        request.setBaseUri("qytera.de");
        request.setBasePath("/home");
        request.setContentType(ContentType.HTML.toString());
        Headers requestHeaders = new Headers(new Header("reqhKey1", "reqhVal1"));
        request.setHeaders(requestHeaders);
        request.setPathParams(Map.of("ppKey1", "ppVal1"));
        request.setQueryParams(Map.of("qpKey1", "qpVal1"));
        request.setFormParams(Map.of("fpKey1", "fpVal1"));
        request.setBodyAsString("REQ BODY");

        ApiLogMessage.Response response = new ApiLogMessage.Response();
        response.setStatusCode(200);
        response.setTime(200L);
        response.setContentType(ContentType.HTML.toString());
        Headers responseHeaders = new Headers(new Header("bar", "foo"));
        response.setHeaders(responseHeaders);
        response.setCookies(Map.of("cKey1", "cKey2"));
        response.setBodyAsString("RES BODY");

        message.setRequest(request);
        message.setResponse(response);
        String expectedMessage = """
                REQUEST:
                
                Request Method: GET
                Base URI: qytera.de
                Base Path: /home
                Content Type: text/html
                                
                Headers:\s
                	reqhKey1 = reqhVal1
                                
                Path Params:\s
                	ppKey1 = ppVal1
                                
                Query Params:\s
                	qpKey1 = qpVal1
                                
                Form Params:\s
                	fpKey1 = fpVal1
                                
                Body:
                REQ BODY
                                
                RESPONSE
                                
                Status Code: 200
                Time: 200
                Content Type: text/html
                                
                Headers:\s
                	bar = foo
                                
                Cookies:\s
                	cKey1 = cKey2
                                
                Body:
                RES BODY""";
        String sMessage = message.buildMessage();
        Assert.assertEquals(sMessage, expectedMessage);
    }
}
