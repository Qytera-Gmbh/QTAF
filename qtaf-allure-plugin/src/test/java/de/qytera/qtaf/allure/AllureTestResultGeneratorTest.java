package de.qytera.qtaf.allure;

import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for Allure Test result Generator
 */
public class AllureTestResultGeneratorTest {
    /**
     * Test mapping of log message status PASSED to Allure status
     */
    @Test()
    public void testGetStepStatusPassedFromQtafStep() {
        StepInformationLogMessage logMessage = new StepInformationLogMessage("foo", "bar");
        logMessage.setStatus(LogMessage.Status.PASSED);
        Assert.assertEquals(
                AllureTestResultGenerator.getStepStatusFromQtafStep(logMessage),
                Status.PASSED,
                "Log message status PASSED should be mapped to Allure status PASSED"
        );
    }

    /**
     * Test mapping of log message status SKIPPED to Allure status
     */
    @Test()
    public void testGetStepStatusSkippedFromQtafStep() {
        StepInformationLogMessage logMessage = new StepInformationLogMessage("foo", "bar");
        logMessage.setStatus(LogMessage.Status.SKIPPED);
        Assert.assertEquals(
                AllureTestResultGenerator.getStepStatusFromQtafStep(logMessage),
                Status.SKIPPED,
                "Log message status SKIPPED should be mapped to Allure status SKIPPED"
        );
    }

    /**
     * Test mapping of log message status PENDING to Allure status
     */
    @Test()
    public void testGetStepStatusPendingFromQtafStep() {
        StepInformationLogMessage logMessage = new StepInformationLogMessage("foo", "bar");
        logMessage.setStatus(LogMessage.Status.PENDING);
        Assert.assertEquals(
                AllureTestResultGenerator.getStepStatusFromQtafStep(logMessage),
                Status.SKIPPED,
                "Log message status PENDING should be mapped to Allure status SKIPPED"
        );
    }

    /**
     * Test mapping of log message status FAILED to Allure status
     */
    @Test()
    public void testGetStepStatusFailedFromQtafStep() {
        StepInformationLogMessage logMessage = new StepInformationLogMessage("foo", "bar");
        logMessage.setStatus(LogMessage.Status.FAILED);
        Assert.assertEquals(
                AllureTestResultGenerator.getStepStatusFromQtafStep(logMessage),
                Status.FAILED,
                "Log message status FAILED should be mapped to Allure status FAILED"
        );
    }
}
