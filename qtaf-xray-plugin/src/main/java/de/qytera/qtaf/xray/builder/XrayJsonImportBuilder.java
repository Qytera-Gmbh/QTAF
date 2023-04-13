package de.qytera.qtaf.xray.builder;

import com.google.inject.Singleton;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.entity.*;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Transforms log collection into Xray Execution Import DTO
 */
@Singleton
public class XrayJsonImportBuilder {

    /**
     * An exception thrown when a test suite did not execute any test marked with {@link XrayTest}.
     */
    public static class NoXrayTestException extends Exception {
        /**
         * Constructs a new exception with a predefined error message.
         */
        public NoXrayTestException() {
            super("Cannot build import execution request because no test linked to Xray was executed");
        }
    }

    /**
     * Factory method for building Xray import execution DTOs. This method creates {@link XrayImportRequestDto} based
     * on a test suite collection.
     *
     * @param collection the test suite collection
     * @return the import execution DTO
     * @throws NoXrayTestException if the executed suite does not contain any tests annotated with {@link XrayTest}
     */
    public synchronized XrayImportRequestDto buildFromTestSuiteLogs(
            TestSuiteLogCollection collection
    ) throws NoXrayTestException {
        // This DTO will be sent to the Xray API
        XrayImportRequestDto xrayImportRequestDto = new XrayImportRequestDto();

        // Object that can create HTML reports for scenarios
        ScenarioReportCreator scenarioReportCreator = new ScenarioReportCreator();

        // Add information about test suite
        if (false) { // TODO add a key to the configuration file for this
            XrayTestExecutionInfoEntity info = new XrayTestExecutionInfoEntity()
                    .addTestEnvironment(collection.getOsName())
                    .addTestEnvironment(collection.getDriverName());

            xrayImportRequestDto.setInfo(info);
        }

        // Iterate over test features
        for (TestFeatureLogCollection testFeatureLogCollection : collection.getTestFeatureLogCollections()) {

            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();

            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {

                // Skip test executions of tests that have not been linked to Xray issues.
                if (entry.getValue().isEmpty() || entry.getValue().get(0).getAnnotation(XrayTest.class) == null) {
                    continue;
                }

                // Tests that have been executed once only.
                if (entry.getValue().size() == 1) {
                    TestScenarioLogCollection scenarioLog = entry.getValue().get(0);

                    // Build the xray test entity
                    XrayTestEntity xrayTestEntity = buildTestEntityForSingleIteration(collection, xrayImportRequestDto, scenarioReportCreator, scenarioLog);

                    if (xrayTestEntity == null) {
                        continue;
                    }

                    // For a single execution we have to set the iterations to null
                    xrayTestEntity.setIterations(null);

                    // Add test to test collection
                    xrayImportRequestDto.addTest(xrayTestEntity);
                }
                // Tests that hav been executed more than once (e.g. data-driven).
                else if (entry.getValue().size() > 1) {
                    XrayTestEntity xrayTestEntity = new XrayTestEntity();

                    // If we have multiple iterations of a test scenario we need to nullify the step parameter of the test entity,
                    // otherwise Xray won't accept out DTO
                    xrayTestEntity.setSteps(null);

                    // This variable will be set to false if any iteration failed
                    boolean allScenariosPassed = true;

                    // Get the xray annotation
                    XrayTest xrayTestAnnotation = (XrayTest) entry.getValue().get(0).getAnnotation(XrayTest.class);

                    xrayTestEntity.setTestKey(xrayTestAnnotation.key());

                    synchronized (entry.getValue()) {
                        // Iterate over concrete test scenario executions
                        for (TestScenarioLogCollection scenarioLog : entry.getValue()) {
                            // Build iteration entity
                            XrayTestIterationResultEntity iteration = new XrayTestIterationResultEntity();

                            // Add test parameters
                            for (TestScenarioLogCollection.TestParameter testParameter : scenarioLog.getTestParameters()) {
                                XrayTestIterationParameterEntity parameterEntity = new XrayTestIterationParameterEntity();
                                parameterEntity.setName(truncateParameterName(testParameter.getName()));
                                parameterEntity.setValue(truncateParameterValue(testParameter.getValue().toString()));
                                iteration.addParameter(parameterEntity);
                            }

                            // Iterate over steps
                            synchronized (scenarioLog.getLogMessages()) {
                                for (LogMessage logMessage : scenarioLog.getLogMessages()) {
                                    // Only StepLogMessage entities are important for Xray
                                    if (logMessage instanceof StepInformationLogMessage stepLog) {
                                        iteration.addStep(buildXrayManualTestStepResultEntity(stepLog));
                                    }
                                }
                            }

                            iteration.setStatus(scenarioLog.getStatus());

                            // Check if the iteration passed. If not set the scenario status pass status to false
                            if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.FAILURE) {
                                allScenariosPassed = false;
                            }

                            // Add iteration entity to test entity
                            xrayTestEntity.addIteration(iteration);
                        }
                    }

                    // Set the test status
                    if (allScenariosPassed) {
                        xrayTestEntity.setStatus(TestScenarioLogCollection.Status.SUCCESS);
                    } else {
                        xrayTestEntity.setStatus(TestScenarioLogCollection.Status.FAILURE);
                    }

                    // Add test to test collection
                    xrayImportRequestDto.addTest(xrayTestEntity);
                }
            }
        }

        if (xrayImportRequestDto.getTests().isEmpty()) {
            throw new NoXrayTestException();
        }

        for (XrayTestEntity xrayTestEntity : xrayImportRequestDto.getTests()) {
            // Xray cloud can't deal with keyword 'examples', so deactivate it
            xrayTestEntity.setExamples(null);
        }

