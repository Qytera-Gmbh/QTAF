package de.qytera.qtaf.core.cucumber.helper;

import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.cucumber.helper.CucumberLogMessageHelper;
import io.cucumber.plugin.event.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for Cucumber log message helper
 */
public class CucumberLogMessageHelperTest {
    /**
     * Test if the Cucumber status gets mapped to the correct log message status
     */
    @Test
    public void testMapCucumberStatusPassedToLogMessageStatus() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.PASSED);
        Assert.assertEquals(status, LogMessage.Status.PASSED);
    }

    /**
     * Test if the Cucumber status gets mapped to the correct log message status
     */
    @Test
    public void testMapCucumberStatusFailedToLogMessageStatus() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.FAILED);
        Assert.assertEquals(status, LogMessage.Status.FAILED);
    }

    /**
     * Test if the Cucumber status gets mapped to the correct log message status
     */
    @Test
    public void testMapCucumberStatusPendingToLogMessageStatus() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.PENDING);
        Assert.assertEquals(status, LogMessage.Status.PENDING);
    }

    /**
     * Test if the Cucumber status gets mapped to the correct log message status
     */
    @Test
    public void testMapCucumberStatusSkippedToLogMessageStatus() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.SKIPPED);
        Assert.assertEquals(status, LogMessage.Status.SKIPPED);
    }

    /**
     * Test if an invalid Cucumber status throws an exception
     */
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testMapCucumberStatusUndefinedThrowsException() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.UNDEFINED);
    }

    /**
     * Test if an invalid Cucumber status throws an exception
     */
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testMapCucumberStatusUnusedThrowsException() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.UNUSED);
    }

    /**
     * Test if an invalid Cucumber status throws an exception
     */
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testMapCucumberStatusAmbiguousThrowsException() {
        LogMessage.Status status = CucumberLogMessageHelper.mapCucumberStatusToLogStatus(Status.AMBIGUOUS);
    }
}
