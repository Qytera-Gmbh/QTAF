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
        // return (RequestSpecification req, ApiLogMessage logMessage) -> req.baseUri(baseUri);
        return (RequestSpecification req) -> req.baseUri(baseUri);
    }

    /**
     * Adding the basePath
     *
     * @param basePath The uri
     * @return lambda
     */
    default ApiRequestSpecification basePath(String basePath) {
        return (RequestSpecification req) -> req.basePath(basePath);
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
        return (RequestSpecification req) -> req.pathParam(parameterKey, parameterValue);
    }

    /**
     * Specify multiple path parameter name-value pairs.
     * Path parameters can be used to improve readability of the request path.
     *
     * @param parameterNameValuePairs A map containing the path parameters.
     * @return lambda
     */
    default ApiRequestSpecification pathParams(Map<String, Object> parameterNameValuePairs) {
        return (RequestSpecification req) -> req.pathParams(parameterNameValuePairs);
    }

    /**
     * Specify a query parameter that'll be sent with the request.
     *
     * @param parameterName The parameter name / key
     * @param parameterValue The parameter value
     * @return lambda
     */
    default ApiRequestSpecification queryParam(String parameterName, Object parameterValue) {
        return (RequestSpecification req) -> req.queryParam(parameterName, parameterValue);
    }

    /**
     * Specify the query parameters that'll be sent with the request.
     *
     * @param parametersMap The Map containing the parameter names and their values to send with the request.
     * @return lambda
     */
    default ApiRequestSpecification queryParams(Map<String, Object> parametersMap) {
        return (RequestSpecification req) -> req.queryParams(parametersMap);
    }

    /**
     * Specify a form parameter that'll be sent with the request. 
     *
     * @param parameterName The parameter name / key
     * @param parameterValue The parameter value
     * @return lambda
     */
    default ApiRequestSpecification formParam(String parameterName, Object parameterValue) {
        return (RequestSpecification req) -> req.formParam(parameterName, parameterValue);
    }

    /**
     * Specify the form parameters that'll be sent with the request.
     *
     * @param parametersMap The Map containing the form parameter names and their values to send with the request.
     * @return lambda
     */
    default ApiRequestSpecification formParams(Map<String, Object> parametersMap) {
        return (RequestSpecification req) -> req.formParams(parametersMap);
    }

    /**
     * Specify a String request body (such as e.g. JSON or XML) that'll be sent with the request.
     * This works for the POST and PUT methods only.
     * Trying to do this for the other http methods will cause an exception to be thrown.
     *
     * @param body The body to send.
     * @return lambda
     */
    default ApiRequestSpecification body(String body) {
        return (RequestSpecification req) -> req.body(body);
    }

    /**
     * Specify an Object request content that will automatically be serialized to JSON or XML
     * and sent with the request.
     * If the object is a primitive or Number the object will be converted to a String and put in the request body.
     * This works for the POST and PUT methods only.
     * Trying to do this for the other http methods will cause an exception to be thrown.
     * Note:
     * If the content-type is "application/json" then REST Assured will automatically
     * try to serialize the object using Jackson  or Gson if they are available in the classpath.
     * If any of these frameworks are not in the classpath then an exception is thrown.
     * If the content-type is "application/xml" then REST Assured will automatically
     * try to serialize the object using JAXB  if it's available in the classpath.
     * Otherwise, an exception will be thrown.
     * If no request content-type is specified then REST Assured determine the parser in the following order:
     * Jackson
     * Gson
     * JAXB
     *
     * @param object The object to serialize and send with the request
     * @return lambda
     */
    default ApiRequestSpecification body(Object object) {
        return (RequestSpecification req) -> req.body(object);
    }

    /**
     * Specify file content that'll be sent with the request.
     * This only works for the POST, PATCH and PUT http method.
     * Trying to do this for the other http methods will cause an exception to be thrown.
     *
     * @param body The content to send.
     * @return lambda
     */
    default ApiRequestSpecification body(File body) {
        return (RequestSpecification req) -> req.body(body);
    }

    /**
     * Specify the content type of the request.
     *
     * @param contentType The content type of the request
     * @return lambda
     */
    default ApiRequestSpecification contentType(String contentType) {
        return (RequestSpecification req) -> req.contentType(contentType);
    }

    /**
     * Specify the content type of the request.
     *
     * @param contentType The content type of the request
     * @return lambda
     */
    default ApiRequestSpecification contentType(ContentType contentType) {
        return (RequestSpecification req) -> req.contentType(contentType);
    }

    /**
     * Instructs rest assured to don't include a content-type in the request
     *
     * @return lambda
     */
    default ApiRequestSpecification removeContentType() {
        return RequestSpecification::noContentType;
    }

    /**
     * Specify a header that'll be sent with the request.
     * You can also specify several headers by using this method multiple times:
     *   header("username", "John"),
     *   header("zipCode", "12345")
     *
     * @param headerName The header name
     * @param headerValue The header value
     * @return lambda
     */
    default ApiRequestSpecification header(String headerName, String headerValue) {
        return (RequestSpecification req) -> req.header(headerName, headerValue);
    }

    /**
     * Specify a Header to send with the request.
     *   e.g:
     *   Header someHeader = new Header("some_name", "some_value");
     * This will set the header some_name=some_value.
     * You can also specify several headers by using this method multiple times:
     *   header(someHeader1),
     *   header(someHeader2)
     *
     * @param header The header to add to the request
     * @return lambda
     */
    default ApiRequestSpecification header(Header header) {
        return (RequestSpecification req) -> req.header(header);
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
            logMessage.getRequest().setBearerToken(bearerToken);
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
