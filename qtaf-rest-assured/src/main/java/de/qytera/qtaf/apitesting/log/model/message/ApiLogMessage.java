package de.qytera.qtaf.apitesting.log.model.message;

import com.google.gson.Gson;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.http.*;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.testng.Assert;

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
        private Map<String, Object> pathParams;

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



        public enum RequestType {
            GET,
            POST,
            HEAD,
            PUT,
            PATCH,
            DELETE,
            TRACE,
            OPTIONS,
            CONNECT,


        }

    }


    public class Response {

        @Getter
        private int statusCode;
        @Getter
        private Headers headers;

        @Getter
        private Map<String, String> cookies;

        @Getter
        private String bodyAsString;

        @Getter
        private String body;

        @Getter
        private long time;


        public void setResponseAttributes(ExtractableResponse<io.restassured.response.Response> response){
            statusCode = response.statusCode();
            headers = response.headers();
            cookies = response.cookies();
            time = response.time();
            // body = response.body(); TODO
            bodyAsString = response.body().asString();
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
