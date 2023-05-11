package de.qytera.qtaf.xray.builder.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.XrayJsonHelper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import de.qytera.qtaf.xray.entity.*;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

/**
 * Builds an {@link XrayTestEntity} objects for multiple test iterations.
 */
public class MultipleIterationsXrayTestEntityBuilder extends XrayTestEntityBuilder<List<TestScenarioLogCollection>> {

    /**
     * Constructs a new Xray test entity builder for tests with multiple test iterations.
     *
     * @param collection     the collection instance required for building HTML reports
     * @param issueSummaries the Jira issue summaries required when updating test issue steps
     */
    public MultipleIterationsXrayTestEntityBuilder(
            @NonNull TestSuiteLogCollection collection,
            @NonNull Map<String, String> issueSummaries
    ) {
        super(collection, issueSummaries);
    }

    @Override
    protected TestScenarioLogCollection.Status getStatus(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        return XrayStatusHelper.combinedScenarioStatus(scenarioLogs);
    }

    @Override
    protected XrayTestInfoEntity getTestInfo(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        XrayTestInfoEntity entity = null;
        if (XrayConfigHelper.shouldResultsUploadTestsInfoStepsUpdate()) {
            String projectKey = XrayConfigHelper.getProjectKey();
            if (XrayConfigHelper.isXrayCloudService()) {
                entity = new XrayTestInfoEntityCloud(issueSummaries.get(xrayTest.key()), projectKey, "Manual");
            } else {
                entity = new XrayTestInfoEntityServer(issueSummaries.get(xrayTest.key()), projectKey, "Manual");
            }
            if (XrayConfigHelper.shouldResultsUploadTestsInfoStepsMerge()) {
                entity.getSteps().add(buildMergedTestInfoStepEntity(scenarioLogs));
            } else {
                entity.setSteps(buildTestInfoStepEntities(xrayTest, scenarioLogs));
            }
        }
        return entity;
    }

    @Override
    protected Date getStartDate(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        Date start = null;
        for (TestScenarioLogCollection scenarioLog : scenarioLogs) {
            if (start == null || scenarioLog.getStart().before(start)) {
                start = scenarioLog.getStart();
            }
        }
        return start;
    }

    @Override
    protected Date getEndDate(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        Date end = null;
        for (TestScenarioLogCollection scenarioLog : scenarioLogs) {
            if (end == null || scenarioLog.getEnd().after(end)) {
                end = scenarioLog.getEnd();
            }
        }
        return end;
    }

    @Override
    protected String getComment(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Not yet supported by the plugin.
        return null;
    }

    @Override
    protected String getExecutedBy(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Not yet supported by the plugin.
        return null;
    }

    @Override
    protected String getAssignee(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Not yet supported by the plugin.
        return null;
    }

    @Override
    protected List<XrayManualTestStepResultEntity> getSteps(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // No step results: we've got entire iterations for that.
        return Collections.emptyList();
    }

    @Override
    protected List<String> getExamples(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Not yet supported by the plugin.
        return Collections.emptyList();
    }

    @Override
    protected List<XrayIterationResultEntity> getIterations(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        List<XrayIterationResultEntity> iterations = new ArrayList<>(scenarioLogs.size());
        for (int i = 0; i < scenarioLogs.size(); i++) {
            TestScenarioLogCollection scenarioLog = scenarioLogs.get(i);
            XrayIterationResultEntity iteration;
            if (XrayConfigHelper.isXrayCloudService()) {
                iteration = new XrayIterationResultEntityCloud(scenarioLog.getStatus());
            } else {
                iteration = new XrayIterationResultEntityServer(scenarioLog.getStatus());
            }
            iterations.add(iteration);
            for (TestScenarioLogCollection.TestParameter testParameter : scenarioLog.getTestParameters()) {
                XrayIterationParameterEntity parameterEntity = new XrayIterationParameterEntity();
                parameterEntity.setName(XrayJsonHelper.truncateParameterName(testParameter.getName()));
                parameterEntity.setValue(XrayJsonHelper.truncateParameterValue(testParameter.getValue().toString()));
                iteration.getParameters().add(parameterEntity);
            }
            List<StepInformationLogMessage> steps = scenarioLog.getLogMessages(StepInformationLogMessage.class);
            // Can happen if there's an error in a Before() or BeforeMethod() call.
            if (steps.isEmpty()) {
                continue;
            }
            if (XrayConfigHelper.shouldResultsUploadTestsInfoStepsMerge()) {
                if (!XrayConfigHelper.shouldResultsUploadTestsInfoStepsUpdate()) {
                    QtafFactory.getLogger().warn(
                            String.format(
                                    "The plugin was configured to merge test steps, but not to update test issue steps. " +
                                            "This might lead to inconsistencies between the test steps of %s and the steps of test iteration %d!",
                                    xrayTest.key(),
                                    i + 1
                            )
                    );
                }
                iteration.getSteps().add(buildMergedManualTestStepResultEntity(steps));
            } else {
                steps.stream().map(XrayTestEntityBuilder::buildManualTestStepResultEntity).forEach(iteration.getSteps()::add);
            }
        }
        return iterations;
    }

