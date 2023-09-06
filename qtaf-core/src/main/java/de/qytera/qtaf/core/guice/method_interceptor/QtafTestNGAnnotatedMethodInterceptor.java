package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.guice.invokation.AbstractStepExecutionInfo;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import rx.subjects.PublishSubject;

/**
 * Method interceptor for methods that are annotated with the Step annotation.
 *
 * @param <T> the test execution info type
 */
public abstract class QtafTestNGAnnotatedMethodInterceptor<T extends AbstractStepExecutionInfo> implements MethodInterceptor, AbstractTestNGAnnotatedMethodInterceptor<T> {
    /**
     * Publisher which notifies all subjects before step executions.
     */
    protected final PublishSubject<T> beforeStepExecution;
    /**
     * Publisher which notifies all subjects after successful step executions.
     */
    protected final PublishSubject<T> afterStepExecutionSuccess;
    /**
     * Publisher which notifies all subjects after failed step executions.
     */
    protected final PublishSubject<T> afterStepExecutionFailure;

    /**
     * Constructor
     * @param beforeStepExecution           Subject that emits events before a step is executed
     * @param afterStepExecutionSuccess     Subject that emits events after a step was executed successfully
     * @param afterStepExecutionFailure     Subject that emits events after a step failed
     */
    protected QtafTestNGAnnotatedMethodInterceptor(
            PublishSubject<T> beforeStepExecution,
            PublishSubject<T> afterStepExecutionSuccess,
            PublishSubject<T> afterStepExecutionFailure
    ) {
        this.beforeStepExecution = beforeStepExecution;
        this.afterStepExecutionSuccess = afterStepExecutionSuccess;
        this.afterStepExecutionFailure = afterStepExecutionFailure;
    }

    /**
     * This method works as a proxy for methods. Instead of executing the annotated method directly this method will
     * be executed.
     *
     * @param methodInvocation Invoked method
     * @return Method execution result
     * @throws Throwable Error
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object instance = methodInvocation.getThis();

        if (instance instanceof IQtafTestContext iqtafInstance) { // executed if this is instance of IQtafTestContext
            onInvoke(methodInvocation);
            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            // Build step execution info object
            T stepExecution = buildStepExecutionInfoEntity(methodInvocation);

            // Dispatch event
            beforeStepExecution.onNext(stepExecution);

            // Create a log collection for the current method
            TestFeatureLogCollection featureLogCollection = buildFeatureLogCollection(methodInvocation, iqtafInstance);
            TestScenarioLogCollection scenarioLogCollection = buildScenarioLogCollection(featureLogCollection, methodInvocation, iqtafInstance);
            updateTestContextWithLogCollection(iqtafInstance, scenarioLogCollection);

            try {
                // Execute step method
                result = executeStepMethod(methodInvocation, scenarioLogCollection);
                stepExecution.setResult(result);

                // Dispatch event
                afterStepExecutionSuccess.onNext(stepExecution);
            } catch (Throwable e) {
                // Dispatch event
                handleStepExecutionFailure(scenarioLogCollection);
                stepExecution.setError(e);

                afterStepExecutionFailure.onNext(stepExecution);
                // throw exception again, so that tests behave as it would without invocation
                throw e;
            }

            return result;
        }

        // If class is not instance of TestContext proceed
        return methodInvocation.proceed();
    }

    /**
     * Code to execute just before method invocation.
     *
     * @param methodInvocation the pending method invocation
     */
    protected abstract void onInvoke(MethodInvocation methodInvocation);

}