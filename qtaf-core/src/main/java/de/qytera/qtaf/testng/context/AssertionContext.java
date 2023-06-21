package de.qytera.qtaf.testng.context;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

public interface AssertionContext {
    TestScenarioLogCollection getLogCollection();

    /**
     * Get a string that will be set as the logging message when no logging message is given by the test case
     * @return default log message
     */
    default String getNoMessageString() {
        return "<no-message>";
    }

    /**
     * Assert that a condition is true
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertTrue(boolean condition, String message) {
        assertTrue(condition, message, false);
    }

    /**
     * Assert that a condition is true
     * @param condition         The condition
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    default void assertTrue(boolean condition, String message, boolean continueOnFailure) {
        try {
            Assert.assertTrue(condition, message);
        } catch(AssertionError error) {
            handleAssertTrue(condition, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertTrue(condition, message, null);
    }

    /**
     * Assert that a condition is false
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertFalse(boolean condition, String message) {
        assertFalse(condition, message, false);
    }

    /**
     * Assert that a condition is false
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    default void assertFalse(boolean condition, String message, boolean continueOnFailure) {
        try {
            Assert.assertFalse(condition, message);
        } catch(AssertionError error) {
            handleAssertFalse(condition, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertFalse(condition, message, null);
    }

    /**
     * Assert that an object is null
     * @param object The object
     */
    default void assertNull(Object object) {
        assertNull(object, getNoMessageString());
    }

    /**
     * Assert that an object is null
     * @param object    The object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertNull(Object object, String message) {
        Assert.assertNull(object, message);
    }

    /**
     * Assert that an object is not null
     * @param object The object
     */
    default void assertNotNull(Object object) {
        assertNotNull(object, getNoMessageString());
    }

    /**
     * Assert that an object is not null
     * @param object    The object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertNotNull(Object object, String message) {
        Assert.assertNotNull(object, message);
    }

    /**
     * Asserts that two objects refer to the same object.
     * @param actual    Actual object
     * @param expected  Expected object
     */
    default void assertSame(Object actual, Object expected) {
        assertSame(actual, expected, getNoMessageString());
    }

    /**
     * Asserts that two objects refer to the same object.
     * @param actual    Actual object
     * @param expected  Expected object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertSame(Object actual, Object expected, String message) {
        Assert.assertSame(actual, expected, message);
    }

    /**
     * Asserts that two objects do not refer to the same objects.
     * @param actual    Actual object
     * @param expected  Expected object
     */
    default void assertNotSame(Object actual, Object expected) {
        assertNotSame(actual, expected, getNoMessageString());
    }

    /**
     * Asserts that two objects do not refer to the same objects.
     * @param actual    Actual object
     * @param expected  Expected object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertNotSame(Object actual, Object expected, String message) {
        Assert.assertNotSame(actual, expected, message);
    }

    /**
     * Checks if two objects are equal
     * @param object1 The first object
     * @param object2 The second object
     */
    default void assertEquals(Object object1, Object object2) {
        assertEquals(object1, object2, getNoMessageString());
    }

    /**
     * Checks if two objects are equal
     * @param object1 The first object
     * @param object2 The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertEquals(Object object1, Object object2, String message) {
        Assert.assertEquals(object1, object2, message);
    }

    /**
     * Checks if two sets are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    default void assertEqualsDeep(Set<?> actual, Set<?> expected) {
        assertEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two sets are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertEqualsDeep(Set<?> actual, Set<?> expected, String message) {
        Assert.assertEqualsDeep(actual, expected, message);
    }

    /**
     * Checks if two maps are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    default void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
        assertEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two maps are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
        Assert.assertEqualsDeep(actual, expected, message);
    }

    /**
     * Checks if two sets are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    default void assertNotEqualsDeep(Set<?> actual, Set<?> expected) {
        assertNotEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two sets are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertNotEqualsDeep(Set<?> actual, Set<?> expected, String message) {
        Assert.assertNotEqualsDeep(actual, expected, message);
    }

    /**
     * Checks if two maps are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    default void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
        assertNotEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two maps are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
        Assert.assertNotEqualsDeep(actual, expected, message);
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     * @param object1 The first object
     * @param object2 The second object
     */
    default void assertEqualsNoOrder(Object[] object1, Object[] object2) {
        assertEqualsNoOrder(object1, object2, getNoMessageString());
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     * @param object1 The first object
     * @param object2 The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertEqualsNoOrder(Object[] object1, Object[] object2, String message) {
        Assert.assertEqualsNoOrder(object1, object2, message);
    }

    /**
     * Checks if two objects are not equal
     * @param object1 The first object
     * @param object2 The second object
     */
    default void assertNotEquals(Object object1, Object object2) {
        assertNotEquals(object1, object2, getNoMessageString());
    }

    /**
     * Checks if two objects are not equal
     * @param object1 The first object
     * @param object2 The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    default void assertNotEquals(Object object1, Object object2, String message) {
        Assert.assertNotEquals(object1, object2, message);
    }

    /**
     * Create a new assertion log message object
     * @param stepLog   step log that belongs to the assertion log
     * @param message   assertion message
     * @return  assertion log object
     */
    default AssertionLogMessage buildAssertionLogMessage(StepInformationLogMessage stepLog, String message) {
        AssertionLogMessage assertionLogMessage = new AssertionLogMessage(LogLevel.INFO, message);
        assertionLogMessage
                .setStep(stepLog)
                .setFeatureId(getLogCollection().getFeatureId())
                .setAbstractScenarioId(getLogCollection().getAbstractScenarioId())
                .setScenarioId(getLogCollection().getScenarioId());
        return assertionLogMessage;
    }

    /**
     * Handle condition assertions
     * @param type      assertion type
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    default void handleAssertCondition(AssertionLogMessageType type, boolean condition, String message, AssertionError error) {
        StepInformationLogMessage stepLog = getLogCollection().getStepLogOfPendingStep();
        AssertionLogMessage assertionLogMessage = buildAssertionLogMessage(stepLog, message);
        assertionLogMessage
                .setType(type)
                .setCondition(condition)
                .setError(error);

        // Check if there was an error
        if (error != null) {
            assertionLogMessage.setStatusToFailed();
        } else {
            assertionLogMessage.setStatusToPassed();
        }

        System.out.println("-");
    }

    /**
     * Handle assertTrue method call
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    default void handleAssertTrue(boolean condition, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_TRUE, condition, message, error);
    }

    /**
     * Handle assertFalse method call
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    default void handleAssertFalse(boolean condition, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_FALSE, condition, message, error);
    }}
