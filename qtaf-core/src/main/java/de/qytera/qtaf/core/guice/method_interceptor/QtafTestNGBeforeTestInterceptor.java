package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.invokation.BeforeTestExecutionInfo;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.BeforeTest;

import java.util.Date;

/**
 * Method interceptor for methods that are annotated with the Step annotation
 */
public class QtafTestNGBeforeTestInterceptor implements MethodInterceptor {
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
            // Get step annotation
            BeforeTest beforeTestAnnotation = methodInvocation.getMethod().getAnnotation(BeforeTest.class);

            // Try to execute method and listen for errors. If an error occurs it will be logged.
            Object result;

            // Build step execution info object
            BeforeTestExecutionInfo beforeTestExecutionInfo = new BeforeTestExecutionInfo()
                    .setBeforeTestAnnotation(beforeTestAnnotation)
                    .setMethodInvocation(methodInvocation);

            // Save individual id of method execution
            beforeTestExecutionInfo.setId(beforeTestExecutionInfo.hashCode());
            beforeTestExecutionInfo.setThread(Thread.currentThread());
            beforeTestExecutionInfo.setStackTraceElements(Thread.currentThread().getStackTrace());

            // Dispatch event
            QtafEvents.beforeTest.onNext(beforeTestExecutionInfo);

            // Create new feature log collection that will collect log messages for the current feature
            String featureId = methodInvocation.getMethod().getDeclaringClass().getName();
            String featureName = methodInvocation.getMethod().getDeclaringClass().getAnnotation(TestFeature.class).name();

            String scenarioId = featureId + "." + methodInvocation.getMethod().getName() + "-" + instance.hashCode();
            String scenarioName = "Before '" + featureName + "' Execution";
            String scenarioDescription = "Executed before feature '" + featureName + "'";

            // Get the feature log collection that is responsible for the current test feature
            TestFeatureLogCollection featureLogCollection = QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                    featureId,
                    featureName
            );

            // Create a log collection for the current method
            TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                    featureId,
                    scenarioId,
                    scenarioName
            );
            scenarioLogCollection.setDescription(scenarioDescription);

            if (((IQtafTestContext) instance).getLogCollection() == null) {
                // Update test instance with log collection
                ((IQtafTestContext) instance).setLogCollection(scenarioLogCollection);
                featureLogCollection.addScenarioLogCollection(scenarioLogCollection);
                ((IQtafTestContext) instance).addLoggerToFieldsRecursively();
            }

            try {
                // Execute step method
                scenarioLogCollection.setStart(new Date());
                result = methodInvocation.proceed();
                scenarioLogCollection.setEnd(new Date());
                scenarioLogCollection.setDuration(scenarioLogCollection.getDuration());
                scenarioLogCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);

                // Dispatch event
                beforeTestExecutionInfo.setResult(result);
                //QtafEvents.stepExecutionSuccess.onNext(beforeTestExecutionInfo);
            } catch (Throwable e) {
                // Dispatch event
                scenarioLogCollection.setEnd(new Date());
                scenarioLogCollection.setDuration(scenarioLogCollection.getDuration());
                scenarioLogCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
                beforeTestExecutionInfo.setError(e);
                //QtafEvents.stepExecutionFailure.onNext(beforeTestExecutionInfo);

                // throw exception again, so that tests behaves as it would without invocation
                throw e;
            }

            return result;
        }

        // If class is not instance of TestContext proceed
        return methodInvocation.proceed();
    }
}