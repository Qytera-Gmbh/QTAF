package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.cucumber.helper.CucumberTestResultHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CucumberTestResultHelperTest {
    @Test
    public void testDidAllStepsPassPending() {
        Assert.assertFalse(
                CucumberTestResultHelper.didAllStepsPass(new SampleTestResultsPending()),
                "There are tests that are still pending in this list, so false is expected"
        );
    }

    @Test
    public void testDidAllStepsPassPassed() {
        Assert.assertTrue(
                CucumberTestResultHelper.didAllStepsPass(new SampleTestResultsPassed()),
                "All tests passed in this list, so true is expected"
        );
    }

    @Test
    public void testDidAllStepsPassFailed() {
        Assert.assertFalse(
                CucumberTestResultHelper.didAllStepsPass(new SampleTestResultsFailed()),
                "Some tests failed in this list, so false is expected"
        );
    }
}
