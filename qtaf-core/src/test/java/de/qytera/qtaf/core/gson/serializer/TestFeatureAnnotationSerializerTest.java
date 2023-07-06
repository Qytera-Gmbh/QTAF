package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestFeatureAnnotationSerializerTest {
    @Test(testName = "Test TestFeatureAnnotationSerializer")
    public void testSerializer() {
        TestFeatureAnnotationSerializer serializer = new TestFeatureAnnotationSerializer();
        TestFeature testFeature = DemoFeature.class.getAnnotation(TestFeature.class);
        JsonElement jsonElement = serializer.serialize(testFeature, null, null);
        Assert.assertEquals(jsonElement.toString(), "{\"name\":\"Demo test feature\",\"description\":\"This is a demo test feature\"}");
    }

    @Test
    public void testSerializerObjectClass() {
        TestFeatureAnnotationSerializer serializer = new TestFeatureAnnotationSerializer();
        Assert.assertEquals(serializer.getSerializedObjectClass(), TestFeature.class);
    }
}

@TestFeature(name = "Demo test feature", description = "This is a demo test feature")
class DemoFeature {
}
