package de.qytera.qtaf.testng;

import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.message.*;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.AssertionContext;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class AssertionContextTest {
    private static final String TEST_ASSERTION_BEHAVIOUR_ON_FAILURE = "tests.continueOnAssertionFailure";

    private void makeFailedAssertionsBreakTests() {
        ConfigurationFactory.getInstance().setBoolean(TEST_ASSERTION_BEHAVIOUR_ON_FAILURE, false);
    }

    private void makeFailedAssertionsContinueTests() {
        ConfigurationFactory.getInstance().setBoolean(TEST_ASSERTION_BEHAVIOUR_ON_FAILURE, true);
    }

    @BeforeMethod
    public void clearIndicesBeforeTest() {
        IndexHelper.clearAllIndices();
    }

    @AfterMethod
    public void clearIndicesAfterTest() {
        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test no message string")
    public void testGetNoMessageString() {
        TestContext context = new TestContext();
        Assert.assertEquals(context.getNoMessageString(), "<no-message>");
    }

    @Test(description = "Test assertTrue")
    public void testAssertTrue() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertTrue(true, "should be true");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_TRUE);
        Assert.assertTrue(assertion.condition());
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

    }

    @Test(description = "Test assertTrue", expectedExceptions = {AssertionError.class}, expectedExceptionsMessageRegExp = "should be true expected \\[true] but found \\[false](.*)")
    public void testAssertTrueFailure() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertTrue(false, "should be true");
    }

    @Test(description = "Test assertTrue")
    public void testAssertTrueFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertTrue(false, "should be true", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_TRUE);
        Assert.assertFalse(assertion.condition());
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

    }

    @Test(description = "Test assertFalse")
    public void testAssertFalse() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertFalse(false, "should be false");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_FALSE);
        Assert.assertFalse(assertion.condition());
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

    }

    @Test(description = "Test assertFalse", expectedExceptions = {AssertionError.class})
    public void testAssertFalseFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertFalse(true, "should be false");
    }

    @Test(description = "Test assertFalse continue")
    public void testAssertFalseFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertFalse(true, "should be false");
    }

    @Test(description = "Test assertFalse")
    public void testAssertFalseFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertFalse(true, "should be false", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_FALSE);
        Assert.assertTrue(assertion.condition());
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertNull with message")
    public void testAssertNullWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull(null, "should be null");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NULL);
        Assert.assertEquals(assertion.getMessage(), "should be null");
        Assert.assertNull(assertion.actual());
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNull without message")
    public void testAssertNullWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull(null);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NULL);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertNull(assertion.actual());
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNull failure", expectedExceptions = {AssertionError.class})
    public void testAssertNullFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull("abc", "should be null");
    }

    @Test(description = "Test assertNull failure continue")
    public void testAssertNullFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull("abc", "should be null");
    }

    @Test(description = "Test assertNull failure but continue")
    public void testAssertNullFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull("abc", "should be null", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NULL);
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertNotNull with message")
    public void testAssertNotNullWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull("abc", "should not be null");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_NULL);
        Assert.assertEquals(assertion.getMessage(), "should not be null");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotNull without message")
    public void testAssertNotNullWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull("abc");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_NULL);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotNull failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotNullFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull(null, "should not be null");
    }

    @Test(description = "Test assertNotNull failure´continue")
    public void testAssertNotNullFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull(null, "should not be null");
    }

    @Test(description = "Test assertNull failure but continue")
    public void testAssertNotNullFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull(null, "should not be null", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_NULL);
        Assert.assertNull(assertion.actual());
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertSame with message")
    public void testAssertSameWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "abc", "should be same");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_SAME);
        Assert.assertEquals(assertion.getMessage(), "should be same");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertSame without message")
    public void testAssertSameWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "abc");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_SAME);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertSame failure", expectedExceptions = {AssertionError.class})
    public void testAssertSameFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "def", "should be null");
    }

    @Test(description = "Test assertSame failure continue")
    public void testAssertSameFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "def", "should be null");
    }

    @Test(description = "Test assertSame failure but continue")
    public void testAssertSameFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "def", "should be same", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_SAME);
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertNotSame with message")
    public void testAssertNotSameWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "def", "should not be same");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_SAME);
        Assert.assertEquals(assertion.getMessage(), "should not be same");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotSame without message")
    public void testAssertNotSameWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "def", "should not be same");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_SAME);
        Assert.assertEquals(assertion.getMessage(), "should not be same");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotSame failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotSameFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "abc", "should not be same");
    }

    @Test(description = "Test assertNotSame failure continue")
    public void testAssertNotSameFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "abc", "should not be same");
    }

    @Test(description = "Test assertNotSame failure but continue")
    public void testAssertNotSameFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "abc", "should not be same", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_SAME);
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertEquals with message")
    public void testAssertEqualsWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "abc", "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS);
        Assert.assertEquals(assertion.getMessage(), "should be equal");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertEquals without message")
    public void testAssertEqualsWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "abc");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertEquals failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "def", "should be null");
    }

    @Test(description = "Test assertEquals failure continue")
    public void testAssertEqualsFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "def", "should be null");
    }

    @Test(description = "Test assertEquals failure but continue")
    public void testAssertEqualsFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "def", "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS);
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEquals with message")
    public void testAssertNotEqualsWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "def", "should not be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS);
        Assert.assertEquals(assertion.getMessage(), "should not be equal");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEquals without message")
    public void testAssertNotEqualsWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "def");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEquals failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotEqualsFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "abc", "should not be equal");
    }

    @Test(description = "Test assertNotEquals failure continue")
    public void testAssertNotEqualsFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "abc", "should not be equal");
    }

    @Test(description = "Test assertNotEquals failure but continue")
    public void testAssertNotEqualsFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "abc", "should not be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS);
        Assert.assertEquals(assertion.actual(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

    }

    @Test(description = "Test assertEquals")
    public void testAssertEqualsNoOrder() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1, 2, 3};
        Object[] obj2 = new Object[]{1, 2, 3};
        context.assertEqualsNoOrder(obj1, obj2, "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER);
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

    }

    @Test(description = "Test assertEquals failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsNoOrderFailure() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1, 2, 3};
        Object[] obj2 = new Object[]{4, 5, 6};
        context.assertEqualsNoOrder(obj1, obj2, "should be null");
    }

    @Test(description = "Test assertEquals failure continue")
    public void testAssertEqualsNoOrderFailureContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1, 2, 3};
        Object[] obj2 = new Object[]{4, 5, 6};
        context.assertEqualsNoOrder(obj1, obj2, "should be null");
    }

    @Test(description = "Test assertEquals failure but continue")
    public void testAssertEqualsNoOrderFailureButContinue() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1, 2, 3};
        Object[] obj2 = new Object[]{4, 5, 6};
        context.assertEqualsNoOrder(obj1, obj2, "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER);
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertEqualsDeep with message")
    public void testAssertDeepEqualsWithMapWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertEqualsDeep(obj1, obj2, "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "should be equal");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertEqualsDeep without message")
    public void testAssertDeepEqualsWithMapWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertEqualsDeep(obj1, obj2);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertEqualsDeep failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsDeepFailureWithMap() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal");
    }

    @Test(description = "Test assertEqualsDeep failure continue")
    public void testAssertEqualsDeepFailureWithMapContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal");
    }

    @Test(description = "Test assertEqualsDeep failure but continue")
    public void testAssertEqualsDeepFailureButContinueWithMap() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertEqualsDeep with set with message")
    public void testAssertDeepEqualsWithSetWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertEqualsDeep(obj1, obj2, "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "should be equal");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertEqualsDeep with set without message")
    public void testAssertDeepEqualsWithSetWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertEqualsDeep(obj1, obj2);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertEqualsDeep with set failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsDeepFailureWithSet() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal");
    }

    @Test(description = "Test assertEqualsDeep with set failure continue")
    public void testAssertEqualsDeepFailureWithSetContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal");
    }

    @Test(description = "Test assertEqualsDeep with set failure but continue")
    public void testAssertEqualsDeepFailureButContinueWithSet() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEqualsDeep with message")
    public void testAsserNotDeepEqualsWithMapWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "should not be equal");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEqualsDeep without message")
    public void testAsserNotDeepEqualsWithMapWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertNotEqualsDeep(obj1, obj2);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEqualsDeep failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotEqualsDeepFailureWithMap() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");
    }

    @Test(description = "Test assertNotEqualsDeep failure continue")
    public void testAssertNotEqualsDeepFailureWithMapContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");
    }

    @Test(description = "Test assertNotEqualsDeep failure but continue")
    public void testAssertNotEqualsDeepFailureButContinueWithMap() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEqualsDeep with message")
    public void testAsserNotDeepEqualsWithSetWithMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "should not be equal");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEqualsDeep without message")
    public void testAsserNotDeepEqualsWithSetWithoutMessage() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertNotEqualsDeep(obj1, obj2);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.getMessage(), "<no-message>");
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());
    }

    @Test(description = "Test assertNotEqualsDeep failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotEqualsDeepFailureWithSet() {
        makeFailedAssertionsBreakTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");
    }

    @Test(description = "Test assertNotEqualsDeep failure continue")
    public void testAssertNotEqualsDeepFailureWithSetContinue() {
        makeFailedAssertionsContinueTests();
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");
    }

    @Test(description = "Test assertNotEqualsDeep failure but continue")
    public void testAssertNotEqualsDeepFailureButContinueWithSet() {
        TestContext context = new TestContext();
        LogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.actual(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

    }

    @Test(description = "Test creation of new step log message")
    public void testCreationOfNewStepLogMessage() {
        TestContext context = new TestContext();
        TestScenarioLogCollection scenarioLogCollection = context.getLogCollection();
        Assert.assertEquals(scenarioLogCollection.getLogMessages().size(), 1, "First there should be only the 'foo' log message");
        ((LogMessage) scenarioLogCollection.getLogMessages().get(0)).setStatus(LogMessage.Status.PASSED);

        StepInformationLogMessage logMessage = new StepInformationLogMessage("m1", "my-message");
        logMessage.setStatus(LogMessage.Status.PENDING);
        scenarioLogCollection.addLogMessage(logMessage);
        Assert.assertEquals(scenarioLogCollection.getLogMessages().size(), 2, "There should be two step log messages before the first assertion");
        Assert.assertEquals(logMessage.getAssertions().size(), 0, "Expected log message to have no assertions yet");

        context.assertEquals(2, 2, "Expected both integers to be identical");
        Assert.assertEquals(scenarioLogCollection.getLogMessages().size(), 2, "There should be two step log messages after the first assertion");
        Assert.assertEquals(logMessage.getAssertions().size(), 1, "Expected assertion log to be added to the pending step");
        Assert.assertEquals(logMessage.getAssertions().get(0).getMessage(), "Expected both integers to be identical");
        Assert.assertEquals(logMessage.getAssertions().get(0).actual(), 2);
        Assert.assertEquals(logMessage.getAssertions().get(0).expected(), 2);

        logMessage.setStatus(LogMessage.Status.PASSED);
        context.assertEquals(3, 3, "Expected both integers to be identical");
        Assert.assertEquals(scenarioLogCollection.getLogMessages().size(), 3, "There should be three step log messages after the second assertion");
        AssertionLogMessage assertionLogMessage = ((LogMessage) scenarioLogCollection.getLogMessages().get(2)).getAssertions().get(0);
        Assert.assertEquals(assertionLogMessage.expected(), 3);
        Assert.assertEquals(assertionLogMessage.actual(), 3);
        Assert.assertEquals(assertionLogMessage.getMessage(), "Expected both integers to be identical");
    }

    static class TestContext implements AssertionContext {
        @Override
        public TestScenarioLogCollection getLogCollection() {
            TestScenarioLogCollection scenario = TestScenarioLogCollection.createTestScenarioLogCollection("f1", "s1", "i1", "scenario1");
            if (scenario.getLogMessages().isEmpty()) {
                scenario.addLogMessage(new StepInformationLogMessage("foo", "message"));
            }
            return scenario;
        }
    }
}

