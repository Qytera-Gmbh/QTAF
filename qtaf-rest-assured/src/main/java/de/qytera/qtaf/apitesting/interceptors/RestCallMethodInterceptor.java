package de.qytera.qtaf.apitesting.interceptors;

import de.qytera.qtaf.apitesting.annotations.RestCall;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.internal.collections.Pair;

/**
 * This class intercepts methods that are annotated with a RestCall annotation.
 */
public class RestCallMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (methodInvocation.getThis() instanceof IQtafTestContext // executed if this is instance of IQtafTestContext
                && methodInvocation.getMethod().getReturnType().getName().contains("io.restassured") // return type comes from restassured
        ) {
            QtafFactory.getLogger().debug(String.format("Intercept @Step method: name=%s", methodInvocation.getMethod().getName()));

            // Get step annotation
            Step stepAnnotation = methodInvocation.getMethod().getAnnotation(Step.class);

            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            TestScenarioLogCollection scenarioLogCollection = ((IQtafTestContext) methodInvocation.getThis()).getLogCollection();
            ApiLogMessage apiLogMessage = new ApiLogMessage(LogLevel.INFO, stepAnnotation.name());

            // Check if one of the method arguments is a request or response object
            for(Object arg : methodInvocation.getArguments()) {
                if (arg instanceof RequestSpecification request) {
                    apiLogMessage.getRequest().setRequestAttributes(SpecificationQuerier.query(request));
                }
                else if (arg instanceof ValidatableResponse response) {
                    apiLogMessage.getResponse().setResponseAttributes(response.extract());
                }
            }

            try {
                // Execute step method
                result = methodInvocation.proceed();

                // Check if the result of the method is a request or response object
                if (result instanceof RequestSpecification request) {
                    apiLogMessage.getRequest().setRequestAttributes(SpecificationQuerier.query(request));
                }
                else if (result instanceof ValidatableResponse response) {
                    apiLogMessage.getResponse().setResponseAttributes(response.extract());
                }
                else if (result instanceof Pair<?,?> pair
                        && pair.first() instanceof RequestSpecification request
                        && pair.second() instanceof ValidatableResponse response
                ) {
                    apiLogMessage.getRequest().setRequestAttributes(SpecificationQuerier.query(request));
                    apiLogMessage.getResponse().setResponseAttributes(response.extract());
                }

                StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage(
                        methodInvocation.getMethod().getName(),
                        apiLogMessage.buildMessage()
                );
                stepInformationLogMessage.setStep(stepAnnotation);
                stepInformationLogMessage.setStatus(LogMessage.Status.PASSED);
                stepInformationLogMessage.setResult(apiLogMessage.buildMessage());

                scenarioLogCollection.addLogMessage(stepInformationLogMessage);
            } catch (Throwable e) {
                apiLogMessage.setError(e);

                StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage(
                        methodInvocation.getMethod().getName(),
                        apiLogMessage.buildMessage()
                );
                stepInformationLogMessage.setStep(stepAnnotation);
                stepInformationLogMessage.setStatus(LogMessage.Status.FAILED);
                stepInformationLogMessage.setResult(apiLogMessage.buildMessage());

                // throw exception again, so that tests behaves as it would without invocation
                throw e;
            }

            return result;
        }

        // If class is not instance of TestContext proceed
        return methodInvocation.proceed();
    }
}
