package de.qytera.qtaf.apitesting.action;

import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URL;

public interface ApiActions {
    /**
     * Send a HEAD request
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction headRequest() {
        return RequestSenderOptions::head;
    }

    /**
     * Send a HEAD request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction headRequest(String path, Object... pathParams) {
        return (RequestSpecification req) -> req.head(path, pathParams);
    }

    /**
     * Send a HEAD request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction headRequest(URI uri) {
        return (RequestSpecification req) -> req.head(uri);
    }

    /**
     * Send a HEAD request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction headRequest(URL url) {
        return (RequestSpecification req) -> req.head(url);
    }

    /**
     * Send an OPTIONS request
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction optionsRequest() {
        return RequestSenderOptions::options;
    }

    /**
     * Send an OPTIONS request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction optionsRequest(String path, Object... pathParams) {
        return (RequestSpecification req) -> req.options(path, pathParams);
    }

    /**
     * Send an OPTIONS request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction optionsRequest(URI uri) {
        return (RequestSpecification req) -> req.options(uri);
    }

    /**
     * Send an OPTIONS request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction optionsRequest(URL url) {
        return (RequestSpecification req) -> req.options(url);
    }

    /**
     * Send a GET request
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction getRequest() {
        return RequestSenderOptions::get;
    }

    /**
     * Send a GET request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction getRequest(String path, Object... pathParams) {
        return (RequestSpecification req) -> req.get(path, pathParams);
    }

    /**
     * Send a GET request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction getRequest(URI uri) {
        return (RequestSpecification req) -> req.get(uri);
    }

    /**
     * Send a GET request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction getRequest(URL url) {
        return (RequestSpecification req) -> req.get(url);
    }

    /**
     * Send a POST request
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction postRequest() {
        return RequestSenderOptions::post;
    }

    /**
     * Send a POST request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction postRequest(String path, Object... pathParams) {
        return (RequestSpecification req) -> req.post(path, pathParams);
    }

    /**
     * Send a POST request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction postRequest(URI uri) {
        return (RequestSpecification req) -> req.post(uri);
    }

    /**
     * Send a POST request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction postRequest(URL url) {
        return (RequestSpecification req) -> req.post(url);
    }

    /**
     * Send a PUT request
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction putRequest() {
        return RequestSenderOptions::put;
    }

    /**
     * Send a PUT request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction putRequest(String path, Object... pathParams) {
        return (RequestSpecification req) -> req.put(path, pathParams);
    }

    /**
     * Send a PUT request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction putRequest(URI uri) {
        return (RequestSpecification req) -> req.put(uri);
    }

    /**
     * Send a PUT request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction putRequest(URL url) {
        return (RequestSpecification req) -> req.put(url);
    }

    /**
     * Send a DELETE request
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction deleteRequest() {
        return RequestSenderOptions::delete;
    }

    /**
     * Send a DELETE request
     * @param path       API path
     * @param pathParams path parameters
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction deleteRequest(String path, Object... pathParams) {
        return (RequestSpecification req) -> req.delete(path, pathParams);
    }

    /**
     * Send a DELETE request
     * @param uri URI object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction deleteRequest(URI uri) {
        return (RequestSpecification req) -> req.delete(uri);
    }

    /**
     * Send a DELETE request
     * @param url URL object
     * @return Response object
     */
    @NotNull
    @Contract(pure = true)
    default ApiAction deleteRequest(URL url) {
        return (RequestSpecification req) -> req.delete(url);
    }
}
