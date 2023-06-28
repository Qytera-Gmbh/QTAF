package de.qytera.testrail.utils;

import org.json.simple.JSONObject;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Testrail API client tests
 */
public class ApiClientTest {
    @Test(description = "Test the constructor of the API client")
    public void testApiClientConstructor() {
        APIClient apiClient = new APIClient("https://inet.com");
        Assert.assertEquals(apiClient.getM_url(), "https://inet.com/index.php?/api/v2/");
    }

    @Test(description = "Test GET request with data")
    public void testSendGetRequestWithData() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        Mockito.doCallRealMethod().when(clientMock).sendGet(Mockito.any(), Mockito.any());
        clientMock.sendGet("", "");
        Mockito.verify(clientMock, Mockito.times(1)).sendRequest(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test(description = "Test GET request without data")
    public void testSendGetRequestWithoutData() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        Mockito.doCallRealMethod().when(clientMock).sendGet(Mockito.any());
        clientMock.sendGet("");
        Mockito.verify(clientMock, Mockito.times(1)).sendRequest(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test(description = "Test POST request with data")
    public void testSendPostRequestWithData() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        Mockito.doCallRealMethod().when(clientMock).sendPost(Mockito.any(), Mockito.any());
        clientMock.sendPost("", "");
        Mockito.verify(clientMock, Mockito.times(1)).sendRequest(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test(description = "Test send GET request via sendRequest")
    public void testSendGetRequestViaSendRequest() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        Mockito.doCallRealMethod().when(clientMock).setM_url(Mockito.any());
        clientMock.setM_url("https://jsonplaceholder.typicode.com/todos/1");
        try (MockedStatic<APIClient> staticMock = Mockito.mockStatic(APIClient.class)) {
            Mockito.doCallRealMethod().when(clientMock).sendRequest(Mockito.any(), Mockito.any(), Mockito.any());
            clientMock.sendRequest("GET", "", "");

            staticMock.verify(
                    () -> APIClient.sendGetRequest(Mockito.any()),
                    Mockito.times(1)
            );
            staticMock.verify(
                    () -> APIClient.sendPostRequest(Mockito.any(), Mockito.any(), Mockito.any()),
                    Mockito.times(0)
            );
        }
    }

    @Test(description = "Test send POST request via sendRequest")
    public void testSendPostRequestViaSendRequest() throws APIException, IOException {
        APIClient clientMock = Mockito.mock(APIClient.class);
        Mockito.doCallRealMethod().when(clientMock).setM_url(Mockito.any());
        clientMock.setM_url("https://jsonplaceholder.typicode.com/todos");
        try (MockedStatic<APIClient> staticMock = Mockito.mockStatic(APIClient.class)) {
            Mockito.doCallRealMethod().when(clientMock).sendRequest(Mockito.any(), Mockito.any(), Mockito.any());
            clientMock.sendRequest("POST", "", "");

            staticMock.verify(
                    () -> APIClient.sendGetRequest(Mockito.any()),
                    Mockito.times(0)
            );
            staticMock.verify(
                    () -> APIClient.sendPostRequest(Mockito.any(), Mockito.any(), Mockito.any()),
                    Mockito.times(1)
            );
        }
    }

    @Test(description = "Test default endpoint handler")
    public void testHandleDefaultEndpoint() throws IOException {
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        String text = APIClient.handleDefaultEndpoint(stream, "");
        Assert.assertEquals(text, "");
    }

    @Test(description = "Test GET attachment endpoint handler", expectedExceptions = {FileNotFoundException.class})
    public void testHandleGetAttachmentEndpoint() throws IOException {
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        Object obj = APIClient.handleGetAttachmentEndpoint("", stream);
    }

    @Test(description = "Test 200 status handler")
    public void testHandleStatus200() throws APIException, MalformedURLException {
        InputStream errorStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        HttpURLConnection conn = new HttpURLConnection(new URL("https://jsonplaceholder.typicode.com/todos")) {
            @Override
            public InputStream getErrorStream() {
                return errorStream;
            }

            @Override
            public void disconnect() {
            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() throws IOException {
                return;
            }
        };

        InputStream inputStream = APIClient.handleStatus200(conn, 200);
    }

    @Test(description = "Test not 200 status handler", expectedExceptions = {APIException.class}, expectedExceptionsMessageRegExp = "TestRail API returned HTTP 404(.*)")
    public void testHandleStatusNot200() throws APIException {
        APIClient.handleStatusNot200(404, new JSONObject());
    }

    @Test(description = "Test add auth header")
    public void testAddAuthorizationHeader() {
        APIClient clientMock = Mockito.mock(APIClient.class);
        Mockito.doCallRealMethod().when(clientMock).addAuthorizationHeader(Mockito.any());
        HttpURLConnection connMock = Mockito.mock(HttpURLConnection.class);
        clientMock.addAuthorizationHeader(connMock);
        Mockito.verify(connMock, Mockito.times(1)).addRequestProperty(Mockito.any(), Mockito.any());
    }

    @Test(description = "Test send GET request")
    public void testSendGetRequest() {
        HttpURLConnection connMock = Mockito.mock(HttpURLConnection.class);
        APIClient.sendGetRequest(connMock);
        Mockito.verify(connMock, Mockito.times(1)).addRequestProperty(Mockito.any(), Mockito.any());
    }

    @Test(description = "Test send POST request with Attachment")
    public void testSendPostRequestWithAttachment() throws IOException {
        try (MockedStatic<APIClient> staticMock = Mockito.mockStatic(APIClient.class)) {
            staticMock.when(() -> APIClient.sendPostRequest(Mockito.any(), Mockito.any(), Mockito.any()))
                    .thenCallRealMethod();
            HttpURLConnection connMock = Mockito.mock(HttpURLConnection.class);
            APIClient.sendPostRequest("add_attachment", "", connMock);
            Mockito.verify(connMock, Mockito.times(1)).setRequestMethod(Mockito.any());

            staticMock.verify(
                    () -> APIClient.sendAttachment(Mockito.any(), Mockito.any()),
                    Mockito.times(1)
            );
            staticMock.verify(
                    () -> APIClient.sendJson(Mockito.any(), Mockito.any()),
                    Mockito.times(0)
            );
        }
    }

    @Test(description = "Test send POST request without Attachment")
    public void testSendPostRequestWithoutAttachment() throws IOException {
        try (MockedStatic<APIClient> staticMock = Mockito.mockStatic(APIClient.class)) {
            staticMock.when(() -> APIClient.sendPostRequest(Mockito.any(), Mockito.any(), Mockito.any()))
                    .thenCallRealMethod();
            HttpURLConnection connMock = Mockito.mock(HttpURLConnection.class);
            APIClient.sendPostRequest("", "", connMock);
            Mockito.verify(connMock, Mockito.times(1)).setRequestMethod(Mockito.any());

            staticMock.verify(
                    () -> APIClient.sendAttachment(Mockito.any(), Mockito.any()),
                    Mockito.times(0)
            );
            staticMock.verify(
                    () -> APIClient.sendJson(Mockito.any(), Mockito.any()),
                    Mockito.times(1)
            );
        }
    }

    @Test(description = "Test send attachment", expectedExceptions = {FileNotFoundException.class})
    public void testSendAttachment() throws IOException {
        HttpURLConnection connMock = Mockito.mock(HttpURLConnection.class);
        OutputStream outputStreamMock = Mockito.mock(OutputStream.class);
        Mockito.when(connMock.getOutputStream()).thenReturn(outputStreamMock);

        try (MockedStatic<APIClient> staticMock = Mockito.mockStatic(APIClient.class)) {
            staticMock.when(() -> APIClient.sendAttachment(Mockito.any(), Mockito.any()))
                    .thenCallRealMethod();
            APIClient.sendAttachment("", connMock);
        }
    }

    @Test(description = "Test send JSON")
    public void testSendJson() throws IOException {
        HttpURLConnection connMock = Mockito.mock(HttpURLConnection.class);
        OutputStream outputStreamMock = Mockito.mock(OutputStream.class);
        Mockito.when(connMock.getOutputStream()).thenReturn(outputStreamMock);

        try (MockedStatic<APIClient> staticMock = Mockito.mockStatic(APIClient.class)) {
            staticMock.when(() -> APIClient.sendJson(Mockito.any(), Mockito.any()))
                    .thenCallRealMethod();
            APIClient.sendJson("", connMock);
        }
    }

    @Test(description = "Test get authorization")
    public void testGetAuthorization() {
        String auth = APIClient.getAuthorization("John", "Doe");
        Assert.assertEquals(auth, "Sm9objpEb2U=");
    }
}
