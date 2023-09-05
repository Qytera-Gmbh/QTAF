package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.BeforeSuiteExecutionInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.BeforeSuite;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the @BeforeSuite annotation
 */
public class QtafTestNGBeforeSuiteInterceptor extends QtafTestNGAnnotatedMethodInterceptor<BeforeSuiteExecutionInfo> {
    /**
     * Constructor
     */
    public QtafTestNGBeforeSuiteInterceptor() {
        super(QtafEvents.beforeTestSuite, QtafEvents.beforeTestSuiteSuccess, QtafEvents.beforeTestSuiteFailure);
    }

    @Override
    public void onInvoke(MethodInvocation methodInvocation) {
        QtafFactory.getLogger().debug(String.format("Intercept @BeforeSuite method: name=%s", methodInvocation.getMethod().getName()));
    }

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        return methodInvocation.getClass().getAnnotation(BeforeSuite.class);
    }

    @Override
    public BeforeSuiteExecutionInfo buildStepExecutionInfoEntity(MethodInvocation methodInvocation) {
        // Build step execution info object
        BeforeSuiteExecutionInfo stepExecutionInfoEntity = (BeforeSuiteExecutionInfo) new BeforeSuiteExecutionInfo()
                .setAnnotation((BeforeSuite) getAnnotation(methodInvocation))
                .setMethodInvocation(methodInvocation);

        // Save individual id of method execution
        stepExecutionInfoEntity.setId(stepExecutionInfoEntity.hashCode());
        stepExecutionInfoEntity.setThread(Thread.currentThread());
        stepExecutionInfoEntity.setStackTraceElements(Thread.currentThread().getStackTrace());

        return stepExecutionInfoEntity;
    }

    @Override
    public String buildScenarioName(String featureName, String scenarioId) {
        return "Before Test Suite Execution";
    }

    @Override
    public String buildScenarioDescription(String featureName, String scenarioName) {
        return "Executed before feature test suite";
    }
}