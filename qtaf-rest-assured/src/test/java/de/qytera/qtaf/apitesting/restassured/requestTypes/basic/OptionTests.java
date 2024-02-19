package de.qytera.qtaf.apitesting.restassured.requestTypes.basic;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(name = "Body Assertion Tests", description = "Check the body assertion methods")
public class OptionTests extends QtafTestNGContext implements ApiTest {
    @Test(testName = "test")
    public void test() {
        String url = "https://jsonplaceholder.typicode.com";
        apiTest(
                this,
                List.of(baseUri(url), basePath("/users/1")),
                getRequest(),
                List.of(
                        statusCodeIsNot(404)
                )
        );
    }
}