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
    default ApiTestRequestSpecification baseUri(String baseUri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.baseUri(baseUri);

            logMessage.getRequest().setBaseUri(baseUri);
        };
    }

    default ApiTestRequestSpecification basePath(String basePath) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.basePath(basePath);

            logMessage.getRequest().setBasePath(basePath);
        };
    }


    default ApiTestRequestSpecification pathParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.pathParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setPathParams(map);
        };
    }


    default ApiTestRequestSpecification pathParam(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.pathParams(params);

            logMessage.getRequest().setPathParams(params);
        };
    }


    default ApiTestRequestSpecification queryParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setPathParams(map);
        };
    }


    default ApiTestRequestSpecification queryParams(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParams(params);

            logMessage.getRequest().setQueryParams(params);
        };
    }


    default ApiTestRequestSpecification formParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setFormParams(map);
        };
    }


    default ApiTestRequestSpecification formParams(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParams(params);

            logMessage.getRequest().setFormParams(params);
        };
    }


    default ApiTestRequestSpecification body(String body) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(body);

            logMessage.getRequest().setBodyString(body);
        };
    }


    default ApiTestRequestSpecification body(Object object) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(object);

            logMessage.getRequest().setBodyObject(object);
        };
    }


    default ApiTestRequestSpecification body(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(file);

            // TODO logMessage.getRequest().setBodyFile(file);
        };
    }


    default ApiTestRequestSpecification contentType(String contentType) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(contentType);

            logMessage.getRequest().setContentTypeString(contentType);
        };
    }


    default ApiTestRequestSpecification file(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(file);

            // TODO logMessage.getRequest().setFile(file);
        };
    }


    default ApiTestRequestSpecification file(String s, File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, file);

            logMessage.getRequest().setFileString(s);
            // TODO logMessage.getRequest().setFile(file);
        };
    }


    default ApiTestRequestSpecification header(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header(key, value);

            Header h = new Header(key, value);
            logMessage.getRequest().setHeader(h);
        };
    }


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


    default ApiTestRequestSpecification headers(Map<String, ?> headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }



    default ApiTestRequestSpecification bearer(String bearerToken) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header("Authorization", "Bearer " + bearerToken);

            logMessage.getRequest().setBeareToken(bearerToken);
        };
    }


    default ApiTestRequestSpecification cookie(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(key, value);

            logMessage.getRequest().setCookieKey(key);
            logMessage.getRequest().setCookieValue(value);

        };
    }

    default ApiTestRequestSpecification cookie(Cookie cookie) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(cookie);

            logMessage.getRequest().setCookie(cookie);
        };
    }

    default ApiTestRequestSpecification cookie(Cookies cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookies(cookies);
        };
    }

    default ApiTestRequestSpecification cookie(Map<String, ?> cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookiesMap(cookies);
        };
    }

    default ApiTestRequestSpecification json() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);

            logMessage.getRequest().setContentType(ContentType.JSON);
        };
    }

    default ApiTestRequestSpecification json(JSONObject jsonObject) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);
            req.body(jsonObject.toString());

            logMessage.getRequest().setContentType(ContentType.JSON);
            logMessage.getRequest().setBodyString(jsonObject.toString());
        };
    }

    default ApiTestRequestSpecification multipart() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);

            logMessage.getRequest().setContentType(ContentType.MULTIPART);
        };
    }

    default ApiTestRequestSpecification multipart(String s, Object o) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, o);

            logMessage.getRequest().setContentType(ContentType.MULTIPART);
            logMessage.getRequest().setMultipartString(s);
            logMessage.getRequest().setMultipartObject(o);
        };
    }

    default ApiTestRequestSpecification multipart(String s1, String s2) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s1, s2);
        };
    }

    default ApiTestRequestSpecification text() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.TEXT);

            logMessage.getRequest().setContentType(ContentType.TEXT);
        };
    }

    default ApiTestRequestSpecification xml() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.XML);

            logMessage.getRequest().setContentType(ContentType.XML);
        };
    }

}
