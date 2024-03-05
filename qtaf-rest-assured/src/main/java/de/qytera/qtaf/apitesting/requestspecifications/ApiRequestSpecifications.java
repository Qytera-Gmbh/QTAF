package de.qytera.qtaf.apitesting.requestspecifications;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.http.*;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;


public interface ApiRequestSpecifications {
    /**
     * Adding the baseUri
     *
     * @param baseUri The uri
     * @return lambda
     */
    default ApiRequestSpecification baseUri(String baseUri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.baseUri(baseUri);
    }

    /**
     * Adding the basePath
     *
     * @param basePath The uri
     * @return lambda
     */
    default ApiRequestSpecification basePath(String basePath) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.basePath(basePath);
    }

    /**
     * Specify a path parameter.
     * Path parameters can be used to improve readability of the request path.
     *
     * @param parameterKey The parameter key
     * @param parameterValue The parameter value
     * @return lambda
     */
    default ApiRequestSpecification pathParam(String parameterKey, Object parameterValue) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.pathParam(parameterKey, parameterValue);
    }

    default ApiRequestSpecification pathParams(Map<String, Object> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.pathParams(params);
    }

    default ApiRequestSpecification queryParam(String key, Object value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.queryParam(key, value);
    }

    default ApiRequestSpecification queryParams(Map<String, Object> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.queryParams(params);
    }

    default ApiRequestSpecification formParam(String key, Object value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.formParam(key, value);
    }

    default ApiRequestSpecification formParams(Map<String, Object> params) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.formParams(params);
    }

    default ApiRequestSpecification body(String body) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.body(body);
    }

    default ApiRequestSpecification body(Object object) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.body(object);
    }

    default ApiRequestSpecification body(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.body(file);
    }

    default ApiRequestSpecification contentType(String contentType) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.contentType(contentType);
    }

    default ApiRequestSpecification contentType(ContentType contentType) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.contentType(contentType);
    }

    default ApiRequestSpecification removeContentType() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.noContentType();
    }

    default ApiRequestSpecification header(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.header(key, value);
    }

    default ApiRequestSpecification header(Header header) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> req.header(header);
    }

    /*
    @Step(name = "add headers", description = "add headers")
    default ApiTestRequestSpecification header(Headers headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }
    */

    /*
    default ApiPrecondition headers(Map<String, ?> headers) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.headers(headers);

            logMessage.getRequest().setHeaders(headers);
        };
    }
    */

    /*
    default ApiPrecondition bearer(String bearerToken) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.header("Authorization", "Bearer " + bearerToken);

            logMessage.getRequest().setBeareToken(bearerToken);
        };
    }
     */

    /*
    default ApiPrecondition cookie(String key, String value) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(key, value);

            logMessage.getRequest().setCookieKey(key);
            logMessage.getRequest().setCookieValue(value);

        };
    }
    */

    /*
    default ApiPrecondition cookie(Cookie cookie) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookie(cookie);

            logMessage.getRequest().setCookie(cookie);
        };
    }
    */

    /*
    default ApiPrecondition cookies(Cookies cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookies(cookies);
        };
    }
    */

    /*
    default ApiPrecondition cookie(Map<String, ?> cookies) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.cookies(cookies);

            logMessage.getRequest().setCookiesMap(cookies);
        };
    }
    */

    /*
    default ApiPrecondition json() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);

            logMessage.getRequest().getContentType().add(ContentType.JSON.toString());
        };
    }
    */

    /*
    default ApiPrecondition json(JSONObject jsonObject) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.JSON);
            req.body(jsonObject.toString());

            logMessage.getRequest().getContentType().add(ContentType.JSON.toString());
            logMessage.getRequest().setBody(jsonObject.toString());
        };
    }
    */

    /*
    default ApiPrecondition multiPart(MultiPartSpecification multiPartSpecification){
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(multiPartSpecification);

            logMessage.getRequest().getContentType().add(ContentType.MULTIPART.toString());
        };
    };
     */

    /*
    default ApiPrecondition multipart() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);

            logMessage.getRequest().getContentType().add(ContentType.MULTIPART.toString());
        };
    }
    */
    /*
    default ApiPrecondition multiPart(File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(file);

            // logMessage.getRequest().setFile(file);
        };
    }
     */

    /*
    default ApiPrecondition multiPart(String s, File file) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, file);
        };
    }
     */





    /*
    default ApiPrecondition multipart(String s, Object o) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s, o);

            logMessage.getRequest().getContentType().add(ContentType.MULTIPART.toString());
            logMessage.getRequest().setMultipartString(s);
            logMessage.getRequest().setMultipartObject(o);
        };
    }
     */

    /*
    default ApiPrecondition multipart(String s1, String s2) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.MULTIPART);
            req.multiPart(s1, s2);
        };
    }
     */

    /*
    default ApiPrecondition text() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.TEXT);

            logMessage.getRequest().getContentType().add(ContentType.TEXT.toString());
        };
    }
     */

    /*
    default ApiPrecondition xml() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            req.contentType(ContentType.XML);

            logMessage.getRequest().getContentType().add(ContentType.XML.toString());
        };
    }
     */

}
