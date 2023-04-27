package de.qytera.qtaf.http;

import com.google.gson.Gson;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

/**
 * HTTP DAO
 */
public class HTTPDao {
    /**
     * Web Client that can perform HTTP Requests
     */
    protected Client client = ClientBuilder.newClient();

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
        try (Response response = this.get(path, mediaType)) {
            return response.readEntity(c);
        }
    }

    /**
     * Perform GET Requests
     *
     * @param path      Url Path
     * @param mediaType Media Type
     * @return Response
     */
    public Response get(String path, String mediaType) {
        String url = host + path;
        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, this.getAuthorizationHeaderValue())
                .accept(mediaType)
                .get();
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
        try (Response response = this.post(path, mediaType, requestEntity)) {
            return response.readEntity(c);
        }
    }

    /**
     * Perform POST Requests
     *
     * @param path          Url Path
     * @param mediaType     Media Type
     * @param requestEntity Request body
     * @return Response
     */
    public Response post(String path, String mediaType, Object requestEntity) {
        String url = host + path;
        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, this.getAuthorizationHeaderValue())
                .post(Entity.entity(requestEntity, mediaType));
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
        try (Response response = this.put(path, mediaType, requestEntity)) {
            return response.readEntity(c);
        }
    }

    /**
     * Perform PUT Requests
     *
     * @param path          Url Path
     * @param mediaType     Media Type
     * @param requestEntity Request body
     * @return Response
     */
    public Response put(String path, String mediaType, Object requestEntity) {
        String url = host + path;
        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, this.getAuthorizationHeaderValue())
                .put(Entity.entity(requestEntity, mediaType));
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
        try (Response response = this.delete(path, mediaType)) {
            return response.readEntity(c);
        }
    }

    /**
     * Perform DELETE Requests
     *
     * @param path      Url Path
     * @param mediaType Media Type
     * @return Response
     */
    public Response delete(String path, String mediaType) {
        String url = host + path;

        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, this.getAuthorizationHeaderValue())
                .accept(mediaType)
                .delete();
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
