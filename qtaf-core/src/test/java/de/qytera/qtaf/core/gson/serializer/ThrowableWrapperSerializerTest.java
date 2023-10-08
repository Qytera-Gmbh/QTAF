package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonObject;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ThrowableWrapperSerializerTest {

    @Test
    public void testToJson() {
        ThrowableWrapperSerializer serializer = new ThrowableWrapperSerializer();
        JsonObject json = serializer.toJson(new ThrowableWrapper(new IllegalStateException("hello")));
        Assert.assertEquals(json.get("type").getAsString(), IllegalStateException.class.getName());
        Assert.assertEquals(json.get("message").getAsString(), "hello");
        Assert.assertFalse(json.get("stackTrace").getAsJsonArray().isEmpty());
    }
}