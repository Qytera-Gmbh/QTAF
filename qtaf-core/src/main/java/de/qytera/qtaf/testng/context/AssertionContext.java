package de.qytera.qtaf.testng.context;

import de.qytera.qtaf.core.config.helper.QtafTestExecutionConfigHelper;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

/**
 * This interface contains all assertions QTAF supports natively.
 */
public interface AssertionContext {
    /**
     * Returns the collection containing all scenario logs.
     *
     * @return the scenario log collection
     */
    @Ignore
    TestScenarioLogCollection getLogCollection();

    /**
     * Get a string that will be set as the logging message when no logging message is given by the test case.
     *
     * @return default log message
     */
    @Ignore
    default String getNoMessageString() {
        return "<no-message>";
    }

    /**
     * Assert that a condition is true.
     *
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertTrue(boolean condition, String message) {
        assertTrue(condition, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Assert that a condition is true.
     *
     * @param condition         The condition
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertTrue(boolean condition, String message, boolean continueOnFailure) {
        try {
            Assert.assertTrue(condition, message);
        } catch (AssertionError error) {
            handleAssertTrue(condition, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertTrue(true, message, null);
    }

    /**
     * Assert that a condition is false.
     *
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertFalse(boolean condition, String message) {
        assertFalse(condition, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Assert that a condition is false.
     *
     * @param condition         The condition
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertFalse(boolean condition, String message, boolean continueOnFailure) {
        try {
            Assert.assertFalse(condition, message);
        } catch (AssertionError error) {
            handleAssertFalse(condition, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertFalse(false, message, null);
    }

    /**
     * Assert that an object is null.
     *
     * @param object The object
     */
    @Ignore
    default void assertNull(Object object) {
        assertNull(object, getNoMessageString());
    }

    /**
     * Assert that an object is null.
     *
     * @param object  The object
     * @param message The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertNull(Object object, String message) {
        assertNull(object, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Assert that an object is null.
     *
     * @param object            The object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertNull(Object object, String message, boolean continueOnFailure) {
        try {
            Assert.assertNull(object, message);
        } catch (AssertionError error) {
            handleAssertNull(object, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertNull(null, message, null);
    }

    /**
     * Assert that an object is not null.
     *
     * @param object The object
     */
    @Ignore
    default void assertNotNull(Object object) {
        assertNotNull(object, getNoMessageString());
    }

    /**
     * Assert that an object is not null.
     *
     * @param object  The object
     * @param message The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertNotNull(Object object, String message) {
        assertNotNull(object, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Assert that an object is not null.
     *
     * @param object            The object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertNotNull(Object object, String message, boolean continueOnFailure) {
        try {
            Assert.assertNotNull(object, message);
        } catch (AssertionError error) {
            handleAssertNotNull(object, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertNotNull(object, message, null);
    }

    /**
     * Asserts that two objects refer to the same object.
     *
     * @param actual   Actual object
     * @param expected Expected object
     */
    @Ignore
    default void assertSame(Object actual, Object expected) {
        assertSame(actual, expected, getNoMessageString());
    }

    /**
     * Asserts that two objects refer to the same object.
     *
     * @param actual   Actual object
     * @param expected Expected object
     * @param message  The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertSame(Object actual, Object expected, String message) {
        assertSame(actual, expected, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Assert that two objects refer to the same object.
     *
     * @param object            Actual object
     * @param expected          Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertSame(Object object, Object expected, String message, boolean continueOnFailure) {
        try {
            Assert.assertSame(object, expected, message);
        } catch (AssertionError error) {
            handleAssertSame(object, expected, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertSame(object, expected, message, null);
    }

    /**
     * Asserts that two objects do not refer to the same objects.
     *
     * @param actual   Actual object
     * @param expected Expected object
     */
    @Ignore
    default void assertNotSame(Object actual, Object expected) {
        assertNotSame(actual, expected, getNoMessageString());
    }

    /**
     * Asserts that two objects do not refer to the same objects.
     *
     * @param actual   Actual object
     * @param expected Expected object
     * @param message  The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertNotSame(Object actual, Object expected, String message) {
        assertNotSame(actual, expected, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Assert that two objects do not refer to the same object.
     *
     * @param object            Actual object
     * @param expected          Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertNotSame(Object object, Object expected, String message, boolean continueOnFailure) {
        try {
            Assert.assertNotSame(object, expected, message);
        } catch (AssertionError error) {
            handleAssertNotSame(object, expected, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertNotSame(object, expected, message, null);
    }

    /**
     * Checks if two objects are equal.
     *
     * @param object1 The first object
     * @param object2 The second object
     */
    @Ignore
    default void assertEquals(Object object1, Object object2) {
        assertEquals(object1, object2, getNoMessageString());
    }

