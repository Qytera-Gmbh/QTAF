package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.cucumber.log.model.message.CucumberStepLogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.cucumber.log.model.message.index.CucumberStepIndex;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that creates log messages from test steps
 */
public class CucumberLogMessageHelper {

    /**
     * Cucumber log index
     */
    static CucumberStepIndex index = CucumberStepIndex.getInstance();

    /**
     * Extract log messages from a Cucumber scenario object
     * @param scenario  Scenario
     */
    public static List<CucumberStepLogMessage> createLogMessagesFromScenario(Scenario scenario) {
        // List of log message objects that is extracted and returned
        List<CucumberStepLogMessage> stepLogMessages = new ArrayList<>();

        // Extract TestCase and TestCaseSate object
        TestCaseState testCaseState = CucumberScenarioHelper.getTestCaseState(scenario);
        TestCase testCase = CucumberTestCaseStateHelper.getTestCase(testCaseState);
        List<Result> testStepResults = CucumberTestCaseStateHelper.getStepResults(testCaseState);

        // Extract TestStep and Result objects
        assert testCase != null;
        List<TestStep> testSteps = testCase.getTestSteps();

        // Get the positions of all test steps
        List<Integer> testStepPositions = CucumberTestStepHelper.getTestStepPositions(testSteps);

        for (Integer pos : testStepPositions) {
            PickleStepTestStep testStep = (PickleStepTestStep) testSteps.get(pos);
            assert testStepResults != null;

            // Create log message
            CucumberStepLogMessage logMessage = createCucumberStepLogMessageFromTestStep(testStep);

            URI uri = ((PickleStepTestStep) testStep).getUri();
            String codeLocation = testStep.getCodeLocation();

            stepLogMessages.add(logMessage);
        }

        return stepLogMessages;
    }

    /**
     * Create log message for cucumber step result
     *
     * @param testStep      Test step object
     * @return              Log message object
     */
    public static CucumberStepLogMessage createCucumberStepLogMessageFromTestStep(
            PickleStepTestStep testStep
    ) {
        // Get the object representing the step
        Step step = testStep.getStep();

        if (index.get(testStep.getId()) != null) {
            return index.get(testStep.getId());
        }

        // Extract information about keyword and text of the step
        String stepKeyword = step.getKeyword(); // Given, When, Then
        String stepText = step.getText();       // Text after Given / When / Then

        StepArgument stepArgument = step.getArgument(); // Argument passed to step (i.e. DataTable, DocString)
        int stepLine = step.getLine();                  // Line of the step definition in the feature file

        // Extract information about location of step definition in the feature file
        Location stepLocation = step.getLocation();
        int locationColumn = stepLocation.getColumn();
        int locationLine = stepLocation.getLine();

        // Create new log message
        CucumberStepLogMessage message = new CucumberStepLogMessage(
                testStep.getId(),
                stepKeyword + stepText,
                stepText
        );


        // Add step argument to log messages
        if (stepArgument instanceof DataTableArgument) {
            message.addStepParameter("data-table", stepArgument.getClass().getName(), stepArgument);
        } else if (stepArgument instanceof DocStringArgument) {
            message.addStepParameter("doc-string", stepArgument.getClass().getName(), stepArgument);
        }

        return message;
    }

    /**
     * Add data of result to log message
     * @param message       log message object
     * @param testResult    result object
     */
    public static void applyResultToLogMessage(
            CucumberStepLogMessage message,
            Result testResult
    ) {
        // Add duration and status of test step
        message.setDuration(testResult.getDuration().getNano() / 1000000);
        message.setStatus(mapCucumberStatusToLogStatus(testResult.getStatus()));

        // Add error message if there is one
        if (testResult.getError() != null) {
            message.setError(testResult.getError());
        }
    }

    /**
     * Map the status of a cucumber step to a QTAF log message step
     * @param status    Cucumber status
     * @return          QTAF status
     */
    public static StepInformationLogMessage.Status mapCucumberStatusToLogStatus(Status status) {
        switch (status) {
            case PASSED:
                return StepInformationLogMessage.Status.PASS;
            case FAILED:
                return StepInformationLogMessage.Status.ERROR;
            case SKIPPED:
                return StepInformationLogMessage.Status.SKIPPED;
            case UNDEFINED:
                return StepInformationLogMessage.Status.UNDEFINED;
            case PENDING:
                return StepInformationLogMessage.Status.PENDING;
            // TODO handle the following status
            case UNUSED:
            case AMBIGUOUS:
        }
        return null;
    }

}
