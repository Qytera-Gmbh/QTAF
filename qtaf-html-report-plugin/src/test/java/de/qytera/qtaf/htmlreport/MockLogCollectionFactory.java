package de.qytera.qtaf.htmlreport;

import de.qytera.qtaf.core.log.model.collection.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class generates mock log collections that can be used within other tests like report generation
 */
public class MockLogCollectionFactory {
    private static final TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();

    /**
     * Generate mock collections
     */
    @Test
    public static void generate() {
        // Mock feature logs
        TestFeatureLogCollection f1 = slc.createFeatureIfNotExists("feature1".hashCode(), "feature1");
        TestFeatureLogCollection f2 = slc.createFeatureIfNotExists("feature2".hashCode(), "feature2");

        // Mock scenario logs
        TestScenarioLogCollection c1 = f1.createScenarioIfNotExists(f1.getFeatureId(), "scenario1", "Scenario 1");
        TestScenarioLogCollection c2 = f1.createScenarioIfNotExists(f1.getFeatureId(), "scenario2", "Scenario 2");
        TestScenarioLogCollection c3 = f2.createScenarioIfNotExists(f2.getFeatureId(), "scenario3", "Scenario 3");
        TestScenarioLogCollection c4 = f2.createScenarioIfNotExists(f2.getFeatureId(), "scenario4", "Scenario 4");

        // There should be one feature log
        Assert.assertEquals(FeatureLogCollectionIndex.getInstance().size(), 2);

        // There should be two scenario logs
        Assert.assertEquals(ScenarioLogCollectionIndex.getInstance().size(), 4);
    }

    /**
     * Delete mock collections
     */
    @Test
    public static void clear() {
        // Clear up
        slc.clearCollection();
        FeatureLogCollectionIndex.getInstance().clear();
        ScenarioLogCollectionIndex.getInstance().clear();

        // There should be zero feature logs
        Assert.assertEquals(FeatureLogCollectionIndex.getInstance().size(), 0);

        // There should be zero scenario logs
        Assert.assertEquals(ScenarioLogCollectionIndex.getInstance().size(), 0);

    }
}
