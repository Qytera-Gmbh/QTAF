package de.qytera.qtaf.core.net.http;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

/**
 * HTTP DAO
 */
public class HTTPDao {
    /**
     * Web Client that can perform HTTP Requests
     */
    protected Client client = new Client();

    /**
     * Host: domain name (i.e. "https://www.google.de") or IP-Address (i.e. "8.8.8.8")
     */
    protected String host;

    /**
     * Authorization header value
     */
    protected String authorizationHeaderValue = null;

    /**
     * String to JSON converter
     */
    protected final Gson gson = new Gson();

    /**
     * Constructor
     *
     * @param host host name
     */
    public HTTPDao(String host) {
        this.host = host;
    }

    /**
     * Get authorizationHeaderValue
     *
     * @return authorizationHeaderValue
     */
    public String getAuthorizationHeaderValue() {
        return authorizationHeaderValue;
    }

    /**
     * Set authorizationHeaderValue
     *
     * @param authorizationHeaderValue AuthorizationHeaderValue
     * @return this
     */
    public HTTPDao setAuthorizationHeaderValue(String authorizationHeaderValue) {
        this.authorizationHeaderValue = authorizationHeaderValue;
        return this;
    }

    /**
     * Perform GET Requests
     *
     * @param path Url Path
     * @param c    Class that the result gets mapped to
     * @param <T>  Type that the Result gets mapped to
     * @return Response of Type T
     */
    protected <T> T get(String path, String mediaType, Class<T> c) {
        String url = host + path;

        return client.resource(url).getRequestBuilder()
                .header("Authorization", this.getAuthorizationHeaderValue())
                .type(mediaType)
                .get(c);
    }

    /**
     * Perform GET Requests
     *
     * @param path      Url Path
     * @param mediaType Media Type
     * @return Response
     */
    public ClientResponse get(String path, String mediaType) {
        return this.get(path, mediaType, ClientResponse.class);
    }

    /**
     * Perform GET Requests
     *
     * @param path      Url Path
     * @param mediaType Media Type
     * @return Response
     */
    public String getAsString(String path, String mediaType) {
        return this.get(path, mediaType, String.class);
    }

    /**
     * Perform POST Requests
     *
     * @param path          Url Path
     * @param c             Class that the result gets mapped to
     * @param <T>           Type that the Result gets mapped to
     * @param requestEntity Request body
     * @return Response of Type T
     */
    protected <T> T post(String path, String mediaType, Class<T> c, Object requestEntity) {
        String url = host + path;

        return client.resource(url).getRequestBuilder()
                .header("Authorization", this.getAuthorizationHeaderValue())
                .type(mediaType)
                .post(c, requestEntity);
    }

    /**
     * Perform POST Requests
     *
     * @param path          Url Path
     * @param mediaType     Media Type
     * @param requestEntity Request body
     * @return Response
     */
    public ClientResponse post(String path, String mediaType, Object requestEntity) {
        return this.post(path, mediaType, ClientResponse.class, requestEntity);
    }

    /**
     * Perform POST Requests
     *
     * @param path          Url Path
     * @param mediaType     Media Type
     * @param requestEntity Request body
     * @return Response
     */
    public String postAsString(String path, String mediaType, Object requestEntity) {
        return this.post(path, mediaType, String.class, requestEntity);
    }

    /**
     * Perform PUT Requests
     *
     * @param path          Url Path
     * @param c             Class that the result gets mapped to
     * @param <T>           Type that the Result gets mapped to
     * @param requestEntity Request body
     * @return Response of Type T
     */
    protected <T> T put(String path, String mediaType, Class<T> c, Object requestEntity) {
        String url = host + path;

        return client.resource(url).getRequestBuilder()
                .header("Authorization", this.getAuthorizationHeaderValue())
                .type(mediaType)
                .put(c, requestEntity);
    }

    /**
     * Perform PUT Requests
     *
     * @param path          Url Path
     * @param mediaType     Media Type
     * @param requestEntity Request body
     * @return Response
     */
    public ClientResponse put(String path, String mediaType, Object requestEntity) {
        return this.put(path, mediaType, ClientResponse.class, requestEntity);
    }

    /**
     * Perform PUT Requests
     *
     * @param path          Url Path
     * @param mediaType     Media Type
     * @param requestEntity Request body
     * @return Response
     */
    public String putAsString(String path, String mediaType, Object requestEntity) {
        return this.put(path, mediaType, String.class, requestEntity);
    }

    /**
     * Perform DELETE Requests
     *
     * @param path Url Path
     * @param c    Class that the result gets mapped to
     * @param <T>  Type that the Result gets mapped to
     * @return Response of Type T
     */
    protected <T> T delete(String path, String mediaType, Class<T> c) {
        String url = host + path;

        return client.resource(url).getRequestBuilder()
                .header("Authorization", this.getAuthorizationHeaderValue())
                .type(mediaType)
                .delete(c);
    }

    /**
     * Perform DELETE Requests
     *
     * @param path      Url Path
     * @param mediaType Media Type
     * @return Response
     */
    public ClientResponse delete(String path, String mediaType) {
        return this.delete(path, mediaType, ClientResponse.class);
    }

    /**
     * Perform DELETE Requests
     *
     * @param path      Url Path
     * @param mediaType Media Type
     * @return Response
     */
    public String deleteAsString(String path, String mediaType) {
        return this.delete(path, mediaType, String.class);
    }
}
