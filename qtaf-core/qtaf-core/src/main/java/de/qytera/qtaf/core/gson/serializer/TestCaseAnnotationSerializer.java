package de.qytera.qtaf.core.gson.serializer;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Converts TestCase annotation to JSON
 */
public class TestCaseAnnotationSerializer implements JsonSerializer<TestFeature> {
    @Override
    public JsonElement serialize(
            TestFeature testFeature,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", testFeature.name());
        jsonObject.addProperty("description", testFeature.description());

        return jsonObject;
    }
}
