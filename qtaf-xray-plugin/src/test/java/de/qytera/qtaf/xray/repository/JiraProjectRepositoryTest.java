package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.*;
import de.qytera.qtaf.xray.repository.jira.JiraProjectRepository;
import de.qytera.qtaf.xray.util.Mocking;
import jakarta.ws.rs.core.Response;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class JiraProjectRepositoryTest {

    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    private static final String PROJECT_KEY = "QTAF";

    @BeforeClass
    public void beforeClass() {
        CONFIG.setString(XrayConfigHelper.PROJECT_KEY, PROJECT_KEY);
        CONFIG.setString(XrayConfigHelper.URL_JIRA_SELECTOR, "https://example.org");
        CONFIG.setString(XrayConfigHelper.AUTHENTICATION_JIRA_USERNAME, "Jeff");
        CONFIG.setString(XrayConfigHelper.AUTHENTICATION_JIRA_API_TOKEN, "Charlie123");
        CONFIG.setString(XrayConfigHelper.AUTHENTICATION_XRAY_BEARER_TOKEN, "hello");
    }

    @Test(description = "get project details should return a proper cloud DTO on successful calls")
    public void testGetProjectOkCloud() {
        ProjectCloudDto expectedDto = new ProjectCloudDto();
        expectedDto.setKey(PROJECT_KEY);
        expectedDto.setDescription("a mocked project");
        expectedDto.setDeleted(false);
        expectedDto.setLead(new UserCloudDto());
        expectedDto.setIssueTypes(List.of(new IssueTypeCloudDto()));
        expectedDto.setVersions(List.of(new VersionCloudDto()));
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE, XrayConfigHelper.XRAY_SERVICE_CLOUD);
        Response response = Mocking.simulateInbound(
                Response.status(Response.Status.OK).entity(GsonFactory.getInstance().toJson(expectedDto)).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.get(any()).close()).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertEquals(JiraProjectRepository.getInstance().getProject(PROJECT_KEY), expectedDto);
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    @Test(description = "get project details should return a proper server DTO on successful calls")
    public void testGetProjectOkServer() {
        ProjectServerDto expectedDto = new ProjectServerDto();
        expectedDto.setKey(PROJECT_KEY);
        expectedDto.setDescription("a mocked project");
        expectedDto.setProjectKeys(Arrays.asList("ABC", "DEF"));
        expectedDto.setLead(new UserServerDto());
        expectedDto.setIssueTypes(List.of(new IssueTypeServerDto()));
        expectedDto.setVersions(List.of(new VersionServerDto()));
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE, XrayConfigHelper.XRAY_SERVICE_SERVER);
        Response response = Mocking.simulateInbound(
                Response.status(Response.Status.OK).entity(GsonFactory.getInstance().toJson(expectedDto)).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.get(any()).close()).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertEquals(JiraProjectRepository.getInstance().getProject(PROJECT_KEY), expectedDto);
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    @Test(description = "get project details should return null on failed calls")
    public void testGetProjectBadRequest() {
        Response response = Mocking.simulateInbound(
                Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": 42}").build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.get(any()).close()).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertNull(JiraProjectRepository.getInstance().getProject(PROJECT_KEY));
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }
}
