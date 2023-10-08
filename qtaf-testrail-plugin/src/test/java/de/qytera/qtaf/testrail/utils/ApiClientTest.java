package de.qytera.qtaf.testrail.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testrail API client tests
 */
public class ApiClientTest {
    @Test(description = "Test the constructor of the API client")
    public void testApiClientConstructor() {
        APIClient apiClient = new APIClient("https://inet.com");
        Assert.assertEquals(apiClient.getUrl(), "https://inet.com/index.php?/api/v2/");
    }

    @Test(
            description = "Test the constructor of the API client with bad parameters",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "TestRail base URL is null, please set the value in your configuration file"
    )
    public void testApiClientConstructorBadUrl() {
        new APIClient(null);
    }

    @Test(description = "Test get authorization")
    public void testGetAuthorization() {
        APIClient apiClient = new APIClient("https://inet.com");
        apiClient.setUser("John");
        apiClient.setPassword("Doe");
        Assert.assertEquals(apiClient.getAuthorizationHeader(), "Basic Sm9objpEb2U=");
    }
}
