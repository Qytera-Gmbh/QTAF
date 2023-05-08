package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.UserCloudDto;
import de.qytera.qtaf.xray.dto.jira.UserDto;
import de.qytera.qtaf.xray.dto.jira.UserServerDto;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import de.qytera.qtaf.xray.util.Mocking;
import jakarta.ws.rs.core.Response;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class JiraIssueRepositoryTest {

    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    @BeforeClass
    public void beforeClass() {
        CONFIG.setString(XrayConfigHelper.PROJECT_KEY, "QTAF");
        CONFIG.setString(XrayConfigHelper.URL_JIRA_SELECTOR, "https://example.org");
        CONFIG.setString(XrayConfigHelper.AUTHENTICATION_JIRA_USERNAME, "Jeff");
        CONFIG.setString(XrayConfigHelper.AUTHENTICATION_JIRA_API_TOKEN, "Charlie123");
        CONFIG.setString(XrayConfigHelper.AUTHENTICATION_XRAY_BEARER_TOKEN, "hello");
    }

    @DataProvider
    public Object[][] assignProvider() {
        List<Object[]> data = new ArrayList<>();
        UserDto<?, ?> userDto = new UserCloudDto();
        userDto.setDisplayName("Jeff1");
        userDto.setEmailAddress("xyz1@abc.de");
        data.add(new Object[]{userDto});
        userDto = new UserServerDto();
        userDto.setDisplayName("Jeff2");
        userDto.setEmailAddress("xyz2@abc.de");
        data.add(new Object[]{userDto});
        return data.toArray(Object[][]::new);
    }

    @Test(description = "assign issue should return true on successful calls", dataProvider = "assignProvider")
    public void testAssign(UserDto<?, ?> user) {
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, XrayConfigHelper.XRAY_SERVICE_CLOUD);
        Response response = Mocking.simulateInbound(
                Response.status(Response.Status.NO_CONTENT).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.put(any(), any()).close()).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertTrue(JiraIssueRepository.getInstance().assign("QTAF-123", user));
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    @Test(description = "assign issue should return false on failed calls")
    public void testAssignBadRequest() {
        Response response = Mocking.simulateInbound(
                Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": 42}").build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.put(any(), any()).close()).thenReturn(response);
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Assert.assertFalse(JiraIssueRepository.getInstance().assign("QTAF-456", null));
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }
}
