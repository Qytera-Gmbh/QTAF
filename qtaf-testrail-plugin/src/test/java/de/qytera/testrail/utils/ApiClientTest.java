package de.qytera.testrail.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Testrail API client tests
 */
public class ApiClientTest {
    //@Test(description = "Test the constructor of the API client")
    public void testApiClientConstructor() {
        APIClient apiClient = new APIClient("https://inet.com");
        Assert.assertEquals(apiClient.getM_url(), "https://inet.com/index.php?/api/v2/");
    }

    //@Test(description = "Test the constructor of the API client")
    public void testSendGetRequest() throws APIException, IOException {
        APIClient apiClient = new APIClient("http://localhost:5000");
        apiClient.sendGet("");
    }

    //@Test(description = "Test the constructor of the API client")
    public void testSendPostRequest() throws APIException, IOException {
        APIClient apiClient = new APIClient("http://localhost:5000");
        apiClient.sendPost("", new Object());
    }
}
