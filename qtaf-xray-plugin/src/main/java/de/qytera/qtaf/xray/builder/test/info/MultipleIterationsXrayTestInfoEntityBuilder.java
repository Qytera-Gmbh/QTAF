package de.qytera.qtaf.xray.builder.test.info;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntity;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntityCloud;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntityServer;
import de.qytera.qtaf.xray.entity.XrayTestStepEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.apache.logging.log4j.util.Supplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Builds an {@link XrayTestInfoEntity} objects for multiple test iterations.
 */
@RequiredArgsConstructor
public class MultipleIterationsXrayTestInfoEntityBuilder extends XrayTestInfoEntityBuilder {

    /**
     * The test scenarios to use for constructing the test information.
     */
    @NonNull
    private List<TestScenarioLogCollection> scenarioCollection;

    @Override
    public XrayTestInfoEntity buildTestInfo(XrayTest xrayTest) {
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
            for (int i = 0; i < scenarioCollection.size(); i++) {
                addScenarioLogMessages(i, linesAction, linesData, linesResults);
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
            int scenarioIndex,
            List<String> linesAction,
            List<String> linesData,
            List<String> linesResult
    ) {
        Collection<LogMessage> logMessages = scenarioCollection.get(scenarioIndex).getLogMessages();
        linesAction.add(getIterationHeader(scenarioIndex + 1));
        linesData.add(getIterationHeader(scenarioIndex + 1));
        linesResult.add(getIterationHeader(scenarioIndex + 1));
        int stepNumber = 1;
        int maxSteps = logMessages.size();
        for (LogMessage logMessage : logMessages) {
            if (logMessage instanceof StepInformationLogMessage stepLog) {
                XrayTestStepEntity step = buildTestStepEntity(stepLog);
                mergeInto(linesAction, stepNumber, maxSteps, step::getAction);
                mergeInto(linesData, stepNumber, maxSteps, step::getData);
                mergeInto(linesResult, stepNumber, maxSteps, step::getResult);
                stepNumber++;
            }
        }
    }

    private String getIterationHeader(int iteration) {
        String iterationDigits = "%0" + String.valueOf(scenarioCollection.size()).length() + "d";
        String headerFormat = String.format("=====> ITERATION %s <=====", iterationDigits);
        return String.format(headerFormat, iteration);
    }

    private void mergeInto(List<String> lines, int stepNumber, int maxSteps, Supplier<Object> contentSupplier) {
        String stepIndexFormat = "%0" + String.valueOf(maxSteps).length() + "d";
        String lineFormat = stepIndexFormat + ": %s";
        lines.add(String.format(lineFormat, stepNumber, contentSupplier.get()));
    }

}
