package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

public class StepInformationLogMessageTest {
    @Test
    public void testAddingErrorsToStepsShouldMakeStepFail() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        // Before adding the error to the step the status of the step should be pending
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        // Add an error object to the step
        Throwable error = new Throwable();
        step.setError(error);
        // Now the step should have failed
        Assert.assertFalse(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertTrue(step.hasFailed());
    }
    @Test
    public void testNoErrorsShouldLeadToPassedSteps() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        // Before adding the error to the step the status of the step should be pending
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        // If we set an end date the status gets computed. In this case the test step should have passed.
        step.setEnd(new Date());
        // Now the step should have passed
        Assert.assertFalse(step.isPending());
        Assert.assertTrue(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
    }

    @Test
    public void testFailedAssertionsShouldMakeTestStepFail() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        // Add new failed assertion log message to step
        AssertionLogMessage assertion = new AssertionLogMessage(LogLevel.INFO, "myMessage").setStatusToFailed();
        step.addAssertion(assertion);
        // Now the step should have failed
        Assert.assertFalse(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertTrue(step.hasFailed());
        // Adding another successful assertion should not change the step status
        AssertionLogMessage assertion2 = new AssertionLogMessage(LogLevel.INFO, "myMessage").setStatusToPassed();
        step.addAssertion(assertion2);
        Assert.assertFalse(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertTrue(step.hasFailed());
    }

    @Test
    public void testPassedAssertionsShouldMakeTestStepPass() {
        StepInformationLogMessage step = new StepInformationLogMessage("myMethod", "myMessage");
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        // Add new failed assertion log message to step
        AssertionLogMessage assertionPassed = new AssertionLogMessage(LogLevel.INFO, "myMessage").setStatusToPassed();
        step.addAssertion(assertionPassed);
        // Now the step should still be pending
        Assert.assertTrue(step.isPending());
        Assert.assertFalse(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
        // If we set an end date the status gets computed. In this case the test step should have passed.
        step.setEnd(new Date());
        Assert.assertFalse(step.isPending());
        Assert.assertTrue(step.hasPassed());
        Assert.assertFalse(step.hasFailed());
    }
}
