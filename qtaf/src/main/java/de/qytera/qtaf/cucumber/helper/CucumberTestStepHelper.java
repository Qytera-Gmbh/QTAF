package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.plugin.event.HookTestStep;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStep;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Helper class for extracting information from Cucumber TestStep objects
 */
public class CucumberTestStepHelper {

    /**
     * Get all test steps that are derived from the PickleStepTestStep class
     * which represents Given / When / Then steps
     *
     * @param testSteps List of TestStep objects
     * @return  List of PickleStepTestStep objects
     */
    public static List<PickleStepTestStep> getPickleStepTestSteps(List<TestStep> testSteps) {
        return testSteps
                .stream()
                .filter(ts -> ts instanceof PickleStepTestStep)
                .map(ts -> (PickleStepTestStep) ts)
                .collect(Collectors.toList());
    }

    /**
     * Get the positions of test steps (Given, When, Then).
     * All test steps that are defined in a feature file are represented by the PickleStepTestStep class.
     * This functions finds these classes and returns their positions.
     *
     * @param testSteps List of test steps
     * @return  List of positions
     */
    public static List<Integer> getTestStepPositions(List<TestStep> testSteps) {
        return testSteps
                .stream()
                .filter(ts -> ts instanceof PickleStepTestStep)
                .map(testSteps::indexOf)
                .collect(Collectors.toList());
    }

    /**
     * Get the positions of the given test step in a list of test steps
     *
     * @param testSteps List of test steps
     * @param testStep  Single test steps
     * @return  TestStep position
     */
    public static int getTestStepPosition(List<TestStep> testSteps, TestStep testStep) {
        Optional<Integer> pos = testSteps
                .stream()
                .filter(ts -> ts.getId().equals(testStep.getId()))
                .map(ts -> testSteps.indexOf(ts))
                .findFirst();

        if (pos.isPresent()) {
            return pos.get();
        }

        return -1;
    }

    /**
     * Find the TestStep object that has another TestStep object with in a List stored in its 'attributeName' Attribute
     * @param testSteps List of TestStep objects
     * @param stepId    Look for this ID inside attributeName
     * @param attributeName Attribute name where to look for ID
     * @return  TestStep object on success, null otherwise
     */
    public static TestStep findByTestStepIdInAttribute(List<TestStep> testSteps, UUID stepId, String attributeName) {
        List<Object> steps = testSteps
                .stream()
                .filter(ts ->
                        FieldHelper.getFieldValue(ts, attributeName) != null
                                && ((List<HookTestStep>) Objects.requireNonNull(
                                FieldHelper.getFieldValue(ts, attributeName)
                        )).stream()
                                .filter(Objects::nonNull)
                                .anyMatch(hook -> hook.getId().equals(stepId))
                )
                .collect(Collectors.toList());

        if (steps.size() == 1) {
            return (TestStep) steps.get(0);
        }

        return null;
    }
}
