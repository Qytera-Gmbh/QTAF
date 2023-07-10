package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertionLogMessageTest {
    @Test(description = "Test construction of AssertionLogMessage")
    public void testAssertionLogMessageConstructor() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        Assert.assertEquals(message.getLevel(), LogLevel.INFO);
    }

    @Test(description = "Test log levels of AssertionLogMessage")
    public void testLogLevels() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        Assert.assertEquals(message.getLevel(), LogLevel.INFO);
        message.setLevel(LogLevel.DEBUG);
        Assert.assertEquals(message.getLevel(), LogLevel.DEBUG);
        message.setLevel(LogLevel.WARN);
        Assert.assertEquals(message.getLevel(), LogLevel.WARN);
        message.setLevel(LogLevel.ERROR);
        Assert.assertEquals(message.getLevel(), LogLevel.ERROR);
    }

    @Test(description = "Test status of AssertionLogMessage")
    public void testStatus() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        Assert.assertFalse(message.wasExecuted());
        Assert.assertFalse(message.hasPassed());
        Assert.assertFalse(message.hasFailed());
        message.setStatusToPassed();
        Assert.assertTrue(message.wasExecuted());
        Assert.assertTrue(message.hasPassed());
        Assert.assertFalse(message.hasFailed());
        message.setStatusToFailed();
        Assert.assertTrue(message.wasExecuted());
        Assert.assertFalse(message.hasPassed());
        Assert.assertTrue(message.hasFailed());
    }

    @Test(description = "Test expected and actual value of AssertionLogMessage")
    public void testExpectedAndActualValue() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        String expected = "abc";
        String actual = "xyz";
        message.setExpected(expected);
        Assert.assertEquals(message.expected(), expected);
        message.setActual(actual);
        Assert.assertEquals(message.actual(), actual);
    }

    @Test(description = "Test condition")
    public void testCondition() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        message.setCondition(true);
        Assert.assertTrue(message.condition());
    }

    @Test(description = "Test error")
    public void testError() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        AssertionError error = new AssertionError();
        message.setError(error);
        Assert.assertEquals(message.error().getClass(), new ThrowableWrapper(error).getClass());
    }

    @Test(description = "Test step")
    public void testStep() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        StepInformationLogMessage logMessage = new StepInformationLogMessage("abc", "def");
        message.setStep(logMessage);
        Assert.assertEquals(message.step(), logMessage);
    }

    @Test(description = "Test type of AssertionLogMessage")
    public void testType() {
        AssertionLogMessage message = new AssertionLogMessage(LogLevel.INFO, "m1");
        message.setType(AssertionLogMessageType.ASSERT_TRUE);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_TRUE);

        message.setType(AssertionLogMessageType.ASSERT_FALSE);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_FALSE);

        message.setType(AssertionLogMessageType.ASSERT_EQUALS);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_EQUALS);

        message.setType(AssertionLogMessageType.ASSERT_NOT_EQUALS);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS);

        message.setType(AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);

        message.setType(AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);

        message.setType(AssertionLogMessageType.ASSERT_SAME);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_SAME);

        message.setType(AssertionLogMessageType.ASSERT_NOT_SAME);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_NOT_SAME);

        message.setType(AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER);

        message.setType(AssertionLogMessageType.ASSERT_NULL);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_NULL);

        message.setType(AssertionLogMessageType.ASSERT_NOT_NULL);
        Assert.assertEquals(message.type(), AssertionLogMessageType.ASSERT_NOT_NULL);
    }
}
