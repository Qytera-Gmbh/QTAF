package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.*;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;

import java.lang.reflect.Type;

/**
 * Converts ThrowableWrapper object to JSON.
 */
public class ThrowableWrapperSerializer implements IQtafJsonSerializer, JsonSerializer<ThrowableWrapper> {
    @Override
    public JsonElement serialize(
            ThrowableWrapper error,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        StackTraceElementSerializer stackTraceElementSerializer = new StackTraceElementSerializer();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("type", error.getClassName());
        jsonObject.addProperty("message", error.getMessage());
        JsonArray stackTraceElements = new JsonArray();

        for (StackTraceElement el : error.getStackTrace()) {
            stackTraceElements.add(stackTraceElementSerializer.toJson(el));
        }

        jsonObject.add("stackTrace", stackTraceElements);

        return jsonObject;
    }

    @Override
    public Class<ThrowableWrapper> getSerializedObjectClass() {
        return ThrowableWrapper.class;
    }
}
