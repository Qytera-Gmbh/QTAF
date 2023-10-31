package de.qytera.qtaf.apitesting;

import de.qytera.qtaf.apitesting.action.ApiAction;
import de.qytera.qtaf.apitesting.request.ApiTestRequestSpecification;
import de.qytera.qtaf.apitesting.response.ApiTestAssertion;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;


import java.util.List;

/**
 * This class contains a method for executing API tests.
 * The function takes a list of preconditions and assertions, which are functions and an action
 * that should be performed on the API
 */
public class Api {
    /**
     * Execute API Test
     * @param preconditions List of request specifications
     * @param action        Action that should be performed
     * @param assertions    List of assertions
     * @return  Test execution result
     */
    public static ApiTestExecution test(
            List<ApiTestRequestSpecification> preconditions,
            ApiAction action,
            List<ApiTestAssertion> assertions
    ) {
        RequestSpecification req = RestAssured.given();
        QueryableRequestSpecification q = SpecificationQuerier.query(req);

        for (ApiTestRequestSpecification cond : preconditions) {
            cond.apply(req);
        }

        req = req.when();

        Response res = action.perform(req);

        ValidatableResponse then = res.then();

        for (ApiTestAssertion a: assertions) {
            a.apply(then);
        }

        return new ApiTestExecution(q, then.extract());
    }
}
