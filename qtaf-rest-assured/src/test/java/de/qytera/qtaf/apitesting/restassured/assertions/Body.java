package de.qytera.qtaf.apitesting.restassured.assertions;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

@TestFeature(name = "Body Assertion Tests", description = "Check the body assertion methods")
public class Body extends QtafTestNGContext implements ApiTest {
    @Test(testName = "test")
    public void test() {
        String url = "https://jsonplaceholder.typicode.com";
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/users/1"),
                List.of(
                        statusCodeIsNot(404)
                )
        );
    }
}