    /**
     * Checks if two objects are equal.
     *
     * @param object1 The first object
     * @param object2 The second object
     * @param message The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertEquals(Object object1, Object object2, String message) {
        assertEquals(object1, object2, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Checks if two objects are equal.
     *
     * @param object1           Actual object
     * @param object2           Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertEquals(Object object1, Object object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertEquals(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertEquals(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertEquals(object1, object2, message, null);
    }

    /**
     * Checks if two sets are deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     */
    @Ignore
    default void assertEqualsDeep(Set<?> actual, Set<?> expected) {
        assertEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two sets are deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     * @param message  The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertEqualsDeep(Set<?> actual, Set<?> expected, String message) {
        assertEqualsDeep(actual, expected, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Checks if two maps are deeply equal.
     * @param actual   The first object
     * @param expected The second object
     */
    @Ignore
    default void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
        assertEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two maps are deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     * @param message  The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
        assertEqualsDeep(actual, expected, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Checks if two objects are equal.
     *
     * @param object1           Actual object
     * @param object2           Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertEqualsDeep(Map<?, ?> object1, Map<?, ?> object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertEqualsDeep(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertEqualsDeep(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertEqualsDeep(object1, object2, message, null);
    }

    /**
     * Checks if two objects are equal.
     *
     * @param object1           Actual object
     * @param object2           Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertEqualsDeep(Set<?> object1, Set<?> object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertEqualsDeep(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertEqualsDeep(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertEqualsDeep(object1, object2, message, null);
    }

    /**
     * Checks if two sets are not deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     */
    @Ignore
    default void assertNotEqualsDeep(Set<?> actual, Set<?> expected) {
        assertNotEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two sets are not deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     * @param message  The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertNotEqualsDeep(Set<?> actual, Set<?> expected, String message) {
        assertNotEqualsDeep(actual, expected, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Checks if two maps are not deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     */
    @Ignore
    default void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
        assertNotEqualsDeep(actual, expected, getNoMessageString());
    }

    /**
     * Checks if two maps are not deeply equal.
     *
     * @param actual   The first object
     * @param expected The second object
     * @param message  The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
        assertNotEqualsDeep(actual, expected, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Checks if two objects are not deeply equal.
     *
     * @param object1           Actual object
     * @param object2           Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertNotEqualsDeep(Map<?, ?> object1, Map<?, ?> object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertNotEqualsDeep(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertNotEqualsDeep(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertNotEqualsDeep(object1, object2, message, null);
    }

    /**
     * Checks if two objects are not deeply equal.
     *
     * @param object1           Actual object
     * @param object2           Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertNotEqualsDeep(Set<?> object1, Set<?> object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertNotEqualsDeep(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertNotEqualsDeep(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertNotEqualsDeep(object1, object2, message, null);
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     *
     * @param object1 The first object
     * @param object2 The second object
     */
    @Ignore
    default void assertEqualsNoOrder(Object[] object1, Object[] object2) {
        assertEqualsNoOrder(object1, object2, getNoMessageString());
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     *
     * @param object1 The first object
     * @param object2 The second object
     * @param message The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertEqualsNoOrder(Object[] object1, Object[] object2, String message) {
        assertEqualsNoOrder(object1, object2, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     *
     * @param object1           The first object
     * @param object2           The second object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertEqualsNoOrder(Object[] object1, Object[] object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertEqualsNoOrder(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertEqualsNoOrder(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertEqualsNoOrder(object1, object2, message, null);
    }

    /**
     * Checks if two objects are not equal.
     *
     * @param object1 The first object
     * @param object2 The second object
     */
    @Ignore
    default void assertNotEquals(Object object1, Object object2) {
        assertNotEquals(object1, object2, getNoMessageString());
    }

    /**
     * Checks if two objects are not equal.
     *
     * @param object1 The first object
     * @param object2 The second object
     * @param message The message that should be displayed if the assertion fails
     */
    @Ignore
    default void assertNotEquals(Object object1, Object object2, String message) {
        assertNotEquals(object1, object2, message, QtafTestExecutionConfigHelper.continueOnAssertionFailure());
    }

    /**
     * Checks if two objects are not equal.
     *
     * @param object1           Actual object
     * @param object2           Expected object
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    @Ignore
    default void assertNotEquals(Object object1, Object object2, String message, boolean continueOnFailure) {
        try {
            Assert.assertNotEquals(object1, object2, message);
        } catch (AssertionError error) {
            handleAssertNotEquals(object1, object2, message, error);
            if (!continueOnFailure) {
                throw error;
            } else {
                return;
            }
        }

        handleAssertNotEquals(object1, object2, message, null);
    }

    /**
     * Create a new assertion log message object.
     *
     * @param stepLog step log that belongs to the assertion log
     * @param message assertion message
     * @return assertion log object
     */
    @Ignore
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
     * Handle condition assertions.
     *
     * @param type      assertion type
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    @Ignore
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
    }

    /**
     * Handle condition assertions.
     *
     * @param type    assertion type
     * @param object  object that should be tested
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertCondition(AssertionLogMessageType type, Object object, String message, AssertionError error) {
        handleAssertCondition(type, object, null, message, error);
    }

    /**
     * Handle condition assertions.
     *
     * @param type     assertion type
     * @param object   object that should be tested
     * @param expected object that was expected
     * @param message  assertion message
     * @param error    assertion error
     */
    @Ignore
    default void handleAssertCondition(AssertionLogMessageType type, Object object, Object expected, String message, AssertionError error) {
        StepInformationLogMessage stepLog = getLogCollection().getStepLogOfPendingStep();
        AssertionLogMessage assertionLogMessage = buildAssertionLogMessage(stepLog, message);
        assertionLogMessage
                .setType(type)
                .setActual(object)
                .setExpected(expected)
                .setError(error);

        // Check if there was an error
        if (error != null) {
            assertionLogMessage.setStatusToFailed();
        } else {
            assertionLogMessage.setStatusToPassed();
        }
    }

    /**
     * Handle assertTrue method call.
     *
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    @Ignore
    default void handleAssertTrue(boolean condition, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_TRUE, condition, message, error);
    }

    /**
     * Handle assertFalse method call.
     *
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    @Ignore
    default void handleAssertFalse(boolean condition, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_FALSE, condition, message, error);
    }

    /**
     * Handle assertNull method call.
     *
     * @param object  object that should be tested
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertNull(Object object, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_NULL, object, message, error);
    }

    /**
     * Handle assertNull method call.
     *
     * @param object  object that should be tested
     * @param message assertion message
     * @param error   assertion error
     */
 @Ignore
    default void handleAssertNotNull(Object object, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_NOT_NULL, object, message, error);
    }

    /**
     * Handle assertSame method call.
     *
     * @param object   object that should be tested
     * @param expected object that is expected to be equal
     * @param message  assertion message
     * @param error    assertion error
     */
    @Ignore
    default void handleAssertSame(Object object, Object expected, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_SAME, object, expected, message, error);
    }

    /**
     * Handle assertNotSame method call.
     *
     * @param object   object that should be tested
     * @param expected object that is expected to be equal
     * @param message  assertion message
     * @param error    assertion error
     */
    @Ignore
    default void handleAssertNotSame(Object object, Object expected, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_NOT_SAME, object, expected, message, error);
    }

    /**
     * Handle assertEquals method call.
     *
     * @param object1 object that should be tested
     * @param object2 object that is expected to be equal
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertEquals(Object object1, Object object2, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_EQUALS, object1, object2, message, error);
    }

    /**
     * Handle assertNotEquals method call.
     *
     * @param object1 object that should be tested
     * @param object2 object that is expected to be equal
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertNotEquals(Object object1, Object object2, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_NOT_EQUALS, object1, object2, message, error);
    }

    /**
     * Handle assertDeepEquals method call.
     *
     * @param object1 object that should be tested
     * @param object2 object that is expected to be equal
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertEqualsDeep(Object object1, Object object2, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_EQUALS_DEEP, object1, object2, message, error);
    }

    /**
     * Handle assertNotEqualsDeep method call.
     *
     * @param object1 object that should be tested
     * @param object2 object that is expected to be equal
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertNotEqualsDeep(Object object1, Object object2, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_NOT_EQUALS_DEEP, object1, object2, message, error);
    }

    /**
     * Handle assertEqualsNoOrder method call.
     *
     * @param object1 object that should be tested
     * @param object2 object that is expected to be equal
     * @param message assertion message
     * @param error   assertion error
     */
    @Ignore
    default void handleAssertEqualsNoOrder(Object[] object1, Object[] object2, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_EQUALS_NO_ORDER, object1, object2, message, error);
    }
}
