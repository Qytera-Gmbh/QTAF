package de.qytera.qtaf.xray.builder.test;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class for building {@link XrayTestEntity} objects. The way these objects are built heavily depends on the test
 * suite configuration.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class XrayTestEntityBuilder {

    /**
     * The entire test suite logs. Used for generating HTML reports.
     */
    @NonNull
    protected TestSuiteLogCollection collection;

    /**
     * HTML report creator for scenarios.
     */
    protected final ScenarioReportCreator reportCreator = new ScenarioReportCreator();

    /**
     * A mapping of test issue keys to their summaries.
     */
    @NonNull
    protected Map<String, String> issueSummaries;

    /**
     * Builds a test entity for an executed test. Returns {@code null} in case the executed scenario was not linked to
     * an Xray issue via {@link XrayTest} or if the plugin configuration prevents QTAF from doing so.
     *
     * @param collection     the test suite collection used for retrieving HTML report data
     * @param issueSummaries a mapping of test issue keys to issue summaries to use during upload
     * @param scenarioLogs   the scenario logs to transform into an {@link XrayTestEntity}
     * @return the test entity
     */
    public static XrayTestEntity buildFrom(
            TestSuiteLogCollection collection,
            Map<String, String> issueSummaries,
            List<TestScenarioLogCollection> scenarioLogs
    ) {
        if (scenarioLogs.isEmpty()) {
            throw new IllegalArgumentException("Cannot build Xray test entity without any scenario log");
        }
        XrayTestEntityBuilder builder;
        if (scenarioLogs.size() == 1) {
            builder = new SingleIterationXrayTestEntityBuilder(collection, issueSummaries);
        } else {
            builder = new MultipleIterationsXrayTestEntityBuilder(collection, issueSummaries);
        }
        return builder.buildTestEntity(scenarioLogs);
    }

    /**
     * Builds a test entity for an executed test.
     *
     * @param scenarioLogs the scenario logs to transform into an {@link XrayTestEntity}
     * @return the test entity
     */
    protected abstract XrayTestEntity buildTestEntity(List<TestScenarioLogCollection> scenarioLogs);

    /**
     * Builds a test information entity for a test. Returns {@code null} if the configuration has not been configured to
     * build test information entities.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioLogs the scenario logs to transform into an {@link XrayTestInfoEntity}
     * @return the test information entity
     */
    protected abstract XrayTestInfoEntity buildTestInfoEntity(XrayTest xrayTest, List<TestScenarioLogCollection> scenarioLogs);

    /**
     * Converts a {@link StepInformationLogMessage} into an {@link XrayTestStepEntity}.
     * <p>
     * <b>Note</b>: these are different from {@link XrayManualTestStepResultEntity}.
     * </p>
     *
     * @param stepLog the step information to convert
     * @return the converted Xray test step
     */
    protected static XrayTestStepEntity buildTestStepEntity(StepInformationLogMessage stepLog) {
        XrayTestStepEntity entity = new XrayTestStepEntity(stepLog.getStep().getName());
        String data = stepLog.getStepParameters().stream()
                .map(p -> {
                    Object value = p.getValue();
                    if (value instanceof Object[] array) {
                        value = Arrays.toString(array);
                    }
                    return String.format("%s=%s", p.getName(), value);
                })
                .collect(Collectors.joining("\n"));
        if (!data.isBlank()) {
            entity.setData(data);
        }
        if (stepLog.getResult() != null) {
            entity.setResult(stepLog.getResult().toString());
        }
        return entity;
    }

    /**
     * Converts a {@link StepInformationLogMessage} into an {@link XrayManualTestStepResultEntity}.
     * <p>
     * <b>Note</b>: these are different from {@link XrayTestStepEntity}.
     * </p>
     *
     * @param stepLog the step information to convert
     * @return the converted Xray test step result
     */
    protected static XrayManualTestStepResultEntity buildManualTestStepResultEntity(StepInformationLogMessage stepLog) {
        XrayManualTestStepResultEntity xrayTestStepEntity;
        if (XrayConfigHelper.isXrayCloudService()) {
            xrayTestStepEntity = new XrayManualTestStepResultEntityCloud(stepLog.getStatus());
        } else {
            xrayTestStepEntity = new XrayManualTestStepResultEntityServer(stepLog.getStatus());
        }
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

}
