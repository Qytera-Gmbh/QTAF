package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.guice.annotations.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StepAnnotationSerializerTest {
    @Test(testName = "Test TestScenarioAnnotationSerializer")
    public void testSerializer() throws NoSuchMethodException {
        StepAnnotationSerializer serializer = new StepAnnotationSerializer();
        Step step = DemoScenario.class.getMethod("demoStep").getAnnotation(Step.class);
        JsonElement jsonElement = serializer.serialize(step, null, null);
        Assert.assertEquals(jsonElement.toString(), """
                {"name":"Demo Step","description":"This is a demonstration step"}""");
    }

    @Test
    public void testSerializerObjectClass() {
        StepAnnotationSerializer serializer = new StepAnnotationSerializer();
        Assert.assertEquals(serializer.getSerializedObjectClass(), Step.class);
    }
}

class DemoScenario {
    @Step(name = "Demo Step", description = "This is a demonstration step")
    public void demoStep() {
    }
}
