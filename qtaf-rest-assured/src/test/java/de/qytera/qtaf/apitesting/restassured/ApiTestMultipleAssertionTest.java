package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;

import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

public class ApiTestMultipleAssertionTest extends QtafTestNGContext implements ApiTest {

    String url = "https://jsonplaceholder.typicode.com";

    @Test
    public void test1() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(404)
                )
        );
    }

    @Test
    public void test2() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIsNot(0),
                        statusCodeIsNot(5)
                )
        );
    }

    @Test
    public void test3() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0),
                        statusCodeIs(404)
                )
        );
    }
}
