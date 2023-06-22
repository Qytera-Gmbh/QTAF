package de.qytera.qtaf.testng.context;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.QtafInitializer;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.context.TestContextHelper;
import de.qytera.qtaf.core.guice.QtafModule;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.testng.event_listener.TestNGEventListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;

import java.util.Map;
import java.util.Set;

/**
 * Test context works as a factory class and state manager for test classes.
 * It provides information about the current test class like information about
 * annotations and holds objects needed during test executing (i.e. loggers, etc).
 */
@Listeners({TestNGEventListener.class})
@Guice(modules = {QtafModule.class})
public abstract class QtafTestNGContext implements IQtafTestContext {

    /**
     * Holds values from JSON configuration files
     */
    public static ConfigMap config;

    /**
     * Web driver instance (chrome, firefox, ...)
     */
    public static WebDriver driver = null;

    /**
     * Global log collection that holds all log messages from the tests
     */
    public static final TestSuiteLogCollection testSuiteLogCollection = QtafFactory.getTestSuiteLogCollection();

    /**
     * Page object annotation reference
     */
    protected TestFeature testFeatureAnnotation;

    /**
     * Log collection for all methods in this class
     */
    protected TestFeatureLogCollection testFeatureLogCollection = null;

    /**
     * Log collection for the current method
     */
    protected TestScenarioLogCollection logCollection;

    /**
     * Flag for controlling if test context has been initialized
     */
    private boolean isInitialized = false;

    /**
     * Placeholder for missing messages in assertions
     */
    protected String NO_MESSAGE = "<no-message>";

    /**
     * Constructor
     */
    protected QtafTestNGContext() {
        initialize();
    }

    /**
     * Initialize test context
     */
    @Override
    public QtafTestNGContext initialize() {
        if (isInitialized) {
            return this;
        }

        // Initialize the framework
        QtafInitializer.initialize();

        // Get configuration
        config = QtafFactory.getConfiguration();

        // Initialize driver
        driver = QtafFactory.getWebDriver();

        // Get Test feature annotation
        testFeatureAnnotation = this.getClass().getAnnotation(TestFeature.class);

        isInitialized = true;

        return this;
    }

    /**
     * Restart browser
     */
    @Override
    public void restartDriver() {
        driver = DriverFactory.getDriver(true);
    }

    /**
     * Get current log collection
     *
     * @return log collection
     */
    @Override
    public TestScenarioLogCollection getLogCollection() {
        return logCollection;
    }

    /**
     * Set the current log collection
     *
     * @param collection log collection
     * @return this
     */
    @Override
    public QtafTestNGContext setLogCollection(TestScenarioLogCollection collection) {
        this.logCollection = collection;
        return this;
    }

    /**
     * Add logger to all instance fields
     */
    @Override
    public void addLoggerToFieldsRecursively() {
        TestContextHelper.addLoggerToFieldsRecursively(this);
    }

