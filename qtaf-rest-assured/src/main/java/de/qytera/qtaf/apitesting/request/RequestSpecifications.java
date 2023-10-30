package de.qytera.qtaf.apitesting.request;

import de.qytera.qtaf.core.guice.annotations.Step;
import io.restassured.http.*;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Map;

public interface RequestSpecifications {
    @Step(name = "baseUri", description = "the base URI of the API")
    default ApiTestRequestSpecification baseUri(String baseUri) {
        return (RequestSpecification req) -> {
            req.baseUri(baseUri);
        };
    }

    @Step(name = "basePath", description = "the path of an endpoint")
    default ApiTestRequestSpecification basePath(String basePath) {
        return (RequestSpecification req) -> {
            req.basePath(basePath);
        };
    }

    @Step(name = "pathParam", description = "Set a path parameter")
    default ApiTestRequestSpecification pathParam(String key, String value) {
        return (RequestSpecification req) -> {
            req.pathParam(key, value);
        };
    }

    @Step(name = "pathParams", description = "Set path parameters")
    default ApiTestRequestSpecification pathParam(Map<String, ?> params) {
        return (RequestSpecification req) -> {
            req.pathParams(params);
        };
    }

    @Step(name = "queryParam", description = "Set a query parameter")
    default ApiTestRequestSpecification queryParam(String key, String value) {
        return (RequestSpecification req) -> {
            req.queryParam(key, value);
        };
    }

    @Step(name = "queryParams", description = "Set query parameters")
    default ApiTestRequestSpecification queryParams(Map<String, ?> params) {
        return (RequestSpecification req) -> {
            req.queryParams(params);
        };
    }

    @Step(name = "formParam", description = "Set a form parameter")
    default ApiTestRequestSpecification formParam(String key, String value) {
        return (RequestSpecification req) -> {
            req.formParam(key, value);
        };
    }

    @Step(name = "formParams", description = "Set form parameters")
    default ApiTestRequestSpecification formParams(Map<String, ?> params) {
        return (RequestSpecification req) -> {
            req.formParams(params);
        };
    }

    @Step(name = "body", description = "set the body of the request")
    default ApiTestRequestSpecification body(String body) {
        return (RequestSpecification req) -> {
            req.body(body);
        };
    }

    @Step(name = "body", description = "set the body of the request")
    default ApiTestRequestSpecification body(Object object) {
        return (RequestSpecification req) -> {
            req.body(object);
        };
    }

    @Step(name = "body", description = "set the body of the request")
    default ApiTestRequestSpecification body(File file) {
        return (RequestSpecification req) -> {
            req.body(file);
        };
    }

    @Step(name = "contentType", description = "content type")
    default ApiTestRequestSpecification contentType(String contentType) {
        return (RequestSpecification req) -> {
            req.contentType(contentType);
        };
    }

    @Step(name = "set file", description = "set the content type to MULTIPART and add a file")
    default ApiTestRequestSpecification file(File file) {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(file);
        };
    }

    @Step(name = "add file", description = "set the content type to MULTIPART and add a file")
    default ApiTestRequestSpecification file(String s, File file) {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, file);
        };
    }

    @Step(name = "add header", description = "add a header")
    default ApiTestRequestSpecification header(String key, String value) {
        return (RequestSpecification req) -> {
            req.header(key, value);
        };
    }

    @Step(name = "add header", description = "add a header")
    default ApiTestRequestSpecification header(Header header) {
        return (RequestSpecification req) -> {
            req.header(header);
        };
    }

    @Step(name = "add headers", description = "add headers")
    default ApiTestRequestSpecification header(Headers headers) {
        return (RequestSpecification req) -> {
            req.headers(headers);
        };
    }

    @Step(name = "add bearer token", description = "add an authorization header with a bearer token")
    default ApiTestRequestSpecification bearer(String bearerToken) {
        return (RequestSpecification req) -> {
            req.header("Authorization", "Bearer " + bearerToken);
        };
    }

    @Step(name = "add cookie", description = "add a cookie to the request")
    default ApiTestRequestSpecification cookie(String key, String value) {
        return (RequestSpecification req) -> {
            req.cookie(key, value);
        };
    }

    @Step(name = "add cookie", description = "add a cookie to the request")
    default ApiTestRequestSpecification cookie(Cookie cookie) {
        return (RequestSpecification req) -> {
            req.cookie(cookie);
        };
    }

    @Step(name = "add cookies", description = "add cookies to the request")
    default ApiTestRequestSpecification cookie(Cookies cookies) {
        return (RequestSpecification req) -> {
            req.cookies(cookies);
        };
    }

    @Step(name = "add cookies", description = "add cookies to the request")
    default ApiTestRequestSpecification cookie(Map<String, ?> cookies) {
        return (RequestSpecification req) -> {
            req.cookies(cookies);
        };
    }

    @Step(name = "content type JSON", description = "set the content type to JSON")
    default ApiTestRequestSpecification json() {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.JSON);
        };
    }

    @Step(name = "content type JSON", description = "set the content type to JSON")
    default ApiTestRequestSpecification json(JSONObject jsonObject) {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.JSON);
            req.body(jsonObject.toString());
        };
    }

    @Step(name = "content type MULTIPART", description = "set the content type to MULTIPART")
    default ApiTestRequestSpecification multipart() {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.MULTIPART);
        };
    }

    @Step(name = "content type MULTIPART", description = "set the content type to MULTIPART and add an object")
    default ApiTestRequestSpecification multipart(String s, Object object) {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, object);
        };
    }

    @Step(name = "content type MULTIPART", description = "set the content type to MULTIPART and add a string")
    default ApiTestRequestSpecification multipart(String s1, String s2) {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s1, s2);
        };
    }

    @Step(name = "content type TEXT", description = "set the content type to TEXT")
    default ApiTestRequestSpecification text() {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.TEXT);
        };
    }

    @Step(name = "content type XML", description = "set the content type to XML")
    default ApiTestRequestSpecification xml() {
        return (RequestSpecification req) -> {
            req.contentType(ContentType.XML);
        };
    }

}
