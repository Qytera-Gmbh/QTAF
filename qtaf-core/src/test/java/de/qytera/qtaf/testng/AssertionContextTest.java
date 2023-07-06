package de.qytera.qtaf.testng;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.testng.context.AssertionContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class AssertionContextTest {
    @Test(description = "Test no message string")
    public void testGetNoMessageString() {
        TestContext context = new TestContext();
        Assert.assertEquals(context.getNoMessageString(), "<no-message>");
    }

    @Test(description = "Test assertTrue")
    public void testAssertTrue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
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

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertTrue", expectedExceptions = {AssertionError.class})
    public void testAssertTrueFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertTrue(false, "should be true");
    }

    @Test(description = "Test assertTrue")
    public void testAssertTrueFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
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

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertFalse")
    public void testAssertFalse() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
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

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertFalse", expectedExceptions = {AssertionError.class})
    public void testAssertFalseFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertFalse(true, "should be false");
    }

    @Test(description = "Test assertFalse")
    public void testAssertFalseFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
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

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNull")
    public void testAssertNull() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull(null, "should be null");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NULL);
        Assert.assertNull(assertion.object());
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNull failure", expectedExceptions = {AssertionError.class})
    public void testAssertNullFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull("abc", "should be null");
    }

    @Test(description = "Test assertNull failure but continue")
    public void testAssertNullFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNull("abc", "should be null", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NULL);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotNull")
    public void testAssertNotNull() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull("abc", "should not be null");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_NULL);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotNull failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotNullFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull(null, "should not be null");
    }

    @Test(description = "Test assertNull failure but continue")
    public void testAssertNotNullFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotNull(null, "should not be null", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_NULL);
        Assert.assertNull(assertion.object());
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertSame")
    public void testAssertSame() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "abc", "should be same");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_SAME);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertSame failure", expectedExceptions = {AssertionError.class})
    public void testAssertSameFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "def", "should be null");
    }

    @Test(description = "Test assertSame failure but continue")
    public void testAssertSameFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertSame("abc", "def", "should be same", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_SAME);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotSame")
    public void testAssertNotSame() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "def", "should not be same");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_SAME);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotSame failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotSameFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "abc", "should not be same");
    }

    @Test(description = "Test assertNotSame failure but continue")
    public void testAssertNotSameFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotSame("abc", "abc", "should not be same", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_SAME);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEquals")
    public void testAssertEquals() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "abc", "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEquals failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "def", "should be null");
    }

    @Test(description = "Test assertEquals failure but continue")
    public void testAssertEqualsFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertEquals("abc", "def", "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotEquals")
    public void testAssertNotEquals() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "def", "should not be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "def");
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotEquals failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotEqualsFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "abc", "should not be equal");
    }

    @Test(description = "Test assertNotEquals failure but continue")
    public void testAssertNotEqualsFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        context.assertNotEquals("abc", "abc", "should not be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS);
        Assert.assertEquals(assertion.object(), "abc");
        Assert.assertEquals(assertion.expected(), "abc");
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEquals")
    public void testAssertEqualsNoOrder() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1,2,3};
        Object[] obj2 = new Object[]{1,2,3};
        context.assertEqualsNoOrder(obj1, obj2, "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEquals failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsNoOrderFailure() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1,2,3};
        Object[] obj2 = new Object[]{4,5,6};
        context.assertEqualsNoOrder(obj1, obj2, "should be null");
    }

    @Test(description = "Test assertEquals failure but continue")
    public void testAssertEqualsNoOrderFailureButContinue() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Object[] obj1 = new Object[]{1,2,3};
        Object[] obj2 = new Object[]{4,5,6};
        context.assertEqualsNoOrder(obj1, obj2, "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEqualsDeep")
    public void testAssertDeepEqualsWithMap() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertEqualsDeep(obj1, obj2, "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEqualsDeep failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsDeepFailureWithMap() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal");
    }

    @Test(description = "Test assertEqualsDeep failure but continue")
    public void testAssertEqualsDeepFailureButContinueWithMap() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEqualsDeep with set")
    public void testAssertDeepEqualsWithSet() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertEqualsDeep(obj1, obj2, "should be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertEqualsDeep with set failure", expectedExceptions = {AssertionError.class})
    public void testAssertEqualsDeepFailureWithSet() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal");
    }

    @Test(description = "Test assertEqualsDeep with set failure but continue")
    public void testAssertEqualsDeepFailureButContinueWithSet() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertEqualsDeep(obj1, obj2, "should be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotEqualsDeep")
    public void testAsserNotDeepEqualsWithMap() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k4", "v4");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotEqualsDeep failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotEqualsDeepFailureWithMap() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");
    }

    @Test(description = "Test assertNotEqualsDeep failure but continue")
    public void testAssertNotEqualsDeepFailureButContinueWithMap() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Map<String, String> obj1 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        Map<String, String> obj2 = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotEqualsDeep")
    public void testAsserNotDeepEqualsWithSet() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v4");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNull(assertion.error());
        Assert.assertTrue(assertion.hasPassed());
        Assert.assertFalse(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    @Test(description = "Test assertNotEqualsDeep failure", expectedExceptions = {AssertionError.class})
    public void testAssertNotEqualsDeepFailureWithSet() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal");
    }

    @Test(description = "Test assertNotEqualsDeep failure but continue")
    public void testAssertNotEqualsDeepFailureButContinueWithSet() {
        IndexHelper.clearAllIndices();
        TestContext context = new TestContext();
        StepInformationLogMessage logMessage = context.getLogCollection().getStepLogOfPendingStep();
        logMessage.getAssertions().clear();
        Assert.assertEquals(logMessage.getAssertions().size(), 0);

        Set<String> obj1 = Set.of("v1", "v2", "v3");
        Set<String> obj2 = Set.of("v1", "v2", "v3");
        context.assertNotEqualsDeep(obj1, obj2, "should not be equal", true);

        Assert.assertEquals(logMessage.getAssertions().size(), 1);
        AssertionLogMessage assertion = logMessage.getAssertions().get(0);
        Assert.assertEquals(assertion.type(), AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP);
        Assert.assertEquals(assertion.object(), obj1);
        Assert.assertEquals(assertion.expected(), obj2);
        Assert.assertNotNull(assertion.error());
        Assert.assertFalse(assertion.hasPassed());
        Assert.assertTrue(assertion.hasFailed());

        IndexHelper.clearAllIndices();
    }

    static class TestContext implements AssertionContext {
        @Override
        public TestScenarioLogCollection getLogCollection() {
            TestScenarioLogCollection scenario = TestScenarioLogCollection.createTestScenarioLogCollection("f1", "s1", "i1", "scenario1");
            if (scenario.getLogMessages().size() == 0) {
                scenario.addLogMessage(new StepInformationLogMessage("foo", "message"));
            }
            return scenario;
        }
    }
}

