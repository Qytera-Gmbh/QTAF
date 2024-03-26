package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.DemoStepClass;
import de.qytera.qtaf.core.log.model.LogLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepInformationLogMessageTest {
    @Test
    public void testConstructor() throws NoSuchMethodException {
        DemoStepClass demoTest = new DemoStepClass();
        Step stepAnnotation = demoTest.getClass().getMethod("foo", String.class, int.class).getAnnotation(Step.class);
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.setStep(stepAnnotation);
        Assert.assertEquals(stepInformationLogMessage.getStep().getName(), stepAnnotation.name());
        Assert.assertEquals(stepInformationLogMessage.getStep().getDescription(), stepAnnotation.description());
    }

    @Test
    public void testSetStepName() throws NoSuchMethodException {
        DemoStepClass demoTest = new DemoStepClass();
        Step stepAnnotation = demoTest.getClass().getMethod("foo", String.class, int.class).getAnnotation(Step.class);
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.setStep(stepAnnotation);
        Assert.assertEquals(stepInformationLogMessage.getStep().getName(), "step one");
        stepInformationLogMessage.setStepName("bar");
        Assert.assertEquals(stepInformationLogMessage.getStep().getName(), "bar");
    }

    @Test
    public void testSetStepDescription() throws NoSuchMethodException {
        DemoStepClass demoTest = new DemoStepClass();
        Step stepAnnotation = demoTest.getClass().getMethod("foo", String.class, int.class).getAnnotation(Step.class);
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.setStep(stepAnnotation);
        Assert.assertEquals(stepInformationLogMessage.getStep().getDescription(), "this is step one");
        stepInformationLogMessage.setStepDescription("bar");
        Assert.assertEquals(stepInformationLogMessage.getStep().getDescription(), "bar");
    }

    @Test
    public void testAddParameter() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.addStepParameter("param1", "value1");
        Assert.assertEquals(stepInformationLogMessage.getStepParameters().size(), 1);
        Assert.assertEquals(stepInformationLogMessage.getStepParameters().get(0).getName(), "param1");
        Assert.assertEquals(stepInformationLogMessage.getStepParameters().get(0).getValue(), "value1");
        Assert.assertEquals(stepInformationLogMessage.getStepParameters().get(0).getType(), "String");
    }

    @Test
    public void testType() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        Assert.assertEquals(stepInformationLogMessage.getTYPE(), "STEP_LOG");
    }

    @Test
    public void testResults() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        String result = "result";
        stepInformationLogMessage.setResult(result);
        Assert.assertEquals(stepInformationLogMessage.getResult(), result);
    }

    @Test
    public void testError() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        Exception exception = new Exception("my error");
        Assert.assertFalse(stepInformationLogMessage.hasError());
        stepInformationLogMessage.setError(exception);
        Assert.assertTrue(stepInformationLogMessage.hasError());
        Assert.assertEquals(stepInformationLogMessage.getError().getMessage(), "my error");

    }

    @Test
    public void testGetMethodName() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        Assert.assertEquals(stepInformationLogMessage.getMethodName(), "method1");
    }

    @Test
    public void testDuration() throws ParseException {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stepInformationLogMessage.setStart(formatter.parse("2020-01-01 12:00:00"));
        stepInformationLogMessage.setEnd(formatter.parse("2020-01-01 12:03:40"));
        Assert.assertEquals(stepInformationLogMessage.getDuration(), 220000L);
    }

    @Test
    public void testScreenshotBefore() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.setScreenshotBefore("/my/file");
        Assert.assertEquals(stepInformationLogMessage.getScreenshotBefore(), "/my/file");
    }

    @Test
    public void testScreenshotAfter() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.setScreenshotAfter("/my/file");
        Assert.assertEquals(stepInformationLogMessage.getScreenshotAfter(), "/my/file");
    }

    @Test
    public void testStartDate() throws ParseException {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        stepInformationLogMessage.setStart(formatter.parse("2020-01-01"));
        Assert.assertEquals(stepInformationLogMessage.getStart().getTime(), formatter.parse("2020-01-01").getTime());
    }

    @Test
    public void testEndDate() throws ParseException {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        stepInformationLogMessage.setEnd(formatter.parse("2020-02-03"));
        Assert.assertEquals(stepInformationLogMessage.getEnd().getTime(), formatter.parse("2020-02-03").getTime());
    }

    @Test
    public void testStatus() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        stepInformationLogMessage.setStatus(StepInformationLogMessage.Status.PASSED);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PASSED);
        stepInformationLogMessage.setStatus(StepInformationLogMessage.Status.FAILED);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.FAILED);
        stepInformationLogMessage.setStatus(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PENDING);
        stepInformationLogMessage.setStatus(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.SKIPPED);
        /*
        stepInformationLogMessage.setStatus(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.UNDEFINED);
         */
    }

    @Test
    public void testStatusComputation() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PENDING);
        stepInformationLogMessage.computeStatus();
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PASSED);
        Exception exception = new Exception("my error");
        stepInformationLogMessage.setError(exception);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.FAILED);
    }

    @Test
    public void testStatusComputationWithAssertions() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PENDING);
        stepInformationLogMessage.computeStatus();
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PASSED);
        AssertionLogMessage assertion1 = new AssertionLogMessage(LogLevel.INFO, "a1").setStatusToPassed();
        stepInformationLogMessage.addAssertion(assertion1);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.PASSED);
        AssertionLogMessage assertion2 = new AssertionLogMessage(LogLevel.INFO, "a1").setStatusToFailed();
        stepInformationLogMessage.addAssertion(assertion2);
        Assert.assertEquals(stepInformationLogMessage.getStatus(), StepInformationLogMessage.Status.FAILED);
    }

    @Test
    public void testSetAssertions() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        AssertionLogMessage assertion1 = new AssertionLogMessage(LogLevel.INFO, "a1").setStatusToPassed();
        AssertionLogMessage assertion2 = new AssertionLogMessage(LogLevel.INFO, "a1").setStatusToFailed();
        Assert.assertEquals(stepInformationLogMessage.getAssertions().size(), 0);
        stepInformationLogMessage.setAssertions(List.of(assertion1, assertion2));
        Assert.assertEquals(stepInformationLogMessage.getAssertions().size(), 2);
    }

    @Test
    public void testSetMethodName() {
        StepInformationLogMessage stepInformationLogMessage = new StepInformationLogMessage("method1", "step one was executed");
        Assert.assertEquals(stepInformationLogMessage.getMethodName(), "method1");
        stepInformationLogMessage.setMethodName("new method name");
        Assert.assertEquals(stepInformationLogMessage.getMethodName(), "new method name");
    }

    @Test
    public void testAddingErrorsToStepsShouldMakeStepFail() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        // Before adding the error to the step the status of the step should be pending
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
        // Add an error object to the step
        Throwable error = new Throwable();
        step.setError(error);
        // Now the step should have failed
        Assert.assertFalse(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertTrue(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
    }

    @Test
    public void testNoErrorsShouldLeadToPassedSteps() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        // Before adding the error to the step the status of the step should be pending
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
        // If we set an end date the status gets computed. In this case the test step should have passed.
        step.setEnd(new Date());
        // Now the step should have passed
        Assert.assertFalse(step.isPending());
        Assert.assertTrue(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
    }

    @Test
    public void testFailedAssertionsShouldMakeTestStepFail() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
        // Add new failed assertion log message to step
        AssertionLogMessage assertion = new AssertionLogMessage(LogLevel.INFO, "myMessage").setStatusToFailed();
        step.addAssertion(assertion);
        // Now the step should have failed
        Assert.assertFalse(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertTrue(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
        // Adding another successful assertion should not change the step status
        AssertionLogMessage assertion2 = new AssertionLogMessage(LogLevel.INFO, "myMessage").setStatusToPassed();
        step.addAssertion(assertion2);
        Assert.assertFalse(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertTrue(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
    }

    @Test
    public void testPassedAssertionsShouldMakeTestStepPass() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
        // Add new failed assertion log message to step
        AssertionLogMessage assertionPassed = new AssertionLogMessage(LogLevel.INFO, "myMessage").setStatusToPassed();
        step.addAssertion(assertionPassed);
        // Now the step should still be pending
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
        // If we set an end date the status gets computed. In this case the test step should have passed.
        step.setEnd(new Date());
        Assert.assertFalse(step.isPending());
        Assert.assertTrue(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        Assert.assertFalse(step.isSkipped());
    }
}