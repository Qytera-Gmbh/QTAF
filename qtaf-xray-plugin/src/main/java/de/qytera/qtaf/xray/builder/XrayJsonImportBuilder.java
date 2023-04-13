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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Writer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Transforms log collection into Xray Execution Import DTO
 */
@Singleton
@RequiredArgsConstructor
public class XrayJsonImportBuilder {

    @NonNull
    private TestSuiteLogCollection collection;

    /**
     * HTML report creator for scenarios.
     */
    private final ScenarioReportCreator reportCreator = new ScenarioReportCreator();

    /**
     * Creates an execution import DTO based on the test suite logs.
     *
     * @return the execution import DTO
     */
    public XrayImportRequestDto buildRequest() {
        XrayImportRequestDto xrayImportRequestDto = new XrayImportRequestDto();
        xrayImportRequestDto.setInfo(buildTestExecutionInfoEntity());
        xrayImportRequestDto.setTests(buildTestEntities());
        return xrayImportRequestDto;
    }

    private static String isoDateString(Date date) {
        // Date in ISO 8601 format: "2022-12-01T02:30:44Z"
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_INSTANT);
    }

    private XrayTestExecutionInfoEntity buildTestExecutionInfoEntity() {
        XrayTestExecutionInfoEntity entity = new XrayTestExecutionInfoEntity();
        if (collection.getStart() != null) {
            String startDate = isoDateString(collection.getStart());
            entity.setStartDate(startDate);
        }
        if (collection.getEnd() != null) {
            String finishDate = isoDateString(collection.getEnd());
            entity.setFinishDate(finishDate);
        }
        entity.getTestEnvironments().add(collection.getOsName());
        entity.getTestEnvironments().add(collection.getDriverName());
        return entity;
    }

    private List<XrayTestEntity> buildTestEntities() {
        List<XrayTestEntity> entities = new ArrayList<>();
        for (TestFeatureLogCollection testFeatureLogCollection : collection.getTestFeatureLogCollections()) {
            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();
            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    XrayTest xrayTestAnnotation = (XrayTest) entry.getValue().get(0).getAnnotation(XrayTest.class);
                    // Ignore tests that don't have an Xray annotation.
                    if (xrayTestAnnotation == null) {
                        continue;
                    }
                    entities.add(buildTestEntity(xrayTestAnnotation, entry.getValue()));
                }
            }
        }
        return entities;
    }

    private XrayTestEntity buildTestEntity(XrayTest xrayTestAnnotation, List<TestScenarioLogCollection> scenarioCollection) {
        XrayTestEntity entity;
        if (scenarioCollection.size() == 1) {
            entity = buildSingleIterationTestEntity(xrayTestAnnotation, scenarioCollection.get(0));
        } else {
            entity = buildMultipleIterationsTestEntity(xrayTestAnnotation, scenarioCollection);
        }
        return entity;
    }

    private XrayTestEntity buildSingleIterationTestEntity(XrayTest xrayTestAnnotation, TestScenarioLogCollection scenarioLog) {
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.PENDING);
        entity.setTestKey(xrayTestAnnotation.key());
        // Skip pending scenarios.
        if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.PENDING) {
            return entity;
        }
        entity.setStart(isoDateString(scenarioLog.getStart()));
        entity.setFinish(isoDateString(scenarioLog.getEnd()));

        if (XrayConfigHelper.isScenarioReportEvidenceEnabled() && xrayTestAnnotation.scenarioReport()) {
            Writer renderedTemplate = reportCreator.getRenderedTemplate(collection, scenarioLog);
            String filename = "scenario_" + scenarioLog.getScenarioName() + ".html";
            entity.getEvidence().add(XrayEvidenceItemEntity.fromString(renderedTemplate.toString(), filename));
        }

        if (XrayConfigHelper.isScenarioImageEvidenceEnabled() && xrayTestAnnotation.screenshots()) {
            for (String filepath : scenarioLog.getScreenshotPaths()) {
                entity.getEvidence().add(XrayEvidenceItemEntity.fromFile(filepath));
            }
        }

        for (LogMessage logMessage : scenarioLog.getLogMessages()) {
            if (logMessage instanceof StepInformationLogMessage stepLog) {
                XrayManualTestStepResultEntity xrayTestStepEntity = buildManualTestStepResultEntity(stepLog);
                entity.getSteps().add(xrayTestStepEntity);
            }
        }

        return entity;
    }

    private static XrayTestEntity buildMultipleIterationsTestEntity(XrayTest xrayTestAnnotation, List<TestScenarioLogCollection> scenarioCollection) {
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.PENDING);
        entity.setTestKey(xrayTestAnnotation.key());
        boolean allScenariosPassed = true;
        Date start = null;
        Date end = null;
        for (TestScenarioLogCollection scenarioLog : scenarioCollection) {
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
            entity.setStart(isoDateString(start));
        }
        if (end != null) {
            entity.setFinish(isoDateString(end));
        }
        if (allScenariosPassed) {
            entity.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        } else {
            entity.setStatus(TestScenarioLogCollection.Status.FAILURE);
        }
        return entity;
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
            parameterEntity.setName(truncateParameterName(testParameter.getName()));
            parameterEntity.setValue(truncateParameterValue(testParameter.getValue().toString()));
            entity.getParameters().add(parameterEntity);
        }
        for (LogMessage logMessage : scenarioLog.getLogMessages()) {
            if (logMessage instanceof StepInformationLogMessage stepLog) {
                entity.getSteps().add(buildManualTestStepResultEntity(stepLog));
            }
        }
        return entity;
    }

    private static XrayManualTestStepResultEntity buildManualTestStepResultEntity(StepInformationLogMessage stepLog) {
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
