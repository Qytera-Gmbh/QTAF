package de.qytera.qtaf.allure;

import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import io.qameta.allure.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for mapping QTAF data structure to Allure data structure
 */
public class AllureTestResultGenerator {
    /**
     * QTAF Test Suite is mapped to list of Allure TestResult entities
     *
     * @param collection QTAF Test suite entity
     * @return List of Allure TestResult entities
     */
    public static List<TestResult> fromQtafTestSuiteCollection(TestSuiteLogCollection collection) {
        List<TestResult> testResults = new ArrayList<>();

        // For each QTAF Test feature an Allure TestResult entity is created
        for (TestFeatureLogCollection feature : collection.getTestFeatureLogCollections()) {
            for (TestScenarioLogCollection scenario : feature.getScenarioLogCollection()) {
                testResults.add(fromQtafTestScenario(scenario));
            }
        }

        return testResults;
    }

    /**
     * Map QTAF Test feature entity to Allure TestResult entity
     *
     * @param scenario QTAF Test Feature entity
     * @return Allure TestResult Entity
     */
    public static TestResult fromQtafTestScenario(TestScenarioLogCollection scenario) {
        List<StepResult> testResultSteps = AllureTestResultGenerator.getStepResultsFromQtafScenario(scenario);
        List<Attachment> testResultAttachments = AllureTestResultGenerator.getAllureTestResultAttachments(scenario);
        List<Parameter> testResultParameters = new ArrayList<>(); // TODO
        List<Label> testResultLabels = new ArrayList<>(); // TODO
        List<Link> testResultLinks = new ArrayList<>(); // TODO

        StatusDetails statusDetails = (new StatusDetails())
                .setKnown(false)
                .setMuted(false)
                .setFlaky(false)
                .setMessage("") // TODO
                .setTrace(""); // TODO

        return (new TestResult())
                .setUuid(UUID.randomUUID().toString())
                .setHistoryId(scenario.getScenarioId())
                .setFullName(scenario.getScenarioId())
                .setLabels(testResultLabels)
                .setLinks(testResultLinks)
                .setName(scenario.getDescription())
                .setStatus(mapQtafScenarioStatusToAllureTestResultStatus(scenario.getStatus()))
                .setStatusDetails(statusDetails)
                .setStage(Stage.FINISHED)
                .setSteps(testResultSteps)
                .setAttachments(testResultAttachments)
                .setParameters(testResultParameters)
                .setDescriptionHtml(null)
                .setStart(scenario.getStart().getTime())
                .setStop(scenario.getEnd().getTime());
    }

    /**
     * Get list of StepResult entities from QTAF scenario
     *
     * @param scenario QTAF scenario
     * @return List of Allure StepResult entities
     */
    public static List<StepResult> getStepResultsFromQtafScenario(TestScenarioLogCollection scenario) {
        List<LogMessage> logMessages = scenario.getLogMessages();
        ArrayList<StepResult> stepResults = new ArrayList<>();

        for (LogMessage logMessage : logMessages) {
            // Cast log message entity to Step Entity
            StepInformationLogMessage stepLog = (StepInformationLogMessage) logMessage;

            // Get step parameters
            List<Parameter> stepParameters = getStepResultParameters(stepLog);

            // Get status details
            StatusDetails statusDetails = getAllureStepResultStatusDetailsFromQtafStep(stepLog);

            // Build StepResult entity
            StepResult stepResult = (new StepResult())
                    .setName(stepLog.getStep().getName())
                    .setDescription(stepLog.getStep().getDescription())
                    .setStart(stepLog.getStart().getTime())
                    .setStop(stepLog.getEnd().getTime())
                    .setParameters(stepParameters)
                    .setDescriptionHtml(null)
                    .setStage(getStepStageFromQtafStep(stepLog))
                    .setStatus(getStepStatusFromQtafStep(stepLog))
                    .setStatusDetails(statusDetails)
                    .setAttachments(getAllureStepResultAttachments(stepLog))
                    .setSteps(null);    // Nested steps could be added here

            stepResults.add(stepResult);
        }

        return stepResults;
    }

