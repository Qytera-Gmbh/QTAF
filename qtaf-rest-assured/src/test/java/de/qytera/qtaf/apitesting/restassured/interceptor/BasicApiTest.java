package de.qytera.qtaf.apitesting.restassured.interceptor;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.RestAssured;
import io.restassured.response.*;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class BasicApiTest extends QtafTestNGContext {
    @Test(testName = "API Test")
    public void testApi()  {
        getSingleTodo(getSingleTodoEndpointSpecification());
        createTodo(getTodoCollectionEndpointSpecification());
        updateTodo(getSingleTodoEndpointSpecification());
        deleteTodo(getSingleTodoEndpointSpecification());
    }
    public RequestSpecification getTodoCollectionEndpointSpecification() {
        return RestAssured
                .given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .basePath("/todos");
    }

    public RequestSpecification getSingleTodoEndpointSpecification() {
        return RestAssured
                .given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .basePath("/todos/1");
    }

    @Step(name = "Retrieve single todo", description = "Retrieve single Todo")
    public ValidatableResponse getSingleTodo(RequestSpecification specification) {
        return specification
                .get()
                .then()
                .statusCode(200);
    }

    @Step(name = "Create Todo", description = "Create Todo")
    public ValidatableResponse createTodo(RequestSpecification specification) {
        return specification
                .post()
                .then()
                .statusCode(500);
    }

    @Step(name = "Update Todo", description = "Update Todo")
    public ValidatableResponse updateTodo(RequestSpecification specification) {
        return specification
                .put()
                .then()
                .statusCode(200);
    }

    @Step(name = "Delete Todo", description = "Delete Todo")
    public ValidatableResponse deleteTodo(RequestSpecification specification) {
        return specification
                .delete()
                .then()
                .statusCode(200);
    }
}