package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.xray.dto.XrayTestDto;
import de.qytera.qtaf.xray.dto.XrayTestDtoCollection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Class that imports Test definitions from Xray API and saves them locally
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XrayImporter {

    /**
     * Create a feature file from a single test.
     *
     * @param testDto  the test
     * @param filePath the feature file path
     * @throws IOException if the feature file cannot be created
     */
    public static void createFeatureFileFromSingleTest(XrayTestDto testDto, String filePath) throws IOException {
        FileHelper.writeFile(filePath, getFeatureFileContentFromSingleTest(testDto));
    }

    /**
     * Create a feature file from a test set.
     *
     * @param testSetId  the test set ID
     * @param collection the test collection
     * @param filePath   the feature file path
     * @throws IOException if the feature file cannot be created
     */
    public static void createFeatureFileFromTestSet(String testSetId, XrayTestDtoCollection collection, String filePath) throws IOException {
        FileHelper.writeFile(filePath, getFeatureFileContentFromTestSet(testSetId, collection, true));
    }

    /**
     * Get the content of a feature file corresponding to the collection of tests.
     *
     * @param testSetId         the test set ID
     * @param testDtoCollection the test collection
     * @param newDriver         the feature file path
     * @return the content
     */
    public static String getFeatureFileContentFromTestSet(String testSetId, XrayTestDtoCollection testDtoCollection, boolean newDriver) {
        StringBuilder fileContent = new StringBuilder();

        for (XrayTestDto testDto : testDtoCollection) {
            fileContent.append("\n").append(addScenarioDefinition(testDto.getKey(), testDto.getDefinition()));
        }

        if (newDriver) {
            fileContent.insert(0, addTag("newDriver", ""));
        }

        return addFeatureDefinition(testSetId, fileContent.toString());
    }

    /**
     * Get the content of a feature file corresponding to the test.
     *
     * @param testDto the test
     * @return the content
     */
    public static String getFeatureFileContentFromSingleTest(XrayTestDto testDto) {
        return addFeatureDefinition(
                testDto.getKey(),
                addScenarioDefinition(testDto.getKey(), testDto.getDefinition())
        );
    }

    /**
     * Add a scenario definition to the content of a feature file.
     *
     * @param scenarioName   the scenario name
     * @param testDefinition the test definition
     * @return the content
     */
    public static String addScenarioDefinition(String scenarioName, String testDefinition) {
        String featureFileContent = String.format("Scenario: %s%n  ", scenarioName);
        featureFileContent += testDefinition.replace("\n", "\n  ");
        return featureFileContent;
    }

    /**
     * Add a feature definition to the content of a feature file.
     *
     * @param featureName    the feature name
     * @param testDefinition the feature definition
     * @return the content
     */
    public static String addFeatureDefinition(String featureName, String testDefinition) {
        String featureFileContent = String.format("Feature: %s%n  ", featureName);
        featureFileContent += testDefinition.replace("\n", "\n  ");
        return featureFileContent;
    }

    /**
     * Add a tag to feature file content.
     *
     * @param key     the tag key
     * @param gherkin the gherkin element
     * @return the tagged content
     */
    public static String addTag(String key, String gherkin) {
        return String.format("@%s%n", key) + gherkin;
    }

    /**
     * Add a tag with a value to feature file content.
     *
     * @param key     the tag key
     * @param value   the tag value
     * @param gherkin the gherkin element
     * @return the tagged content
     */
    public static String addTag(String key, String value, String gherkin) {
        return String.format("@%s:%s%n", key, value) + gherkin;
    }

    /**
     * Add a group tag to feature file content.
     *
     * @param featureName       the feature name
     * @param featureDefinition the feature definition
     * @return the group content
     */
    public static String addGroupTagToFeatureDefinition(String featureName, String featureDefinition) {
        return addTag("Groups", featureName, featureDefinition);
    }
}
