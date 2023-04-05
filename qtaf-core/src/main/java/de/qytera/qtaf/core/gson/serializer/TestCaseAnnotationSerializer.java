package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.qytera.qtaf.core.config.annotations.TestFeature;

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
