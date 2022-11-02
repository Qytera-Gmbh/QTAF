package de.qytera.qtaf.core.gson;

import com.google.gson.Gson;

public class GsonHelper {
    /**
     * Gson object
     */
    private static Gson gson = GsonFactory.getInstance();

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
