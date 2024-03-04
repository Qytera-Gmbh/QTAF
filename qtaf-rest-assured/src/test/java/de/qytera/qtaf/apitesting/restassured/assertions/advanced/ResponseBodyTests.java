package de.qytera.qtaf.apitesting.restassured.assertions.advanced;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

public class ResponseBodyTests extends QtafTestNGContext implements ApiTest {
    private static String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "Test Response Body")
    public void testResponseBody() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(baseUri(url), basePath("/posts/1")),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );

        String expectedUserId = "1";
        String expectedId = "1";
        String expectedTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
        String expectedBody = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";

        Assert.assertEquals(
                result.getRes().body().jsonPath().getString("userId"),
                expectedUserId,
                "Expected the userId attribute to be '%s'".formatted(expectedUserId)
        );
        Assert.assertEquals(
                result.getRes().body().jsonPath().getString("id"),
                expectedId,
                "Expected the id attribute to be '%s'".formatted(expectedId)
        );
        Assert.assertEquals(
                result.getRes().body().jsonPath().getString("title"),
                expectedTitle,
                "Expected the title attribute to be '%s'".formatted(expectedTitle)
        );
        Assert.assertEquals(
                result.getRes().body().jsonPath().getString("body"),
                expectedBody,
                "Expected the body attribute to be '%s'".formatted(expectedBody)
        );
    }
}
