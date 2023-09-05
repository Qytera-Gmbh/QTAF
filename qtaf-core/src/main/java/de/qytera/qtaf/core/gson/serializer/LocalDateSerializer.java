package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Serializer class for LocalDate objects.
 */
public class LocalDateSerializer implements IQtafJsonSerializer, JsonSerializer<LocalDate> {

    @Override
    public Class<?> getSerializedObjectClass() {
        return LocalDate.class;
    }

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDate.toString());
    }
}
