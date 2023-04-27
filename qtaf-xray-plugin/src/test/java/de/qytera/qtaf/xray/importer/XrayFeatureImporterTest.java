package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.XrayTestDto;
import de.qytera.qtaf.xray.dto.XrayTestDtoCollection;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class XrayFeatureImporterTest {
    @BeforeMethod
    public void setXrayPlatform() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
    }

    @Test
    public void testScenarioTextCreation() {
        String scenarioDefinition = "When I am late\nThen I have to work longer";
        String expectedFeatureFileContent = "Scenario: QTAF-1\n  When I am late\n  Then I have to work longer";

        String result = XrayImporter.addScenarioDefinition(
                "QTAF-1",
                scenarioDefinition
        );

        Assert.assertEquals(expectedFeatureFileContent, result);
    }

    @Test
    public void testFeatureTextCreation() {
        String scenarioDefinition = "Scenario: QTAF-1\n  When I am late\n  Then I have to work longer";
        String expectedFeatureFileContent = "Feature: QTAF-100\n  Scenario: QTAF-1\n    When I am late\n    Then I have to work longer";

        String result = XrayImporter.addFeatureDefinition(
                "QTAF-100",
                scenarioDefinition
        );

        Assert.assertEquals(expectedFeatureFileContent, result);
    }

    @Test
    public void testFeatureFileContentFromSingleTest() {
        String scenarioDefinition = "When I am late\nThen I have to work longer";
        String expectedFeatureFileContent = "Feature: QTAF-1\n  Scenario: QTAF-1\n    When I am late\n    Then I have to work longer";

        XrayTestDto testDto = new XrayTestDto().setKey("QTAF-1").setDefinition(scenarioDefinition);
        String result = XrayImporter.getFeatureFileContentFromSingleTest(testDto);

        Assert.assertEquals(expectedFeatureFileContent, result);
    }

    @Test
    public void testFeatureFileContentFromTestSet() {
        String scenarioDefinition1 = "When I am late\nThen I have to work longer";
        String scenarioDefinition2 = "When I am early\nThen I have to work shorter";
        String expectedFeatureFileContent = "Feature: QTAF-100\n  \n" +
                "  Scenario: QTAF-1\n    When I am late\n    Then I have to work longer\n" +
                "  Scenario: QTAF-2\n    When I am early\n    Then I have to work shorter";

        XrayTestDto testDto1 = new XrayTestDto().setKey("QTAF-1").setDefinition(scenarioDefinition1);
        XrayTestDto testDto2 = new XrayTestDto().setKey("QTAF-2").setDefinition(scenarioDefinition2);

        XrayTestDtoCollection collection = new XrayTestDtoCollection();
        collection.add(testDto1);
        collection.add(testDto2);

        String result = XrayImporter.getFeatureFileContentFromTestSet("QTAF-100", collection, false);

        Assert.assertEquals(expectedFeatureFileContent, result);
    }

    @Test
    public void testAddTag() {
        String key = "Name";
        String value = "Foo";
        String gherkin = "Feature: QTAF-123";
        String expectedResult = "@Name:Foo\nFeature: QTAF-123";
        String realResult = XrayImporter.addTag(key, value, gherkin);
        Assert.assertEquals(expectedResult, realResult);
    }

    @Test
    public void testGroupTagAddition() {
        String featureName = "QTAF-123";
        String featureDefinition = "Feature: QTAF-123";
        String expectedResult = "@Groups:QTAF-123\nFeature: QTAF-123";
        String realResult = XrayImporter.addGroupTagToFeatureDefinition(featureName, featureDefinition);
        Assert.assertEquals(expectedResult, realResult);
    }

    @Test
    public void testFeatureFileCreationFromSingleTest() throws IOException {
        String filePath = "$USER_DIR/src/test/resources/scenario1.feature";
        String scenarioDefinition = "When I am late\nThen I have to work longer";
        String expectedFeatureFileContent = "Feature: QTAF-1\n  Scenario: QTAF-1\n    When I am late\n    Then I have to work longer";

        Assert.assertFalse(FileHelper.exists(filePath));

        XrayTestDto testDto = new XrayTestDto().setKey("QTAF-1").setDefinition(scenarioDefinition);
        XrayImporter.createFeatureFileFromSingleTest(testDto, filePath);

        Assert.assertTrue(FileHelper.exists(filePath));
        Assert.assertEquals(FileHelper.getFileContentAsUTF8String(filePath), expectedFeatureFileContent);
        FileHelper.delete(filePath);
        Assert.assertFalse(FileHelper.exists(filePath));
    }

    @Test
    public void testFeatureFileCreationFromTestSet() throws IOException {
        String filePath = "$USER_DIR/src/test/resources/scenario2.feature";
        String scenarioDefinition1 = "When I am late\nThen I have to work longer";
        String scenarioDefinition2 = "When I am early\nThen I have to work shorter";
        String expectedFeatureFileContent = "Feature: QTAF-100\n  @newDriver\n  \n" +
                "  Scenario: QTAF-1\n    When I am late\n    Then I have to work longer\n" +
                "  Scenario: QTAF-2\n    When I am early\n    Then I have to work shorter";

        Assert.assertFalse(FileHelper.exists(filePath));

        XrayTestDto testDto1 = new XrayTestDto().setKey("QTAF-1").setDefinition(scenarioDefinition1);
        XrayTestDto testDto2 = new XrayTestDto().setKey("QTAF-2").setDefinition(scenarioDefinition2);

        XrayTestDtoCollection collection = new XrayTestDtoCollection();
        collection.add(testDto1);
        collection.add(testDto2);

        XrayImporter.createFeatureFileFromTestSet("QTAF-100", collection, filePath);

        Assert.assertTrue(FileHelper.exists(filePath));
        Assert.assertEquals(FileHelper.getFileContentAsUTF8String(filePath), expectedFeatureFileContent);
        FileHelper.delete(filePath);
        Assert.assertFalse(FileHelper.exists(filePath));
    }
}
