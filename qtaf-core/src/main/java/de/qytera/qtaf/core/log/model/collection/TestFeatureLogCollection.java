package de.qytera.qtaf.core.log.model.collection;


import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;

import java.util.ArrayList;
import java.util.Map;

/**
 * Collection that holds all log messages from a specific test case class
 */
public class TestFeatureLogCollection {

    /**
     * Search index
     */
    private static final transient Map<Integer, TestFeatureLogCollection> index = FeatureLogCollectionIndex.getInstance();

    /**
     * Test feature unique ID
     */
    private final int featureId;

    /**
     * Test feature name
     */
    private String featureName = "";

    /**
     * Test feature name
     */
    private String featureDescription = "";

    /**
     * Page object
     */
    private TestFeature testFeatureAnnotation = null;

    /**
     * Holds a collection of test log collection instances
     */
    private final ArrayList<TestScenarioLogCollection> testScenarioLogCollection = new ArrayList<>();

    /**
     * Constructor
     *
     * @param featureId Collection ID
     */
    private TestFeatureLogCollection(int featureId, TestFeature testFeatureAnnotation) {
        this.featureId = featureId;
        this.featureName = testFeatureAnnotation.name();
        this.featureDescription = testFeatureAnnotation.description();
        this.testFeatureAnnotation = testFeatureAnnotation;
    }

    /**
     * Constructor
     *
     * @param featureId   Collection ID
     * @param featureName Collection Name
     */
    private TestFeatureLogCollection(int featureId, String featureName) {
        this.featureId = featureId;
        this.featureName = featureName;
    }

    /**
     * Override equals to compare two TestFeatureLogCollection objects
     *
     * @param o Object to compare with this instance
     * @return true if both are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof TestFeatureLogCollection)) {
            return false;
        }

        TestFeatureLogCollection c = (TestFeatureLogCollection) o;

        return this.getFeatureId() == c.getFeatureId();
    }

    /**
     * Calculate hash code for this instance
     * The contains() methods of the collections use the hash code to check if object is already stored.
     * @return  hash code
     */
    @Override
    public int hashCode() {
        return this.getFeatureId();
    }

    /**
     * Factory method
     * This method has to be synchronized so that only one thread at a time can execute this method
     *
     * @param featureId   Collection ID
     * @param featureName Collection Name
     */
    public static synchronized TestFeatureLogCollection createFeatureLogCollectionIfNotExists(
            int featureId,
            String featureName
    ) {
        QtafFactory.getLogger().debug(String.format("feature log index: size=%s", index.size()));

        if (index.get(featureId) != null) {
            return index.get(featureId);
        }

        TestFeatureLogCollection logCollection = new TestFeatureLogCollection(featureId, featureName);
        QtafFactory.getLogger().debug(String.format("Created feature log: id=%s, name=%s", featureId, featureName));
        index.put(featureId, logCollection);

        return logCollection;
    }

    /**
     * Factory method
     * This method has to be synchronized so that only one thread at a time can execute this method
     *
     * @param featureId             Collection ID
     * @param testFeatureAnnotation Test feature annotation
     */
    public static synchronized TestFeatureLogCollection createFeatureLogCollectionIfNotExists(
            int featureId,
            TestFeature testFeatureAnnotation
    ) {
        if (index.get(featureId) != null) {
            return index.get(featureId);
        }

        return new TestFeatureLogCollection(featureId, testFeatureAnnotation);
    }

    /**
     * Get collection ID
     *
     * @return Collection ID
     */
    public int getFeatureId() {
        return featureId;
    }

    /**
     * Get collection Name
     *
     * @return Collection Name
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Get collection Description
     *
     * @return Collection Description
     */
    public String getFeatureDescription() {
        return featureDescription;
    }

    /**
     * Get test step collections
     *
     * @return log collections
     */
    public ArrayList<TestScenarioLogCollection> getScenarioLogCollection() {
        return testScenarioLogCollection;
    }

    /**
     * Add new TestMethodLogCollection
     *
     * @param featureId    Unique test hash code
     * @param scenarioId    Method ID / Scenario name
     * @param scenarioName      Test ID / Feature name
     * @return  this
     */
    public TestScenarioLogCollection createScenarioIfNotExists(String featureId, String scenarioId, String scenarioName) {
        return createScenarioIfNotExists(featureId.hashCode(), scenarioId, scenarioName);
    }

    /**
     * Add new TestMethodLogCollection
     *
     * @param featureId    Unique test hash code
     * @param scenarioId    Method ID / Scenario name
     * @param scenarioName      Test ID / Feature name
     * @return  this
     */
    public TestScenarioLogCollection createScenarioIfNotExists(int featureId, String scenarioId, String scenarioName) {
        TestScenarioLogCollection testScenarioLogCollection = TestScenarioLogCollection
            .createTestScenarioLogCollection(
                featureId,
                scenarioId,
                scenarioName
        );

        // Check if log collection already has a scenario log collection with the given scenario ID
        if (!this.testScenarioLogCollection.contains(testScenarioLogCollection)) {
            this.testScenarioLogCollection.add(testScenarioLogCollection);
        }

        return testScenarioLogCollection;
    }

    /**
     * Add a scenario log collection if it not already exists
     * @param collection    scenario log collection
     * @return  scenario log collection
     */
    public TestScenarioLogCollection addScenarioLogCollection(TestScenarioLogCollection collection) {
        if (!this.testScenarioLogCollection.contains(collection)) {
            this.testScenarioLogCollection.add(collection);
        }

        return collection;
    }

    /**
     * Get testCaseAnnotation
     *
     * @return testCaseAnnotation
     */
    public TestFeature getTestCaseAnnotation() {
        return testFeatureAnnotation;
    }

    /**
     * Set testCaseAnnotation
     *
     * @param testFeatureAnnotation TestCaseAnnotation
     * @return this
     */
    public TestFeatureLogCollection setTestCaseAnnotation(TestFeature testFeatureAnnotation) {
        this.testFeatureAnnotation = testFeatureAnnotation;
        return this;
    }

    /**
     * Count the number of scenario log collections
     * @return  number of scenario log collections
     */
    public int countScenarioLogs() {
        return this.testScenarioLogCollection.size();
    }

    /**
     * Remove all elements from the collection.
     */
    public void clearCollection() {
        this.testScenarioLogCollection.clear();
    }

    /**
     * Check if feature log with given ID already exists
     * @param featureId Feature ID
     * @return  true if exists, false otherwise
     */
    public static boolean exists(int featureId) {
        return index.get(featureId) != null;
    }

    /**
     * Count the number of scenario log collections
     * @return  number of scenario log collections
     */
    public static int getIndexSize() {
        return index.size();
    }

    /**
     * Remove all scenario log collections
     */
    public static void clearIndex() {
        index.clear();
    }
}
