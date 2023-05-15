package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.xray.dto.XrayTestDto;
import de.qytera.qtaf.xray.dto.XrayTestDtoCollection;
import de.qytera.qtaf.xray.repository.XrayServerTestRepository;

import java.io.IOException;

/**
 * Class that imports Test definitions from Xray API and saves them locally
 */
public class XrayImporter {
    private XrayImporter() {}
    private static final XrayServerTestRepository repository = new XrayServerTestRepository();

    public static void importTestAsFeature(String testId, String featureFilePath) throws IOException {
        XrayTestDto testDto = repository.findByTestId(testId);
        createFeatureFileFromSingleTest(testDto, featureFilePath);
    }
    public static void createFeatureFileFromSingleTest(XrayTestDto testDto, String filePath) throws IOException {
        FileHelper.writeFile(filePath, getFeatureFileContentFromSingleTest(testDto));
    }

    public static void createFeatureFileFromTestSet(String testSetId, XrayTestDtoCollection collection, String filePath) throws IOException {
        FileHelper.writeFile(filePath, getFeatureFileContentFromTestSet(testSetId, collection, true));
    }

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

    public static String getFeatureFileContentFromSingleTest(XrayTestDto testDto) {
        return addFeatureDefinition(
                testDto.getKey(),
                addScenarioDefinition(testDto.getKey(), testDto.getDefinition())
        );
    }

    public static String addScenarioDefinition(String scenarioName, String testDefinition) {
        String featureFileContent = String.format("Scenario: %s\n  ", scenarioName);
        featureFileContent += testDefinition.replaceAll("\n", "\n  ");
        return featureFileContent;
    }

    public static String addFeatureDefinition(String featureName, String testDefinition) {
        String featureFileContent = String.format("Feature: %s\n  ", featureName);
        featureFileContent += testDefinition.replaceAll("\n", "\n  ");
        return featureFileContent;
    }

    public static String addTag(String key, String gherkin) {
        return String.format("@%s\n", key) + gherkin;
    }

    public static String addTag(String key, String value, String gherkin) {
        return String.format("@%s:%s\n", key, value) + gherkin;
    }

    public static String addGroupTagToFeatureDefinition(String featureName, String featureDefinition) {
        return addTag("Groups", featureName, featureDefinition);
    }
}
