package de.qytera.qtaf.xray.builder;

import com.google.inject.Singleton;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayEvidenceEntity;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.entity.XrayTestExecutionInfoEntity;
import de.qytera.qtaf.xray.entity.XrayTestStepEntity;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.error.EvidenceUploadError;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;

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

            // Iterate over test scenarios
            for (TestScenarioLogCollection scenarioLog : testFeatureLogCollection.getScenarioLogCollection()) {
                if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.PENDING) {
                    continue;
                }

                // Set Xray test attributes
                XrayTestEntity xrayTestEntity = initializeXrayTestEntity(scenarioLog);

                // Add a scenario report to the xray test entity
                addScenarioReport(collection, scenarioReportCreator, scenarioLog, xrayTestEntity);

                // add scenario image evidence
                addScenarioImageEvidence(scenarioLog, xrayTestEntity);

                // Add test to test collection
                xrayImportRequestDto.addTest(xrayTestEntity);

                // Set test status
                setTestStatus(scenarioLog, xrayTestEntity);

                // Iterate over log messages
                for (LogMessage logMessage : scenarioLog.getLogMessages()) {

                    // Only StepLogMessage entities are important for Xray
                    if (logMessage instanceof StepInformationLogMessage) {
                        StepInformationLogMessage stepLog = (StepInformationLogMessage) logMessage;

                        // Create Step Log entity
                        XrayTestStepEntity xrayTestStepEntity = new XrayTestStepEntity();

                        // Set Step comment
                        if (stepLog.getStep() != null) {
                            xrayTestStepEntity.setComment(stepLog.getStep().getName());
                        }

                        // Set status (we have to treat xray server and xray cloud differently because of the different APIs)
                        setStepStatus(stepLog, xrayTestStepEntity);

                        // Add step log to test log
                        xrayTestEntity.addXrayStep(xrayTestStepEntity);
                    }
                }
            }
        }

        return xrayImportRequestDto;
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
            XrayEvidenceEntity xrayReportEvidence = new XrayEvidenceEntity()
                    .setFilename("scenario_" + scenarioLog.getScenarioName() + ".html")
                    .setContentType("text/html")
                    .setData(Base64Helper.encode(renderedTemplate.toString()));

            // Add report evidence to upload
            xrayTestEntity
                    .addXrayEvidenceEntity(xrayReportEvidence);
        }
    }

    /**
     * Factory method for an XrayTestEntity object
     * @param scenarioLog   Scenario Log information
     * @return  XrayTestEntity object
     */
    protected XrayTestEntity initializeXrayTestEntity(TestScenarioLogCollection scenarioLog) {
        XrayTestEntity xrayTestEntity = new XrayTestEntity()
                .setTestKey(scenarioLog.getScenarioName())
                .setStart(null) // TODO
                .setFinish(null) // TODO
                .setComment("");
        return xrayTestEntity;
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
                    XrayEvidenceEntity xrayEvidenceEntity = new XrayEvidenceEntity()
                            .setFilename(Paths.get(filepath).getFileName().toString())
                            .setContentType("image/png")
                            .setData(Base64Helper.encodeFileContent(filepath));

                    // Add evidence entity to upload DTO
                    xrayTestEntity
                            .addXrayEvidenceEntity(xrayEvidenceEntity);
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
    public abstract void setStepStatus(StepInformationLogMessage stepLog, XrayTestStepEntity xrayTestStepEntity);
}