    /**
     * Assert that a condition is true
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertTrue(boolean condition, String message) {
        assertTrue(condition, message, false);
    }

    /**
     * Assert that a condition is true
     * @param condition         The condition
     * @param message           The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    public void assertTrue(boolean condition, String message, boolean continueOnFailure) {
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
    public void assertFalse(boolean condition, String message) {
        assertFalse(condition, message, false);
    }

    /**
     * Assert that a condition is false
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     * @param continueOnFailure Should the assertion stop the test case on failure?
     */
    public void assertFalse(boolean condition, String message, boolean continueOnFailure) {
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
    public void assertNull(Object object) {
        assertNull(object, NO_MESSAGE);
    }

    /**
     * Assert that an object is null
     * @param object    The object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertNull(Object object, String message) {
        Assert.assertNull(object, message);
    }

    /**
     * Assert that an object is not null
     * @param object The object
     */
    public void assertNotNull(Object object) {
        assertNotNull(object, NO_MESSAGE);
    }

    /**
     * Assert that an object is not null
     * @param object    The object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertNotNull(Object object, String message) {
        Assert.assertNotNull(object, message);
    }

    /**
     * Asserts that two objects refer to the same object.
     * @param actual    Actual object
     * @param expected  Expected object
     */
    public void assertSame(Object actual, Object expected) {
        assertSame(actual, expected, NO_MESSAGE);
    }

    /**
     * Asserts that two objects refer to the same object.
     * @param actual    Actual object
     * @param expected  Expected object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertSame(Object actual, Object expected, String message) {
        Assert.assertSame(actual, expected, message);
    }

    /**
     * Asserts that two objects do not refer to the same objects.
     * @param actual    Actual object
     * @param expected  Expected object
     */
    public void assertNotSame(Object actual, Object expected) {
        assertNotSame(actual, expected, NO_MESSAGE);
    }

    /**
     * Asserts that two objects do not refer to the same objects.
     * @param actual    Actual object
     * @param expected  Expected object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertNotSame(Object actual, Object expected, String message) {
        Assert.assertNotSame(actual, expected, message);
    }

    /**
     * Checks if two objects are equal
     * @param object1 The first object
     * @param object2 The second object
     */
    public void assertEquals(Object object1, Object object2) {
        assertEquals(object1, object2, NO_MESSAGE);
    }

    /**
     * Checks if two objects are equal
     * @param object1 The first object
     * @param object2 The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertEquals(Object object1, Object object2, String message) {
        Assert.assertEquals(object1, object2, message);
    }

    /**
     * Checks if two sets are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    public void assertEqualsDeep(Set<?> actual, Set<?> expected) {
        assertEqualsDeep(actual, expected, NO_MESSAGE);
    }

    /**
     * Checks if two sets are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertEqualsDeep(Set<?> actual, Set<?> expected, String message) {
        Assert.assertEqualsDeep(actual, expected, message);
    }

    /**
     * Checks if two maps are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    public void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
        assertEqualsDeep(actual, expected, NO_MESSAGE);
    }

    /**
     * Checks if two maps are deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
        Assert.assertEqualsDeep(actual, expected, message);
    }

    /**
     * Checks if two sets are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    public void assertNotEqualsDeep(Set<?> actual, Set<?> expected) {
        assertNotEqualsDeep(actual, expected, NO_MESSAGE);
    }

    /**
     * Checks if two sets are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertNotEqualsDeep(Set<?> actual, Set<?> expected, String message) {
        Assert.assertNotEqualsDeep(actual, expected, message);
    }

    /**
     * Checks if two maps are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     */
    public void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
        assertNotEqualsDeep(actual, expected, NO_MESSAGE);
    }

    /**
     * Checks if two maps are not deeply equal
     * @param actual    The first object
     * @param expected  The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
        Assert.assertNotEqualsDeep(actual, expected, message);
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     * @param object1 The first object
     * @param object2 The second object
     */
    public void assertEqualsNoOrder(Object[] object1, Object[] object2) {
        assertEqualsNoOrder(object1, object2, NO_MESSAGE);
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order.
     * @param object1 The first object
     * @param object2 The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertEqualsNoOrder(Object[] object1, Object[] object2, String message) {
        Assert.assertEqualsNoOrder(object1, object2, message);
    }

    /**
     * Checks if two objects are not equal
     * @param object1 The first object
     * @param object2 The second object
     */
    public void assertNotEquals(Object object1, Object object2) {
        assertNotEquals(object1, object2, NO_MESSAGE);
    }

    /**
     * Checks if two objects are not equal
     * @param object1 The first object
     * @param object2 The second object
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertNotEquals(Object object1, Object object2, String message) {
        Assert.assertNotEquals(object1, object2, message);
    }

    /**
     * Create a new assertion log message object
     * @param stepLog   step log that belongs to the assertion log
     * @param message   assertion message
     * @return  assertion log object
     */
    protected AssertionLogMessage buildAssertionLogMessage(StepInformationLogMessage stepLog, String message) {
        AssertionLogMessage assertionLogMessage = new AssertionLogMessage(LogLevel.INFO, message);
        assertionLogMessage
                .setStep(stepLog)
                .setFeatureId(logCollection.getFeatureId())
                .setAbstractScenarioId(logCollection.getAbstractScenarioId())
                .setScenarioId(logCollection.getScenarioId());
        return assertionLogMessage;
    }

    /**
     * Handle condition assertions
     * @param type      assertion type
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    protected void handleAssertCondition(AssertionLogMessageType type, boolean condition, String message, AssertionError error) {
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
    protected void handleAssertTrue(boolean condition, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_TRUE, condition, message, error);
    }

    /**
     * Handle assertFalse method call
     * @param condition condition that should be tested
     * @param message   assertion message
     * @param error     assertion error
     */
    protected void handleAssertFalse(boolean condition, String message, AssertionError error) {
        handleAssertCondition(AssertionLogMessageType.ASSERT_FALSE, condition, message, error);
    }
}
