package de.qytera.qtaf.apitesting.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.http.*;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ApiLogMessage extends LogMessage {

    @Getter @Setter
    Request request = new Request();

    @Getter @Setter
    Action action = new Action();

    @Getter @Setter
    Response response = new Response();

    /*
    @Getter @Setter
    private Status status = Status.PENDING;
     */

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

        @Getter @Setter
        private String baseUri;

        @Getter @Setter
        private String basePath;

        @Getter @Setter
        private Map<String, ?> pathParams;

        @Getter @Setter
        private Map<String, ?> queryParams;

        @Getter @Setter
        private Map<String, ?>  formParams;

        @Getter @Setter
        private String bodyString;

        @Getter @Setter
        private Object bodyObject;

        /* TODO
        @Getter @Setter
        private File bodyFile;
        */

        @Getter @Setter
        private String contentTypeString;

        @Getter @Setter
        private ContentType contentType;

        /* TODO
        @Getter @Setter
        private File file;
         */

        @Getter @Setter
        private String fileString;

        @Getter @Setter
        private Header header;

        /* TODO
        @Getter @Setter
        private Headers headers;
         */

        @Getter @Setter
        private Map<String, ?> headers;

        @Getter @Setter
        private String beareToken;

        @Getter @Setter
        private String cookieKey;

        @Getter @Setter
        private String cookieValue;

        @Getter @Setter
        private Cookie cookie;

        @Getter @Setter
        private Cookies cookies;

        @Getter @Setter
        private Map<String, ?> cookiesMap;

        @Getter @Setter
        private JSONObject jsonObject;

        @Getter @Setter
        private String multipartString;

        @Getter @Setter
        private Object multipartObject;

    }

    public class Action {

        @Getter @Setter
        private RequestType requestType;

        @Getter @Setter
        private String requestPath;

        @Getter @Setter
        private List<Object> requestPathParams;

        @Getter @Setter
        private URI requestUri;

        @Getter @Setter
        private URL requestUrl;


        /*
        @Getter @Setter
        private String headRequestPath;

        @Getter @Setter
        private List<Object> headRequestPathParams;

         */
        /*
        @Getter @Setter
        private URI headRequestUri;

        @Getter @Setter
        private URL headRequestUrl;

         */
        /*
        @Getter @Setter
        private String optionsRequestPath;

        @Getter @Setter
        private List<Object> optionsRequestPathParams;

        @Getter @Setter
        private URI optionRequestUri;

        @Getter @Setter
        private URL optionRequestUrl;

        @Getter @Setter
        private String getRequestPath;

        @Getter @Setter
        private List<Object> getRequestPathParams;

        @Getter @Setter
        private URI getRequestUri;

        @Getter @Setter
        private URL getRequestUrl;

        @Getter @Setter
        private String postRequestPath;

        @Getter @Setter
        private List<Object> postRequestPathParams;

        @Getter @Setter
        private URI postRequestUri;

        @Getter @Setter
        private URL postRequestUrl;

        @Getter @Setter
        private String putRequestPath;

        @Getter @Setter
        private List<Object> putRequestPathParams;

        @Getter @Setter
        private URI putRequestUri;

        @Getter @Setter
        private URL putRequestUrl;

        @Getter @Setter
        private String deleteRequestPath;

        @Getter @Setter
        private List<Object> deleteRequestPathParams;

        @Getter @Setter
        private URI deleteRequestUri;

        @Getter @Setter
        private URL deleteRequestUrl;

         */

        public enum RequestType {
            /**
             * This request type is used to request a resource from the server by specifying a URI.
             * Content can also be transferred to the server as arguments in the URI,
             * but according to the standard, a GET request should only retrieve data and have no other effects
             */
            GET,

            /**
             * sends data (e.g. the content of a file) to the server for further processing
             */
            POST,
            HEAD,
            PUT,
            PATCH,
            DELETE,
            TRACE,
            OPTIONS,
            CONNECT,


        }
        /*
        @Getter @Setter
        private String headRequestPath;

        @Getter @Setter
        private List<Object> headRequestPathParams;

        @Getter @Setter
        private URI headRequestUri;

        @Getter @Setter
        private URL headRequestUrl;

        @Getter @Setter
        private String optionsRequestPath;

        @Getter @Setter
        private List<Object> optionsRequestPathParams;

        @Getter @Setter
        private URI optionRequestUri;

        @Getter @Setter
        private URL optionRequestUrl;

        @Getter @Setter
        private String getRequestPath;

        @Getter @Setter
        private List<Object> getRequestPathParams;

        @Getter @Setter
        private URI getRequestUri;

        @Getter @Setter
        private URL getRequestUrl;

        @Getter @Setter
        private String postRequestPath;

        @Getter @Setter
        private List<Object> postRequestPathParams;

        @Getter @Setter
        private URI postRequestUri;

        @Getter @Setter
        private URL postRequestUrl;

        @Getter @Setter
        private String putRequestPath;

        @Getter @Setter
        private List<Object> putRequestPathParams;

        @Getter @Setter
        private URI putRequestUri;

        @Getter @Setter
        private URL putRequestUrl;

        @Getter @Setter
        private String deleteRequestPath;

        @Getter @Setter
        private List<Object> deleteRequestPathParams;

        @Getter @Setter
        private URI deleteRequestUri;

        @Getter @Setter
        private URL deleteRequestUrl;

         */
    }


    public class Response {

        private ExtractableResponse<io.restassured.response.Response> response;
        private int statusCode;
        private Headers headers;
        private Map<String, String> cookies;
        private String bodyAsString;

        // private ResponseBodyExtractionOptions body; TODO
        private long time;


        public void setResponseAndAttributes(ExtractableResponse<io.restassured.response.Response> response){
            this.response = response;
            statusCode = response.statusCode();
            headers = response.headers();
            cookies = response.cookies();
            bodyAsString = response.body().asString();
            // body = response.body();
            time = response.time();
        }
    }

    /**
     * Step status.
     */
    public enum Status {
        /**
         * The step is still pending execution.
         */
        PENDING,
        /**
         * The step was executed successfully.
         */
        PASS,
        /**
         * There were errors during step execution.
         */
        ERROR,
        /**
         * The step's execution was skipped.
         */
        SKIPPED,
        /**
         * The step status could not be determined.
         */
        UNDEFINED,
    }
}
