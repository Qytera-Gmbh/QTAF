package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.response.jira.ProjectDto;
import de.qytera.qtaf.xray.repository.jira.JiraProjectRepository;
import de.qytera.qtaf.xray.util.Mocking;
import jakarta.ws.rs.core.Response;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;


public class JiraProjectRepositoryTest {

    private static final String JIRA_URL = "https://example.org";
    private static final String JIRA_USERNAME = "Jeff";
    private static final String JIRA_TOKEN = "Charlie123";
    private static final String PROJECT_KEY = "QTAF";

    @BeforeClass
    public void beforeClass() {
        QtafFactory.getConfiguration().setString(XrayConfigHelper.PROJECT_KEY, PROJECT_KEY);
        QtafFactory.getConfiguration().setString(XrayConfigHelper.URL_JIRA_SELECTOR, JIRA_URL);
        QtafFactory.getConfiguration().setString(XrayConfigHelper.AUTHENTICATION_JIRA_USERNAME, JIRA_USERNAME);
        QtafFactory.getConfiguration().setString(XrayConfigHelper.AUTHENTICATION_JIRA_API_TOKEN, JIRA_TOKEN);
    }

    @Test(description = "get project details should return a proper DTO on successful calls")
    public void testGetProjectOk() {
        ProjectDto projectDetails = new ProjectDto();
        projectDetails.setKey(PROJECT_KEY);
        projectDetails.setDescription("a mocked project");
        Response response = Mocking.simulateInbound(
                Response.status(Response.Status.OK).entity(GsonFactory.getInstance().toJson(projectDetails)).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.get(any())).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertEquals(JiraProjectRepository.getInstance().getProject(PROJECT_KEY), projectDetails);
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
            webService.when(() -> WebService.get(any())).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertNull(JiraProjectRepository.getInstance().getProject(PROJECT_KEY));
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }
}
