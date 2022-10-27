package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.cucumber.helper.CucumberFeatureHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CucumberFeatureHelper Tests
 */
public class CucumberFeatureHelperTest {
    @Test
    public void testGetRelativeFeatureFilePath() {
        String userDir = System.getProperty("user.dir").replace("\\", "/");
        String input1 = "file:///" + userDir + "/src/java/features/my-feature-1";
        String input2 = "file:///" + userDir + "/src/test/features/my-feature-2";

        Assert.assertEquals(
                CucumberFeatureHelper.getRelativeFeatureFilePath(input1),
                "features/my-feature-1",
                "Assert that the relative feature file path gets extracted correctly"
        );

        Assert.assertEquals(
                CucumberFeatureHelper.getRelativeFeatureFilePath(input2),
                "features/my-feature-2",
                "Assert that the relative feature file path gets extracted correctly"
        );
    }
}
