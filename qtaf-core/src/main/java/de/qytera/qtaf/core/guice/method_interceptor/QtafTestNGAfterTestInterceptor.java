package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.AfterTestExecutionInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.AfterTest;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGAfterTestInterceptor extends QtafTestNGAnnotatedMethodInterceptor<AfterTestExecutionInfo> {

    /**
     * Creates a new {@link QtafTestNGAfterTestInterceptor}
     */
    public QtafTestNGAfterTestInterceptor() {
        super(QtafEvents.afterTestFeature, QtafEvents.afterTestFeatureSuccess, QtafEvents.afterTestFeatureFailure);
    }

    @Override
    public void onInvoke(MethodInvocation methodInvocation) {
        QtafFactory.getLogger().debug(String.format("Intercept @AfterTest method: name=%s", methodInvocation.getMethod().getName()));
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