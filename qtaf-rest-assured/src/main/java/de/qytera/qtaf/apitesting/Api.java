package de.qytera.qtaf.apitesting;

import de.qytera.qtaf.apitesting.action.ApiAction;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.apitesting.request.ApiTestRequestSpecification;
import de.qytera.qtaf.apitesting.response.ApiTestAssertion;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
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
            IQtafTestContext context,
            List<ApiTestRequestSpecification> preconditions,
            ApiAction action,
            List<ApiTestAssertion> assertions
    ) {
        TestScenarioLogCollection logCollect = context.getLogCollection();
        ApiLogMessage logMessage = new ApiLogMessage(LogLevel.INFO, "Api Call");
        logCollect.addLogMessage(logMessage);

        RequestSpecification req = RestAssured.given();
        QueryableRequestSpecification q = SpecificationQuerier.query(req);

        // Set Preconditions

        for (ApiTestRequestSpecification precondition : preconditions) {
            precondition.apply(req, logMessage);
            System.out.println(logMessage.getMessage());
        }

        req = req.when(); // Warum ist dieser Aufruf notwendig?

        // Perform Action

        Response res = action.perform(req, logMessage);
        // logMessage.setResponse(res);
        ValidatableResponse then = res.then();

        // Check Assertions

        for (ApiTestAssertion assertion: assertions) {
            try {
                assertion.apply(then);
            }catch (AssertionError error){
                logMessage.setStatus(LogMessage.Status.FAILURE); // TODO: Changed FAILED to FAILURE for testing
                logCollect.setStatus(TestScenarioLogCollection.Status.FAILURE);
            }
        }

        FilterableRequestSpecification filter = (FilterableRequestSpecification) req;
        return new ApiTestExecution(q, then.extract()); // was macht dieser Rückgabetyp ?
    }
}
