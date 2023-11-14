package de.qytera.qtaf.apitesting.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.http.*;
import lombok.Getter;
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
    private String baseUri;

    private String basePath;

    private Map<String, ?> pathParams;

    private Map<String, ?> queryParams;

    private Map<String, ?>  formParams;

    private String bodyString;

    private Object bodyObject;

    private File bodyFile;

    private String contentTypeString;

    private ContentType contentType;

    private File file;

    private String fileString;

    private Header header;

    private Headers headers;

    private String beareToken;

    private String cookieKey;

    private String cookieValue;

    private Cookie cookie;

    private Cookies cookies;

    private Map<String, ?> cookiesMap;

    private JSONObject jsonObject;

    private String multipartString;

    private Object multipartObject;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Map<String, ?> getPathParams() {
        return pathParams;
    }

    public void setPathParams(Map<String, ?> pathParams) {
        this.pathParams = pathParams;
    }

    public Map<String, ?> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, ?> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, ?> getFormParams() {
        return formParams;
    }

    public void setFormParams(Map<String, ?> formParams) {
        this.formParams = formParams;
    }

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }

    public Object getBodyObject() {
        return bodyObject;
    }

    public void setBodyObject(Object bodyObject) {
        this.bodyObject = bodyObject;
    }

    public File getBodyFile() {
        return bodyFile;
    }

    public void setBodyFile(File bodyFile) {
        this.bodyFile = bodyFile;
    }

    public String getContentTypeString() {
        return contentTypeString;
    }

    public void setContentTypeString(String contentTypeString) {
        this.contentTypeString = contentTypeString;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileString() {
        return fileString;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public String getBeareToken() {
        return beareToken;
    }

    public void setBeareToken(String beareToken) {
        this.beareToken = beareToken;
    }

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public Map<String, ?> getCookiesMap() {
        return cookiesMap;
    }

    public void setCookiesMap(Map<String, ?> cookiesMap) {
        this.cookiesMap = cookiesMap;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getMultipartString() {
        return multipartString;
    }

    public void setMultipartString(String multipartString) {
        this.multipartString = multipartString;
    }

    public Object getMultipartObject() {
        return multipartObject;
    }

    public void setMultipartObject(Object multipartObject) {
        this.multipartObject = multipartObject;
    }
}
