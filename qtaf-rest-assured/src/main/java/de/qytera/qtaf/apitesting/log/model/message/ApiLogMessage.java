package de.qytera.qtaf.apitesting.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.http.*;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Map;

public class ApiLogMessage extends LogMessage {
    /**
     * Constructor.
     *
     * @param level   log level
     * @param message log message
     */
    public ApiLogMessage(LogLevel level, String message) {
        super(level, message);
    }
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

    @Getter @Setter
    private File bodyFile;

    @Getter @Setter
    private String contentTypeString;

    @Getter @Setter
    private ContentType contentType;

    @Getter @Setter
    private File file;

    @Getter @Setter
    private String fileString;

    @Getter @Setter
    private Header header;

    @Getter @Setter
    private Headers headers;

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
