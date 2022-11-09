package de.qytera.qtaf.core.gson;

import com.google.gson.Gson;

/**
 * Helper class for creating GSON objects
 */
public class GsonHelper {
    /**
     * Gson object
     */
    private static final Gson gson = GsonFactory.getInstance();

    /**
     * From JSON to entity
     * @param json      JSON string
     * @param tClass    Entity class
     * @param <T>       Entity Type
     * @return  Entity object
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }
}
