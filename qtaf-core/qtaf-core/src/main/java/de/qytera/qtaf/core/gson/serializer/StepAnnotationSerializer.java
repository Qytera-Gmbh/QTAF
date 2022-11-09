package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.qytera.qtaf.core.guice.annotations.Step;

import java.lang.reflect.Type;

/**
 * Converts Step annotation to JSON
 */
public class StepAnnotationSerializer implements JsonSerializer<Step> {
    @Override
    public JsonElement serialize(
            Step step,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", step.name());
        jsonObject.addProperty("description", step.description());

        return jsonObject;
    }
}
