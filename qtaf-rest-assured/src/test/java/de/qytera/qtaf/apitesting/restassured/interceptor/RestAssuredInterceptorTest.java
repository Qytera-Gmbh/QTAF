package de.qytera.qtaf.apitesting.restassured.interceptor;

import de.qytera.qtaf.apitesting.annotations.RestCall;
import de.qytera.qtaf.core.QtafInitializer;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredInterceptorTest extends QtafTestNGContext {
    @Test
    public void testInterceptor() {
        IndexHelper.clearAllIndices();
        TestSuiteLogCollection.getInstance().clearCollection();
        // We need to recreate the log message entity because we have deleted it before
        this.logCollection = TestScenarioLogCollection.createTestScenarioLogCollection("f1", "as1", "i1", "s1");
        QtafInitializer.initialize();
        int sizeBefore = this.getLogCollection().getLogMessages().size();
        Assert.assertEquals(sizeBefore, 0);
        ApiTest apiTest = load(ApiTest.class);
        apiTest.apiCall(apiTest.getRequestSpecification("https://jsonplaceholder.typicode.com", "/todos/1"));
        int sizeAfter = this.getLogCollection().getLogMessages().size();
        Assert.assertEquals(sizeAfter, 1);
    }
}

class ApiTest extends QtafTestNGContext {
    public RequestSpecification getRequestSpecification(String uri, String path) {
        return RestAssured
                .given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .basePath("/todos/1");
    }

    @Step(name = "JSON Placeholder Test")
    public ValidatableResponse apiCall(RequestSpecification specification) {
        return specification
                .get()
                .then()
                .statusCode(200);
    }
}