package de.qytera.qtaf.xray.builder.test;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.XrayJsonHelper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.*;
import lombok.NonNull;

import java.io.Writer;
import java.util.List;

/**
 * Builds {@link XrayTestEntity} objects for single test iterations.
 */
class SingleIterationXrayTestEntityBuilder extends XrayTestEntityBuilder {

    protected SingleIterationXrayTestEntityBuilder(@NonNull TestSuiteLogCollection collection) {
        super(collection);
    }

    @Override
    protected XrayTestEntity buildTestEntity(List<TestScenarioLogCollection> scenarioLogs) {
        if (scenarioLogs.isEmpty()) {
            return null;
        } else if (scenarioLogs.size() > 1) {
            throw new IllegalArgumentException("Cannot transform multiple iterations into single test run entity");
        }
        TestScenarioLogCollection scenarioLog = scenarioLogs.get(0);
        XrayTest xrayTestAnnotation = (XrayTest) scenarioLog.getAnnotation(XrayTest.class);
        // Ignore tests that don't have an Xray annotation.
        if (xrayTestAnnotation == null) {
            return null;
        }
        XrayTestEntity entity = new XrayTestEntity(scenarioLog.getStatus());
        entity.setTestInfo(buildTestInfoEntity(xrayTestAnnotation, scenarioLogs));


        entity.setStart(XrayJsonHelper.isoDateString(scenarioLog.getStart()));
        entity.setFinish(XrayJsonHelper.isoDateString(scenarioLog.getEnd()));

        if (Boolean.TRUE.equals(XrayConfigHelper.isScenarioReportEvidenceEnabled()) && xrayTestAnnotation.scenarioReport()) {
            Writer renderedTemplate = reportCreator.getRenderedTemplate(collection, scenarioLog);
            String filename = "scenario_" + scenarioLog.getScenarioName() + ".html";
            entity.getEvidence().add(XrayEvidenceItemEntity.fromString(renderedTemplate.toString(), filename));
        }

        if (Boolean.TRUE.equals(XrayConfigHelper.isScenarioImageEvidenceEnabled()) && xrayTestAnnotation.screenshots()) {
            for (String filepath : scenarioLog.getScreenshotPaths()) {
                entity.getEvidence().add(XrayEvidenceItemEntity.fromFile(filepath));
            }
        }

        for (LogMessage logMessage : scenarioLog.getLogMessages()) {
            if (logMessage instanceof StepInformationLogMessage stepLog) {
                entity.getSteps().add(buildManualTestStepResultEntity(stepLog));
            }
        }

        return entity;
    }

    @Override
    protected XrayTestInfoEntity buildTestInfoEntity(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        XrayTestInfoEntity entity = null;
        if (Boolean.TRUE.equals(XrayConfigHelper.getResultsUploadTestsInfoStepsUpdateSingleIteration())) {
            String projectKey = xrayTest.key().substring(0, xrayTest.key().indexOf("-"));
            if (XrayConfigHelper.isXrayCloudService()) {
                entity = new XrayTestInfoEntityCloud("", projectKey, "Manual");
            } else {
                entity = new XrayTestInfoEntityServer("", projectKey, "Manual");
            }
            for (LogMessage logMessage : scenarioLogs.get(0).getLogMessages()) {
                if (logMessage instanceof StepInformationLogMessage stepLog) {
                    entity.getSteps().add(buildTestStepEntity(stepLog));
                }
            }
        }
        return entity;
    }

}
