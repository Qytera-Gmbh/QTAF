package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.invokation.AfterSuiteExecutionInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.AfterSuite;
import rx.subjects.PublishSubject;

import java.lang.annotation.Annotation;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGAfterSuiteInterceptor extends QtafTestNGAnnotatedMethodInterceptor<AfterSuiteExecutionInfo> {
    private static final PublishSubject<AfterSuiteExecutionInfo> beforeStepExecution = QtafEvents.afterTestSuite;
    private static final PublishSubject<AfterSuiteExecutionInfo> afterStepExecutionSuccess = QtafEvents.afterTestSuiteSuccess;
    private static final PublishSubject<AfterSuiteExecutionInfo> afterStepExecutionFailure = QtafEvents.afterTestSuiteFailure;

    protected QtafTestNGAfterSuiteInterceptor() {
        super(beforeStepExecution, afterStepExecutionSuccess, afterStepExecutionFailure);
    }

    @Override
    public void onInvoke(MethodInvocation methodInvocation) {
        QtafFactory.getLogger().debug(String.format("Intercept @AfterSuite method: name=%s", methodInvocation.getMethod().getName()));
    }

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        return methodInvocation.getClass().getAnnotation(AfterSuite.class);
    }

    @Override
    public AfterSuiteExecutionInfo buildStepExecutionInfoEntity(MethodInvocation methodInvocation) {
        // Build step execution info object
        AfterSuiteExecutionInfo stepExecutionInfoEntity = (AfterSuiteExecutionInfo) new AfterSuiteExecutionInfo()
                .setAnnotation((AfterSuite) getAnnotation(methodInvocation))
                .setMethodInvocation(methodInvocation);

        // Save individual id of method execution
        stepExecutionInfoEntity.setId(stepExecutionInfoEntity.hashCode());
        stepExecutionInfoEntity.setThread(Thread.currentThread());
        stepExecutionInfoEntity.setStackTraceElements(Thread.currentThread().getStackTrace());

        return stepExecutionInfoEntity;
    }

    @Override
    public String buildScenarioName(String featureName, String scenarioId) {
        return "After Test Suite Execution";
    }

    @Override
    public String buildScenarioDescription(String featureName, String scenarioName) {
        return "Executed after test suite";
    }
}