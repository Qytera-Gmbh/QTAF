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
public abstract class QtafTestNGContext implements IQtafTestContext, AssertionContext {

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
}
