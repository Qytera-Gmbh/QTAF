package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Converts Throwable object to JSON
 */
public class ThrowableSerializer implements JsonSerializer<Throwable> {
    @Override
    public JsonElement serialize(
            Throwable error,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", error.getClass().getName());
        jsonObject.addProperty("message", error.getMessage());

        return jsonObject;
    }
}
