package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

public class TestExecutionRequestTests extends QtafTestNGContext implements ApiTest {
    private static String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "Test Request Getters") @Ignore
    public void testRequestGetters() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/posts/{postId}"),
                        pathParam(Map.of("postId", 1))
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );

        Assert.assertEquals(result.getReq().getMethod(), "GET", "Expected method to be GET");
        Assert.assertEquals(result.getReq().getBaseUri(), "https://jsonplaceholder.typicode.com", "Expected baseUri to be %s");
        Assert.assertEquals(result.getReq().getBasePath(), "/posts/{postId}", "Expected basePath to be %s");
    }
}
