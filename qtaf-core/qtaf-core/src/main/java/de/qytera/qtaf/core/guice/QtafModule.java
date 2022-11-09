package de.qytera.qtaf.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import de.qytera.qtaf.core.guice.method_interceptor.QtafStepTrackerInterceptor;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.cucumber.java.en.When;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * Guice module used in TestContext classes
 */
public class QtafModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind to all methods that are annotated with the Step annotation
        bindInterceptor(
                Matchers.any(),                     // Any class
                Matchers.annotatedWith(Step.class), // Any method annotated with Step
                new QtafStepTrackerInterceptor()    // Logic for method invocation
        );
    }
}


/**
 * Method interceptor for methods that are annotated with the Step annotation
 * @deprecated TODO delete
 */
class CallTrackerService implements MethodInterceptor {
    /**
     * This method works as a proxy for methods. Instead of executing the annotated method directly this method will
     * be executed.
     *
     * @param methodInvocation  Invoked method
     * @return                  Method execution result
     * @throws Throwable        Error
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (methodInvocation.getThis() instanceof IQtafTestContext) { // executed if this is instance of IQtafTestContext
            // Get step annotation
            Step step = methodInvocation.getMethod().getAnnotation(Step.class);

            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            // Build step execution info object
            StepExecutionInfo stepExecutionInfo = new StepExecutionInfo()
                    .setStep(step)
                    .setMethodInvocation(methodInvocation);

            // Save individual id of method execution
            stepExecutionInfo.setId(stepExecutionInfo.hashCode());

            // Dispatch event
            QtafEvents.beforeStepExecution.onNext(stepExecutionInfo);

            try {
                // Execute step method
                result = methodInvocation.proceed();

                // Dispatch event
                stepExecutionInfo.setResult(result);
                QtafEvents.stepExecutionSuccess.onNext(stepExecutionInfo);
            } catch (Throwable e) {
                // Dispatch event
                stepExecutionInfo.setError(e);
                QtafEvents.stepExecutionFailure.onNext(stepExecutionInfo);

                // throw exception again, so that tests behaves as it would without invocation
                throw e;
            }

            return result;
        }

        // If class is not instance of TestContext proceed
        return methodInvocation.proceed();
    }
}