package de.qytera.qtaf.testrail.utils;

import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.testrail.entity.Attachment;
import de.qytera.qtaf.testrail.entity.Attachments;
import de.qytera.qtaf.testrail.entity.Link;
import jakarta.ws.rs.core.Response;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestRailManagerTest {
    @Test(description = "Test add result for test case")
    public void testAddResultForTestCase() throws APIException {
        APIClient client = new APIClient("https://example.org");
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.post(Mockito.any(), Mockito.any()).close()).thenReturn(Response.ok().build());
            // Assert that it does not throw.
            TestRailManager.addResultForTestCase(client, "c1", "", 200, "");
        }
    }

    @Test(description = "Test add result for test case with bad response")
    public void testAddResultForTestCaseBadResponse() {
        APIClient client = new APIClient("https://example.org");
        Response response = Mocking.simulateInbound(
                Response.serverError().entity(GsonFactory.getInstance().toJson("very bad")).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.post(Mockito.any(), Mockito.any()).close()).thenReturn(response);
            TestRailManager.addResultForTestCase(client, "c1", "", 200, "");
        } catch (APIException e) {
            Assert.assertEquals(e.getMessage(), "TestRail API returned HTTP 500 (\"very bad\")");
        }
    }

    @Test(description = "Test add attachment for test case")
    public void testAddAttachmentForTestCase() {
        APIClient client = new APIClient("https://example.org");
        Response response = Mocking.simulateInbound(
                Response.ok().entity(GsonFactory.getInstance().toJson("abcdef-12342-535363")).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.post(Mockito.any(), Mockito.any()).close()).thenReturn(response);
            // Assert that it does not throw.
            TestRailManager.addAttachmentForTestCase(client, "c1", "pom.xml");
        }
    }

    @Test(description = "Test add attachment for test case with bad response")
    public void testAddAttachmentForTestCaseBadResponse() {
        APIClient client = new APIClient("https://example.org");
        Response response = Mocking.simulateInbound(
                Response.serverError().entity(GsonFactory.getInstance().toJson("things are going down")).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.post(Mockito.any(), Mockito.any()).close()).thenReturn(response);
            TestRailManager.addAttachmentForTestCase(client, "c1", "pom.xml");
        }
    }

    @Test(description = "Test delete attachment for test case")
    public void testDeleteAttachmentForTestCase() throws APIException {
        APIClient client = new APIClient("https://example.org");
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.post(Mockito.any()).close()).thenReturn(Response.ok().build());
            // Assert that it does not throw.
            TestRailManager.deleteAttachmentForTestCase(client, "");
        }
    }

    @Test(description = "Test delete attachment for test case with bad response")
    public void testDeleteAttachmentForTestCaseBadResponse() {
        APIClient client = new APIClient("https://example.org");
        Response response = Mocking.simulateInbound(
                Response.serverError().entity(GsonFactory.getInstance().toJson("oh no")).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.post(Mockito.any()).close()).thenReturn(response);
            TestRailManager.deleteAttachmentForTestCase(client, "");
        } catch (APIException e) {
            Assert.assertEquals(e.getMessage(), "TestRail API returned HTTP 500 (\"oh no\")");
        }
    }

    @Test(description = "Test get attachments for test case")
    public void testGetAttachmentForTestCase() throws APIException {
        APIClient client = new APIClient("https://example.org");
        // See https://support.testrail.com/hc/en-us/articles/7077196481428-Attachments#getattachmentsforcase
        Attachments attachments = new Attachments();
        attachments.setOffset(0);
        attachments.setLimit(250);
        attachments.setSize(4);
        attachments.setLink(new Link(null, null));
        attachments.setAttachments(List.of(
                new Attachment(
                        "dll",
                        "other",
                        "3",
                        614308,
                        "case",
                        "msdia80.dll",
                        904704,
                        2,
                        1631722975,
                        "63c82867-526d-43be-b1a5-9ddfcf581cf5",
                        1,
                        "msdia80.dll",
                        0,
                        false,
                        "2ec27be4-812f-4806-9a5d-d39130d1691a"
                )
        ));
        Response response = Mocking.simulateInbound(
                Response.ok().entity(GsonFactory.getInstance().toJson(attachments)).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.get(Mockito.any()).close()).thenReturn(response);
            Assert.assertEquals(TestRailManager.getAttachmentsForTestCase(client, ""), attachments);
        }
    }

    @Test(description = "Test get attachments for test case with bad response")
    public void testGetAttachmentForTestCaseBadResponse() {
        APIClient client = new APIClient("https://example.org");
        Response response = Mocking.simulateInbound(
                Response.serverError().entity(GsonFactory.getInstance().toJson("houston we have a problem")).build()
        );
        try (MockedStatic<WebService> webService = Mockito.mockStatic(WebService.class)) {
            webService.when(() -> WebService.buildRequest(Mockito.any())).thenCallRealMethod();
            webService.when(() -> WebService.get(Mockito.any()).close()).thenReturn(response);
            TestRailManager.getAttachmentsForTestCase(client, "");
        } catch (APIException e) {
            Assert.assertEquals(e.getMessage(), "TestRail API returned HTTP 500 (\"houston we have a problem\")");
        }
    }
}
