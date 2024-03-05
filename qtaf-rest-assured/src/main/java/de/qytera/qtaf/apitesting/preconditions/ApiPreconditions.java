package de.qytera.qtaf.apitesting.preconditions;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.http.*;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.log.model.message.ApiLogMessageUpdater.updateHeaderLogsByAddingAHeader;

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
            if (logMessage.getRequest().getPathParams() != null){
                logMessage.getRequest().getPathParams().putAll(params);
            } else {
                logMessage.getRequest().setPathParams(params);
            }
        };
    }


    default ApiPrecondition queryParam(String key, Object value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParam(key, value);
            if (logMessage.getRequest().getQueryParams() != null){
                logMessage.getRequest().getQueryParams().put(key, value);
            } else {
                HashMap<String, Object> map = new HashMap<>();
                map.put(key, value);
                logMessage.getRequest().setQueryParams(map);
            }
        };
    }


    default ApiPrecondition queryParams(Map<String, Object> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.queryParams(params);
            if (logMessage.getRequest().getQueryParams() != null){
                logMessage.getRequest().getQueryParams().putAll(params);
            } else {
                logMessage.getRequest().setQueryParams(params);
            }
        };
    }


    default ApiPrecondition formParam(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParam(key, value);

            logMessage.getRequest().getFormParams().put(key, value);
        };
    }


    default ApiPrecondition formParams(Map<String, Object> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.formParams(params);

            for (Map.Entry<String, Object> param: params.entrySet()){
                logMessage.getRequest().getFormParams().put(param.getKey(), param.getValue());
            }
        };
    }



    default ApiPrecondition body(String body) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(body);

            logMessage.getRequest().setBody(body);
        };
    }


    default ApiPrecondition body(Object object) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(object);

            logMessage.getRequest().setBody(object.toString());
        };
    }


    default ApiPrecondition body(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.body(file);

            logMessage.getRequest().setBody("Filepath: " + file.getPath());
        };
    }


    default ApiPrecondition contentType(String contentType) {
        // TODO String as a parameter doesn't seem right
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(contentType);

            logMessage.getRequest().getContentType().add(contentType);
        };
    }

    default ApiPrecondition contentType(ContentType contentType) {
        // TODO String as a parameter doesn't seem right
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(contentType);

            logMessage.getRequest().getContentType().add(contentType.toString());
        };
    }

    default ApiPrecondition removeContentType() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.noContentType();

            logMessage.getRequest().getContentType().clear();
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

            Header newHeader = new Header(key, value);
            updateHeaderLogsByAddingAHeader(logMessage, newHeader);
        };
    }


    default ApiPrecondition header(Header header) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header(header);

            updateHeaderLogsByAddingAHeader(logMessage, header);
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

    /*
    default ApiPrecondition headers(Map<String, ?> headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }
     */




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

            logMessage.getRequest().getContentType().add(ContentType.JSON.toString());
        };
    }

    default ApiPrecondition json(JSONObject jsonObject) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);
            req.body(jsonObject.toString());

            logMessage.getRequest().getContentType().add(ContentType.JSON.toString());
            logMessage.getRequest().setBody(jsonObject.toString());
        };
    }

    default ApiPrecondition multipart() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);

            logMessage.getRequest().getContentType().add(ContentType.MULTIPART.toString());
        };
    }

    default ApiPrecondition multipart(String s, Object o) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, o);

            logMessage.getRequest().getContentType().add(ContentType.MULTIPART.toString());
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

            logMessage.getRequest().getContentType().add(ContentType.TEXT.toString());
        };
    }

    default ApiPrecondition xml() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.XML);

            logMessage.getRequest().getContentType().add(ContentType.XML.toString());
        };
    }

}
