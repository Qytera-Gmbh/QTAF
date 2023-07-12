package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestScenarioAnnotationSerializerTest {
    @Test(testName = "Test TestScenarioAnnotationSerializer")
    public void testSerializer() throws NoSuchMethodException {
        TestScenarioAnnotationSerializer serializer = new TestScenarioAnnotationSerializer();
        Method method = this.getClass().getMethod("testSerializer");
        Test annotation = method.getAnnotation(Test.class);
        JsonElement jsonElement = serializer.serialize(annotation, null, null);
        Assert.assertEquals(jsonElement.toString(), """
                {\
                "attributes":"[]",\
                "alwaysRun":false,\
                "dataProvider":"",\
                "dataProviderClass":"class java.lang.Object",\
                "dataProviderDynamicClass":"",\
                "dependsOnGroups":"[]",\
                "dependsOnMethods":"[]",\
                "description":"",\
                "enabled":true,\
                "expectedExceptions":"[]",\
                "expectedExceptionsMessageRegExp":".*",\
                "groups":"[]",\
                "ignoreMissingDependencies":false,\
                "invocationCount":1,\
                "invocationTimeOut":0,\
                "priority":0,\
                "retryAnalyzer":"class org.testng.internal.annotations.DisabledRetryAnalyzer",\
                "singleThreaded":false,\
                "skipFailedInvocations":false,\
                "successPercentage":100,\
                "suiteName":"",\
                "threadPoolSize":0,\
                "timeOut":0,\
                "testName":"Test TestScenarioAnnotationSerializer"\
                }""");
    }

    @Test
    public void testSerializerObjectClass() {
        TestScenarioAnnotationSerializer serializer = new TestScenarioAnnotationSerializer();
        Assert.assertEquals(serializer.getSerializedObjectClass(), Test.class);
    }

}
