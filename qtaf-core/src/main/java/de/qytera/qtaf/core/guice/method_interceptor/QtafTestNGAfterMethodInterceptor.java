package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.AfterMethodExecutionInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.AfterMethod;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGAfterMethodInterceptor extends QtafTestNGAnnotatedMethodInterceptor<AfterMethodExecutionInfo> {

    /**
     * Creates a new {@link QtafTestNGAfterMethodInterceptor}.
     */
    public QtafTestNGAfterMethodInterceptor() {
        super(QtafEvents.afterTestScenario, QtafEvents.afterTestScenarioSuccess, QtafEvents.afterTestScenarioFailure);
    }

    @Override
    protected void onInvoke(MethodInvocation methodInvocation) {
        QtafFactory.getLogger().debug(String.format("Intercept @AfterMethod method: name=%s", methodInvocation.getMethod().getName()));
    }

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        return methodInvocation.getClass().getAnnotation(AfterMethod.class);
    }

    @Override
    public AfterMethodExecutionInfo buildStepExecutionInfoEntity(MethodInvocation methodInvocation) {
        // Build step execution info object
        AfterMethodExecutionInfo stepExecutionInfoEntity = (AfterMethodExecutionInfo) new AfterMethodExecutionInfo()
                .setAnnotation((AfterMethod) getAnnotation(methodInvocation))
                .setMethodInvocation(methodInvocation);

        // Save individual id of method execution
        stepExecutionInfoEntity.setId(stepExecutionInfoEntity.hashCode());
        stepExecutionInfoEntity.setThread(Thread.currentThread());
        stepExecutionInfoEntity.setStackTraceElements(Thread.currentThread().getStackTrace());

        return stepExecutionInfoEntity;
    }

    @Override
    public String buildScenarioName(String featureName, String scenarioId) {
        return "After Scenario '" + scenarioId + "' Execution";
    }

    @Override
    public String buildScenarioDescription(String featureName, String scenarioName) {
        return "Executed after scenarios of feature " + featureName;
    }
}