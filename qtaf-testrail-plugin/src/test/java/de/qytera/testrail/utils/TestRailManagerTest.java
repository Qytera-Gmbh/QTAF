package de.qytera.testrail.utils;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestRailManagerTest {
    @Test(description = "Test add result for test case")
    public void testAddResultForTestCase() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        TestRailManager.addResultForTestCase(clientMock, "c1", "", 200, "");
        Mockito.verify(clientMock, Mockito.times(0)).sendGet(Mockito.any());
        Mockito.verify(clientMock, Mockito.times(1)).sendPost(Mockito.any(), Mockito.any());
    }

    @Test(description = "Test add attachment for test case")
    public void testAddAttachmentForTestCase() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        TestRailManager.addAttachmentForTestCase(clientMock, "c1", "");
        Mockito.verify(clientMock, Mockito.times(0)).sendGet(Mockito.any());
        Mockito.verify(clientMock, Mockito.times(1)).sendPost(Mockito.any(), Mockito.any());
    }

    @Test(description = "Test delete attachment for test case")
    public void testDeleteAttachmentForTestCase() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        TestRailManager.deleteAttachmentForTestCase(clientMock, "");
        Mockito.verify(clientMock, Mockito.times(0)).sendGet(Mockito.any());
        Mockito.verify(clientMock, Mockito.times(1)).sendPost(Mockito.any(), Mockito.any());
    }

    @Test(description = "Test delete attachment for test case")
    public void testGetAttachmentForTestCase() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        TestRailManager.getAttachmentForTestCase(clientMock, "");
        Mockito.verify(clientMock, Mockito.times(1)).sendGet(Mockito.any());
        Mockito.verify(clientMock, Mockito.times(0)).sendPost(Mockito.any(), Mockito.any());
    }
}
