package de.qytera.qtaf.testng;

import de.qytera.qtaf.testng.events.payload.TestNGTestEventPayload;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestNGEventPayloadTest {
    @Test
    public void testPayload() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Equality check
        Assert.assertEquals(iTestResult, payload.getOriginalEvent());
    }

    @Test
    public void testFeatureId() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Get feature id from class hash code
        Assert.assertEquals(
                payload.getFeatureId(),
                iTestResult.getTestClass().getRealClass().getName()
        );

    }

    @Test
    public void testFeatureName() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Get feature name from class annotation
        Assert.assertEquals(
                payload.getFeatureName(),
                "sample-test-feature-annotation-name"
        );

    }

    @Test
    public void testFeatureDescription() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Get feature description from class annotation
        Assert.assertEquals(
                payload.getFeatureDescription(),
                "sample-test-feature-annotation-description"
        );
    }

    @Test
    public void testScenarioID() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Scenario ID
        Assert.assertEquals(
                payload.getAbstractScenarioId(),
                "de.qytera.qtaf.testng.SampleRealTestClass.sampleTestNGMethodName"
        );

    }

    @Test
    public void testScenarioName() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Scenario name from Test annotation
        Assert.assertEquals(
                payload.getScenarioName(),
                "sample-test-method-annotation-name"
        );
    }

    @Test
    public void testScenarioParameterTypes() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Expect two scenario parameters
        Assert.assertEquals(
                payload.getScenarioParameters().length,
                2
        );

        // Expect first parameter to be String
        Assert.assertEquals(
                payload.getScenarioParameters()[0].getType().getName(),
                "java.lang.String"
        );

        // Expect second parameter to be integer
        Assert.assertEquals(
                payload.getScenarioParameters()[1].getType().getName(),
                "java.lang.Integer"
        );

    }

    @Test
    public void testScenarioParameterValues() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Expect first parameter value to be ""
        Assert.assertEquals(
                payload.getParameterValues()[0],
                "my-parameter-1"
        );

        // Expect first parameter value to be ""
        Assert.assertEquals(
                payload.getParameterValues()[1],
                2000
        );
    }

    @Test
    public void testScenarioStart() throws NoSuchMethodException, ParseException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Test scenario start
        Assert.assertEquals(
                payload.getScenarioStart().getTime(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse("2020-01-01 12:00:00")
                        .getTime()
        );
    }

    @Test
    public void testScenarioEnd() throws NoSuchMethodException, ParseException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Test scenario start
        Assert.assertEquals(
                payload.getScenarioEnd().getTime(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse("2020-01-01 12:05:00")
                        .getTime()
        );
    }

    @Test
    public void testScenarioGroups() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Test scenario groups
        Assert.assertEquals(
                payload.getGroupDependencies().length,
                2
        );

        // Test first group name
        Assert.assertEquals(
                payload.getGroupDependencies()[0],
                "dependent-group-1"
        );

        // Test second group name
        Assert.assertEquals(
                payload.getGroupDependencies()[1],
                "dependent-group-2"
        );
    }

    @Test
    public void testMethodDependencies() throws NoSuchMethodException {
        ITestResult iTestResult = new SampleTestResult();
        TestNGTestEventPayload payload = new TestNGTestEventPayload(iTestResult);

        // Test scenario groups
        Assert.assertEquals(
                payload.getMethodDependencies().length,
                2
        );

        // Test first method name
        Assert.assertEquals(
                payload.getMethodDependencies()[0],
                "dependent-method-1"
        );

        // Test second method name
        Assert.assertEquals(
                payload.getMethodDependencies()[1],
                "dependent-method-2"
        );
    }
}
