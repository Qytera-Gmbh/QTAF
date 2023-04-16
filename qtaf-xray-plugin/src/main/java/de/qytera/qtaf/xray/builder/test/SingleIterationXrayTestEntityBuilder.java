package de.qytera.qtaf.xray.builder.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.*;
import lombok.NonNull;

import java.io.Writer;
import java.util.*;

/**
 * Builds {@link XrayTestEntity} objects for single test iterations.
 */
public class SingleIterationXrayTestEntityBuilder extends XrayTestEntityBuilder<TestScenarioLogCollection> {

    /**
     * Constructs a new Xray test entity builder for tests with single test iterations.
     *
     * @param collection     the collection instance required for building HTML reports
     * @param issueSummaries the Jira issue summaries required when updating test issue steps
     */
    public SingleIterationXrayTestEntityBuilder(
            @NonNull TestSuiteLogCollection collection,
            @NonNull Map<String, String> issueSummaries
    ) {
        super(collection, issueSummaries);
    }

    @Override
    protected TestScenarioLogCollection.Status getStatus(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        return scenario.getStatus();
    }

    @Override
    protected XrayTestInfoEntity getTestInfo(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        XrayTestInfoEntity entity = null;
        if (XrayConfigHelper.shouldResultsUploadTestsInfoStepsUpdate()) {
            String projectKey = xrayTest.key().substring(0, xrayTest.key().indexOf("-"));
            if (XrayConfigHelper.isXrayCloudService()) {
                entity = new XrayTestInfoEntityCloud(issueSummaries.get(xrayTest.key()), projectKey, "Manual");
            } else {
                entity = new XrayTestInfoEntityServer(issueSummaries.get(xrayTest.key()), projectKey, "Manual");
            }
            if (XrayConfigHelper.shouldResultsUploadTestsInfoStepsMerge()) {
                entity.getSteps().add(buildMergedTestStepEntity(scenario.getLogMessages(StepInformationLogMessage.class)));
            } else {
                for (LogMessage logMessage : scenario.getLogMessages()) {
                    if (logMessage instanceof StepInformationLogMessage stepLog) {
                        entity.getSteps().add(buildTestStepEntity(stepLog));
                    }
                }
            }
        }
        return entity;
    }

    @Override
    protected Date getStartDate(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        return scenario.getStart();
    }

    @Override
    protected Date getEndDate(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        return scenario.getEnd();
    }

    @Override
    protected String getComment(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // Not yet supported by the plugin.
        return null;
    }

    @Override
    protected String getExecutedBy(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // Not yet supported by the plugin.
        return null;
    }

    @Override
    protected String getAssignee(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // Not yet supported by the plugin.
        return null;
    }

    @Override
    protected List<XrayManualTestStepResultEntity> getSteps(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        List<XrayManualTestStepResultEntity> steps = new ArrayList<>();
        if (XrayConfigHelper.shouldResultsUploadTestsInfoStepsMerge()) {
            if (!XrayConfigHelper.shouldResultsUploadTestsInfoStepsUpdate()) {
                QtafFactory.getLogger().warn(
                        String.format(
                                "The plugin was configured to merge test steps, but not to update test issue steps. " +
                                        "This might lead to inconsistencies between the test steps of %s and the steps of the test execution!",
                                xrayTest.key()
                        )
                );
            }
            steps.add(buildMergedManualTestStepResultEntity(scenario.getLogMessages(StepInformationLogMessage.class)));
        } else {
            for (StepInformationLogMessage step : scenario.getLogMessages(StepInformationLogMessage.class)) {
                steps.add(buildManualTestStepResultEntity(step));
            }
        }
        return steps;
    }

    @Override
    protected List<String> getExamples(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // Not yet supported by the plugin.
        return Collections.emptyList();
    }

    @Override
    protected List<XrayIterationResultEntity> getIterations(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // No iterations in a single test run.
        return Collections.emptyList();
    }

    @Override
    protected List<String> getDefects(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // Not yet supported by the plugin.
        return Collections.emptyList();
    }

    @Override
    protected List<XrayEvidenceItemEntity> getEvidence(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        List<XrayEvidenceItemEntity> evidence = new ArrayList<>();
        if (XrayConfigHelper.isScenarioReportEvidenceEnabled() && xrayTest.scenarioReport()) {
            Writer renderedTemplate = reportCreator.getRenderedTemplate(collection, scenario);
            String filename = "scenario_" + scenario.getScenarioName() + ".html";
            evidence.add(XrayEvidenceItemEntity.fromString(renderedTemplate.toString(), filename));
        }

        if (XrayConfigHelper.isScenarioImageEvidenceEnabled() && xrayTest.screenshots()) {
            for (String filepath : scenario.getScreenshotPaths()) {
                evidence.add(XrayEvidenceItemEntity.fromFile(filepath));
            }
        }
        return evidence;
    }

    @Override
    protected List<XrayCustomFieldEntity> getCustomFields(XrayTest xrayTest, TestScenarioLogCollection scenario) {
        // Not yet supported by the plugin.
        return Collections.emptyList();
    }

}
