package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCase;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * Helper class for extracting information from Cucumber Scenario objects.
 */
public class CucumberTestCaseStateHelper {
    private CucumberTestCaseStateHelper() {
    }

    /**
     * Get TestCase object from TestCaseState object.
     *
     * @param testCaseState TestCaseState object
     * @return TestCase object
     */
    public static TestCase getTestCase(TestCaseState testCaseState) {
        for (Field field : FieldHelper.getDeclaredFieldsRecursively(testCaseState.getClass())) {
            // Make field accessible
            field.setAccessible(true);

            try {
                if (field.getName().equals("testCase")) {
                    return (TestCase) field.get(testCaseState);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Get test result object list from a TestCaseState object.
     *
     * @param testCaseState TestCaseState object
     * @return List of test case result objects
     */
    public static List<Result> getStepResults(TestCaseState testCaseState) {
        for (Field field : FieldHelper.getDeclaredFieldsRecursively(testCaseState.getClass())) {
            // Make field accessible
            field.setAccessible(true);

            try {
                if (field.getName().equals("stepResults")) {
                    return (List<Result>) field.get(testCaseState);

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

}
