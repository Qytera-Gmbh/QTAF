package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.Date;

/**
 * This class provides helper methods for building scenario and step objects
 */
public class XrayTestEntityHelper {
    public static final XrayTest ANNOTATION = new XrayTest() {

        @Override
        public String key() {
            return "QTAF-42";
        }

        @Override
        public boolean scenarioReport() {
            return true;
        }

        @Override
        public boolean screenshots() {
            return true;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return XrayTest.class;
        }
    };

    /**
     * This is a method for building a dummy scenario
     * @param scenarioIteration scenario iteration number
     * @param featureName       name of the feature the scenario belongs to
     * @return  a dummy scenario
     */
    public static TestScenarioLogCollection scenario(int scenarioIteration, String featureName) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureName,
                "SomeClass.doesSomething",
                String.valueOf(scenarioIteration),
                "a test scenario"
        );
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[]{ANNOTATION});
        // Add scenario to its corresponding feature collection.
        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                featureName,
                "a test feature"
        );
        featureCollection.addScenarioLogCollection(scenarioCollection);
        // Add feature to suite collection.
        TestSuiteLogCollection.getInstance().addTestClassLogCollection(featureCollection);
        return scenarioCollection;
    }

    /**
     * This is a method for building a dummy scenario
     * @param scenarioIteration scenario iteration number
     * @param featureName       name of the feature the scenario belongs to
     * @param annotation        A list of test annotations
     * @return  a dummy scenario
     */
    public static TestScenarioLogCollection scenario(int scenarioIteration, String featureName, XrayTest annotation) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureName,
                "SomeClass.doesSomething",
                String.valueOf(scenarioIteration),
                "a test scenario"
        );
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[] {annotation});
        // Add scenario to its corresponding feature collection.
        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                featureName,
                "a test feature"
        );
        featureCollection.addScenarioLogCollection(scenarioCollection);
        // Add feature to suite collection.
        TestSuiteLogCollection.getInstance().addTestClassLogCollection(featureCollection);
        return scenarioCollection;
    }

    /**
     * This is a method for building a dummy scenario without an annotation.
     * @param scenarioIteration scenario iteration number
     * @param featureName       name of the feature the scenario belongs to
     * @return  a dummy scenario
     */
    public static TestScenarioLogCollection scenarioWithoutAnnotation(int scenarioIteration, String featureName) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureName,
                "SomeClass.doesSomething",
                String.valueOf(scenarioIteration),
                "a test scenario"
        );
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[]{/* empty */});
        // Add scenario to its corresponding feature collection.
        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                featureName,
                "a test feature"
        );
        featureCollection.addScenarioLogCollection(scenarioCollection);
        // Add feature to suite collection.
        TestSuiteLogCollection.getInstance().addTestClassLogCollection(featureCollection);
        return scenarioCollection;
    }

    /**
     * Build an entity for a successful step
     * @param methodName    name of the step's method
     * @return  step entity for a successful step
     */
    public static StepInformationLogMessage successfulStep(String methodName) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.PASSED);
        return message;
    }

    /**
     * Build an entity for a failing step
     * @param methodName    name of the step's method
     * @return  step entity for a failing step
     */
    public static StepInformationLogMessage failingStep(String methodName) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.PASSED);
        return message;
    }

    /**
     * Build an entity for a failing step
     * @param methodName    name of the step's method
     * @param screenshotPathBefore    path of the screenshot that was taken before the step execution
     * @return  step entity for a failing step
     */
    public static StepInformationLogMessage failingStep(
            String methodName,
            String screenshotPathBefore
    ) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.FAILED);
        message.setScreenshotBefore(screenshotPathBefore);
        return message;
    }

    /**
     * Build an entity for a failing step
     * @param methodName    name of the step's method
     * @param screenshotPathBefore    path of the screenshot that was taken before the step execution
     * @param screenshotPathAfter    path of the screenshot that was taken after the step execution
     * @return  step entity for a failing step
     */
    public static StepInformationLogMessage failingStep(
            String methodName,
            String screenshotPathBefore,
            String screenshotPathAfter
    ) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.FAILED);
        message.setScreenshotBefore(screenshotPathBefore);
        message.setScreenshotAfter(screenshotPathAfter);
        return message;
    }
}
