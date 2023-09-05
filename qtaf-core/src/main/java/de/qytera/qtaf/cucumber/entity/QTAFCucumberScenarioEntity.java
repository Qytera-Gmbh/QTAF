package de.qytera.qtaf.cucumber.entity;

import de.qytera.qtaf.core.util.TokenSeparatedStringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Entity class that holds information about a scenario.
 */
public class QTAFCucumberScenarioEntity {
    private static final String GROUPS_TAG_NAME = "Groups";
    private static final String TEST_SET_TAG_NAME = "TestSet";

    private String featureName = "";
    private Map<String, String> featureTags = new HashMap<>();
    private String scenarioName = "";
    private Map<String, List<String>> scenarioTags = new HashMap<>();
    private List<String> groupNames = new ArrayList<>();
    private List<String> testSets = new ArrayList<>();

    /**
     * Get featureName.
     *
     * @return featureName
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Set featureName.
     *
     * @param featureName FeatureName
     * @return this
     */
    public QTAFCucumberScenarioEntity setFeatureName(String featureName) {
        this.featureName = featureName;
        return this;
    }

    /**
     * Get feature Tag.
     *
     * @param key Tag key
     * @return feature Tag value
     */
    public String getFeatureTag(String key) {
        return featureTags.get(key);
    }

    /**
     * Get featureTags.
     *
     * @return featureTags
     */
    public Map<String, String> getFeatureTags() {
        return featureTags;
    }

    /**
     * Check if feature tag exists.
     *
     * @param key Tag key
     * @return true if feature tag exists, false otherwise
     */
    public boolean hasFeatureTag(String key) {
        return this.featureTags.get(key) != null;
    }

    /**
     * Add feature tag.
     *
     * @param key   Tag key
     * @param value Tag value
     */
    public void addFeatureTag(String key, String value) {
        this.featureTags.put(key, value);
    }

    /**
     * Set featureTags.
     *
     * @param featureTags FeatureTags
     * @return this
     */
    public QTAFCucumberScenarioEntity setFeatureTags(Map<String, String> featureTags) {
        this.featureTags = featureTags;
        return this;
    }

    /**
     * Remove feature tag.
     *
     * @param key Tag key
     */
    public void removeFeatureTag(String key) {
        this.featureTags.remove(key);
    }

