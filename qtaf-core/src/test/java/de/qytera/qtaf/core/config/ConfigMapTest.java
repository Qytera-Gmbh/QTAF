package de.qytera.qtaf.core.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Map;

public class ConfigMapTest {

    @BeforeMethod
    public void clearConfiguration() {
        ConfigurationFactory.getInstance().clear();
        ErrorLogCollection.getInstance().getErrorLogs().clear();
    }

    @Test
    public void testGetInt() {
        String key = "hello.there.int";
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertNull(config.getInt(key));
        Assert.assertEquals(config.getInt(key, 42), 42);
        config.setInt(key, 1337);
        Assert.assertEquals(config.getInt(key), 1337);
    }

    @Test
    public void testGetDouble() {
        String key = "hello.there.double";
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertNull(config.getDouble(key));
        Assert.assertEquals(config.getDouble(key, 42.0), 42.0);
        config.setDouble(key, 1337.0);
        Assert.assertEquals(config.getDouble(key), 1337.0);
    }

    @Test
    public void testGetString() {
        String key = "hello.there.string";
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertNull(config.getString(key));
        Assert.assertEquals(config.getString(key, "42"), "42");
        config.setString(key, "1337");
        Assert.assertEquals(config.getString(key), "1337");
    }

    @Test
    public void testGetBoolean() {
        ConfigMap config = QtafFactory.getConfiguration();

        // Key should be null
        String key = "hello.there.boolean";
        config.setString(key, null);

        Assert.assertNull(config.getBoolean(key));
        Assert.assertFalse(config.getBoolean(key, false));
        Assert.assertTrue(config.getBoolean(key, true));

        config.setString(key, "0");
        Assert.assertFalse(config.getBoolean(key));

        config.setString(key, "1");
        Assert.assertTrue(config.getBoolean(key));

        config.setString(key, "n");
        Assert.assertFalse(config.getBoolean(key));

        config.setString(key, "y");
        Assert.assertTrue(config.getBoolean(key));

        config.setString(key, "false");
        Assert.assertFalse(config.getBoolean(key));

        config.setString(key, "true");
        Assert.assertTrue(config.getBoolean(key));

        config.setInt(key, 0);
        Assert.assertFalse(config.getBoolean(key));

        config.setInt(key, 1);
        Assert.assertTrue(config.getBoolean(key));

        config.setBoolean(key, false);
        Assert.assertFalse(config.getBoolean(key));

        config.setBoolean(key, true);
        Assert.assertTrue(config.getBoolean(key));
    }

    @Test
    public void testGetList() {
        String key = "hello.there.array";
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertNotNull(config.getList(key));
        System.setProperty(key, "[a, 1, 2, \"hello\"]");
        Assert.assertEquals(config.getList(key).size(), 4);
        System.setProperty(key, "null");
        Assert.assertTrue(config.getList(key).isEmpty());
        System.setProperty(key, "[\"missingQuote]");
        Assert.assertTrue(config.getList(key).isEmpty());
        config.setString(key, null);
        System.clearProperty(key);
        Assert.assertTrue(config.getList(key).isEmpty());
    }

    @Test
    public void testGetMap() {
        String key = "hello.there.object";
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertEquals(config.getMap(key), Collections.emptyMap());
        System.setProperty(key, "null");
        Assert.assertEquals(config.getMap(key), Collections.emptyMap());
        System.setProperty(key, """
                {
                  "a": 123,
                  "b": ["good", "morning"],
                  "c": null,
                  "d": {
                    "d.a": "good",
                    "d.b": ["evening", 456]
                  }
                }
                """);
        JsonArray goodMorningArray = new JsonArray();
        goodMorningArray.add("good");
        goodMorningArray.add("morning");
        JsonObject d = new JsonObject();
        d.add("d.a", new JsonPrimitive("good"));
        JsonArray goodEveningArray = new JsonArray();
        goodEveningArray.add("evening");
        goodEveningArray.add(456);
        d.add("d.b", goodEveningArray);
        Assert.assertEquals(config.getMap(key), Map.of(
                "a", new JsonPrimitive(123),
                "b", goodMorningArray,
                "c", JsonNull.INSTANCE,
                "d", d
        ));
        System.setProperty(key, """
                {
                  "a": "missingQuote
                }
                """);
        Assert.assertEquals(config.getMap(key), Collections.emptyMap());
        config.setString(key, null);
        System.clearProperty(key);
        Assert.assertEquals(config.getMap(key), Collections.emptyMap());
    }

    @Test
    public void TestLogUnknownValue() {
        ConfigMap configMap = ConfigurationFactory.getInstance();

        // if
        configMap.logUnknownValue("key", "unknownValue", "fallbackValue");
        String expectedMessage = "Unknown value for 'key': 'unknownValue'. Defaulting to 'fallbackValue'.";
        Assert.assertEquals(ErrorLogCollection.getInstance().getErrorLogs().get(0).getErrorMessage(), expectedMessage);

        String fallbackValue = configMap.logUnknownValue("key", "unknownValue", "fallbackValue", "knownValues");
        expectedMessage = "Unknown value for 'key': 'unknownValue' (known values: '[knownValues]'). Defaulting to 'fallbackValue'.";
        Assert.assertEquals(ErrorLogCollection.getInstance().getErrorLogs().get(1).getErrorMessage(), expectedMessage);

        Assert.assertEquals(fallbackValue, "fallbackValue"); // Check return value from logUnknownValue
    }

    @Test
    public void TestLogMissingValue() {
        ConfigMap configMap = ConfigurationFactory.getInstance();
        Assert.assertEquals(configMap.logMissingValue("key", "fallbackValue"), "fallbackValue");
    }
}
