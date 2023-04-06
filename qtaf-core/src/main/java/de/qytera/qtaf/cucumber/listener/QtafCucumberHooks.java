package de.qytera.qtaf.cucumber.listener;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.IQtafTestStepEventPayload;
import de.qytera.qtaf.core.events.payload.QtafTestStepEventPayload;
import de.qytera.qtaf.core.events.payload.ScenarioStatus;
import de.qytera.qtaf.core.reflection.FieldHelper;
import de.qytera.qtaf.cucumber.events.payload.CucumberScenarioEventPayload;
import de.qytera.qtaf.cucumber.helper.CucumberLogMessageHelper;
import de.qytera.qtaf.cucumber.helper.CucumberScenarioHelper;
import de.qytera.qtaf.cucumber.helper.CucumberTestCaseStateHelper;
import de.qytera.qtaf.cucumber.helper.CucumberTestStepHelper;
import de.qytera.qtaf.cucumber.log.model.message.CucumberStepLogMessage;
import de.qytera.qtaf.cucumber.log.model.message.index.CucumberStepIndex;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestStep;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class that listens to Cucumber events and dispatches QTAF events
 */
public class QtafCucumberHooks extends QtafTestNGContext {
    /**
     * Cucumber log message index
     */
    private static CucumberStepIndex index = CucumberStepIndex.getInstance();

    /**
     * This method runs before each scenario. It creates log messages objects and stores them
     * in an index, so that other methods cann access the log messages by their IDs.
     *
     * @param scenario Scenario object
     */
    public final void beforeScenario(Scenario scenario) {
        CucumberScenarioEventPayload eventPayload = new CucumberScenarioEventPayload(scenario);
        eventPayload.setScenarioStatus(ScenarioStatus.PENDING);
        QtafEvents.testStarted.onNext(eventPayload);

        List<CucumberStepLogMessage> logMessages = null;

        // Get a list of log messages from the scenario object
        try {
            logMessages = CucumberLogMessageHelper
                    .createLogMessagesFromScenario(scenario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add log messages to the index
        assert logMessages != null;
        for (CucumberStepLogMessage logMessage : logMessages) {
            index.put(logMessage.getId(), logMessage);
        }
    }

    /**
     * This code is executed before every step
     *
     * @param scenario Scenario
     */
    public final void beforeStep(Scenario scenario) {
        TestCaseState state = CucumberScenarioHelper.getTestCaseState(scenario);
        TestCase testCase = CucumberTestCaseStateHelper.getTestCase(state);
        assert testCase != null;

        UUID stepId = (UUID) FieldHelper.getFieldValue(state, "currentTestStepId");
        TestStep testStep = CucumberTestStepHelper.findByTestStepIdInAttribute(testCase.getTestSteps(), stepId, "beforeStepHookSteps");
        assert testStep != null;

        CucumberStepLogMessage logMessage = index.get(testStep.getId());
        logMessage.setStart(new Date());
    }

    /**
     * This code is executed after every step
     *
     * @param scenario Scenario
     */
    public final void afterStep(Scenario scenario) {
        TestCaseState state = CucumberScenarioHelper.getTestCaseState(scenario);
        TestCase testCase = CucumberTestCaseStateHelper.getTestCase(state);
        assert testCase != null;

        UUID stepId = (UUID) FieldHelper.getFieldValue(state, "currentTestStepId");
        TestStep testStep = CucumberTestStepHelper.findByTestStepIdInAttribute(
                testCase.getTestSteps(),
                stepId,
                "afterStepHookSteps"
        );

        int testStepPosition = CucumberTestStepHelper.getTestStepPosition(testCase.getTestSteps(), testStep);
        List<Result> testStepResults = CucumberTestCaseStateHelper.getStepResults(state);
        Result result = testStepResults.get(testStepPosition);

        assert testStep != null;

        CucumberStepLogMessage logMessage = index.get(testStep.getId());

        if (logMessage == null) {
            return;
        }

        logMessage
                .setScenarioId(scenario.getId())
                .setEnd(new Date());

        CucumberLogMessageHelper.applyResultToLogMessage(logMessage, result);
    }

    /**
     * This code is executed after every Cucumber scenario
     *
     * @param scenario Scenario
     */
    public final void afterScenario(Scenario scenario) {
        CucumberScenarioEventPayload eventPayload = new CucumberScenarioEventPayload(scenario);

        // Dispatch event depending on the scenario status
        if (eventPayload.getScenarioStatus() == ScenarioStatus.SUCCESS) {
            eventPayload.setScenarioEnd(new Date());
            QtafEvents.testSuccess.onNext(eventPayload);
        } else if (eventPayload.getScenarioStatus() == ScenarioStatus.FAILURE) {
            eventPayload.setScenarioEnd(new Date());
            QtafEvents.testFailure.onNext(eventPayload);
        }

        // Get a list of log messages from the scenario object
        List<CucumberStepLogMessage> logMessages = CucumberLogMessageHelper
                .createLogMessagesFromScenario(scenario);

        // Dispatch log message events
        this.dispatchLogMessageEvents(scenario, logMessages);

        // Update end date of TestSuite
        QtafFactory.getTestSuiteLogCollection().setEnd(new Date());
    }

    /**
     * Create list of log messages and dispatch an event for each one
     *
     * @param scenario    Scenario object
     * @param logMessages List og log messages
     */
    protected void dispatchLogMessageEvents(
            Scenario scenario,
            List<CucumberStepLogMessage> logMessages
    ) {
        // Dispatch events for each log message
        for (CucumberStepLogMessage logMessage : logMessages) {
            IQtafTestStepEventPayload stepEventPayload = new QtafTestStepEventPayload()
                    .setScenarioId(scenario.getId())
                    .setLogMessage(logMessage);

            QtafEvents.stepLog.onNext(stepEventPayload);
        }
    }
}