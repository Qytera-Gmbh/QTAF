package de.qytera.qtaf.apitesting.restassured.mixed;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

@TestFeature(name = "Status Code Assertion Tests", description = "Check the status code assertion methods")
public class EdgeCasesTest extends QtafTestNGContext implements ApiTest {
    private static final String url = "https://jsonplaceholder.typicode.com";


    @Test(testName = "issue #254 failed test causes a NoSuchMethodError for no obvious reason")
    public void noSuchMethodErrorTest() {
        String body = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
        apiTest(
                this,
                List.of(
                        baseUri("https://reqres.in"),
                        basePath("/api/users"),
                        body(body)
                ),
                postRequest(),
                List.of(statusCodeIs(201)) // Generates a NoSuchMethodError according to Issue#254 if the test case fails
        );
    }
}
