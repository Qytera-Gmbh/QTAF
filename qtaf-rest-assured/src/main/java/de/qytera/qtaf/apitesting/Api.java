package de.qytera.qtaf.apitesting;

import de.qytera.qtaf.apitesting.action.ApiAction;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.apitesting.request.ApiTestRequestSpecification;
import de.qytera.qtaf.apitesting.response.ApiAssertionLogMessageHelper;
import de.qytera.qtaf.apitesting.response.ApiTestAssertion;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;


import java.util.List;

import static de.qytera.qtaf.apitesting.response.ApiAssertionLogMessageHelper.computeAcualValue;
import static de.qytera.qtaf.apitesting.response.AssertionTypes.Type.*;

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
        TestScenarioLogCollection logCollection = context.getLogCollection();
        ApiLogMessage logMessage = new ApiLogMessage(LogLevel.INFO, "Api Call");
        logCollection.addLogMessage(logMessage);

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
        ValidatableResponse validatableResponse = res.then();
        FilterableRequestSpecification filter = (FilterableRequestSpecification) req;
        ExtractableResponse<Response> response = validatableResponse.extract();
        logMessage.getResponse().setResponseAttributes(response);
        response.body();

        // Check Assertions
        boolean hasPassed = true;
        int i = 0;
        for (ApiTestAssertion assertion: assertions) {
            try {
                assertion.apply(validatableResponse, logMessage);
                // this code won't get executet if apply() causes an exception

                List<LogMessage> logMessages = logCollection.getLogMessages();
                LogMessage latestLogMessage = logMessages.get(0);
                List<AssertionLogMessage> assertionLogMessages = latestLogMessage.getAssertions();
                AssertionLogMessage currentAssertionLogMessage = assertionLogMessages.get(i);
                computeAcualValue(currentAssertionLogMessage, LogMessage.Status.PASSED, response, null);

            } catch (AssertionError error){
                System.out.println(error);
                System.out.println("==============");
                hasPassed = false;

                List<LogMessage> logMessages = logCollection.getLogMessages();
                LogMessage latestLogMessage = logMessages.get(0);
                List<AssertionLogMessage> assertionLogMessages = latestLogMessage.getAssertions();
                AssertionLogMessage currentAssertionLogMessage = assertionLogMessages.get(i);

                computeAcualValue(currentAssertionLogMessage, LogMessage.Status.FAILURE, response, error.getMessage());


            }
            i++;
        }

        if (hasPassed){
            logMessage.setStatus(LogMessage.Status.PASSED);
        } else {
            logMessage.setStatus(LogMessage.Status.FAILURE);
        }


        return new ApiTestExecution(q, validatableResponse.extract()); // was macht dieser Rückgabetyp ?
    }
}
