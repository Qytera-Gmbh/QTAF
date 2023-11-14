package de.qytera.qtaf.apitesting.restassured;


import de.qytera.qtaf.apitesting.Api;
import de.qytera.qtaf.apitesting.action.ApiActions;
import de.qytera.qtaf.apitesting.request.RequestSpecifications;
import de.qytera.qtaf.apitesting.response.ResponseAssertions;

import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;



public class QtafRestAssuredTest extends QtafTestNGContext implements RequestSpecifications, ApiActions, ResponseAssertions {

    String url = "https://jsonplaceholder.typicode.com";

    @Test
    public void QtafApiTestStatusCodeIs() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(statusCodeIs(404))
        );
    }

    @Test
    public void QtafApiTestStatusCodeAndResponseTime() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(404),
                        responseTimeShouldBeLessThanXMilliseconds(2000)
                )
        );
    }

    @Test
    public void QtafApiTestStatusCodeXx() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeShouldIs4xx(),
                        statusCodeShouldIsNot2xx()
                )
        );
    }
}