    @Override
    protected List<String> getDefects(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Not yet supported by the plugin.
        return Collections.emptyList();
    }

    @Override
    protected List<XrayEvidenceItemEntity> getEvidence(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Evidence is attached to iterations instead of the scenario here.
        return Collections.emptyList();
    }

    @Override
    protected List<XrayCustomFieldEntity> getCustomFields(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs) {
        // Not yet supported by the plugin.
        return Collections.emptyList();
    }

    private static List<XrayTestStepEntity> buildTestInfoStepEntities(
            XrayTest xrayTest,
            List<TestScenarioLogCollection> iterations
    ) {
        Set<Integer> iterationSizes = new HashSet<>();
        // Store the longest list of steps to use for defining the issue's steps in Xray during upload.
        // Otherwise, iterations with more steps than steps present in the issue will have excess steps omitted.
        List<StepInformationLogMessage> longestStepSequence = null;
        for (TestScenarioLogCollection iteration : iterations) {
            List<StepInformationLogMessage> steps = iteration.getLogMessages(StepInformationLogMessage.class);
            iterationSizes.add(steps.size());
            if (longestStepSequence == null || steps.size() > longestStepSequence.size()) {
                longestStepSequence = steps;
            }
        }
        if (longestStepSequence == null) {
            return Collections.emptyList();
        }
        if (iterationSizes.size() != 1) {
            QtafFactory.getLogger().warn(
                    String.format(
                            "Iterations of %s have varying numbers of steps: %s. %s",
                            xrayTest.key(),
                            iterationSizes,
                            "The issue's steps might not match the iterations' steps after upload."
                    )
            );
        }
        return longestStepSequence.stream().map(XrayTestEntityBuilder::buildTestStepEntity).toList();
    }

    private static XrayTestStepEntity buildMergedTestInfoStepEntity(List<TestScenarioLogCollection> iterations) {
        List<String> linesAction = new ArrayList<>();
        List<String> linesData = new ArrayList<>();
        List<String> linesResults = new ArrayList<>();
        for (int i = 0; i < iterations.size(); i++) {
            linesAction.add(getIterationHeader(iterations, i));
            linesData.add(getIterationHeader(iterations, i));
            linesResults.add(getIterationHeader(iterations, i));
            List<StepInformationLogMessage> steps = iterations.get(i).getLogMessages(StepInformationLogMessage.class);
            XrayTestStepEntity stepEntity = buildMergedTestStepEntity(steps);
            linesAction.add(stepEntity.getAction());
            linesData.add(stepEntity.getData());
            linesResults.add(stepEntity.getResult());
        }
        XrayTestStepEntity mergedStep = new XrayTestStepEntity(Strings.join(linesAction, '\n'));
        mergedStep.setData(Strings.join(linesData, '\n'));
        mergedStep.setResult(Strings.join(linesResults, '\n'));
        return mergedStep;
    }

    private static String getIterationHeader(List<TestScenarioLogCollection> scenarioCollection, int iteration) {
        // Xray starts counting at 1.
        String iterationDigits = "%0" + String.valueOf(scenarioCollection.size() + 1).length() + "d";
        String headerFormat = String.format("=====> ITERATION %s <=====", iterationDigits);
        return String.format(headerFormat, iteration + 1);
    }

}
