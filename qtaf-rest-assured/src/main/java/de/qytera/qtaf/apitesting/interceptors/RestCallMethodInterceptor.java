package de.qytera.qtaf.apitesting.interceptors;

import de.qytera.qtaf.apitesting.annotations.RestCall;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.internal.collections.Pair;

public class RestCallMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (methodInvocation.getThis() instanceof IQtafTestContext) { // executed if this is instance of IQtafTestContext
            QtafFactory.getLogger().debug(String.format("Intercept @RestCall method: name=%s", methodInvocation.getMethod().getName()));

            // Get step annotation
            RestCall restCall = methodInvocation.getMethod().getAnnotation(RestCall.class);

            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            TestScenarioLogCollection scenarioLogCollection = ((IQtafTestContext) methodInvocation.getThis()).getLogCollection();
            ApiLogMessage logMessage = new ApiLogMessage(LogLevel.INFO, restCall.name());
            scenarioLogCollection.addLogMessage(logMessage);

            // Check if one of the method arguments is a request or response object
            for(Object arg : methodInvocation.getArguments()) {
                if (arg instanceof RequestSpecification request) {
                    logMessage.getRequest().setRequestAttributes(SpecificationQuerier.query(request));
                }
                else if (arg instanceof ValidatableResponse response) {
                    logMessage.getResponse().setResponseAttributes(response.extract());
                }
            }

            try {
                // Execute step method
                result = methodInvocation.proceed();

                // Check if the result of the method is a request or response object
                if (result instanceof RequestSpecification request) {
                    logMessage.getRequest().setRequestAttributes(SpecificationQuerier.query(request));
                }
                else if (result instanceof ValidatableResponse response) {
                    logMessage.getResponse().setResponseAttributes(response.extract());
                }
                else if (result instanceof Pair<?,?> pair
                        && pair.first() instanceof RequestSpecification request
                        && pair.second() instanceof ValidatableResponse response
                ) {
                    logMessage.getRequest().setRequestAttributes(SpecificationQuerier.query(request));
                    logMessage.getResponse().setResponseAttributes(response.extract());
                }
            } catch (Throwable e) {
                logMessage.setError(e);
                // throw exception again, so that tests behaves as it would without invocation
                throw e;
            }

            return result;
        }

        // If class is not instance of TestContext proceed
        return methodInvocation.proceed();
    }
}
