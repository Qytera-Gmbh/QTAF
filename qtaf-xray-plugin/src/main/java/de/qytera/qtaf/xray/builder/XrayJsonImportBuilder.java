package de.qytera.qtaf.xray.builder;

import com.google.inject.Singleton;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.test.MultipleIterationsXrayTestEntityBuilder;
import de.qytera.qtaf.xray.builder.test.SingleIterationXrayTestEntityBuilder;
import de.qytera.qtaf.xray.builder.test.XrayTestEntityBuilder;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.jira.issues.AdditionalField;
import de.qytera.qtaf.xray.dto.request.xray.ImportExecutionResultsRequestDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueResponseDto;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestExecutionInfoEntity;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Transforms log collection into Xray Execution Import DTO.
 */
@Singleton
public class XrayJsonImportBuilder implements RequestBodyBuilder<ImportExecutionResultsRequestDto> {

    /**
     * An exception thrown when a test suite did not execute any test marked with {@link XrayTest}.
     */
    public static class NoXrayTestException extends RuntimeException {
        /**
         * Constructs a new exception with a predefined error message.
         */
        public NoXrayTestException() {
            super("Cannot build import execution request because no test linked to Xray was executed");
        }
    }

    /**
     * The test suite collection used for retrieving scenarios and HTML report data.
     */
    private final TestSuiteLogCollection collection;
    /**
     * An issue repository for retrieving additional Jira issue information.
     */
    private final JiraIssueRepository jiraIssueRepository;
    /**
     * A builder for building {@link XrayTestEntity} instances out of scenarios with a single test run.
     */
    private final XrayTestEntityBuilder<TestScenarioLogCollection> singleIterationBuilder;
    /**
     * A builder for building {@link XrayTestEntity} instances out of scenarios with multiple test runs (DDT).
     */
    private final XrayTestEntityBuilder<List<TestScenarioLogCollection>> multipleIterationsBuilder;

    /**
     * Create a new import builder for uploading the given test suite to Xray.
     *
     * @param collection          the test suite collection
     * @param jiraIssueRepository a {@link JiraIssueRepository} for retrieving additional issue data
     * @throws URISyntaxException                 if any network request's URL is invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    public XrayJsonImportBuilder(TestSuiteLogCollection collection, JiraIssueRepository jiraIssueRepository) throws URISyntaxException, MissingConfigurationValueException {
        this.collection = collection;
        this.jiraIssueRepository = jiraIssueRepository;
        Map<String, String> issueSummaries = getIssueSummaries(collection);
        this.singleIterationBuilder = new SingleIterationXrayTestEntityBuilder(this.collection, issueSummaries);
        this.multipleIterationsBuilder = new MultipleIterationsXrayTestEntityBuilder(this.collection, issueSummaries);
    }

    /**
     * Creates an execution import DTO based on the test suite logs.
     *
     * @return the execution import DTO
     * @throws NoXrayTestException if no test annotated with {@link XrayTest} was executed
     */
    public ImportExecutionResultsRequestDto build() {
        ImportExecutionResultsRequestDto xrayImportRequestDto = new ImportExecutionResultsRequestDto();
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
        entity.setTestPlanKey(XrayConfigHelper.getResultsUploadTestPlanKey());
        entity.addTestEnvironment(collection.getOsName());
        entity.addTestEnvironment(collection.getDriverName());
        return entity;
    }

    private List<XrayTestEntity> buildTestEntities() {
        List<XrayTestEntity> entities = new ArrayList<>();
        for (TestFeatureLogCollection testFeatureLogCollection : collection.getTestFeatureLogCollections()) {
            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();
            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {
                List<TestScenarioLogCollection> scenarioLogs = entry.getValue();
                // Ignore tests that don't have an Xray annotation.
                XrayTest xrayTest = getXrayAnnotation(scenarioLogs);
                if (xrayTest == null) {
                    continue;
                }
                XrayTestEntity entity;
                if (scenarioLogs.size() == 1) {
                    entity = singleIterationBuilder.buildTestEntity(xrayTest, scenarioLogs.get(0));
                } else {
                    entity = multipleIterationsBuilder.buildTestEntity(xrayTest, scenarioLogs);
                }
                entities.add(entity);
            }
        }
        return entities;
    }