        return xrayImportRequestDto;
    }

    /**
     * Add a XrayTestEntity object to the DTO for tests with a single iteration
     *
     * @param collection            test suite log collection
     * @param xrayImportRequestDto  import DTO
     * @param scenarioReportCreator scenario report creator
     * @param scenarioLog           scenario log collection
     */
    private XrayTestEntity buildTestEntityForSingleIteration(
            TestSuiteLogCollection collection,
            XrayImportRequestDto xrayImportRequestDto,
            ScenarioReportCreator scenarioReportCreator,
            TestScenarioLogCollection scenarioLog
    ) {
        // Get the xray annotation if scenario has one
        XrayTest xrayTestAnnotation = (XrayTest) scenarioLog.getAnnotation(XrayTest.class);

        // Skip all pending scenarios
        if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.PENDING) {
            return null;
        }

        // Only methods that are annotated with @XrayTest are of interest
        if (xrayTestAnnotation == null) {
            return null;
        }

        return buildXrayTestEntity(collection, scenarioReportCreator, scenarioLog, xrayTestAnnotation);
    }

    private XrayTestEntity buildXrayTestEntity(
            TestSuiteLogCollection collection,
            ScenarioReportCreator scenarioReportCreator,
            TestScenarioLogCollection scenarioLog,
            XrayTest xrayTestAnnotation
    ) {
        // Set Xray test attributes
        XrayTestEntity xrayTestEntity = initializeXrayTestEntity(scenarioLog);

        // Add a scenario report to the xray test entity
        if (XrayConfigHelper.isScenarioReportEvidenceEnabled() && xrayTestAnnotation.scenarioReport()) {
            // Get rendered template
            Writer renderedTemplate = scenarioReportCreator.getRenderedTemplate(collection, scenarioLog);
            String filename = "scenario_" + scenarioLog.getScenarioName() + ".html";
            xrayTestEntity.addEvidence(XrayEvidenceItemEntity.fromString(renderedTemplate.toString(), filename));
        }

        // add scenario image evidence
        if (XrayConfigHelper.isScenarioImageEvidenceEnabled() && xrayTestAnnotation.screenshots()) {
            for (String filepath : scenarioLog.getScreenshotPaths()) {
                xrayTestEntity.addEvidence(XrayEvidenceItemEntity.fromFile(filepath));
            }
        }

        xrayTestEntity.setStatus(scenarioLog.getStatus());

        // Iterate over log messages
        for (LogMessage logMessage : scenarioLog.getLogMessages()) {

            // Only StepLogMessage entities are important for Xray
            if (logMessage instanceof StepInformationLogMessage stepLog) {
                XrayManualTestStepResultEntity xrayTestStepEntity = buildXrayManualTestStepResultEntity(stepLog);

                // Add step log to test log
                xrayTestEntity.addStep(xrayTestStepEntity);
            }
        }
        return xrayTestEntity;
    }

    private XrayManualTestStepResultEntity buildXrayManualTestStepResultEntity(StepInformationLogMessage stepLog) {
        XrayManualTestStepResultEntity xrayTestStepEntity = new XrayManualTestStepResultEntity();
        xrayTestStepEntity.setStatus(stepLog.getStatus());
        if (stepLog.getStep() != null) {
            xrayTestStepEntity.setComment(stepLog.getStep().getName());
        }
        if (stepLog.getResult() != null) {
            xrayTestStepEntity.setActualResult(stepLog.getResult().toString());
        }
        if (stepLog.getScreenshotBefore() != null && !stepLog.getScreenshotBefore().isBlank()) {
            xrayTestStepEntity.addEvidenceIfPresent(XrayEvidenceItemEntity.fromFile(stepLog.getScreenshotBefore()));
        }
        if (stepLog.getScreenshotAfter() != null && !stepLog.getScreenshotAfter().isBlank()) {
            xrayTestStepEntity.addEvidenceIfPresent(XrayEvidenceItemEntity.fromFile(stepLog.getScreenshotAfter()));
        }
        return xrayTestStepEntity;
    }

    /**
     * Factory method for an XrayTestEntity object
     *
     * @param scenarioLog Scenario Log information
     * @return XrayTestEntity object
     */
    protected XrayTestEntity initializeXrayTestEntity(TestScenarioLogCollection scenarioLog) {
        // Get all annotations of scenario's Java method
        Annotation[] annotations = scenarioLog.getAnnotations();

        // Get the xray annotation if scenario has one
        XrayTest xrayTestAnnotation = (XrayTest) scenarioLog.getAnnotation(XrayTest.class);

        // Build xray test key
        String xrayTestKey;
        if (xrayTestAnnotation != null) {
            xrayTestKey = xrayTestAnnotation.key();
        } else {
            xrayTestKey = scenarioLog.getScenarioName();
        }

        // Build xray test entity
        return new XrayTestEntity()
                .setTestKey(xrayTestKey)
                .setStart(null) // TODO
                .setFinish(null) // TODO
                .setComment(scenarioLog.getDescription());
    }

    private static String truncateParameterName(String parameterName) {
        Integer maxLength = XrayConfigHelper.getIterationParameterNameMaxLength();
        if (maxLength == null || parameterName.length() <= maxLength) {
            return parameterName;
        }
        return parameterName.substring(0, maxLength);
    }

    private static String truncateParameterValue(String parameterValue) {
        Integer maxLength = XrayConfigHelper.getIterationParameterValueMaxLength();
        if (maxLength == null || parameterValue.length() <= maxLength) {
            return parameterValue;
        }
        return parameterValue.substring(0, maxLength);
    }

}
