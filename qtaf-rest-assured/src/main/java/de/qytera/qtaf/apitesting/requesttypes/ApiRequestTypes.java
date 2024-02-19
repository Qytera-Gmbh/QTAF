package de.qytera.qtaf.apitesting.requesttypes;

import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import io.restassured.specification.RequestSpecification;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public interface ApiRequestTypes {

    // TODO: Missing methods for: PATCH, TRACE, CONNECT Calls

    // ========== HEAD ==========

    /**
     * TODO: Fix Docu
     * Send a HEAD request
     * @return Response object
     */
    @NotNull
    default ApiRequestType headRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.HEAD);

            // return RequestSenderOptions::head;
            return req.head();
        };
    }

    /*
    @NotNull
    default ApiRequestType headRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.HEAD);
            logMessage.getAction().setRequestUri(uri);

            return req.head(uri);
        };
    }

    @NotNull
    default ApiRequestType headRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.HEAD);
            logMessage.getAction().setRequestUrl(url);

            return req.head(url);
        };
    }

    @NotNull
    default ApiRequestType headRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.HEAD);
            logMessage.getAction().setRequestPathParams(pathParamsList);

            return req.head(path, pathParams);
        };
    }
     */

    // ========== OPTION ==========

    /**
     * TODO: Fix Docu
     * Send an OPTIONS request
     * @return Response object
     */

    @NotNull
    default ApiRequestType optionsRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.OPTIONS);

            // return RequestSenderOptions::options;
            return req.options();
        };
    }

    /*
    @NotNull
    default ApiRequestType optionsRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.OPTIONS);
            logMessage.getAction().setRequestUri(uri);
            return req.options(uri);
        };
    }

    @NotNull
    default ApiRequestType optionsRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.OPTIONS);
            logMessage.getAction().setRequestUrl(url);
            return req.options(url);
        };
    }

    @NotNull
    default ApiRequestType optionsRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.OPTIONS);
            logMessage.getAction().setRequestPathParams(pathParamsList);

            return req.options(path, pathParams);
        };
    }

     */

    // ========== GET ==========

    /**
     * TODO: Fix Docu
     * Send a GET request
     * @return Response object
     */

    // @NotNull
    default ApiRequestType getRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.GET);

            // return RequestSenderOptions::get;
            return req.get();
        };
    }


    /*
    @NotNull
    default ApiRequestType getRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.GET);
            logMessage.getAction().setRequestUri(uri);

            return req.get(uri);
        };
    }

    @NotNull
    default ApiRequestType getRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.GET);
            logMessage.getAction().setRequestUrl(url);

            return req.get(url);
        };
    }

    @NotNull
    default ApiRequestType getRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            // TODO: Specify desired behaviour and adapt implementation
            // Option 1: the path passed must be complete. this can be confusing if you have already set the basePath or baseUri beforehand.
            // Option 2: an incomplete path is attempted to be calculated from the already set baseUri and basePath
            // Option 3: an exception is thrown if baseUir or basePath have already been set

            // TODO: potential Bug-> Methode doesn't seem to work when basePath or baseUri got set

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.GET);
            logMessage.getAction().setRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setRequestPathParams(pathParamsList);

            // TODO: potential Fix:

            String newPath = path;

            if (logMessage.getRequest().getBasePath() != null){
                newPath = logMessage.getRequest().getBasePath() + path;
            }
            if (logMessage.getRequest().getBaseUri() != null){
                newPath = "";
                newPath = logMessage.getRequest().getBaseUri() + path;
            }

            //return req.get(newPath, pathParams);
        };
    }
    */


    // ========== POST ==========

    /**
     * Send a POST request
     * @return Response object
     */
    @NotNull
    default ApiRequestType postRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.POST);

            // return RequestSenderOptions::get;
            return req.post();
        };
    }

    /*
    @NotNull
    default ApiRequestType postRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.POST);
            logMessage.getAction().setRequestUri(uri);

            return req.post(uri);
        };
    }


    @NotNull
    default ApiRequestType postRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.POST);
            logMessage.getAction().setRequestUrl(url);

            return req.post(url);
        };
    }

    @NotNull
    default ApiRequestType postRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.POST);
            logMessage.getAction().setRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setRequestPathParams(pathParamsList);

            return req.post(path, pathParams);
        };
    }
     */


    // ========== PUT ==========


    /**
     * Send a PUT request
     * @return Response object
     */

    @NotNull
    default ApiRequestType putRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.PUT);

            return req.put();
        };
    }
    /*
    @NotNull
    default ApiRequestType putRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.PUT);
            logMessage.getAction().setRequestUri(uri);

            return req.put(uri);
        };
    }

    @NotNull
    default ApiRequestType putRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.PUT);
            logMessage.getAction().setRequestUrl(url);

            return req.put(url);
        };
    }

    @NotNull
    default ApiRequestType putRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.PUT);
            logMessage.getAction().setRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setRequestPathParams(pathParamsList);

            return req.put(path, pathParams);
        };
    }
     */

    // ========== DELETE ==========


    /**
     * Send a DELETE request
     * @return Response object
     */
    @NotNull
    default ApiRequestType deleteRequest() {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.DELETE);

            return req.delete();
        };
    }

    /*
    @NotNull
    @Contract(pure = true)
    default ApiAction deleteRequest() {
        return RequestSenderOptions::delete;
    }

    @NotNull
    default ApiRequestType deleteRequest(URI uri) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.DELETE);
            logMessage.getAction().setRequestUri(uri);

            return req.delete(uri);
        };
    }

    @NotNull
    default ApiRequestType deleteRequest(URL url) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.DELETE);
            logMessage.getAction().setRequestUrl(url);

            return req.delete(url);
        };
    }

    @NotNull
    default ApiRequestType deleteRequest(String path, Object... pathParams) {
        return (RequestSpecification req, ApiLogMessage logMessage) -> {

            logMessage.getAction().setRequestType(ApiLogMessage.Action.RequestType.DELETE);
            logMessage.getAction().setRequestPath(path);
            ArrayList<Object> pathParamsList = new ArrayList<>(Arrays.asList(pathParams));
            logMessage.getAction().setRequestPathParams(pathParamsList);

            return req.delete(path, pathParams);
        };
    }
     */
}
