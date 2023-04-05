package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.cucumber.events.payload.CucumberScenarioEventPayload;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CucumberEventPayloadTest {
    /**
     * Create a mock scenario object
     *
     * @return mock scenario object
     */
    private Scenario createScenarioObject() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Scenario> constructor;
        constructor = Scenario.class.getDeclaredConstructor(TestCaseState.class);
        constructor.setAccessible(true);
        Scenario scenario = constructor.newInstance(new SampleTestCaseState());
        return scenario;
    }

    @Test
    public void testOriginalEvent() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Scenario scenario = this.createScenarioObject();
        IQtafTestEventPayload payload = new CucumberScenarioEventPayload(scenario);

        Assert.assertEquals(
                payload.getOriginalEvent().hashCode(),
                scenario.hashCode(),
                "Assert original event to be correctly taken from scenario object"
        );
    }

    @Test
    public void testFeatureId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Scenario scenario = this.createScenarioObject();
        IQtafTestEventPayload payload = new CucumberScenarioEventPayload(scenario);

        Assert.assertEquals(
                payload.getFeatureId(),
                "file:///features/sample-feature",
                "Assert feature id to be correctly taken from scenario object"
        );
    }

    @Test
    public void testFeatureName() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Scenario scenario = this.createScenarioObject();
        IQtafTestEventPayload payload = new CucumberScenarioEventPayload(scenario);

        Assert.assertEquals(
                payload.getFeatureName(),
                "file:///features/sample-feature",
                "Assert feature name to be correctly taken from scenario object"
        );
    }

    @Test
    public void testScenarioId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Scenario scenario = this.createScenarioObject();
        IQtafTestEventPayload payload = new CucumberScenarioEventPayload(scenario);

        Assert.assertEquals(
                payload.getAbstractScenarioId(),
                "test-case-state-id",
                "Assert scenario id to be correctly taken from scenario object"
        );
    }

    @Test
    public void testScenarioName() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Scenario scenario = this.createScenarioObject();
        IQtafTestEventPayload payload = new CucumberScenarioEventPayload(scenario);

        Assert.assertEquals(
                payload.getScenarioName(),
                "SampleTestName-1",
                "Assert scenario name to be correctly taken from scenario object"
        );
    }

    @Test
    public void testScenarioDescription() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Scenario scenario = this.createScenarioObject();
        IQtafTestEventPayload payload = new CucumberScenarioEventPayload(scenario);

        Assert.assertEquals(
                payload.getScenarioDescription(),
                "test-case-state-name",
                "Assert scenario description to be correctly taken from scenario object"
        );
    }
}
