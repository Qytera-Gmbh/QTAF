package de.qytera.qtaf.apitesting.action;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public interface ApiActions {
    /**
     * Send a HEAD request
     * @return Response object
     */
    /* @NotNull
    @Contract(pure = true)
    default ApiAction headRequest() {
        return RequestSenderOptions::head;
    }
     */

    /**
     * Send a HEAD request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    default ApiAction headRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setHeadRequestPathParams(pathParamsList);

            return req.head(path, pathParams);
        };
    }

    /**
     * Send a HEAD request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    default ApiAction headRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setHeadRequestUri(uri);

            return req.head(uri);
        };
    }

    /**
     * Send a HEAD request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    default ApiAction headRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setHeadRequestUrl(url);

            return req.head(url);
        };
    }

    /**
     * Send an OPTIONS request
     * @return Response object
     */
    /* TODO
    @NotNull
    @Contract(pure = true)
    default ApiAction optionsRequest() {
        return RequestSenderOptions::options;
    }
     */

    /**
     * Send an OPTIONS request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    default ApiAction optionsRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setOptionsRequestPathParams(pathParamsList);

            return req.options(path, pathParams);
        };
    }

    /**
     * Send an OPTIONS request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    default ApiAction optionsRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setOptionRequestUri(uri);
            return req.options(uri);
        };
    }

    /**
     * Send an OPTIONS request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    default ApiAction optionsRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setOptionRequestUrl(url);
            return req.options(url);
        };
    }

    /**
     * Send a GET request
     * @return Response object
     */
    /* TODO
    @NotNull
    @Contract(pure = true)
    default ApiAction getRequest() {
        return RequestSenderOptions::get;
    }
     */

    /**
     * Send a GET request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    default ApiAction getRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setGetRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setGetRequestPathParams(pathParamsList);

            return req.get(path, pathParams);
        };
    }

    /**
     * Send a GET request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    default ApiAction getRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setGetRequestUri(uri);
            return req.get(uri);
        };
    }

    /**
     * Send a GET request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    default ApiAction getRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setGetRequestUrl(url);
            return req.get(url);
        };
    }

    /**
     * Send a POST request
     * @return Response object
     */

    /* TODO
    @NotNull
    @Contract(pure = true)
    default ApiAction postRequest() {
        return RequestSenderOptions::post;
    }

     */

    /**
     * Send a POST request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    default ApiAction postRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setPostRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setPostRequestPathParams(pathParamsList);

            return req.post(path, pathParams);
        };
    }

    /**
     * Send a POST request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    default ApiAction postRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setPostRequestUri(uri);
            return req.post(uri);
        };
    }

    /**
     * Send a POST request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    default ApiAction postRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setPostRequestUrl(url);
            return req.post(url);
        };
    }

    /**
     * Send a PUT request
     * @return Response object
     */
    /* TODO
    @NotNull
    @Contract(pure = true)
    default ApiAction putRequest() {
        return RequestSenderOptions::put;
    }
    */

    @NotNull
    default ApiAction putRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            return req.put();
            // RequestSenderOptions::put;
        };
    }


    /**
     * Send a PUT request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    default ApiAction putRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setPutRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setPutRequestPathParams(pathParamsList);

            return req.put(path, pathParams);
        };
    }

    /**
     * Send a PUT request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    default ApiAction putRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setPutRequestUri(uri);
            return req.put(uri);
        };
    }

    /**
     * Send a PUT request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    default ApiAction putRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setPutRequestUrl(url);
            return req.put(url);
        };
    }

    /**
     * Send a DELETE request
     * @return Response object
     */

    /* TODO
    @NotNull
    @Contract(pure = true)
    default ApiAction deleteRequest() {
        return RequestSenderOptions::delete;
    }
    */

    /**
     * Send a DELETE request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    default ApiAction deleteRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setDeleteRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setDeleteRequestPathParams(pathParamsList);

            return req.delete(path, pathParams);
        };
    }

    /**
     * Send a DELETE request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    default ApiAction deleteRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setDeleteRequestUri(uri);
            return req.delete(uri);
        };
    }

    /**
     * Send a DELETE request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    default ApiAction deleteRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {
            logMessage.getAction().setDeleteRequestUrl(url);
            return req.delete(url);
        };
    }
}
