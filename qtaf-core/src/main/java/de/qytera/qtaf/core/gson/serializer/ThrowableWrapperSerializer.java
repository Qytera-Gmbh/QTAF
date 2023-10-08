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
        return toJson(error);
    }

    /**
     * Converts a {@link ThrowableWrapper} into a JSON object.
     *
     * @param wrapper the wrapper
     * @return JSON object
     */
    public JsonObject toJson(ThrowableWrapper wrapper) {
        StackTraceElementSerializer stackTraceElementSerializer = new StackTraceElementSerializer();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", wrapper.getClassName());
        jsonObject.addProperty("message", wrapper.getMessage());
        JsonArray stackTraceElements = new JsonArray();
        for (StackTraceElement el : wrapper.getStackTrace()) {
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