    /**
     * Get scenarioName.
     *
     * @return scenarioName
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Set scenarioName.
     *
     * @param scenarioName ScenarioName
     * @return this
     */
    public QTAFCucumberScenarioEntity setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
        return this;
    }

    /**
     * Get scenario Tag.
     *
     * @param key Tag key
     * @return scenario Tag value
     */
    public List<String> getScenarioTag(String key) {
        return scenarioTags.get(key);
    }

    /**
     * Get scenarioTags.
     *
     * @return scenarioTags
     */
    public Map<String, List<String>> getScenarioTags() {
        return scenarioTags;
    }

    /**
     * Check if scenario tag exists.
     *
     * @param key Tag key
     * @return true if scenario tag exists, false otherwise
     */
    public boolean hasScenarioTag(String key) {
        return this.scenarioTags.get(key) != null;
    }

    /**
     * Add scenario tag.
     *
     * @param key   Tag key
     * @param value Tag value
     */
    public void addScenarioTag(String key, String value) {
        this.addScenarioTag(
                key,
                (ArrayList<String>) Stream.of(value).collect(Collectors.toCollection(ArrayList::new))
        );
    }

    /**
     * Add scenario tag.
     *
     * @param key    Tag key
     * @param values List of values
     */
    public void addScenarioTag(String key, List<String> values) {
        if (this.scenarioTags.get(key) != null) { // Key already exists
            // Update list of values by appending new list to the end of existing list
            this.scenarioTags.get(key).addAll(values);
        } else { // Key does not exist
            // Create new key and add list of values for this key
            this.scenarioTags.put(key, values);
        }
    }

    /**
     * Set scenarioTags.
     *
     * @param scenarioTags ScenarioTags
     * @return this
     */
    public QTAFCucumberScenarioEntity setScenarioTags(Map<String, List<String>> scenarioTags) {
        this.scenarioTags = scenarioTags;
        return this;
    }

    /**
     * Remove scenario tag.
     *
     * @param key Tag key
     */
    public void removeScenarioTag(String key) {
        this.scenarioTags.remove(key);
    }

    /**
     * Get groupNames.
     *
     * @return groupNames
     */
    public List<String> getGroupNames() {
        return groupNames;
    }

    /**
     * Add group name.
     *
     * @param groupName Name of group
     */
    public void addGroupName(String groupName) {
        this.groupNames.add(groupName);
    }

    /**
     * Set groupNames.
     *
     * @param groupNames GroupNames
     */
    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    /**
     * Check if scenario entity has group name.
     *
     * @param groupName Group name
     * @return true if entity has group name, false otherwise
     */
    public boolean hasGroupName(String groupName) {
        return this.groupNames.contains(groupName);
    }

    /**
     * Check if scenario entity has all of the given group names.
     *
     * @param names List of group names
     * @return true if entity has all of the given group names, false otherwise
     */
    public boolean hasAllGroupNames(List<String> names) {
        for (String name : names) {
            if (!this.hasGroupName(name))
                return false;
        }

        return true;
    }

    /**
     * Check if scenario entity has any of the given group names.
     *
     * @param names List of group names
     * @return true if entity has at least one of the given group names, false otherwise
     */
    public boolean hasAnyGroupName(List<String> names) {
        for (String name : names) {
            if (this.hasGroupName(name))
                return true;
        }

        return false;
    }

    /**
     * Get test sets.
     *
     * @return List of test sets
     */
    public List<String> getTestSets() {
        return testSets;
    }

    /**
     * Add test set.
     *
     * @param testSet Name of test set
     */
    public void addTestSet(String testSet) {
        this.testSets.add(testSet);
    }

    /**
     * Set test sets.
     *
     * @param testSets List of test sets
     * @return this
     */
    public QTAFCucumberScenarioEntity setTestSet(List<String> testSets) {
        this.testSets = testSets;
        return this;
    }

    /**
     * Remove test set.
     *
     * @param testSet test set
     */
    public void removeTestSet(String testSet) {
        this.testSets.remove(testSet);
    }

    /**
     * Check if scenario entity belongs to test set.
     *
     * @param testSet test set name
     * @return true if entity belongs to test set, false otherwise
     */
    public boolean belongsToTestSet(String testSet) {
        return this.testSets.contains(testSet);
    }

    /**
     * Check if scenario entity belongs to any of the given test sets.
     *
     * @param testSets List of test sets
     * @return true if entity belongs at least to one of the given test sets, false otherwise
     */
    public boolean belongsToAnyTestSet(List<String> testSets) {
        for (String name : testSets) {
            if (this.belongsToTestSet(name))
                return true;
        }

        return false;
    }

    /**
     * Check if scenario entity belongs to all of the given test sets.
     *
     * @param names List of test sets
     * @return true if entity belongs to all of the given test sets, false otherwise
     */
    public boolean belongsToAllTestSets(List<String> names) {
        for (String name : names) {
            if (!this.belongsToTestSet(name))
                return false;
        }

        return true;
    }

    /**
     * Remove group name.
     *
     * @param groupName Group name
     */
    public void removeGroupName(String groupName) {
        this.groupNames.remove(groupName);
    }

    /**
     * Parse group tag.
     *
     * @return this
     */
    public QTAFCucumberScenarioEntity parseGroupTags() {
        List<String> tagGroupsStrings = this.getScenarioTags().get(GROUPS_TAG_NAME);

        if (tagGroupsStrings == null) { // There is nothing to do if there are no tags of interest
            return this;
        }

        // Iterate over all @Group tags and parse their values
        for (String value : tagGroupsStrings) {
            this.groupNames.addAll(TokenSeparatedStringHelper.toList(value, ",", true));
        }

        return this;
    }

    /**
     * Parse test set tag.
     */
    public void parseTestSetTags() {
        List<String> testSetTagValues = this.getScenarioTags().get(TEST_SET_TAG_NAME);

        if (testSetTagValues == null) { // There is nothing to do if there are no tags of interest
            return;
        }

        // Iterate over all @TestSet tags and parse their values
        for (String value : testSetTagValues) {
            this.testSets.addAll(TokenSeparatedStringHelper.toList(value, ",", true));
        }

    }
}
