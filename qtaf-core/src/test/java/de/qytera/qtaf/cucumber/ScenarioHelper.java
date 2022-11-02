package de.qytera.qtaf.cucumber;

import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;

import java.lang.reflect.Constructor;

/**
 * Helper class for generating scenario objects
 */
public class ScenarioHelper {
    /**
     * Create a mock scenario object
     * @return  Mock scenario object
     */
    public static Scenario getScenario() {
        try {
            Constructor<Scenario> constructor;
            constructor = Scenario.class.getDeclaredConstructor(TestCaseState.class);
            constructor.setAccessible(true);
            Scenario scenario = constructor.newInstance(new SampleTestCaseState());
            return scenario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
