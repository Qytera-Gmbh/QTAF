package de.qytera.qtaf.xray.builder.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.XrayJsonHelper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import de.qytera.qtaf.xray.entity.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class for building {@link XrayTestEntity} objects. The way these objects are built heavily depends on the test
 * suite configuration.
 *
 * @param <T> a type describing scenario data to transform into a test entity, e.g. {@code List<TestScenarioLogCollection>}
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class XrayTestEntityBuilder<T> {

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
     * Builds a test entity for an executed test.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to transform into an {@link XrayTestEntity}
     * @return the test entity
     */
    public XrayTestEntity buildTestEntity(XrayTest xrayTest, T scenarioData) {
        XrayTestEntity entity = new XrayTestEntity(getStatus(xrayTest, scenarioData));
        entity.setTestKey(xrayTest.key());
        entity.setTestInfo(getTestInfo(xrayTest, scenarioData));
        entity.setStart(XrayJsonHelper.isoDateString(getStartDate(xrayTest, scenarioData)));
        entity.setFinish(XrayJsonHelper.isoDateString(getEndDate(xrayTest, scenarioData)));
        entity.setComment(getComment(xrayTest, scenarioData));
        entity.setExecutedBy(getExecutedBy(xrayTest, scenarioData));
        entity.setAssignee(getAssignee(xrayTest, scenarioData));

        // Only when there is one execution we can add steps directly to the test entity
        if (scenarioData instanceof TestScenarioLogCollection) {
            entity.setSteps(getSteps(xrayTest, scenarioData));
        } else {
            entity.setSteps(null);
        }

        // Empty arrays for examples are not accepted by the Xray API
        List<String> examples = getExamples(xrayTest, scenarioData);
        if (!examples.isEmpty()) {
            entity.setExamples(examples);
        } else {
            entity.setExamples(null);
        }

        // Only if there are multiple iterations a "iterations" key can be added to a test entity
        if (scenarioData instanceof List<?>) {
            entity.setIterations(getIterations(xrayTest, scenarioData));
        } else {
            entity.setIterations(null);
        }

        entity.setDefects(getDefects(xrayTest, scenarioData));
        entity.setEvidence(getEvidence(xrayTest, scenarioData));
        entity.setCustomFields(getCustomFields(xrayTest, scenarioData));
        return entity;
    }

    /**
     * Extracts the test's status from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the status from
     * @return the test's status
     * @see XrayTestEntity
     */
    protected abstract TestScenarioLogCollection.Status getStatus(XrayTest xrayTest, T scenarioData);

    /**
     * Builds a test information entity for a test. Returns {@code null} if the configuration has not been configured to
     * build test information entities.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to transform into an {@link XrayTestInfoEntity}
     * @return the test information entity
     */
    protected abstract XrayTestInfoEntity getTestInfo(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test start date from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the start date from
     * @return the test's start date
     * @see XrayTestEntity
     */
    protected abstract Date getStartDate(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test end date from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the end date from
     * @return the test's end date
     * @see XrayTestEntity
     */
    protected abstract Date getEndDate(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test comment from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the comment from
     * @return the test's comment
     * @see XrayTestEntity
     */
    protected abstract String getComment(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the user id who executed the test from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the user id from
     * @return the user id of the user who executed the test
     * @see XrayTestEntity
     */
    protected abstract String getExecutedBy(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the user id who the test should be assigned to from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the assignee from
     * @return the test's assignee
     * @see XrayTestEntity
     */
    protected abstract String getAssignee(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test's list of test step results from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the steps from
     * @return the test's steps
     * @see XrayTestEntity
     */
    protected abstract List<XrayManualTestStepResultEntity> getSteps(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test's list of examples from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the examples from
     * @return the test's examples
     * @see XrayTestEntity
     */
    protected abstract List<String> getExamples(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test's list of iterations from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the iterations from
     * @return the test's iterations
     * @see XrayTestEntity
     */
    protected abstract List<XrayIterationResultEntity> getIterations(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test's list of defects from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the defects from
     * @return the test's defects
     * @see XrayTestEntity
     */
    protected abstract List<String> getDefects(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test's list of (defect) evidence from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the evidence from
     * @return the test's evidence
     * @see XrayTestEntity
     */
    protected abstract List<XrayEvidenceItemEntity> getEvidence(XrayTest xrayTest, T scenarioData);

    /**
     * Extracts the test's list of custom field (values) from provided scenario data.
     *
     * @param xrayTest     the {@link XrayTest} annotation of the test
     * @param scenarioData the scenario data to extract the custom fields from
     * @return the test's custom field (values)
     * @see XrayTestEntity
     */
    protected abstract List<XrayCustomFieldEntity> getCustomFields(XrayTest xrayTest, T scenarioData);

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
     * Converts a list of {@link StepInformationLogMessage} into a single {@link XrayTestStepEntity}.
     * <p>
     * <b>Note</b>: these are different from {@link XrayManualTestStepResultEntity}.
     * </p>
     *
     * @param steps the steps' information to convert
     * @return the converted Xray test step
     */
    protected static XrayTestStepEntity buildMergedTestStepEntity(List<StepInformationLogMessage> steps) {
        List<String> linesAction = new ArrayList<>();
        List<String> linesData = new ArrayList<>();
        List<String> linesResult = new ArrayList<>();
        if (steps.isEmpty()) {
            QtafFactory.getLogger().warn("Merging empty step log collection into single step");
            linesAction.add("// no action defined");
            linesData.add("// no data defined");
            linesResult.add("// no result defined");
        } else {
            for (int i = 0; i < steps.size(); i++) {
                StepInformationLogMessage step = steps.get(i);
                linesAction.add(stepString(i, steps.size(), step.getStep().getName()));
                linesData.add(stepString(i, steps.size(), stepDataString(step)));
                linesResult.add(stepString(i, steps.size(), step.getResult()));
            }
        }
        XrayTestStepEntity mergedStep = new XrayTestStepEntity(Strings.join(linesAction, '\n'));
        mergedStep.setData(Strings.join(linesData, '\n'));
        mergedStep.setResult(Strings.join(linesResult, '\n'));
        return mergedStep;
    }

    /**
     * Converts a {@link StepInformationLogMessage} into an {@link XrayManualTestStepResultEntity}.
     * <p>
     * <b>Note</b>: these are different from {@link XrayTestStepEntity}.
     * </p>
     *
     * @param step the step information to convert
     * @return the converted Xray test step result
     */
    protected static XrayManualTestStepResultEntity buildManualTestStepResultEntity(StepInformationLogMessage step) {
        XrayManualTestStepResultEntity entity;
        if (XrayConfigHelper.isXrayCloudService()) {
            entity = new XrayManualTestStepResultEntityCloud(step.getStatus());
        } else {
            entity = new XrayManualTestStepResultEntityServer(step.getStatus());
        }
        if (step.getStep() != null) {
            entity.setComment(step.getStep().getName());
        }
        entity.setActualResult(buildActualResult(step));
        if (step.getScreenshotBefore() != null && !step.getScreenshotBefore().isBlank()) {
            entity.addEvidenceIfPresent(XrayEvidenceItemEntity.fromFile(step.getScreenshotBefore()));
        }
        if (step.getScreenshotAfter() != null && !step.getScreenshotAfter().isBlank()) {
            entity.addEvidenceIfPresent(XrayEvidenceItemEntity.fromFile(step.getScreenshotAfter()));
        }
        return entity;
    }

    /**
     * Generate the value of the "actualResult" attribute of a test step execution.
     *
     * @param step Step log message object
     * @return actualResult value
     */
    protected static String buildActualResult(StepInformationLogMessage step) {
        StringBuilder actualResult = new StringBuilder();
        List<AssertionLogMessage> passedAssertions = step.getAssertions().stream().filter(AssertionLogMessage::hasPassed).toList();
        List<AssertionLogMessage> failedAssertions = step.getAssertions().stream().filter(AssertionLogMessage::hasFailed).toList();

        // Generate text for passed assertions
        if (!passedAssertions.isEmpty()) {
            actualResult.append("PASSED ASSERTIONS:\n\n");

            for (AssertionLogMessage assertionLogMessage : passedAssertions) {
                actualResult.append("  - %s".formatted(assertionLogMessage.getMessage()));
            }
        }

        // Generate text for failed assertions
        if (!failedAssertions.isEmpty()) {
            if (!actualResult.isEmpty()) {
                actualResult.append("\n\n");
            }

            actualResult.append("FAILED ASSERTIONS:\n\n");

            for (AssertionLogMessage assertionLogMessage : failedAssertions) {
                actualResult.append("  - %s".formatted(assertionLogMessage.getMessage()));
            }
        }

        // Generate text for the error
        if (step.getError() != null) {
            if (!actualResult.isEmpty()) {
                actualResult.append("\n\n");
            }

            actualResult.append("ERRORS:\n\n");
            actualResult.append(step.getError().getMessage());
        }

        // Generate text for step result
        if (step.getResult() != null) {
            if (!actualResult.isEmpty()) {
                actualResult.append("\n\n");
            }

            actualResult.append("RESULT:\n\n");
            actualResult.append(step.getResult());
        }

        return actualResult.toString();
    }

    /**
     * Converts a list of {@link StepInformationLogMessage} into a single {@link XrayManualTestStepResultEntity}.
     * <p>
     * <b>Note</b>: these are different from {@link XrayTestStepEntity}.
     * </p>
     *
     * @param steps the steps' information to convert
     * @return the converted Xray test step result
     */
    protected static XrayManualTestStepResultEntity buildMergedManualTestStepResultEntity(
            List<StepInformationLogMessage> steps
    ) {
        XrayManualTestStepResultEntity entity;
        if (XrayConfigHelper.isXrayCloudService()) {
            entity = new XrayManualTestStepResultEntityCloud(XrayStatusHelper.combinedStepStatus(steps));
        } else {
            entity = new XrayManualTestStepResultEntityServer(XrayStatusHelper.combinedStepStatus(steps));
        }
        List<XrayManualTestStepResultEntity> stepResults = steps.stream()
                .map(XrayTestEntityBuilder::buildManualTestStepResultEntity)
                .toList();
        String comment = IntStream.range(0, stepResults.size())
                .mapToObj(i -> stepString(i, stepResults.size(), stepResults.get(i).getComment()))
                .collect(Collectors.joining("\n"));
        List<String> defects = stepResults.stream()
                .map(XrayManualTestStepResultEntity::getDefects)
                .flatMap(Collection::stream)
                .toList();
        String actualResult = IntStream.range(0, stepResults.size())
                .mapToObj(i -> stepString(i, stepResults.size(), stepResults.get(i).getActualResult()))
                .collect(Collectors.joining("\n"));
        List<XrayEvidenceItemEntity> evidence = stepResults.stream()
                .map(XrayManualTestStepResultEntity::getAllEvidence)
                .flatMap(Collection::stream)
                .toList();
        entity.setComment(comment);
        entity.setDefects(defects);
        entity.setActualResult(actualResult);
        entity.getAllEvidence().addAll(evidence);
        return entity;
    }

    private static String stepString(int stepNumber, int maxSteps, Object content) {
        // Xray starts counting at 1.
        String stepIndexFormat = "%0" + String.valueOf(maxSteps + 1).length() + "d";
        String lineFormat = stepIndexFormat + ": %s";
        return String.format(lineFormat, stepNumber + 1, content);
    }

    private static String stepDataString(StepInformationLogMessage step) {
        if (step.getStepParameters().isEmpty()) {
            return "<none>";
        }
        List<String> parameterStrings = new ArrayList<>(step.getStepParameters().size());
        for (StepInformationLogMessage.StepParameter parameter : step.getStepParameters()) {
            Object value = parameter.getValue();
            if (value instanceof Object[] array) {
                value = Arrays.toString(array);
            }
            parameterStrings.add(String.format("%s=%s", parameter.getName(), value));
        }
        return Strings.join(parameterStrings, '\n');
    }

}
