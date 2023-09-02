package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.UserCloudDto;
import de.qytera.qtaf.xray.dto.jira.UserDto;
import de.qytera.qtaf.xray.dto.jira.UserServerDto;
import de.qytera.qtaf.xray.dto.request.jira.issues.JiraIssueSearchRequestDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueResponseDto;
import de.qytera.qtaf.xray.dto.response.jira.issues.JiraIssueSearchResponseDto;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import de.qytera.qtaf.xray.util.Mocking;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test(description = "issue search should construct correct requests")
    public void testSearchJQL() {
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, XrayConfigHelper.XRAY_SERVICE_CLOUD);

        JiraIssueResponseDto[] issues = new JiraIssueResponseDto[]{new JiraIssueResponseDto()};
        issues[0].setKey("QTAF-123");
        JiraIssueSearchResponseDto dto = new JiraIssueSearchResponseDto();
        dto.setTotal(1);
        dto.setIssues(issues);
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.post(any(), any()).close()).thenAnswer((Answer<Response>) invocation -> {
                RequestBuilder request = invocation.getArgument(0);
                Assert.assertEquals(request.getPath(), URI.create("https://example.org/rest/api/3/search"));
                Entity<JiraIssueSearchRequestDto> body = invocation.getArgument(1);
                Assert.assertEquals(body.getEntity().getJql(), "issue in (QTAF-123)");
                return Mocking.simulateInbound(
                        Response.status(Response.Status.OK).entity(QtafFactory.getGson().toJson(dto)).build()
                );
            });
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            JiraIssueRepository.getInstance().search(List.of("QTAF-123"));
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    @Test(description = "issue search should paginate responses")
    public void testSearch() {
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, XrayConfigHelper.XRAY_SERVICE_CLOUD);

        JiraIssueResponseDto issue1 = new JiraIssueResponseDto();
        issue1.setKey("QTAF-123");
        JiraIssueResponseDto issue2 = new JiraIssueResponseDto();
        issue1.setKey("QTAF-456");
        JiraIssueResponseDto issue3 = new JiraIssueResponseDto();
        issue1.setKey("QTAF-789");

        JiraIssueResponseDto[] issues1 = new JiraIssueResponseDto[]{issue1, issue2};
        JiraIssueResponseDto[] issues2 = new JiraIssueResponseDto[]{issue3};
        JiraIssueSearchResponseDto dto1 = new JiraIssueSearchResponseDto();
        dto1.setTotal(3);
        dto1.setIssues(issues1);
        JiraIssueSearchResponseDto dto2 = new JiraIssueSearchResponseDto();
        dto2.setTotal(3);
        dto2.setIssues(issues2);
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.post(any(), any()).close())
                    .thenAnswer(new Answer<Response>() {
                                    private int invocationCount = 0;

                                    @Override
                                    public Response answer(InvocationOnMock invocation) {
                                        if (invocationCount++ == 0) {
                                            return Mocking.simulateInbound(
                                                    Response.status(Response.Status.OK)
                                                            .entity(QtafFactory.getGson().toJson(dto1))
                                                            .build()
                                            );
                                        }
                                        return Mocking.simulateInbound(
                                                Response.status(Response.Status.OK)
                                                        .entity(QtafFactory.getGson().toJson(dto2))
                                                        .build()
                                        );
                                    }
                                }

                    );
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            List<JiraIssueResponseDto> issueData = JiraIssueRepository.getInstance().search(List.of("QTAF-123", "QTAF-456", "QTAF-789"));
            Assert.assertEquals(issueData, Arrays.asList(issue1, issue2, issue3));
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    @Test(description = "issue search should print errors for bad responses")
    public void testSearchBadResponse() {
        CONFIG.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, XrayConfigHelper.XRAY_SERVICE_CLOUD);

        JiraIssueResponseDto issue1 = new JiraIssueResponseDto();
        issue1.setKey("QTAF-123");
        JiraIssueResponseDto issue2 = new JiraIssueResponseDto();
        issue1.setKey("QTAF-456");

        JiraIssueResponseDto[] issues1 = new JiraIssueResponseDto[]{issue1, issue2};
        JiraIssueSearchResponseDto dto1 = new JiraIssueSearchResponseDto();
        dto1.setTotal(3);
        dto1.setIssues(issues1);
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.post(any(), any()).close())
                    .thenAnswer(new Answer<Response>() {
                                    private int invocationCount = 0;

                                    @Override
                                    public Response answer(InvocationOnMock invocation) {
                                        if (invocationCount++ == 0) {
                                            return Mocking.simulateInbound(
                                                    Response.status(Response.Status.OK)
                                                            .entity(QtafFactory.getGson().toJson(dto1))
                                                            .build()
                                            );
                                        }
                                        return Mocking.simulateInbound(
                                                Response.status(Response.Status.BAD_REQUEST)
                                                        .entity("Project QTAF does not exist")
                                                        .build()
                                        );
                                    }
                                }

                    );
            webService.when(() -> WebService.buildRequest(any())).thenCallRealMethod();
            Logger logger = Mockito.mock(Logger.class);
            try (MockedStatic<QtafFactory> factory = Mockito.mockStatic(QtafFactory.class)) {
                factory.when(QtafFactory::getLogger).thenReturn(logger);
                factory.when(QtafFactory::getConfiguration).thenCallRealMethod();
                factory.when(QtafFactory::getGson).thenCallRealMethod();
                List<JiraIssueResponseDto> issueData = JiraIssueRepository.getInstance().search(
                        List.of("QTAF-123", "QTAF-456", "QTAF-789")
                );
                Assert.assertEquals(issueData, Arrays.asList(issue1, issue2));
                Mockito.verify(logger, Mockito.times(1))
                        .error("""
                                [QTAF Xray Plugin] \
                                Failed to search for Jira issues using search body '{"jql":"issue in (QTAF-123,QTAF-456,QTAF-789)","startAt":2,"fields":[]}': \
                                400 Bad Request: Project QTAF does not exist""");
            }
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

}
