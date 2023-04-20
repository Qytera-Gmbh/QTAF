package de.qytera.qtaf.testng.context;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.QtafInitializer;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.context.TestContextHelper;
import de.qytera.qtaf.core.guice.QtafModule;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.testng.event_listener.TestNGEventListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;

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
     * Constructor
     */
    public QtafTestNGContext() {
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
     */
    public void assertTrue(boolean condition) {
        Assert.assertTrue(condition);
        handleAssert();
    }

    /**
     * Assert that a condition is true
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    /**
     * Assert that a condition is false
     * @param condition The condition
     */
    public void assertFalse(boolean condition) {
        Assert.assertFalse(condition);
    }

    /**
     * Assert that a condition is false
     * @param condition The condition
     * @param message   The message that should be displayed if the assertion fails
     */
    public void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    /**
     * Assert that an object is null
     * @param object The object
     */
    public void assertNull(Object object) {
        Assert.assertNull(object);
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
        Assert.assertNotNull(object);
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
     * Checks if two objects are equal
     * @param object1 The first object
     * @param object2 The second object
     */
    public void assertEquals(Object object1, Object object2) {
        Assert.assertEquals(object1, object2);
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
     * Checks if two objects are not equal
     * @param object1 The first object
     * @param object2 The second object
     */
    public void assertNotEquals(Object object1, Object object2) {
        Assert.assertNotEquals(object1, object2);
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

    protected void handleAssert() {
        StepInformationLogMessage logMessage = getLogCollection().getStepLogOfPendingStep();
        System.out.println("-");
    }
}
