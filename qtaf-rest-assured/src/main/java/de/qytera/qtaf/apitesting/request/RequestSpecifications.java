package de.qytera.qtaf.apitesting.request;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.http.*;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public interface RequestSpecifications {
    @Step(name = "baseUri", description = "the base URI of the API")
    default ApiTestRequestSpecification baseUri(String baseUri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.baseUri(baseUri);

            logMessage.getRequest().setBaseUri(baseUri);
        };
    }

    @Step(name = "basePath", description = "the path of an endpoint")
    default ApiTestRequestSpecification basePath(String basePath) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.basePath(basePath);

            logMessage.getRequest().setBasePath(basePath);
        };
    }

    @Step(name = "pathParam", description = "Set a path parameter")
    default ApiTestRequestSpecification pathParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.pathParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setPathParams(map);
        };
    }

    @Step(name = "pathParams", description = "Set path parameters")
    default ApiTestRequestSpecification pathParam(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.pathParams(params);

            logMessage.getRequest().setPathParams(params);
        };
    }

    @Step(name = "queryParam", description = "Set a query parameter")
    default ApiTestRequestSpecification queryParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setPathParams(map);
        };
    }

    @Step(name = "queryParams", description = "Set query parameters")
    default ApiTestRequestSpecification queryParams(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParams(params);

            logMessage.getRequest().setQueryParams(params);
        };
    }

    @Step(name = "formParam", description = "Set a form parameter")
    default ApiTestRequestSpecification formParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setFormParams(map);
        };
    }

    @Step(name = "formParams", description = "Set form parameters")
    default ApiTestRequestSpecification formParams(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParams(params);

            logMessage.getRequest().setFormParams(params);
        };
    }

    @Step(name = "body", description = "set the body of the request")
    default ApiTestRequestSpecification body(String body) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(body);

            logMessage.getRequest().setBodyString(body);
        };
    }

    @Step(name = "body", description = "set the body of the request")
    default ApiTestRequestSpecification body(Object object) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(object);

            logMessage.getRequest().setBodyObject(object);
        };
    }

    @Step(name = "body", description = "set the body of the request")
    default ApiTestRequestSpecification body(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(file);

            // TODO logMessage.getRequest().setBodyFile(file);
        };
    }

    @Step(name = "contentType", description = "content type")
    default ApiTestRequestSpecification contentType(String contentType) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(contentType);

            logMessage.getRequest().setContentTypeString(contentType);
        };
    }

    @Step(name = "set file", description = "set the content type to MULTIPART and add a file")
    default ApiTestRequestSpecification file(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(file);

            // TODO logMessage.getRequest().setFile(file);
        };
    }

    @Step(name = "add file", description = "set the content type to MULTIPART and add a file")
    default ApiTestRequestSpecification file(String s, File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, file);

            logMessage.getRequest().setFileString(s);
            // TODO logMessage.getRequest().setFile(file);
        };
    }

    @Step(name = "add header", description = "add a header")
    default ApiTestRequestSpecification header(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header(key, value);

            Header h = new Header(key, value);
            logMessage.getRequest().setHeader(h);
        };
    }

    @Step(name = "add header", description = "add a header")
    default ApiTestRequestSpecification header(Header header) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header(header);

            logMessage.getRequest().setHeader(header);
        };
    }
    /* TODO
    @Step(name = "add headers", description = "add headers")
    default ApiTestRequestSpecification header(Headers headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }

     */

    // RequestSpecification headers(Map<String, ?> var1);

    @Step(name = "add headers", description = "add headers")
    default ApiTestRequestSpecification headers(Map<String, ?> headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }


    @Step(name = "add bearer token", description = "add an authorization header with a bearer token")
    default ApiTestRequestSpecification bearer(String bearerToken) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header("Authorization", "Bearer " + bearerToken);

            logMessage.getRequest().setBeareToken(bearerToken);
        };
    }

    @Step(name = "add cookie", description = "add a cookie to the request")
    default ApiTestRequestSpecification cookie(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(key, value);

            logMessage.getRequest().setCookieKey(key);
            logMessage.getRequest().setCookieValue(value);

        };
    }

    @Step(name = "add cookie", description = "add a cookie to the request")
    default ApiTestRequestSpecification cookie(Cookie cookie) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(cookie);

            logMessage.getRequest().setCookie(cookie);
        };
    }

    @Step(name = "add cookies", description = "add cookies to the request")
    default ApiTestRequestSpecification cookie(Cookies cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookies(cookies);
        };
    }

    @Step(name = "add cookies", description = "add cookies to the request")
    default ApiTestRequestSpecification cookie(Map<String, ?> cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookiesMap(cookies);
        };
    }

    @Step(name = "content type JSON", description = "set the content type to JSON")
    default ApiTestRequestSpecification json() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);

            logMessage.getRequest().setContentType(ContentType.JSON);
        };
    }

    @Step(name = "content type JSON", description = "set the content type to JSON")
    default ApiTestRequestSpecification json(JSONObject jsonObject) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);
            req.body(jsonObject.toString());

            logMessage.getRequest().setContentType(ContentType.JSON);
            logMessage.getRequest().setBodyString(jsonObject.toString());
        };
    }

    @Step(name = "content type MULTIPART", description = "set the content type to MULTIPART")
    default ApiTestRequestSpecification multipart() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);

            logMessage.getRequest().setContentType(ContentType.MULTIPART);
        };
    }

    @Step(name = "content type MULTIPART", description = "set the content type to MULTIPART and add an object")
    default ApiTestRequestSpecification multipart(String s, Object o) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, o);

            logMessage.getRequest().setContentType(ContentType.MULTIPART);
            logMessage.getRequest().setMultipartString(s);
            logMessage.getRequest().setMultipartObject(o);
        };
    }

    @Step(name = "content type MULTIPART", description = "set the content type to MULTIPART and add a string")
    default ApiTestRequestSpecification multipart(String s1, String s2) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s1, s2);
        };
    }

    @Step(name = "content type TEXT", description = "set the content type to TEXT")
    default ApiTestRequestSpecification text() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.TEXT);

            logMessage.getRequest().setContentType(ContentType.TEXT);
        };
    }

    @Step(name = "content type XML", description = "set the content type to XML")
    default ApiTestRequestSpecification xml() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.XML);

            logMessage.getRequest().setContentType(ContentType.XML);
        };
    }

}
