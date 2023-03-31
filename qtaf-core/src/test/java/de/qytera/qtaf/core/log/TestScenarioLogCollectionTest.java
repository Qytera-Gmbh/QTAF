package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.log.model.collection.*;
import de.qytera.qtaf.core.log.model.index.FeatureLogCollectionIndex;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestScenarioLogCollectionTest {
    /**
     * Assert that two ScenarioLogCollection objects with the same ID are equal
     */
    @Test
    public void testObjectEquality() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        TestScenarioLogCollection c2 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 2");

        Assert.assertEquals(c1, c2);

        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testFactoryMethodUniqueness() {
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0, "Assert empty index");

        TestScenarioLogCollection scenarioLogCollection1 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature1",
                        "scenario1",
                        "instance1",
                        "test1"
                );

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        TestScenarioLogCollection scenarioLogCollection2 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature2",
                        "scenario1",
                        "instance1",
                        "test2"
                );

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        TestScenarioLogCollection scenarioLogCollection3 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature3",
                        "scenario2",
                        "instance1",
                        "test3"
                );

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 2);

        // Clear indices
        IndexHelper.clearAllIndices();
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);
    }

    @Test
    public void testQtafEventFactoryMethodUniqueness() {
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);

        QtafTestEventPayload eventPayload1 = new QtafTestEventPayload()
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1");

        QtafTestEventPayload eventPayload2 = new QtafTestEventPayload()
                .setFeatureId("feature2")
                .setAbstractScenarioId("scenario1");

        QtafTestEventPayload eventPayload3 = new QtafTestEventPayload()
                .setFeatureId("feature3")
                .setAbstractScenarioId("scenario2");

        TestScenarioLogCollection scenarioLogCollection1 = TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload1);

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        TestScenarioLogCollection scenarioLogCollection2 = TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload2);

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        TestScenarioLogCollection scenarioLogCollection3 = TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload3);

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 2);

        // Clear indices
        IndexHelper.clearAllIndices();
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);
    }
}
