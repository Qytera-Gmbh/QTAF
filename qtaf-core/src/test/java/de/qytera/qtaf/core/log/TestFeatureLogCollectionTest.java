package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.collection.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;

public class TestFeatureLogCollectionTest {

    class TestFeatureImplementation implements TestFeature {

        @Override
        public String name() {
            return "test-feature-annotation-name";
        }

        @Override
        public String description() {
            return "test-feature-annotation-desc";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }

    /**
     * Assert that two FeatureLogCollection objects with the same ID are equal
     */
    @Test
    public void testObjectEquality() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection c1 = slc.createFeatureIfNotExists("feature1", "Feature 1");
        TestFeatureLogCollection c2 = slc.createFeatureIfNotExists("feature1", "Feature 2");

        Assert.assertEquals(c2, c1);

        // Clear up
        slc.clearCollection();
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
    }

    /**
     * Assert that ID and name are correctly assigned
     */
    @Test
    public void testFeatureLogCollectionConstructor1() {
        TestFeatureLogCollection testFeatureLogCollection = TestFeatureLogCollection
            .createFeatureLogCollectionIfNotExists(
                "id-1",
                "feature1"
        );

        Assert.assertEquals(testFeatureLogCollection.getFeatureId(), "id-1");
        Assert.assertNotEquals(testFeatureLogCollection.getFeatureId(), "id-2");
        Assert.assertEquals(testFeatureLogCollection.getFeatureName(), "feature1");
        Assert.assertNotEquals(testFeatureLogCollection.getFeatureName(), "feature2");

        // Clean up
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);
    }

    /**
     * Assert that ID, name and description are correctly assigned from the TestFeature annotation
     */
    @Test
    public void testFeatureLogCollectionConstructor2() {
        // Instantiate class with TestFeature annotation
        TestFeatureImplementation testFeatureImplementation = new TestFeatureImplementation();

        // Create FeatureLogCollection by annotated class
        TestFeatureLogCollection testFeatureLogCollection = TestFeatureLogCollection
            .createFeatureLogCollectionIfNotExists(
                "id-1",
                testFeatureImplementation
        );

        Assert.assertEquals(testFeatureLogCollection.getFeatureId(), "id-1");
        Assert.assertNotEquals(testFeatureLogCollection.getFeatureId(), "id-2");
        Assert.assertEquals(testFeatureLogCollection.getFeatureName(), "test-feature-annotation-name");
        Assert.assertNotEquals(testFeatureLogCollection.getFeatureName(), "abc");
        Assert.assertEquals(testFeatureLogCollection.getFeatureDescription(), "test-feature-annotation-desc");
        Assert.assertNotEquals(testFeatureLogCollection.getFeatureDescription(), "abc");

        // Clean up
        FeatureLogCollectionIndex.getInstance().clear();
        ScenarioLogCollectionIndex.getInstance().clear();
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);
    }

    /**
     * Test that scenario IDs are unique
     */
    @Test
    public void testScenarioLogUniqueness() {
        // There should be no scenario logs at the beginning
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);

        TestFeatureLogCollection featureLogCollection = TestFeatureLogCollection
            .createFeatureLogCollectionIfNotExists(
                "feature1",
                "feature1"
        );

        // Create a new log collection
        TestScenarioLogCollection scenarioLogCollection1 = featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario1", "test1");

        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 1);
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        // Create another log collection with the same ID.
        // This should not create a new collection but return the existing one
        TestScenarioLogCollection scenarioLogCollection2 = featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario1", "test2");

        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 1);
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        // Create a new collection with an ID that has not been used before.
        // This should create a new collection.
        TestScenarioLogCollection scenarioLogCollection3 = featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario2", "test3");

        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 2);
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 2);

        Assert.assertEquals(
                scenarioLogCollection1,
                scenarioLogCollection2,
                "ScenarioLogCollection1 and ScenarioLogCollection2 should be equal"
        );

        Assert.assertNotEquals(
                scenarioLogCollection2,
                scenarioLogCollection3,
                "ScenarioLogCollection2 and ScenarioLogCollection3 should not be equal"
        );
        Assert.assertNotEquals(
                scenarioLogCollection1,
                scenarioLogCollection3,
                "ScenarioLogCollection1 and ScenarioLogCollection3 should not be equal"
        );

        // Clear all scenario logs
        featureLogCollection.clearCollection();
        FeatureLogCollectionIndex.getInstance().clear();
        ScenarioLogCollectionIndex.getInstance().clear();

        // Now there shouldn't be any scenario log anymore
        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 0);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);
    }
}
