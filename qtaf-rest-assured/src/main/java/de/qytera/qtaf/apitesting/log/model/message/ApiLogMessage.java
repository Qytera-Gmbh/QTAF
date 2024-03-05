package de.qytera.qtaf.apitesting.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.http.*;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.QueryableRequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ApiLogMessage extends LogMessage {

    @Getter @Setter
    Request request = new Request();

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

        public void setResponseAttributes(ExtractableResponse<io.restassured.response.Response> response){

            statusCode = response.statusCode();
            headers = response.headers();
            cookies = response.cookies();
            contentType = response.contentType();
            time = response.time();
            bodyAsString = response.body().asString();
        }
    }
}
