package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.BeforeTestExecutionInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.BeforeTest;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGBeforeTestInterceptor extends QtafTestNGAnnotatedMethodInterceptor<BeforeTestExecutionInfo> {

    public QtafTestNGBeforeTestInterceptor() {
        super(QtafEvents.beforeTestFeature, QtafEvents.beforeTestFeatureSuccess, QtafEvents.beforeTestFeatureFailure);
    }

    @Override
    public void onInvoke(MethodInvocation methodInvocation) {
        QtafFactory.getLogger().debug(String.format("Intercept @BeforeSuite method: name=%s", methodInvocation.getMethod().getName()));
    }

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        return methodInvocation.getClass().getAnnotation(BeforeTest.class);
    }

    @Override
    public BeforeTestExecutionInfo buildStepExecutionInfoEntity(MethodInvocation methodInvocation) {
        // Build step execution info object
        BeforeTestExecutionInfo stepExecutionInfoEntity = (BeforeTestExecutionInfo) new BeforeTestExecutionInfo()
                .setAnnotation((BeforeTest) getAnnotation(methodInvocation))
                .setMethodInvocation(methodInvocation);

        // Save individual id of method execution
        stepExecutionInfoEntity.setId(stepExecutionInfoEntity.hashCode());
        stepExecutionInfoEntity.setThread(Thread.currentThread());
        stepExecutionInfoEntity.setStackTraceElements(Thread.currentThread().getStackTrace());

        return stepExecutionInfoEntity;
    }

    @Override
    public String buildScenarioName(String featureName, String scenarioId) {
        return "Before Feature '" + featureName + "' Execution";
    }

    @Override
    public String buildScenarioDescription(String featureName, String scenarioName) {
        return "Executed before feature '" + featureName + "'";
    }
}