    private static XrayTest getXrayAnnotation(List<TestScenarioLogCollection> scenarioLogs) {
        XrayTest xrayTest = scenarioLogs.isEmpty() ? null : scenarioLogs.get(0).getAnnotation(XrayTest.class);
        if (xrayTest == null) {
            return null;
        }
        String projectKey = XrayConfigHelper.getProjectKey();
        if (!xrayTest.key().contains(projectKey)) {
            QtafFactory.getLogger().warn(
                    String.format(
                            """
                                    Xray annotation of scenario '%s' contains a project key that was not configured in %s: '%s'. \
                                    This scenario's results will not be uploaded.""",
                            scenarioLogs.get(0).getScenarioName(),
                            QtafFactory.getConfiguration().getLocation(),
                            xrayTest.key()
                    )
            );
            return null;
        }
        String issuePattern = String.format("^%s-%s$", projectKey, "[1-9]\\d*");
        if (!xrayTest.key().matches(issuePattern)) {
            QtafFactory.getLogger().warn(
                    String.format(
                            """
                                    Found project key '%s' in Xray annotation of scenario '%s', but failed to extract issue number: '%s'. \
                                    This scenario's results will not be uploaded.""",
                            projectKey,
                            scenarioLogs.get(0).getScenarioName(),
                            xrayTest.key()
                    )
            );
            return null;
        }
        return xrayTest;
    }

    private Map<String, String> getIssueSummaries(TestSuiteLogCollection collection) throws URISyntaxException, MissingConfigurationValueException {
        // Jira issue summaries are only required when updating test issue steps.
        if (!XrayConfigHelper.shouldResultsUploadTestsInfoStepsUpdate()) {
            return Collections.emptyMap();
        }
        if (XrayConfigHelper.shouldResultsUploadTestsInfoKeepJiraSummary()) {
            return getIssueSummariesFromJira(collection);
        }
        Map<String, String> issueSummaries = new HashMap<>();
        for (TestFeatureLogCollection featureLog : collection.getTestFeatureLogCollections()) {
            for (TestScenarioLogCollection scenarioLog : featureLog.getScenarioLogCollection()) {
                XrayTest xrayTest = scenarioLog.getAnnotation(XrayTest.class);
                if (xrayTest != null) {
                    issueSummaries.put(xrayTest.key(), scenarioLog.getScenarioName());
                }
            }
        }
        return issueSummaries;
    }

    private Map<String, String> getIssueSummariesFromJira(TestSuiteLogCollection collection) throws URISyntaxException, MissingConfigurationValueException {
        Map<String, String> issueSummaries = new HashMap<>();
        Set<String> testKeys = collection.getTestFeatureLogCollections().stream()
                .map(TestFeatureLogCollection::getScenarioLogCollection)
                .flatMap(Collection::stream)
                .map(scenario -> scenario.getAnnotation(XrayTest.class))
                .filter(Objects::nonNull)
                .map(XrayTest::key)
                .collect(Collectors.toSet());
        List<JiraIssueResponseDto> issues = jiraIssueRepository.search(testKeys, AdditionalField.SUMMARY);
        for (JiraIssueResponseDto issue : issues) {
            issueSummaries.put(issue.getKey(), issue.getFields().get(AdditionalField.SUMMARY.text).getAsString());
            testKeys.remove(issue.getKey());
        }
        if (!testKeys.isEmpty()) {
            String message = String.format("Failed to retrieve issue summaries of the following issues from Jira: %s", testKeys);
            QtafFactory.getLogger().error(message);
            ErrorLogCollection.getInstance().addErrorLog(new IllegalStateException(message));
        }
        return issueSummaries;
    }

}
