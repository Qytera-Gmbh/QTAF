package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.FeatureLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSuiteLogCollectionTest {
    /**
     * Assert that clearing of the TestSuiteLogCollection works
     */
    @Test
    public void testClearCollection() {
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clearCollection();

        Assert.assertEquals(suiteLogCollection.getDriverName(), "none");
        Assert.assertEquals(suiteLogCollection.getDuration(), 0);
        Assert.assertNull(suiteLogCollection.getSuiteInfo().getName());
        Assert.assertNull(suiteLogCollection.getSuiteInfo().getOutputDir());

    }

    /**
     * Test that hte suite log collection gets created
     */
    @Test
    public void testSuiteLogCollectionCreation() {
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();
        Assert.assertNotNull(logCollection);
    }

    /**
     * Test that the suite log collection gets created
     */
    @Test
    public void testCreateFeatureCollection() {
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();
        logCollection.clearCollection();
        FeatureLogCollectionIndex.getInstance().clear();

        // No feature log collections were added, so count should be zero
        Assert.assertEquals(logCollection.countFeatureLogs(), 0);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);

        TestFeatureLogCollection testFeatureLogCollection = logCollection.createFeatureIfNotExists("feature1", "Feature 1");

        // New feature log collection was added, so count should be one
        Assert.assertEquals(logCollection.countFeatureLogs(), 1);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 1);

        TestFeatureLogCollection testFeatureLogCollection2 = logCollection.createFeatureIfNotExists(
                "feature1",
                "Feature 1"
        );

        // New feature log collection with same ID was added, so count should still be one
        // and returned feature log collection should be identical to previous one
        Assert.assertEquals(logCollection.countFeatureLogs(), 1);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 1);
        Assert.assertEquals(testFeatureLogCollection.hashCode(), testFeatureLogCollection2.hashCode());

        // New feature log collection with new ID was added, so count should be two
        TestFeatureLogCollection testFeatureLogCollection3 = logCollection.createFeatureIfNotExists("feature2", "Feature 2");
        Assert.assertEquals(logCollection.countFeatureLogs(), 2);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 2);
        Assert.assertNotEquals(testFeatureLogCollection3.hashCode(), testFeatureLogCollection.hashCode());
        Assert.assertNotEquals(testFeatureLogCollection3.hashCode(), testFeatureLogCollection2.hashCode());

        logCollection.clearCollection();
        FeatureLogCollectionIndex.getInstance().clear();
        Assert.assertEquals(logCollection.countFeatureLogs(), 0);
        Assert.assertEquals(TestFeatureLogCollection.getIndexSize(), 0);
    }

    /**
     * Test if tag can be added and then be read
     */
    @Test
    public void testAddAndRemoveTag() {
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();
        Assert.assertNull(logCollection.getTag("foo"));

        logCollection.addTag("foo", "bar");
        Assert.assertEquals(logCollection.getTag("foo"), "bar");

        logCollection.removeTag("foo");
        Assert.assertNull(logCollection.getTag("foo"));
    }

    /**
     * Test if tag can be added and if tags get removed when log collection is cleared
     */
    @Test
    public void testAddAndClearTags() {
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();
        Assert.assertNull(logCollection.getTag("foo"));

        logCollection.addTag("foo", "bar");
        Assert.assertEquals(logCollection.getTag("foo"), "bar");

        logCollection.clear();
        Assert.assertNull(logCollection.getTag("foo"));
    }
}
