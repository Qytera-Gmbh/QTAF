package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Converts StackTraceElement object to JSON
 */
public class StackTraceElementSerializer implements JsonSerializer<StackTraceElement> {
    @Override
    public JsonElement serialize(
            StackTraceElement stackTraceElement,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("moduleName", stackTraceElement.getModuleName());
        jsonObject.addProperty("moduleVersion", stackTraceElement.getModuleVersion());
        jsonObject.addProperty("className", stackTraceElement.getClassName());
        jsonObject.addProperty("classLoaderName", stackTraceElement.getClassLoaderName());
        jsonObject.addProperty("methodName", stackTraceElement.getMethodName());
        jsonObject.addProperty("fileName", stackTraceElement.getFileName());
        jsonObject.addProperty("lineNumber", stackTraceElement.getLineNumber());

        return jsonObject;
    }
}
