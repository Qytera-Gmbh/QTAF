package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StackTraceElementSerializerTest {

    @Test
    public void testToJsonMinimal() {
        StackTraceElementSerializer serializer = new StackTraceElementSerializer();
        JsonObject json = serializer.toJson(
                new StackTraceElement(
                        "SomeClass",
                        "SomeMethod",
                        "SomeFile",
                        12345
                )
        );
        Assert.assertTrue(json.get("classLoaderName").isJsonNull());
        Assert.assertTrue(json.get("moduleName").isJsonNull());
        Assert.assertEquals(json.get("className").getAsString(), "SomeClass");
        Assert.assertEquals(json.get("methodName").getAsString(), "SomeMethod");
        Assert.assertEquals(json.get("fileName").getAsString(), "SomeFile");
        Assert.assertEquals(json.get("lineNumber").getAsInt(), 12345);
        Assert.assertFalse(json.get("isNativeMethod").getAsBoolean());
    }

    @Test
    public void testToJsonExtended() {
        StackTraceElementSerializer serializer = new StackTraceElementSerializer();
        JsonObject json = serializer.toJson(
                new StackTraceElement(
                        "ClassLoader",
                        "Module",
                        "1.0.0",
                        "SomeClass",
                        "SomeMethod",
                        "SomeFile",
                        42
                )
        );
        Assert.assertEquals(json.get("classLoaderName").getAsString(), "ClassLoader");
        Assert.assertEquals(json.get("moduleName").getAsString(), "Module");
        Assert.assertEquals(json.get("className").getAsString(), "SomeClass");
        Assert.assertEquals(json.get("methodName").getAsString(), "SomeMethod");
        Assert.assertEquals(json.get("fileName").getAsString(), "SomeFile");
        Assert.assertEquals(json.get("lineNumber").getAsInt(), 42);
        Assert.assertFalse(json.get("isNativeMethod").getAsBoolean());
    }
}