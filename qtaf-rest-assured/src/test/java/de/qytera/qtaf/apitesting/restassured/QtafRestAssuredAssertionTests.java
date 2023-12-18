package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.Api;
import de.qytera.qtaf.apitesting.ApiTestExecution;
import de.qytera.qtaf.apitesting.action.ApiActions;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.apitesting.request.RequestSpecifications;
import de.qytera.qtaf.apitesting.response.ResponseAssertions;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.util.List;

public class QtafRestAssuredAssertionTests  extends QtafTestNGContext implements RequestSpecifications, ApiActions, ResponseAssertions {

    String url = "https://jsonplaceholder.typicode.com";


    // ========== BODY ==========


    // ========== STATUS CODE ==========

    @Test
    public void statusCodeIsAssertionTestFail() {
        ApiTestExecution apiTestExecution = Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0)
                )
        );

        List<LogMessage> logMessages = this.getLogCollection().getLogMessages();
        LogMessage latestLogMessage = logMessages.get(0);
        ApiLogMessage currentLogMessage = (latestLogMessage instanceof ApiLogMessage ? (ApiLogMessage)latestLogMessage : null);
        if (latestLogMessage == null){
            throw new RuntimeException("currentLogMessage was unexpacted not an ApiLogMessage");
        }
        assertEquals(currentLogMessage.getResponse().getStatusCode(), 404);


        System.out.println(apiTestExecution.getRes().statusCode());
    }

    @Test
    public void statusCodeIsAssertionTestPass() {
        ApiTestExecution apiTestExecution = Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(404)
                )
        );
    }

    @Test
    public void statusCodeIs1xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIsNot1xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIs2xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIsNot2xxAssertionTest() {
        // TODO
    }

    @Test
    public void statusCodeIsNotAssertionTest() {
        // TODO
    }

    @Test
    public void QtafApiTeststatusCodeFailed() {
        Api.test(
                this,
                List.of(baseUri(url)),
                getRequest("/user/1"),
                List.of(
                        statusCodeIs(0),
                        responseTimeShouldBeLessThanXMilliseconds(5000)
                        // statusCodeIs(0)
                )
        );
    }

    // ========== TIME ==========
}
