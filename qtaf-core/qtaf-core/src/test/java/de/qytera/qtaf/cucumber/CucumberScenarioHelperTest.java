package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.cucumber.helper.CucumberScenarioHelper;
import io.cucumber.java.Scenario;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Test the CucumberTestCaseStateHelper helper class
 */
public class CucumberScenarioHelperTest {
    @Test
    public void testGetTestCaseState() {
        Scenario scenario = ScenarioHelper.getScenario();
        Assert.assertTrue(
                CucumberScenarioHelper.getTestCaseState(scenario) instanceof SampleTestCaseState,
                "Assert that the TestCaseState object gets extracted correctly from the scenario object"
        );
    }

    @Test
    public void testGetTestCaseScenarioIdFromTag() {
        Scenario scenario = ScenarioHelper.getScenario();
        Assert.assertEquals(
                CucumberScenarioHelper.getScenarioIdFromTag(scenario),
                "SampleTestName-1",
                "Assert that the scenario ID gets extracted correctly from the scenario object"
        );
    }

    @Test
    public void testGetTagMetaData() {
        Scenario scenario = ScenarioHelper.getScenario();
        Map<String, String> tagMetaData = CucumberScenarioHelper.getTagMetaData(scenario);

        Assert.assertNotNull(
                tagMetaData,
                "Assert tag meta data to be not null"
        );

        Assert.assertEquals(
                tagMetaData.get("TestName"),
                "SampleTestName-1",
                "Assert that the scenario ID gets extracted correctly from the scenario object"
        );
    }
}
