package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Helper class for extracting information from Cucumber Scenario objects
 */
public class CucumberScenarioHelper {

    /**
     * Get the test case state object from a scenario object
     *
     * @param scenario Cucumber scenario object
     * @return TestCaseState object
     */
    public static TestCaseState getTestCaseState(Scenario scenario) {
        TestCaseState testCaseState = null;

        for (Field field : FieldHelper.getDeclaredFieldsRecursively(scenario.getClass())) {
            // Make field accessible
            field.setAccessible(true);

            try {
                Object fieldObject = field.get(scenario);

                if (fieldObject instanceof TestCaseState) {
                    testCaseState = (TestCaseState) fieldObject;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return testCaseState;
    }

    /**
     * Extract key value pairs from cucumber tags
     * i.e. a tag @MyKey:MyValue will be added to the resulting kv-pairs as ["MyKey" => "MyValue"]
     *
     * @param scenario Cucumber scenario object
     * @return cucumber tag metadata
     */
    public static Map<String, String> getTagMetaData(Scenario scenario) {
        // Get scenario tags
        List<String> scenarioTags = (List<String>) scenario.getSourceTagNames();
        return CucumberTagHelper.getKeyValuePairs(scenarioTags);
    }

    /**
     * Find the tag that represents the test ID and extracts the test ID
     *
     * @param scenario Cucumber scenario object
     * @return test ID
     */
    public static String getScenarioIdFromTag(Scenario scenario) {
        // Get scenario tags
        List<String> scenarioTags = (List<String>) scenario.getSourceTagNames();
        return CucumberTagHelper.getTestId(scenarioTags);
    }
}
