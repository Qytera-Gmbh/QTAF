package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseCloudDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import de.qytera.qtaf.xray.events.XrayEvents;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

public class TransitionImportTestExecutionIssueSubscriberTest {

    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    private static final JiraIssueRepository JIRA_ISSUE_REPOSITORY = Mockito.mock(JiraIssueRepository.class);

    private static final String TRANSITION_PASSED = "Ok";
    private static final String TRANSITION_FAILED = "Back to square one";

    @BeforeClass
    public void setup() {
        TransitionImportTestExecutionIssueSubscriber subscriber = new TransitionImportTestExecutionIssueSubscriber();
        subscriber.initialize();
    }

    @BeforeMethod
    public void clear() {
        CONFIG.clear();
        CONFIG.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_EXECUTION_ISSUE_PASSED, TRANSITION_PASSED);
        CONFIG.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_EXECUTION_ISSUE_FAILED, TRANSITION_FAILED);
        QtafFactory.getTestSuiteLogCollection().clear();
    }

    private static List<TestFeatureLogCollection> createTestFeatures(int index, TestScenarioLogCollection.Status status) {
        TestFeatureLogCollection feature = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                String.valueOf(index),
                "this is a test feature"
        );
        TestScenarioLogCollection scenario = TestScenarioLogCollection.createTestScenarioLogCollection(
                String.valueOf(index),
                "someScenario()",
                String.valueOf(index),
                "this is a test scenario"
        );
        scenario.setStatus(status);
        feature.addScenarioLogCollection(scenario);
        return List.of(feature);
    }

    @DataProvider
    public Object[][] transitionProvider() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{createTestFeatures(1, TestScenarioLogCollection.Status.SUCCESS), "QTAF-123", TRANSITION_PASSED});
        data.add(new Object[]{createTestFeatures(2, TestScenarioLogCollection.Status.PENDING), "QTAF-456", TRANSITION_FAILED});
        data.add(new Object[]{createTestFeatures(3, TestScenarioLogCollection.Status.SKIPPED), "QTAF-789", TRANSITION_FAILED});
        data.add(new Object[]{createTestFeatures(4, TestScenarioLogCollection.Status.FAILURE), "QTAF-101", TRANSITION_FAILED});
        return data.toArray(Object[][]::new);
    }

    @Test(dataProvider = "transitionProvider", description = "verify that a correct transitioning issue call is made")
    public void testTestSuiteTransition(List<TestFeatureLogCollection> features, String issueKey, String transitionName) {
        QtafFactory.getTestSuiteLogCollection().getTestFeatureLogCollections().addAll(features);
        ImportExecutionResultsResponseDto response = new ImportExecutionResultsResponseCloudDto();
        response.setKey(issueKey);
        try (MockedStatic<JiraIssueRepository> jiraRepository = Mockito.mockStatic(JiraIssueRepository.class, Mockito.RETURNS_DEEP_STUBS)) {
            jiraRepository.when(JiraIssueRepository::getInstance).thenReturn(JIRA_ISSUE_REPOSITORY);
            XrayEvents.responseDtoAvailable.onNext(response);
            Mockito.verify(JIRA_ISSUE_REPOSITORY, Mockito.times(1)).transitionIssue(issueKey, transitionName);
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during transition issue call", exception);
        }
    }

    @Test(description = "verify that no transition issue call is made without custom test execution issue statuses")
    public void testSkippedTestSuiteTransition() {
        QtafFactory.getTestSuiteLogCollection().getTestFeatureLogCollections().addAll(createTestFeatures(0, TestScenarioLogCollection.Status.SUCCESS));
        CONFIG.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_EXECUTION_ISSUE_PASSED, null);
        CONFIG.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_EXECUTION_ISSUE_FAILED, null);
        ImportExecutionResultsResponseDto response = new ImportExecutionResultsResponseCloudDto();
        response.setKey("QTAF-12345");
        try (MockedStatic<JiraIssueRepository> jiraRepository = Mockito.mockStatic(JiraIssueRepository.class, Mockito.RETURNS_DEEP_STUBS)) {
            jiraRepository.when(JiraIssueRepository::getInstance).thenReturn(JIRA_ISSUE_REPOSITORY);
            XrayEvents.responseDtoAvailable.onNext(response);
            Mockito.verify(JIRA_ISSUE_REPOSITORY, Mockito.times(0)).transitionIssue(anyString(), anyString());
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during transition issue call", exception);
        }
    }

}
