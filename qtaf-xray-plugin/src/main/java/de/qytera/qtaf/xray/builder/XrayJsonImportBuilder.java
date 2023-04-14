package de.qytera.qtaf.xray.builder;

import com.google.inject.Singleton;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.test.XrayTestEntityBuilder;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.request.issues.AdditionalField;
import de.qytera.qtaf.xray.dto.response.issues.JiraIssueResponseDto;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestExecutionInfoEntity;
import de.qytera.qtaf.xray.service.XrayCloudService;
import de.qytera.qtaf.xray.service.XrayServerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Transforms log collection into Xray Execution Import DTO
 */
@Singleton
@RequiredArgsConstructor
public class XrayJsonImportBuilder {

    /**
     * An exception thrown when a test suite did not execute any test marked with {@link XrayTest}.
     */
    public static class NoXrayTestException extends Exception {
        /**
         * Constructs a new exception with a predefined error message.
         */
        public NoXrayTestException() {
            super("Cannot build import execution request because no test linked to Xray was executed");
        }
    }

    @NonNull
    private TestSuiteLogCollection collection;

    /**
     * Creates an execution import DTO based on the test suite logs.
     *
     * @return the execution import DTO
     */
    public XrayImportRequestDto buildRequest() throws NoXrayTestException {
        XrayImportRequestDto xrayImportRequestDto = new XrayImportRequestDto();
        xrayImportRequestDto.setInfo(buildTestExecutionInfoEntity());
        xrayImportRequestDto.setTests(buildTestEntities());
        if (xrayImportRequestDto.getTests().isEmpty()) {
            throw new NoXrayTestException();
        }
        return xrayImportRequestDto;
    }

    private XrayTestExecutionInfoEntity buildTestExecutionInfoEntity() {
        XrayTestExecutionInfoEntity entity = new XrayTestExecutionInfoEntity();
        if (collection.getStart() != null) {
            String startDate = XrayJsonHelper.isoDateString(collection.getStart());
            entity.setStartDate(startDate);
        }
        if (collection.getEnd() != null) {
            String finishDate = XrayJsonHelper.isoDateString(collection.getEnd());
            entity.setFinishDate(finishDate);
        }
        entity.addTestEnvironment(collection.getOsName());
        entity.addTestEnvironment(collection.getDriverName());
        return entity;
    }

    private List<XrayTestEntity> buildTestEntities() {
        List<XrayTestEntity> entities = new ArrayList<>();
        for (TestFeatureLogCollection testFeatureLogCollection : collection.getTestFeatureLogCollections()) {
            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();
            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    XrayTestEntity entity = XrayTestEntityBuilder.buildFrom(
                            collection,
                            getIssueSummaries(),
                            entry.getValue()
                    );
                    if (entity != null) {
                        entities.add(entity);
                    }
                }
            }
        }
        return entities;
    }

    private Map<String, String> getIssueSummaries() {
        if (Boolean.TRUE.equals(XrayConfigHelper.getResultsUploadTestsInfoUseJiraSummary())) {
            return getIssueSummariesFromJira();
        }
        Map<String, String> issueSummaries = new HashMap<>();
        for (TestFeatureLogCollection featureLog : collection.getTestFeatureLogCollections()) {
            for (TestScenarioLogCollection scenarioLog : featureLog.getScenarioLogCollection()) {
                if (scenarioLog.getAnnotation(XrayTest.class) instanceof XrayTest xrayTest) {
                    issueSummaries.put(xrayTest.key(), scenarioLog.getScenarioName());
                }
            }
        }
        return issueSummaries;
    }

    private Map<String, String> getIssueSummariesFromJira() {
        Map<String, String> issueSummaries = new HashMap<>();
        Set<String> testKeys = collection.getTestFeatureLogCollections().stream()
                .map(TestFeatureLogCollection::getScenarioLogCollection)
                .flatMap(Collection::stream)
                .map(scenario -> scenario.getAnnotation(XrayTest.class))
                .filter(Objects::nonNull)
                .map(XrayTest.class::cast)
                .map(XrayTest::key)
                .collect(Collectors.toSet());
        List<JiraIssueResponseDto> issues;
        if (XrayConfigHelper.isXrayCloudService()) {
            issues = XrayCloudService.getInstance().searchJiraIssues(testKeys, AdditionalField.SUMMARY);
        } else {
            issues = XrayServerService.getInstance().searchJiraIssues(testKeys, AdditionalField.SUMMARY);
        }
        for (JiraIssueResponseDto issue : issues) {
            issueSummaries.put(issue.getKey(), issue.getFields().get(AdditionalField.SUMMARY.text).getAsString());
            testKeys.remove(issue.getKey());
        }
        if (!testKeys.isEmpty()) {
            String message = String.format("Failed to retrieve the following issue summaries from Jira: %s", testKeys);
            QtafFactory.getLogger().error(message);
            ErrorLogCollection.getInstance().addErrorLog(new IllegalStateException(message));
        }
        return issueSummaries;
    }

}
