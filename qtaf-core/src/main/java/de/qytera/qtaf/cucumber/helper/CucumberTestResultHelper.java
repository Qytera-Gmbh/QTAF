package de.qytera.qtaf.cucumber.helper;

import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class for extracting information from Cucumber test result objects
 */
public class CucumberTestResultHelper {
    private CucumberTestResultHelper() {}

    /**
     * Checks if all steps passed
     *
     * @param testResults Step result list
     * @return True if all steps passed, false otherwise
     */
    public static boolean didAllStepsPass(List<Result> testResults) {
        List<Result> passedSteps = testResults
                .stream()
                .filter(tr -> tr.getStatus() == Status.PASSED)
                .toList();

        return passedSteps.size() == testResults.size();
    }

}
