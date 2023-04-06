package de.qytera.qtaf.core.guice.method_interceptor;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.util.Date;

/**
 * This class serves as a base class for all interceptor classes that intercept methods annotated with TestNG
 * annotations that deal with the test flow like @BeforeTest and @AfterTest
 */
public abstract class AbstractTestNGAnnotatedMethodInterceptor {
    /**
     * Build an entity that contains information about the executed method
     *
     * @param methodInvocation The method that was intercepted
     * @return Step execution information entity
     */
    public abstract Object buildStepExecutionInfoEntity(MethodInvocation methodInvocation);

    /**
     * Get the TestNG Annotation of the intercepted method
     *
     * @param methodInvocation The method that was intercepted
     * @return The TestNG annotation of the method
     */
    public abstract Annotation getAnnotation(MethodInvocation methodInvocation);

    /**
     * Build the name of the scenario for the log file
     *
     * @param featureName Name of the feature the scenario belongs to
     * @param scenarioId  ID of the scenario
     * @return Name of the scenario
     */
    public abstract String buildScenarioName(String featureName, String scenarioId);

    /**
     * Build the description of the scenario for the log file
     *
     * @param featureName  Name of the feature
     * @param scenarioName Name of the scenario
     * @return Description of the scenario
     */
    public abstract String buildScenarioDescription(String featureName, String scenarioName);

    /**
     * Get or build the feature log collection object that should be used for the intercepted method
     *
     * @param methodInvocation The method that was intercepted
     * @param instance         The object that called the method
     * @return Feature log collection object
     */
    public TestFeatureLogCollection buildFeatureLogCollection(MethodInvocation methodInvocation, Object instance) {
        // Create new feature log collection that will collect log messages for the current feature
        String featureId = methodInvocation.getMethod().getDeclaringClass().getName();
        String featureName = methodInvocation.getMethod().getDeclaringClass().getAnnotation(TestFeature.class).name();

        // Get the feature log collection that is responsible for the current test feature
        return QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                featureId,
                featureName
        );
    }

    /**
     * Get or build the scenario log collection object that should be used for the intercepted method
     *
     * @param featureLogCollection The feature log collection object the scenario log collection object belongs to
     * @param methodInvocation     The method that was intercepted
     * @param instance             The object that called the method
     * @return Scenario log collection object
     */
    public TestScenarioLogCollection buildScenarioLogCollection(TestFeatureLogCollection featureLogCollection, MethodInvocation methodInvocation, Object instance) {
        String featureId = featureLogCollection.getFeatureId();
        String featureName = featureLogCollection.getFeatureName();

        String scenarioId = featureId + "." + methodInvocation.getMethod().getName();
        String scenarioName = buildScenarioName(featureName, scenarioId);
        String scenarioDescription = buildScenarioDescription(featureName, scenarioName);


        // Create a log collection for the current method
        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureId,
                scenarioId,
                "",
                scenarioName
        );

        scenarioLogCollection.setDescription(scenarioDescription);
        scenarioLogCollection.setAnnotations(methodInvocation.getMethod().getAnnotations());
        featureLogCollection.addScenarioLogCollection(scenarioLogCollection);

        return scenarioLogCollection;
    }

    /**
     * Add the scenario log collection to the TestContext object the method call came from and all its nested TestContext objects like page objects
     *
     * @param instance              The object that called the method
     * @param scenarioLogCollection The scenario log collection object
     */
    public void updateTestContextWithLogCollection(IQtafTestContext instance, TestScenarioLogCollection scenarioLogCollection) {
        // Update test instance with log collection
        instance.setLogCollection(scenarioLogCollection);
        instance.addLoggerToFieldsRecursively();
    }

    /**
     * Execute the original method that was intercepted by this interceptor
     *
     * @param methodInvocation      The method that was intercepted
     * @param scenarioLogCollection The scenario log collection object for the current method
     * @return The result of the intercepted method
     * @throws Throwable Exception that was thrown by the intercepted method
     */
    public Object executeStepMethod(MethodInvocation methodInvocation, TestScenarioLogCollection scenarioLogCollection) throws Throwable {
        // Log the start time
        scenarioLogCollection.setStart(new Date());

        // Execute step method
        Object result = methodInvocation.proceed();

        // Log the end time
        scenarioLogCollection.setEnd(new Date());
        scenarioLogCollection.setDuration(scenarioLogCollection.getDuration());
        scenarioLogCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        return result;
    }

    /**
     * Deal with a failure of the execution of the intercepted method
     *
     * @param scenarioLogCollection The scenario log collection object for the method that was intercepted
     */
    public void handleStepExecutionFailure(TestScenarioLogCollection scenarioLogCollection) {
        scenarioLogCollection.setEnd(new Date());
        scenarioLogCollection.setDuration(scenarioLogCollection.getDuration());
        scenarioLogCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
    }
}
