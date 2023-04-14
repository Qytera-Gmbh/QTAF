package de.qytera.qtaf.xray.builder.test;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.XrayJsonHelper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import de.qytera.qtaf.xray.entity.*;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.apache.logging.log4j.util.Supplier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Builds an {@link XrayTestEntity} objects for multiple test iterations.
 */
class MultipleIterationsXrayTestEntityBuilder extends XrayTestEntityBuilder {

    protected MultipleIterationsXrayTestEntityBuilder(@NonNull TestSuiteLogCollection collection) {
        super(collection);
    }

    @Override
    protected XrayTestEntity buildTestEntity(List<TestScenarioLogCollection> scenarioLogs) {
        if (scenarioLogs.isEmpty()) {
            return null;
        }
        XrayTest xrayTestAnnotation = (XrayTest) scenarioLogs.get(0).getAnnotation(XrayTest.class);
        // Ignore tests that don't have an Xray annotation.
        if (xrayTestAnnotation == null) {
            return null;
        }
        XrayTestEntity entity = new XrayTestEntity(XrayStatusHelper.getStatus(scenarioLogs));
        entity.setTestInfo(buildTestInfoEntity(xrayTestAnnotation, scenarioLogs));
        boolean allScenariosPassed = true;
        Date start = null;
        Date end = null;
        for (TestScenarioLogCollection scenarioLog : scenarioLogs) {
            if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.FAILURE) {
                allScenariosPassed = false;
            }
            entity.getIterations().add(buildIterationResultEntity(scenarioLog));
            if (start == null || scenarioLog.getStart().before(start)) {
                start = scenarioLog.getStart();
            }
            if (end == null || scenarioLog.getEnd().after(end)) {
                end = scenarioLog.getEnd();
            }
        }
        if (start != null) {
            entity.setStart(XrayJsonHelper.isoDateString(start));
        }
        if (end != null) {
            entity.setFinish(XrayJsonHelper.isoDateString(end));
        }
        if (allScenariosPassed) {
            entity.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        } else {
            entity.setStatus(TestScenarioLogCollection.Status.FAILURE);
        }
        return entity;
    }

    @Override
    protected XrayTestInfoEntity buildTestInfoEntity(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        XrayTestInfoEntity entity = null;
        if (Boolean.TRUE.equals(XrayConfigHelper.getResultsTestsInfoStepsMergeOnMultipleIterations())) {
            String projectKey = xrayTest.key().substring(0, xrayTest.key().indexOf("-"));
            if (XrayConfigHelper.isXrayCloudService()) {
                entity = new XrayTestInfoEntityCloud("", projectKey, "Manual");
            } else {
                entity = new XrayTestInfoEntityServer("", projectKey, "Manual");
            }
            List<String> linesAction = new ArrayList<>();
            List<String> linesData = new ArrayList<>();
            List<String> linesResults = new ArrayList<>();
            for (int i = 0; i < scenarioLogs.size(); i++) {
                addScenarioLogMessages(scenarioLogs, i, linesAction, linesData, linesResults);
            }
            XrayTestStepEntity mergedStep = new XrayTestStepEntity(projectKey);
            mergedStep.setAction(Strings.join(linesAction, '\n'));
            mergedStep.setData(Strings.join(linesData, '\n'));
            mergedStep.setResult(Strings.join(linesResults, '\n'));
            entity.getSteps().add(mergedStep);
        }
        return entity;
    }

    private void addScenarioLogMessages(
            List<TestScenarioLogCollection> scenarioCollection,
            int scenarioIndex,
            List<String> linesAction,
            List<String> linesData,
            List<String> linesResult
    ) {
        linesAction.add(getIterationHeader(scenarioCollection, scenarioIndex + 1));
        linesData.add(getIterationHeader(scenarioCollection, scenarioIndex + 1));
        linesResult.add(getIterationHeader(scenarioCollection, scenarioIndex + 1));
        List<StepInformationLogMessage> logMessages = scenarioCollection.get(scenarioIndex).getLogMessages().stream()
                .filter(StepInformationLogMessage.class::isInstance)
                .map(StepInformationLogMessage.class::cast)
                .toList();
        if (logMessages.isEmpty()) {
            linesAction.add("// Warning: no action defined");
            linesData.add("// Warning: no data defined");
            linesResult.add("// Warning: no result defined");
            return;
        }
        int stepNumber = 1;
        int maxSteps = logMessages.size();
        for (StepInformationLogMessage logMessage : logMessages) {
            XrayTestStepEntity step = buildTestStepEntity(logMessage);
            mergeInto(linesAction, stepNumber, maxSteps, step::getAction);
            mergeInto(linesData, stepNumber, maxSteps, step::getData);
            mergeInto(linesResult, stepNumber, maxSteps, step::getResult);
            stepNumber++;
        }
    }

    private String getIterationHeader(List<TestScenarioLogCollection> scenarioCollection, int iteration) {
        String iterationDigits = "%0" + String.valueOf(scenarioCollection.size()).length() + "d";
        String headerFormat = String.format("=====> ITERATION %s <=====", iterationDigits);
        return String.format(headerFormat, iteration);
    }

    private void mergeInto(List<String> lines, int stepNumber, int maxSteps, Supplier<Object> contentSupplier) {
        String stepIndexFormat = "%0" + String.valueOf(maxSteps).length() + "d";
        String lineFormat = stepIndexFormat + ": %s";
        lines.add(String.format(lineFormat, stepNumber, contentSupplier.get()));
    }

    private static XrayIterationResultEntity buildIterationResultEntity(TestScenarioLogCollection scenarioLog) {
        XrayIterationResultEntity entity;
        if (XrayConfigHelper.isXrayCloudService()) {
            entity = new XrayIterationResultEntityCloud(scenarioLog.getStatus());
        } else {
            entity = new XrayIterationResultEntityServer(scenarioLog.getStatus());
        }
        for (TestScenarioLogCollection.TestParameter testParameter : scenarioLog.getTestParameters()) {
            XrayIterationParameterEntity parameterEntity = new XrayIterationParameterEntity();
            parameterEntity.setName(XrayJsonHelper.truncateParameterName(testParameter.getName()));
            parameterEntity.setValue(XrayJsonHelper.truncateParameterValue(testParameter.getValue().toString()));
            entity.getParameters().add(parameterEntity);
        }
        for (LogMessage logMessage : scenarioLog.getLogMessages()) {
            if (logMessage instanceof StepInformationLogMessage stepLog) {
                entity.getSteps().add(buildManualTestStepResultEntity(stepLog));
            }
        }
        return entity;
    }

}
