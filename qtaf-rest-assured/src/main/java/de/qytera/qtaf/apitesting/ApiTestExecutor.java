package de.qytera.qtaf.apitesting;

import de.qytera.qtaf.apitesting.requesttypes.ApiRequestType;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.apitesting.requestspecifications.ApiRequestSpecification;
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
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.jetbrains.annotations.NotNull;


import java.util.List;

import static de.qytera.qtaf.apitesting.assertions.ApiAssertionLogMessageHelper.*;

/**
 * The class is responsible for executing the API tests
 * and thus represents the application interface to the QTAF API Testing feature.
 * It is recommended to statically import the apiTest() method
 * contained in this class into the test class in order to perform API tests
 */
public class ApiTestExecutor {
    /**
     * This function is intended to be called by a user of QTAF when he wants to perform an API test.
     * The function takes a list of requestSpecifications, the request type and a list of assertions
     * that should be performed during the API Test.
     * The requestSpecifications and assertions passed are themselves methods of functional interfaces
     * that are evaluated when apiText is executed.
     * This allows QTAF to hook into the existing RestAssured library
     * and generate corresponding LogMessages.
     *
     * @param requestSpecifications List of api requestSpecifications
     * @param requestType   Api request type that should be performed
     * @param assertions    List of assertions
     * @return  ExecutedApiTest result
     */
    public static ExecutedApiTest apiTest(
            IQtafTestContext context,
            List<ApiRequestSpecification> requestSpecifications,
            ApiRequestType requestType,
            List<ApiAssertion> assertions
    ) {
        // Instance of ApiTestExecutor to perform class methods inside this static apiTest() method
        ApiTestExecutor apiTestExecutor = new ApiTestExecutor();

        // Setup for Log Messages
        TestScenarioLogCollection logCollection = context.getLogCollection();
        ApiLogMessage logMessage = new ApiLogMessage(LogLevel.INFO, "Api Test");
        logCollection.addLogMessage(logMessage);

        // Setup Request
        RequestSpecification req = RestAssured.given();
        QueryableRequestSpecification q = SpecificationQuerier.query(req);

        // Preconditions
        apiTestExecutor.applyRequestSpecifications(req, requestSpecifications, logMessage);

        // Api request based on type
        Response res = requestType.perform(req, logMessage);

        // Handel Response
        ValidatableResponse validatableResponse = res.then();
        ExtractableResponse<Response> response = validatableResponse.extract();

        // Update QTAF-LogMessage
        logMessage.getRequest().setRequestAttributes(q);
        logMessage.getResponse().setResponseAttributes(response);

        // Check Assertions
        boolean hasPassed = true;
        int i = 0;
        for (ApiAssertion assertion: assertions) {
            try {
                /* The implementations of apply() ensure
                 * that an AssertionLogMessage is created
                 * and appended to the LogMessage
                 * before an exception is thrown.
                 */

                // It is necessary to provide a new ValidatalbeResponse via res.then() here
                // to avoid undesirable behaviour
                assertion.apply(res.then(), logMessage);


                // The following code is not executed if the above call of apply() throws an exception

                AssertionLogMessage currentAssertionLogMessage = logMessage.getAssertions().get(i);
                changeMessageAccordingToAssertionPassed(currentAssertionLogMessage, response);

            } catch (AssertionError error){
                hasPassed = false;

                AssertionLogMessage currentAssertionLogMessage = logMessage.getAssertions().get(i);
                changeMessageAccordingToAssertionFailure(currentAssertionLogMessage, response, error);
            }
            i++;
        }
        if (hasPassed){
            logMessage.setStatus(LogMessage.Status.PASSED);
        } else {
            logMessage.setStatus(LogMessage.Status.FAILED);
        }
        return new ExecutedApiTest(q, validatableResponse.extract());
    }

    private void applyRequestSpecifications(RequestSpecification request, @NotNull List<ApiRequestSpecification> preconditions, ApiLogMessage logMessage){
        for (ApiRequestSpecification precondition : preconditions) {
            precondition.apply(request, logMessage);
        }
    }

    /**
     * This function is syntactic sugar.
     * It can be called in the apiTest()-Method
     * to provide a List<ApiPrecondition>.
     * Due to the naming of this method
     * the test case is more readable for users.
     *
     * @param apiPreconditions api preconditions to specify the request
     * @return  a list of the provided api preconditions
     */
    public static List<ApiRequestSpecification> specifyRequest(ApiRequestSpecification... apiPreconditions){
        return List.of(apiPreconditions);
    }

    /**
     * This function is syntactic sugar.
     * It can be called in the apiTest()-Method
     * to provide a List<ApiAssertion>.
     * Due to the naming of this method
     * the test case is more readable for users.
     *
     * @param apiAssertions api preconditions to specify the request
     * @return  a list of the provided api assertions
     */
    public static List<ApiAssertion> specifyAssertions(ApiAssertion ... apiAssertions){
        return List.of(apiAssertions);
    }
}
