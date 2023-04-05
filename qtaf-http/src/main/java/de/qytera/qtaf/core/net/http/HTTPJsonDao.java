package de.qytera.qtaf.core.net.http;

import de.qytera.qtaf.core.gson.GsonHelper;

import javax.ws.rs.core.MediaType;

public class HTTPJsonDao extends HTTPDao {

    /**
     * Constructor
     *
     * @param host host name
     */
    public HTTPJsonDao(String host) {
        super(host);
    }

    /**
     * Send GET Request
     *
     * @param path   URL path
     * @param tClass Entity class
     * @param <T>    Entity Type
     * @return Entity object
     */
    public <T> T get(String path, Class<T> tClass) {
        String json = super.getAsString(path, MediaType.APPLICATION_JSON);
        return GsonHelper.fromJson(json, tClass);
    }

    /**
     * Send POST Request
     *
     * @param path          URL path
     * @param tClass        Entity class
     * @param <T>           Entity Type
     * @param requestEntity Request payload
     * @return Entity object
     */
    public <T> T post(String path, Class<T> tClass, Object requestEntity) {
        String json = super.postAsString(path, MediaType.APPLICATION_JSON, gson.toJson(requestEntity));
        return GsonHelper.fromJson(json, tClass);
    }

    /**
     * Send PUT Request
     *
     * @param path          URL path
     * @param tClass        Entity class
     * @param <T>           Entity Type
     * @param requestEntity Request payload
     * @return Entity object
     */
    public <T> T put(String path, Class<T> tClass, Object requestEntity) {
        String json = super.putAsString(path, MediaType.APPLICATION_JSON, gson.toJson(requestEntity));
        return GsonHelper.fromJson(json, tClass);
    }

    /**
     * Send DELETE Request
     *
     * @param path   URL path
     * @param tClass Entity class
     * @param <T>    Entity Type
     * @return Entity object
     */
    public <T> T delete(String path, Class<T> tClass) {
        String json = super.deleteAsString(path, MediaType.APPLICATION_JSON);
        return GsonHelper.fromJson(json, tClass);
    }

}
