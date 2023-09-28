package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Converts StackTraceElement object to JSON.
 */
public class StackTraceElementSerializer implements IQtafJsonSerializer, JsonSerializer<StackTraceElement> {
    @Override
    public JsonElement serialize(
            StackTraceElement stackTraceElement,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        return toJson(stackTraceElement);
    }

    /**
     * This method converts a StackTraceElement into a JSON object.
     *
     * @param stackTraceElement stack trace element
     * @return  json object
     */
    public JsonObject toJson(StackTraceElement stackTraceElement) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("moduleName", stackTraceElement.getModuleName());
        jsonObject.addProperty("moduleVersion", stackTraceElement.getModuleVersion());
        jsonObject.addProperty("className", stackTraceElement.getClassName());
        jsonObject.addProperty("classLoaderName", stackTraceElement.getClassLoaderName());
        jsonObject.addProperty("methodName", stackTraceElement.getMethodName());
        jsonObject.addProperty("fileName", stackTraceElement.getFileName());
        jsonObject.addProperty("lineNumber", stackTraceElement.getLineNumber());
        jsonObject.addProperty("isNativeMethod", stackTraceElement.isNativeMethod());

        return jsonObject;
    }

    @Override
    public Class<StackTraceElementSerializer> getSerializedObjectClass() {
        return StackTraceElementSerializer.class;
    }
}
