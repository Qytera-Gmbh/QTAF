package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.BeforeMethodExecutionInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.BeforeMethod;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGBeforeMethodInterceptor extends QtafTestNGAnnotatedMethodInterceptor<BeforeMethodExecutionInfo> {

    /**
     * Creates a new {@link QtafTestNGBeforeMethodInterceptor}.
     */
    public QtafTestNGBeforeMethodInterceptor() {
        super(QtafEvents.beforeTestScenario, QtafEvents.beforeTestScenarioSuccess, QtafEvents.beforeTestScenarioFailure);
    }

    @Override
    public void onInvoke(MethodInvocation methodInvocation) {
        QtafFactory.getLogger().debug(String.format("Intercept @BeforeMethod method: name=%s", methodInvocation.getMethod().getName()));
    }

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        return methodInvocation.getClass().getAnnotation(BeforeMethod.class);
    }

    @Override
    public BeforeMethodExecutionInfo buildStepExecutionInfoEntity(MethodInvocation methodInvocation) {
        // Build step execution info object
        BeforeMethodExecutionInfo stepExecutionInfoEntity = (BeforeMethodExecutionInfo) new BeforeMethodExecutionInfo()
                .setAnnotation((BeforeMethod) getAnnotation(methodInvocation))
                .setMethodInvocation(methodInvocation);

        // Save individual id of method execution
        stepExecutionInfoEntity.setId(stepExecutionInfoEntity.hashCode());
        stepExecutionInfoEntity.setThread(Thread.currentThread());
        stepExecutionInfoEntity.setStackTraceElements(Thread.currentThread().getStackTrace());

        return stepExecutionInfoEntity;
    }

    @Override
    public String buildScenarioName(String featureName, String scenarioId) {
        return "Before Scenario '" + scenarioId + "' Execution";
    }

    @Override
    public String buildScenarioDescription(String featureName, String scenarioName) {
        return "Executed before scenarios of feature " + featureName;
    }
}