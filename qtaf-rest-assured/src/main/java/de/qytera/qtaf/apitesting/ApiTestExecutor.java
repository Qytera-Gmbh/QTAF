package de.qytera.qtaf.apitesting;

import de.qytera.qtaf.apitesting.requesttypes.ApiRequestType;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.apitesting.preconditions.ApiPrecondition;
import de.qytera.qtaf.apitesting.assertions.ApiAssertion;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;


import java.util.List;

import static de.qytera.qtaf.apitesting.assertions.ApiAssertionLogMessageHelper.computeAcualValue;

/**
 * The class is responsible for executing the API tests
 * and thus represents the application interface to the QTAF API Testing feature.
 * It is recommended to statically import the apiTest() method
 * contained in this class into the test class in order to perform API tests
 */
public class ApiTestExecutor {
    /**
     * This function is intended to be called by a user of QTAF when he wants to perform an API test.
     * The function takes a list of preconditions, the request type and a list of assertions
     * that should be performed during the API Test.
     * The preconditions and assertions passed are themselves methods of functional interfaces
     * that are evaluated when apiText is executed.
     * This allows QTAF to hook into the existing RestAssured library
     * and generate corresponding LogMessages.
     *
     * @param preconditions List of api preconditions
     * @param requestType   Api request type that should be performed
     * @param assertions    List of assertions
     * @return  ExecutedApiTest result
     */
    public static ExecutedApiTest apiTest(
            IQtafTestContext context,
            List<ApiPrecondition> preconditions,
            ApiRequestType requestType,
            List<ApiAssertion> assertions
    ) {
        TestScenarioLogCollection logCollection = context.getLogCollection();
        ApiLogMessage logMessage = new ApiLogMessage(LogLevel.INFO, "Api Call");
        logCollection.addLogMessage(logMessage);

        RequestSpecification req = RestAssured.given();
        QueryableRequestSpecification q = SpecificationQuerier.query(req);

        // Set Preconditions

        for (ApiPrecondition precondition : preconditions) {
            precondition.apply(req, logMessage);
            System.out.println(logMessage.getMessage());
        }

        req = req.when(); // Warum ist dieser Aufruf notwendig?

        // Perform Action

        Response res = requestType.perform(req, logMessage);
        // logMessage.setResponse(res);
        ValidatableResponse validatableResponse = res.then();
        FilterableRequestSpecification filter = (FilterableRequestSpecification) req;
        ExtractableResponse<Response> response = validatableResponse.extract();
        logMessage.getResponse().setResponseAttributes(response);
        response.body();

        // Check Assertions
        boolean hasPassed = true;
        int i = 0;
        for (ApiAssertion assertion: assertions) {
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


        return new ExecutedApiTest(q, validatableResponse.extract()); // was macht dieser Rückgabetyp ?
    }
}
