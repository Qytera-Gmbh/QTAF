package de.qytera.qtaf.core.log.model.collection;


import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.index.FeatureLogCollectionIndex;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection that holds all log messages from a specific test case class
 */
public class TestFeatureLogCollection {

    /**
     * Search index
     */
    private static final transient FeatureLogCollectionIndex index = FeatureLogCollectionIndex.getInstance();

    /**
     * Test feature unique ID
     */
    private final String featureId;

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
    private final List<TestScenarioLogCollection> testScenarioLogCollection = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constructor
     *
     * @param featureId Collection ID
     */
    private TestFeatureLogCollection(String featureId, TestFeature testFeatureAnnotation) {
        this.featureId = featureId;
        this.featureName = testFeatureAnnotation.name();
        this.featureDescription = testFeatureAnnotation.description();
        this.testFeatureAnnotation = testFeatureAnnotation;
        QtafFactory.getLogger().debug(
                String.format(
                        "[FeatureLogCollection] Created feature log: id=%s, hash=%s, name=%s, description=%S",
                        featureId,
                        this.hashCode(),
                        featureName,
                        featureDescription
                )
        );
    }

    /**
     * Constructor
     *
     * @param featureId   Collection ID
     * @param featureName Collection Name
     */
    private TestFeatureLogCollection(String featureId, String featureName) {
        this.featureId = featureId;
        this.featureName = featureName;
        QtafFactory.getLogger().debug(
                String.format(
                        "[FeatureLogCollection] Created feature log: id=%s, hash=%s, name=%s",
                        featureId,
                        this.hashCode(),
                        featureName
                )
        );
    }

    /**
     * Factory method
     *
     * This method has to be synchronized so that only one thread at a time can execute this method
     *
     * @param featureId   Collection ID
     * @param featureName Collection Name
     */
    public static synchronized TestFeatureLogCollection createFeatureLogCollectionIfNotExists(
            String featureId,
            String featureName
    ) {
        QtafFactory.getLogger().debug(
                String.format(
                        "[FeatureLogCollection]  feature log index: size=%s, scenario log index: size=%s",
                        index.size(),
                        ScenarioLogCollectionIndex.getInstance().size())
        );

        if (index.get(featureId) != null) {
            return index.get(featureId);
        }

        TestFeatureLogCollection collection = new TestFeatureLogCollection(featureId, featureName);

        return index.put(featureId, collection);
    }

    /**
     * Factory method
     *
     * This method has to be synchronized so that only one thread at a time can execute this method
     *
     * @param featureId             Collection ID
     * @param testFeatureAnnotation Test feature annotation
     */
    public static synchronized TestFeatureLogCollection createFeatureLogCollectionIfNotExists(
            String featureId,
            TestFeature testFeatureAnnotation
    ) {
        if (index.get(featureId) != null) {
            return index.get(featureId);
        }

        TestFeatureLogCollection collection = new TestFeatureLogCollection(featureId, testFeatureAnnotation);

        return index.put(featureId, collection);
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
        if (!(o instanceof TestFeatureLogCollection c)) {
            return false;
        }

        return this.getFeatureId().equals(c.getFeatureId());
    }

    /**
     * Calculate hash code for this instance
     * The contains() methods of the collections use the hash code to check if object is already stored.
     * @return  hash code
     */
    @Override
    public int hashCode() {
        return this.getFeatureId().hashCode();
    }

    /**
     * Get collection ID
     *
     * @return Collection ID
     */
    public String getFeatureId() {
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
    public synchronized List<TestScenarioLogCollection> getScenarioLogCollection() {
        return testScenarioLogCollection;
    }

    /**
     * Group scenario logs by abstract scenario ID
     * @return  Map of grouped scenario logs
     */
    public synchronized Map<String, List<TestScenarioLogCollection>> getScenariosGroupedByAbstractScenarioId() {
        return Collections.synchronizedMap(testScenarioLogCollection
                .stream()
                .collect(Collectors.groupingBy(TestScenarioLogCollection::getAbstractScenarioId)));
    }

    /**
     * Add new Scenario Log Collection
     *
     * @param featureId    Unique test hash code
     * @param scenarioId    Method ID / Scenario name
     * @param scenarioName      Test ID / Feature name
     * @return  this
     */
    public synchronized TestScenarioLogCollection createScenarioIfNotExists(String featureId, String scenarioId, String scenarioName) {
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
    public synchronized TestScenarioLogCollection addScenarioLogCollection(TestScenarioLogCollection collection) {
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
    public TestFeature getTestFeatureAnnotation() {
        return testFeatureAnnotation;
    }

    /**
     * Set testCaseAnnotation
     *
     * @param testFeatureAnnotation TestCaseAnnotation
     * @return this
     */
    public TestFeatureLogCollection setTestFeatureAnnotation(TestFeature testFeatureAnnotation) {
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
    public static boolean exists(String featureId) {
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
