package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.collection.*;
import de.qytera.qtaf.core.log.model.index.FeatureLogCollectionIndex;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

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
        IndexHelper.clearAllIndices();
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
        IndexHelper.clearAllIndices();
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
        IndexHelper.clearAllIndices();
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
                .createScenarioIfNotExists("feature1", "scenario1", "instance1", "test1");

        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 1);
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        // Create another log collection with the same ID.
        // This should not create a new collection but return the existing one
        TestScenarioLogCollection scenarioLogCollection2 = featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario1", "instance1", "test2");

        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 1);
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        // Create a new collection with an ID that has not been used before.
        // This should create a new collection.
        TestScenarioLogCollection scenarioLogCollection3 = featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario2", "instance1", "test3");

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
        IndexHelper.clearAllIndices();

        // Now there shouldn't be any scenario log anymore
        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 0);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);
    }

    /**
     * Test the grouping-by-abstractScenarioId method of the feature log collection class
     */
    @Test
    public void testGroupByAbstractScenarioId() {
        // There should be no scenario logs at the beginning
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);

        // Create a new feature log collection
        TestFeatureLogCollection featureLogCollection = TestFeatureLogCollection
                .createFeatureLogCollectionIfNotExists(
                        "feature1",
                        "feature1"
                );

        // Create three scenario log collections
        featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario1", "iteration1", "test1")
                .setAbstractScenarioId("scenario1");
        featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario1", "iteration2", "test2")
                .setAbstractScenarioId("scenario1");
        featureLogCollection
                .createScenarioIfNotExists("feature1", "scenario2", "iteration1", "test3")
                .setAbstractScenarioId("scenario2");

        // Group scenarios by abstract scenario id
        Map<String, List<TestScenarioLogCollection>> map = featureLogCollection.getScenariosGroupedByAbstractScenarioId();

        Assert.assertEquals(map.size(), 2, "There should be two groups of scenarios");
        Assert.assertNotNull(map.get("scenario1"), "There should be a group called 'scenario1'");
        Assert.assertNotNull(map.get("scenario2"), "There should be a group called 'scenario2'");
        Assert.assertEquals(map.get("scenario1").size(), 2, "There should be two items in the group 'scenario1'");
        Assert.assertEquals(map.get("scenario2").size(), 1, "There should be one item in the group 'scenario2'");
        Assert.assertEquals(
                map.get("scenario1").get(0).getScenarioId(),
                "scenario1-iteration1",
                "The scenario id of the first item in the group 'scenario1' should be 'scenario1-iteration1'"
        );
        Assert.assertEquals(
                map.get("scenario1").get(1).getScenarioId(),
                "scenario1-iteration2",
                "The scenario id of the first item in the group 'scenario1' should be 'scenario1-iteration2'"
        );
        Assert.assertEquals(
                map.get("scenario2").get(0).getScenarioId(),
                "scenario2-iteration1",
                "The scenario id of the first item in the group 'scenario1' should be 'scenario2-iteration1'"
        );

        // Clear all scenario logs
        featureLogCollection.clearCollection();
        IndexHelper.clearAllIndices();

        // Now there shouldn't be any scenario log anymore
        Assert.assertEquals(featureLogCollection.countScenarioLogs(), 0);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);
    }
}
