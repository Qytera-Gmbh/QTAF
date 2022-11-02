package de.qytera.qtaf.testng;

import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the TestNGHelper class
 */
public class TestNGHelperTest {
    @Test
    public void testGetTestClassId() {
        SampleTestResult testResult = new SampleTestResult();
        Assert.assertEquals(
                TestResultHelper.getTestClassId(testResult),
                "de.qytera.qtaf.testng.SampleRealTestClass",
                "Expect that the test class name gets extracted from ITestresult object correctly"
        );
    }

    @Test
    public void testGetTestMethodId() {
        SampleTestResult testResult = new SampleTestResult();
        Assert.assertEquals(
                TestResultHelper.getTestMethodId(testResult),
                "de.qytera.qtaf.testng.SampleRealTestClass.sampleTestNGMethodName",
                "Expect that the test method name gets extracted from ITestresult object correctly"
        );
    }
}
