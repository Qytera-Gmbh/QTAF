package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.Api;
import de.qytera.qtaf.apitesting.action.ApiActions;
import de.qytera.qtaf.apitesting.request.RequestSpecifications;
import de.qytera.qtaf.apitesting.response.ResponseAssertions;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

public class QtafRestAssuredAssertionTests  extends QtafTestNGContext implements RequestSpecifications, ApiActions, ResponseAssertions {

    String url = "https://jsonplaceholder.typicode.com";
    @Test
    public void QtafApiTeststatusCodeFailed() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0)
                )
        );
    }
}
