package de.qytera.qtaf.apitesting.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import io.restassured.http.*;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.QueryableRequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * This class provides the data format for Api log messages, which QTAF uses for reporting and logging.
 * This format is visible to users and is also used in other QTAF modules,
 * e.g. when creating JSON or HTML reports or in plugins.
 */
public class ApiLogMessage extends LogMessage {
    /**
     * Request object.
     */
    @Getter @Setter
    Request request = new Request();

    /**
     * Response object.
     */
    @Getter @Setter
    Response response = new Response();

    /**
     * Constructor.
     *
     * @param level   log level
     * @param message log message
     */
    public ApiLogMessage(LogLevel level, String message) {
        super(level, message);
    }

    /**
     * One part of the API log message is the request.
     * It's data format is defined in this class
     */
    public class Request {

        @Getter
        private String requestMethod;

        @Getter
        private String baseUri;

        @Getter
        private String basePath;

        @Getter
        private Map<String, String> pathParams;

        @Getter
        private Map<String, String> queryParams;

        @Getter
        private Map<String, String>  formParams;

        @Getter
        private String bodyAsString;

        @Getter
        private String contentType;

        @Getter
        private Headers headers;

        /**
         * Set the corresponding values in the Request-Api-LogMessage.
         * Depending on the response of the API request from RESTAssured.
         *
         * @param request queryable request specification of RESTassured
         */
        public void setRequestAttributes(QueryableRequestSpecification request){

            requestMethod = request.getMethod();
            baseUri = (!Objects.equals(request.getBaseUri(), "")) ? request.getBaseUri() : null;
            basePath = (!Objects.equals(request.getBasePath(), "")) ? request.getBasePath() : null;
            pathParams = !request.getPathParams().isEmpty() ? request.getPathParams() : null;
            queryParams = !request.getQueryParams().isEmpty() ? request.getQueryParams() : null;
            headers = request.getHeaders();
            contentType = request.getContentType();
            formParams = request.getFormParams();
            bodyAsString = (request.getBody() != null) ? request.getBody().toString() : null;
        }
    }

    /**
     * One part of the API log message is the response.
     * It's data format is defined in this class
     */
    public class Response {

        @Getter
        private int statusCode;

        @Getter
        private Headers headers;

        @Getter
        private String contentType;

        @Getter
        private Map<String, String> cookies;

        @Getter
        private String bodyAsString;

        @Getter
        private long time;

        /**
         * Set the corresponding values in the Response-Api-LogMessage,
         * depending on the request the API call is based on provided by RESTAssured.
         *
         * @param response extractable response of RESTassured
         */
        public void setResponseAttributes(ExtractableResponse<io.restassured.response.Response> response){

            statusCode = response.statusCode();
            headers = response.headers();
            cookies = response.cookies();
            contentType = response.contentType();
            time = response.time();
            bodyAsString = response.body().asString();
        }
    }

    /**
     * Create a log message based on the attributes.
     *
     * @return Log message text
     */
    public String buildMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("REQUEST:\n\n");
        if (this.getRequest().getRequestMethod() != null) {
            sb.append("Request Method: " + this.getRequest().getRequestMethod() + "\n");
        }
        if (this.getRequest().getBaseUri() != null) {
            sb.append("Base URI: " + this.getRequest().getBaseUri() + "\n");
        }
        if (this.getRequest().getBasePath() != null) {
            sb.append("Base Path: " + this.getRequest().getBasePath() + "\n");
        }
        if (this.getRequest().getContentType() != null) {
            sb.append("Content Type: " + this.getRequest().getContentType() + "\n");
        }
        if (this.getRequest().getHeaders() != null && this.getRequest().getHeaders().size() > 0) {
            sb.append("\nHeaders: \n");
            for (Header header : this.getRequest().getHeaders()) {
                sb.append("\t" + header.getName() + " = " + header.getValue() + "\n");
            }
        }
        if (this.getRequest().getPathParams() != null && this.getRequest().getPathParams().size() > 0) {
            sb.append("\nPath Params: \n");
            for (Map.Entry<String, String> pathParam : this.getRequest().getPathParams().entrySet()) {
                sb.append("\t" + pathParam.getKey() + " = " + pathParam.getValue() + "\n");
            }
        }
        if (this.getRequest().getQueryParams() != null && this.getRequest().getQueryParams().size() > 0) {
            sb.append("\nQuery Params: \n");
            for (Map.Entry<String, String> queryParam : this.getRequest().getQueryParams().entrySet()) {
                sb.append("\t" + queryParam.getKey() + " = " + queryParam.getValue() + "\n");
            }
        }
        if (this.getRequest().getFormParams() != null && this.getRequest().getFormParams().size() > 0) {
            sb.append("\nForm Params: \n");
            for (Map.Entry<String, String> formParam : this.getRequest().getFormParams().entrySet()) {
                sb.append("\t" + formParam.getKey() + " = " + formParam.getValue() + "\n");
            }
        }
        if (this.getRequest().getBodyAsString() != null) {
            sb.append("\nBody:\n");
            sb.append(this.getRequest().getBodyAsString());
        }

        sb.append("\n\nRESPONSE\n\n");
        sb.append("Status Code: " + this.getResponse().getStatusCode() + "\n");
        sb.append("Time: " + this.getResponse().getTime() + "\n");
        if (this.getResponse().getContentType() != null) {
            sb.append("Content Type: " + this.getResponse().getContentType() + "\n");
        }
        if (this.getResponse().getHeaders() != null && this.getResponse().getHeaders().size() > 0) {
            sb.append("\nHeaders: \n");
            for (Header header : this.getResponse().getHeaders()) {
                sb.append("\t" + header.getName() + " = " + header.getValue() + "\n");

            }
        }
        if (this.getResponse().getCookies() != null && this.getResponse().getCookies().size() > 0) {
            sb.append("\nCookies: \n");
            for (Map.Entry<String, String> cookie : this.getResponse().getCookies().entrySet()) {
                sb.append("\t" + cookie.getKey() + " = " + cookie.getValue() + "\n");
            }
        }
        if (this.getResponse().getBodyAsString() != null) {
            sb.append("\nBody:\n");
            sb.append(this.getResponse().getBodyAsString());
        }

        return sb.toString();
    }
}
