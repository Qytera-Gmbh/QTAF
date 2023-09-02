package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.UserCloudDto;
import de.qytera.qtaf.xray.dto.jira.UserServerDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseCloudDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseServerDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class AssignTestExecutionIssueSubscriberTest {

    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    private static JiraIssueRepository JIRA_ISSUE_REPOSITORY;

    @BeforeClass
    public void setup() {
        AssignTestExecutionIssueSubscriber subscriber = new AssignTestExecutionIssueSubscriber();
        subscriber.initialize();
    }

    @BeforeMethod
    public void clear() {
        CONFIG.clear();
        QtafFactory.getTestSuiteLogCollection().clear();
        JIRA_ISSUE_REPOSITORY = Mockito.mock(JiraIssueRepository.class);
    }

    @DataProvider
    public Object[][] assignProviderCloud() {
        List<Object[]> data = new ArrayList<>();
        UserCloudDto user = new UserCloudDto();
        user.setAccountId("Jeff");
        data.add(new Object[]{"QTAF-123", user.getAccountId(), user});
        return data.toArray(Object[][]::new);
    }

    @Test(dataProvider = "assignProviderCloud", description = "verify that a correct assign issue call is made for Jira cloud")
    public void testAssignIssueCloud(String issueKey, String assignee, UserCloudDto expectedUser) {
        CONFIG.setString(XrayConfigHelper.RESULTS_UPLOAD_ASSIGNEE, assignee);
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE, XrayConfigHelper.XRAY_SERVICE_CLOUD);
        ImportExecutionResultsResponseDto response = new ImportExecutionResultsResponseCloudDto();
        response.setKey(issueKey);
        try (MockedStatic<JiraIssueRepository> jiraRepository = Mockito.mockStatic(JiraIssueRepository.class, Mockito.RETURNS_DEEP_STUBS)) {
            jiraRepository.when(JiraIssueRepository::getInstance).thenReturn(JIRA_ISSUE_REPOSITORY);
            XrayEvents.responseDtoAvailable.onNext(response);
            Mockito.verify(JIRA_ISSUE_REPOSITORY, Mockito.times(1)).assign(issueKey, expectedUser);
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during assign issue call", exception);
        }
    }

    @DataProvider
    public Object[][] assignProviderServer() {
        List<Object[]> data = new ArrayList<>();
        UserServerDto user = new UserServerDto();
        user.setName("Jeff");
        data.add(new Object[]{"QTAF-123", user.getName(), user});
        return data.toArray(Object[][]::new);
    }

    @Test(dataProvider = "assignProviderServer", description = "verify that a correct assign issue call is made for Jira server")
    public void testAssignIssueServer(String issueKey, String assignee, UserServerDto expectedUser) {
        CONFIG.setString(XrayConfigHelper.RESULTS_UPLOAD_ASSIGNEE, assignee);
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE, XrayConfigHelper.XRAY_SERVICE_SERVER);
        ImportExecutionResultsResponseDto response = new ImportExecutionResultsResponseServerDto();
        response.setKey(issueKey);
        try (MockedStatic<JiraIssueRepository> jiraRepository = Mockito.mockStatic(JiraIssueRepository.class, Mockito.RETURNS_DEEP_STUBS)) {
            jiraRepository.when(JiraIssueRepository::getInstance).thenReturn(JIRA_ISSUE_REPOSITORY);
            XrayEvents.responseDtoAvailable.onNext(response);
            Mockito.verify(JIRA_ISSUE_REPOSITORY, Mockito.times(1)).assign(issueKey, expectedUser);
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during assign issue call", exception);
        }
    }

    @Test(description = "verify that no assign issue call is made for non-existent assignees")
    public void testSkippedAssignment() {
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE, XrayConfigHelper.XRAY_SERVICE_CLOUD);
        ImportExecutionResultsResponseDto response = new ImportExecutionResultsResponseCloudDto();
        response.setKey("QTAF-123");
        try (MockedStatic<JiraIssueRepository> jiraRepository = Mockito.mockStatic(JiraIssueRepository.class, Mockito.RETURNS_DEEP_STUBS)) {
            jiraRepository.when(JiraIssueRepository::getInstance).thenReturn(JIRA_ISSUE_REPOSITORY);
            XrayEvents.responseDtoAvailable.onNext(response);
            Mockito.verify(JIRA_ISSUE_REPOSITORY, Mockito.times(0)).assign(anyString(), any());
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during assign issue call", exception);
        }
    }

}
