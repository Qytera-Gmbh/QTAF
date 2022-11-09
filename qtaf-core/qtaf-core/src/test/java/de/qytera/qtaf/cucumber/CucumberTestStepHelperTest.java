package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.cucumber.helper.CucumberTestStepHelper;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CucumberTestStepHelperTest {
    @Test
    public void testGetPickleStepTestSteps() {
        SampleTestSteps testSteps = new SampleTestSteps();

        List<PickleStepTestStep> pickleStepTestSteps = CucumberTestStepHelper
                .getPickleStepTestSteps(testSteps);

        Assert.assertEquals(
                pickleStepTestSteps.size(),
                3,
                "Assert that list contains three instances of PickleStepTestStep"
        );
    }

    @Test
    public void testGetTestStepPositions() {
        SampleTestSteps testSteps = new SampleTestSteps();

        List<Integer> pickleStepTestSteps = CucumberTestStepHelper
                .getTestStepPositions(testSteps);

        Assert.assertEquals(
                pickleStepTestSteps.size(),
                3,
                "Assert that list contains three instances of PickleStepTestStep"
        );

        Assert.assertEquals(
                pickleStepTestSteps.get(0),
                Integer.valueOf(1),
                "Assert that first PickleStepTestStep object is at index 1"
        );

        Assert.assertEquals(
                pickleStepTestSteps.get(1),
                Integer.valueOf(3),
                "Assert that first PickleStepTestStep object is at index 3"
        );

        Assert.assertEquals(
                pickleStepTestSteps.get(2),
                Integer.valueOf(4),
                "Assert that first PickleStepTestStep object is at index 4"
        );
    }

    @Test
    public void testGetTestStepPosition() {
        SampleTestSteps testSteps = new SampleTestSteps();

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(
                    CucumberTestStepHelper.getTestStepPosition(testSteps, testSteps.get(i)),
                    i,
                    "Assert that first PickleStepTestStep object is at index " + i
            );
        }
    }
}
