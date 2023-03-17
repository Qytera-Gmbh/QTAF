package de.qytera.qtaf.xray.builder;

import com.google.inject.Singleton;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.*;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.error.EvidenceUploadError;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Transforms log collection into Xray Execution Import DTO
 */
@Singleton
public abstract class AbstractXrayJsonImportBuilder {

    /**
     * Factory method for Xray Import Execution DTO. This method creates the DTO based on a Test Suite Log Entity
     * @param collection    Test Suite Collection
     * @return  Import DTO
     */
    public XrayImportRequestDto buildFromTestSuiteLogs(TestSuiteLogCollection collection) {
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
                if (entry.getValue().size() == 1) { // The group contains only one item
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
                } else if (entry.getValue().size() > 1) { // The group contains multiple items
                    XrayTestEntity xrayTestEntity = new XrayTestEntity();

                    // If we have multiple iterations of a test scenario we need to nullify the step parameter of the test entity,
                    // otherwise Xray won't accept out DTO
                    xrayTestEntity.setSteps(null);

                    // This variable will be set to false if any iteration failed
                    boolean didScenarioPass = true;

                    // Get the xray annotation if scenario has one
                    XrayTest xrayTestAnnotation = (XrayTest) entry.getValue().get(0).getAnnotation(XrayTest.class);
                    assert xrayTestAnnotation != null;
                    xrayTestEntity.setTestKey(xrayTestAnnotation.key());

                    // Iterate over concrete test scenario executions
                    for (TestScenarioLogCollection scenarioLog : entry.getValue()) {
                        // Build iteration entity
                        XrayTestIterationResultEntity iteration = new XrayTestIterationResultEntity();

                        // This variable will be set to false if any step failed
                        boolean didIterationPass = true;

                        // Add test parameters
                        for (TestScenarioLogCollection.TestParameter testParameter : scenarioLog.getTestParameters()) {
                            XrayTestIterationParameterEntity parameterEntity = new XrayTestIterationParameterEntity();
                            parameterEntity.setName(testParameter.getName());
                            parameterEntity.setValue(testParameter.getValue().toString());
                            iteration.addParameter(parameterEntity);
                        }

                        // Iterate over steps
                        for (LogMessage logMessage : scenarioLog.getLogMessages()) {
                            // Only StepLogMessage entities are important for Xray
                            if (logMessage instanceof StepInformationLogMessage stepLog) {
                                // Build step entity
                                XrayManualTestStepResultEntity xrayTestStepEntity = buildXrayManualTestStepResultEntity(stepLog);

                                // Add result to step
                                if (stepLog.getResult() != null) {
                                    xrayTestStepEntity.setActualResult(stepLog.getResult().toString());
                                }

                                // Add step log to test log
                                iteration.addStep(xrayTestStepEntity);

                                // Check step status
                                if (stepLog.getStatus() == StepInformationLogMessage.Status.ERROR) {
                                    didIterationPass = false;
                                }
                            }
                        }

                        // Set iteration status
                        if (didIterationPass) {
                            iteration.setStatus(XrayTestIterationResultEntity.Status.PASSED);
                        } else {
                            iteration.setStatus(XrayTestIterationResultEntity.Status.FAILED);
                        }

                        // Check if the iteration passed. If not set the scenario status pass status to false
                        if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.FAILURE) {
                            didScenarioPass = false;
                        }

                        // Add iteration entity to test entity
                        xrayTestEntity.addIteration(iteration);
                    }

                    // Set the scenario status
                    if (didScenarioPass) {
                        xrayTestEntity.setStatus(XrayTestEntity.Status.PASS);
                    } else {
                        xrayTestEntity.setStatus(XrayTestEntity.Status.FAIL);
                    }

                    // Add test to test collection
                    xrayImportRequestDto.addTest(xrayTestEntity);
                } else { // The group doesn't contain any items
                }
            }
        }

        for (XrayTestEntity xrayTestEntity : xrayImportRequestDto.getTests()) {
            // Xray cloud can't deal with keyword 'examples', so deactivate it
            xrayTestEntity.setExamples(null);
        }

        return xrayImportRequestDto;
    }

    /**
     * Add a XrayTestEntity object to the DTO for tests with a single iteration
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

    /**
     * Build a XrayTestEntity object
     * @param collection            test suite log collection
     * @param scenarioReportCreator scenario report creator
     * @param scenarioLog           test scenario log
     * @param xrayTestAnnotation    XrayTest annotation
     * @return  Xray test entity instance
     */
    private XrayTestEntity buildXrayTestEntity(
            TestSuiteLogCollection collection,
            ScenarioReportCreator scenarioReportCreator,
            TestScenarioLogCollection scenarioLog,
            XrayTest xrayTestAnnotation
    ) {
        // Set Xray test attributes
        XrayTestEntity xrayTestEntity = initializeXrayTestEntity(scenarioLog);

        // Add a scenario report to the xray test entity
        if (xrayTestAnnotation.scenarioReport()) {
            addScenarioReport(collection, scenarioReportCreator, scenarioLog, xrayTestEntity);
        }

        // add scenario image evidence
        if (xrayTestAnnotation.screenshots()) {
            addScenarioImageEvidence(scenarioLog, xrayTestEntity);
        }

        // Set test status
        setTestStatus(scenarioLog, xrayTestEntity);

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

    /**
     * Build a step entity from a step log message
     * @param stepLog   step log message
     * @return  Xray step entity
     */
    private XrayManualTestStepResultEntity buildXrayManualTestStepResultEntity(StepInformationLogMessage stepLog) {
        // Create Step Log entity
        XrayManualTestStepResultEntity xrayTestStepEntity = new XrayManualTestStepResultEntity();

        // Set Step comment
        if (stepLog.getStep() != null) {
            xrayTestStepEntity.setComment(stepLog.getStep().getName());
        }

        // Set status (we have to treat xray server and xray cloud differently because of the different APIs)
        setStepStatus(stepLog, xrayTestStepEntity);
        return xrayTestStepEntity;
    }

    /**
     * Add an HTML Report to the Xray Test Entity
     * @param collection                Test Suite Logs
     * @param scenarioReportCreator     Object that can build a scenario report
     * @param scenarioLog               Scenario Logs
     * @param xrayTestEntity            Xray Test Entity
     */
    protected void addScenarioReport(TestSuiteLogCollection collection, ScenarioReportCreator scenarioReportCreator, TestScenarioLogCollection scenarioLog, XrayTestEntity xrayTestEntity) {
        if (XrayConfigHelper.isScenarioReportEvidenceEnabled()) {
            // Get rendered template
            Writer renderedTemplate = scenarioReportCreator.getRenderedTemplate(
                    collection, scenarioLog
            );

            // Create evidence entity for report
            XrayEvidenceItemEntity xrayReportEvidence = new XrayEvidenceItemEntity()
                    .setFilename("scenario_" + scenarioLog.getScenarioName() + ".html")
                    .setContentType("text/html")
                    .setData(Base64Helper.encode(renderedTemplate.toString()));

            // Add report evidence to upload
            xrayTestEntity
                    .addEvidence(xrayReportEvidence);
        }
    }

    /**
     * Factory method for an XrayTestEntity object
     * @param scenarioLog   Scenario Log information
     * @return  XrayTestEntity object
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

    /**
     * Add evidence to a xray test entity if available and enabled in the configuration
     * @param scenarioLog       Scenario Log object
     * @param xrayTestEntity    Xray Test Entity object
     */
    protected void addScenarioImageEvidence(TestScenarioLogCollection scenarioLog, XrayTestEntity xrayTestEntity) {
        if (XrayConfigHelper.isScenarioImageEvidenceEnabled()) {
            // Iterate over scenario screenshot paths
            for (String filepath : scenarioLog.getScreenshotPaths()) {
                try {
                    // Create image evidence entity
                    XrayEvidenceItemEntity xrayEvidenceEntity = new XrayEvidenceItemEntity()
                            .setFilename(Paths.get(filepath).getFileName().toString())
                            .setContentType("image/png")
                            .setData(Base64Helper.encodeFileContent(filepath));

                    // Add evidence entity to upload DTO
                    xrayTestEntity
                            .addEvidence(xrayEvidenceEntity);
                } catch (IOException e) {
                    // Create error entity
                    EvidenceUploadError error = new EvidenceUploadError(e)
                            .setFilepath(filepath);
                    ErrorLogCollection errors = ErrorLogCollection.getInstance();
                    errors.addErrorLog(error);
                }
            }
        }
    }

    /**
     * Set the test status for the Xray Test Entity based on the log data of a scenario
     * @param scenarioLog       Log data of a scenario
     * @param xrayTestEntity    Xray test entity
     */
    public abstract void setTestStatus(TestScenarioLogCollection scenarioLog, XrayTestEntity xrayTestEntity);

    /**
     * Set the step status for the Xray Step Entity based on the log data of a step
     * @param stepLog               Log data for a step
     * @param xrayTestStepEntity    Xray Step Entity
     */
    public abstract void setStepStatus(StepInformationLogMessage stepLog, XrayManualTestStepResultEntity xrayTestStepEntity);
}
