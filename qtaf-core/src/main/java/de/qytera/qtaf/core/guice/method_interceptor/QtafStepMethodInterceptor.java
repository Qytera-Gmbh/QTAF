package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafStepMethodInterceptor implements MethodInterceptor {
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
            QtafFactory.getLogger().debug(String.format("Intercept @Step method: name=%s", methodInvocation.getMethod().getName()));

            // Get step annotation
            Step step = methodInvocation.getMethod().getAnnotation(Step.class);

            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            // Build step execution info object
            StepExecutionInfo stepExecutionInfo = new StepExecutionInfo()
                    .setAnnotation(step)
                    .setMethodInvocation(methodInvocation);

            // Save individual id of method execution
            stepExecutionInfo.setId(stepExecutionInfo.hashCode());
            stepExecutionInfo.setThread(Thread.currentThread());
            stepExecutionInfo.setStackTraceElements(Thread.currentThread().getStackTrace());

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