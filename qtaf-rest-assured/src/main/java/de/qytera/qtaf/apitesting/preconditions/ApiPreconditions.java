package de.qytera.qtaf.apitesting.preconditions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.http.*;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public interface ApiPreconditions {
    default ApiPrecondition baseUri(String baseUri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.baseUri(baseUri);

            logMessage.getRequest().setBaseUri(baseUri);
        };
    }

    default ApiPrecondition basePath(String basePath) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.basePath(basePath);

            logMessage.getRequest().setBasePath(basePath);
        };
    }


    default ApiPrecondition pathParam(String key, Object value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.pathParam(key, value);
            if (logMessage.getRequest().getPathParams() != null){
                logMessage.getRequest().getPathParams().put(key, value);
            } else {
                HashMap<String, Object> map = new HashMap<>();
                map.put(key, value);
                logMessage.getRequest().setPathParams(map);
            }
        };
    }


    default ApiPrecondition pathParams(Map<String, Object> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.pathParams(params);

            logMessage.getRequest().setPathParams(params);
        };
    }


    default ApiPrecondition queryParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setQueryParams(map);
        };
    }


    default ApiPrecondition queryParams(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParams(params);

            logMessage.getRequest().setQueryParams(params);
        };
    }


    default ApiPrecondition formParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParam(key, value);

            HashMap<String, String> map = new HashMap<>();
            map.put(key, value);
            logMessage.getRequest().setFormParams(map);
        };
    }


    default ApiPrecondition formParams(Map<String, ?> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParams(params);

            logMessage.getRequest().setFormParams(params);
        };
    }


    default ApiPrecondition body(String body) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(body);

            logMessage.getRequest().setBodyString(body);
        };
    }


    default ApiPrecondition body(Object object) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(object);

            logMessage.getRequest().setBodyObject(object);
        };
    }


    default ApiPrecondition body(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(file);

            // TODO logMessage.getRequest().setBodyFile(file);
        };
    }


    default ApiPrecondition contentType(String contentType) {
        // TODO String as a parameter doesn't seem right
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(contentType);

            logMessage.getRequest().setContentTypeString(contentType);
        };
    }

    default ApiPrecondition contentType(ContentType contentType) {
        // TODO String as a parameter doesn't seem right
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(contentType);

            logMessage.getRequest().setContentType(contentType);
        };
    }


    default ApiPrecondition file(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(file);

            // TODO logMessage.getRequest().setFile(file);
        };
    }


    default ApiPrecondition file(String s, File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, file);

            logMessage.getRequest().setFileString(s);
            // TODO logMessage.getRequest().setFile(file);
        };
    }


    default ApiPrecondition header(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header(key, value);

            Header h = new Header(key, value);
            logMessage.getRequest().setHeader(h);
        };
    }


    default ApiPrecondition header(Header header) {
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


    default ApiPrecondition headers(Map<String, ?> headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }



    default ApiPrecondition bearer(String bearerToken) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header("Authorization", "Bearer " + bearerToken);

            logMessage.getRequest().setBeareToken(bearerToken);
        };
    }


    default ApiPrecondition cookie(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(key, value);

            logMessage.getRequest().setCookieKey(key);
            logMessage.getRequest().setCookieValue(value);

        };
    }

    default ApiPrecondition cookie(Cookie cookie) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(cookie);

            logMessage.getRequest().setCookie(cookie);
        };
    }

    default ApiPrecondition cookies(Cookies cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookies(cookies);
        };
    }

    default ApiPrecondition cookie(Map<String, ?> cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookiesMap(cookies);
        };
    }

    default ApiPrecondition json() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);

            logMessage.getRequest().setContentType(ContentType.JSON);
        };
    }

    default ApiPrecondition json(JSONObject jsonObject) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);
            req.body(jsonObject.toString());

            logMessage.getRequest().setContentType(ContentType.JSON);
            logMessage.getRequest().setBodyString(jsonObject.toString());
        };
    }

    default ApiPrecondition multipart() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);

            logMessage.getRequest().setContentType(ContentType.MULTIPART);
        };
    }

    default ApiPrecondition multipart(String s, Object o) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, o);

            logMessage.getRequest().setContentType(ContentType.MULTIPART);
            logMessage.getRequest().setMultipartString(s);
            logMessage.getRequest().setMultipartObject(o);
        };
    }

    default ApiPrecondition multipart(String s1, String s2) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s1, s2);
        };
    }

    default ApiPrecondition text() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.TEXT);

            logMessage.getRequest().setContentType(ContentType.TEXT);
        };
    }

    default ApiPrecondition xml() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.XML);

            logMessage.getRequest().setContentType(ContentType.XML);
        };
    }

}
