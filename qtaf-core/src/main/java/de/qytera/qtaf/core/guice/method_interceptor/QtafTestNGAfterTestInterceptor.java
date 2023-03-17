package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.AfterTestExecutionInfo;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.AfterTest;
import rx.subjects.PublishSubject;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGAfterTestInterceptor extends AbstractTestNGAnnotatedMethodInterceptor implements MethodInterceptor {
    private final PublishSubject<AfterTestExecutionInfo> beforeStepExecution = QtafEvents.afterTestFeature;
    private final PublishSubject<AfterTestExecutionInfo> afterStepExecutionSuccess = QtafEvents.afterTestFeatureSuccess;
    private final PublishSubject<AfterTestExecutionInfo> afterStepExecutionFailure = QtafEvents.afterTestFeatureFailure;

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
        Object instance = methodInvocation.getThis();

        if (instance instanceof IQtafTestContext) { // executed if this is instance of IQtafTestContext
            QtafFactory.getLogger().debug(String.format("Intercept @AfterTest method: name=%s", methodInvocation.getMethod().getName()));

            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            // Build step execution info object
            AfterTestExecutionInfo stepExecution = buildStepExecutionInfoEntity(methodInvocation);

            // Dispatch event
            beforeStepExecution.onNext(stepExecution);

            // Create a log collection for the current method
            TestFeatureLogCollection featureLogCollection = buildFeatureLogCollection(methodInvocation, instance);
            TestScenarioLogCollection scenarioLogCollection = buildScenarioLogCollection(featureLogCollection, methodInvocation, instance);
            updateTestContextWithLogCollection((IQtafTestContext) instance, scenarioLogCollection);

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

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        return methodInvocation.getClass().getAnnotation(AfterTest.class);
    }

    @Override
    public AfterTestExecutionInfo buildStepExecutionInfoEntity(MethodInvocation methodInvocation) {
        // Build step execution info object
        AfterTestExecutionInfo stepExecutionInfoEntity = (AfterTestExecutionInfo) new AfterTestExecutionInfo()
                .setAnnotation((AfterTest) getAnnotation(methodInvocation))
                .setMethodInvocation(methodInvocation);

        // Save individual id of method execution
        stepExecutionInfoEntity.setId(stepExecutionInfoEntity.hashCode());
        stepExecutionInfoEntity.setThread(Thread.currentThread());
        stepExecutionInfoEntity.setStackTraceElements(Thread.currentThread().getStackTrace());

        return stepExecutionInfoEntity;
    }

    @Override
    public String buildScenarioName(String featureName, String scenarioId) {
        return "After Feature '" + featureName + "' Execution";
    }

    @Override
    public String buildScenarioDescription(String featureName, String scenarioName) {
        return "Executed after feature '" + featureName + "'";
    }
}