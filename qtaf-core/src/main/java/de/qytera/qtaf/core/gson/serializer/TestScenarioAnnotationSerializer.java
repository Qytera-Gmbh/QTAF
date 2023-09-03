package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Converts TestCase annotation to JSON.
 */
public class TestScenarioAnnotationSerializer implements IQtafJsonSerializer, JsonSerializer<Test> {
    @Override
    public JsonElement serialize(
            Test test,
            Type type,
            JsonSerializationContext jsonSerializationContext
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("attributes", Arrays.toString(test.attributes()));
        jsonObject.addProperty("alwaysRun", test.alwaysRun());
        jsonObject.addProperty("dataProvider", test.dataProvider());
        jsonObject.addProperty("dataProviderClass", test.dataProviderClass().toString());
        jsonObject.addProperty("dataProviderDynamicClass", test.dataProviderDynamicClass());
        jsonObject.addProperty("dependsOnGroups", Arrays.toString(test.dependsOnGroups()));
        jsonObject.addProperty("dependsOnMethods", Arrays.toString(test.dependsOnMethods()));
        jsonObject.addProperty("description", test.description());
        jsonObject.addProperty("enabled", test.enabled());
        jsonObject.addProperty("expectedExceptions", Arrays.toString(test.expectedExceptions()));
        jsonObject.addProperty("expectedExceptionsMessageRegExp", test.expectedExceptionsMessageRegExp());
        jsonObject.addProperty("groups", Arrays.toString(test.groups()));
        jsonObject.addProperty("ignoreMissingDependencies", test.ignoreMissingDependencies());
        jsonObject.addProperty("invocationCount", test.invocationCount());
        jsonObject.addProperty("invocationTimeOut", test.invocationTimeOut());
        jsonObject.addProperty("priority", test.priority());
        jsonObject.addProperty("retryAnalyzer", test.retryAnalyzer().toString());
        jsonObject.addProperty("singleThreaded", test.singleThreaded());
        jsonObject.addProperty("skipFailedInvocations", test.skipFailedInvocations());
        jsonObject.addProperty("successPercentage", test.successPercentage());
        jsonObject.addProperty("suiteName", test.suiteName());
        jsonObject.addProperty("threadPoolSize", test.threadPoolSize());
        jsonObject.addProperty("timeOut", test.timeOut());
        jsonObject.addProperty("testName", test.testName());

        return jsonObject;
    }

    @Override
    public Class<Test> getSerializedObjectClass() {
        return Test.class;
    }
}