    private static StatusDetails getAllureStepResultStatusDetailsFromQtafStep(StepInformationLogMessage stepLog) {
        StatusDetails statusDetails = (new StatusDetails())
                .setTrace(stepLog.getMessage())
                .setMessage("")
                .setMuted(false)
                .setKnown(false)
                .setFlaky(false);
        return statusDetails;
    }

    /**
     * Map QTAF Step to Allure StepResult stage
     *
     * @param stepLog QTAF step entity
     * @return Allure Step status
     */
    public static Stage getStepStageFromQtafStep(StepInformationLogMessage stepLog) {
        return switch (stepLog.getStatus()) {
            case PASS, ERROR, SKIPPED, UNDEFINED -> Stage.FINISHED;
            case PENDING -> Stage.PENDING;
        };
    }


    /**
     * Map QTAF Step status to Allure StepResult status
     *
     * @param stepLog QTAF step entity
     * @return Allure Step status
     */
    public static Status getStepStatusFromQtafStep(StepInformationLogMessage stepLog) {
        return switch (stepLog.getStatus()) {
            case PASS -> Status.PASSED;
            case SKIPPED, PENDING -> Status.SKIPPED;
            case ERROR -> Status.FAILED;
            case UNDEFINED -> Status.BROKEN;
        };
    }

    /**
     * Get List of StepResult parameters from QTAF Step entity
     *
     * @param stepLog QTAF step entity
     * @return List of Allure Step parameters
     */
    public static List<Parameter> getStepResultParameters(StepInformationLogMessage stepLog) {
        // Create list of parameters
        List<Parameter> stepParameters = new ArrayList<>();

        for (StepInformationLogMessage.StepParameter stepParameter : stepLog.getStepParameters()) {
            Parameter parameter = (new Parameter())
                    .setName(stepParameter.getName())
                    .setExcluded(true)
                    .setValue(stepParameter.getValue().toString())
                    .setMode(Parameter.Mode.DEFAULT);

            stepParameters.add(parameter);
        }

        return stepParameters;
    }

    /**
     * Get Allure TestResult attachments from QTAF scenario entity
     *
     * @param scenarioLogCollection QTAF scenario entity
     * @return List if attachments
     */
    public static List<Attachment> getAllureTestResultAttachments(TestScenarioLogCollection scenarioLogCollection) {
        Attachment beforeScreenshot = (new Attachment())
                .setName("Before Scenario")
                .setType("image/png")
                .setSource(scenarioLogCollection.getScreenshotBefore());

        Attachment afterScreenshot = (new Attachment())
                .setName("After Scenario")
                .setType("image/png")
                .setSource(scenarioLogCollection.getScreenshotAfter());

        return List.of(beforeScreenshot, afterScreenshot);
    }

    /**
     * Get Allure StepResult attachments from QTAF step entity
     *
     * @param stepLog QTAF step entity
     * @return List if attachments
     */
    public static List<Attachment> getAllureStepResultAttachments(StepInformationLogMessage stepLog) {
        Attachment beforeScreenshot = (new Attachment())
                .setName("Before Step")
                .setType("image/png")
                .setSource(stepLog.getScreenshotBefore());

        Attachment afterScreenshot = (new Attachment())
                .setName("After Step")
                .setType("image/png")
                .setSource(stepLog.getScreenshotAfter());

        return List.of(beforeScreenshot, afterScreenshot);
    }

    /**
     * Map QTAF scenario status to Allure TestResult status
     *
     * @param status QTAF scenario status
     * @return Allure TestResult status
     */
    public static Status mapQtafScenarioStatusToAllureTestResultStatus(TestScenarioLogCollection.Status status) {
        if (status == TestScenarioLogCollection.Status.SUCCESS) {
            return Status.PASSED;
        } else if (status == TestScenarioLogCollection.Status.SKIPPED) {
            return Status.SKIPPED;
        } else if (status == TestScenarioLogCollection.Status.FAILURE) {
            return Status.FAILED;
        } else {
            return Status.BROKEN;
        }
    }
}
