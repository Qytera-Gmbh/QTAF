package de.qytera.qtaf.core.log.repository;

import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the scenario log repository
 */
public class ScenarioLogCollectionRepositoryTest {
    @Test
    public void testFindByIdWithExistingEntity() {
        // Assert that there are no scenario logs before we start the test
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);

        String featureId = "feature1";
        String abstractScenarioId = "abc123";
        String instanceId = "i1";

        // Create new scenario log
        QtafTestEventPayload eventPayload1 = new QtafTestEventPayload()
                .setFeatureId(featureId)
                .setAbstractScenarioId(abstractScenarioId)
                .setInstanceId(instanceId);

        // Add scenario log entity to index
        TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload1);

        // Try to find existing scenario log
        TestScenarioLogCollection scenarioLogCollection = ScenarioLogCollectionRepository.findById(abstractScenarioId + "-" + instanceId);
        Assert.assertNotNull(scenarioLogCollection);

        // Clear the index
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testFindByIdWithNonExistentEntity() {
        // Clear the index
        IndexHelper.clearAllIndices();

        // Assert that there are no scenario logs before we start the test
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);

        // Try to find non-existing scenario log
        TestScenarioLogCollection nonExistentScenarioLogCollection2 = ScenarioLogCollectionRepository.findById("test123");
        Assert.assertNull(nonExistentScenarioLogCollection2);

        // Clear the index
        IndexHelper.clearAllIndices();
    }
}
