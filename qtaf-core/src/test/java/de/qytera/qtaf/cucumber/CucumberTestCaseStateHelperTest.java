package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.cucumber.helper.CucumberTestCaseStateHelper;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test the CucumberTestCaseStateHelper helper class
 */
public class CucumberTestCaseStateHelperTest {
    @Test
    public void testGetTestCase() {
        Scenario scenario = ScenarioHelper.getScenario();
        SampleTestCaseState state = new SampleTestCaseState();
        Assert.assertTrue(
                CucumberTestCaseStateHelper.getTestCase(state) instanceof SampleTestCase,
                "Assert that the testCase object gets extracted correctly from the state object"
        );
    }

    @Test
    public void testGetStepResults() {
        Scenario scenario = ScenarioHelper.getScenario();
        SampleTestCaseState state = new SampleTestCaseState();
        List<Result> results = CucumberTestCaseStateHelper.getStepResults(state);

        Assert.assertEquals(
                results.size(),
                1,
                "Assert that the test result list gets extracted correctly from the state object"
        );
    }
}
