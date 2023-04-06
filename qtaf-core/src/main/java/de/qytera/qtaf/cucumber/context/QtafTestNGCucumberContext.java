package de.qytera.qtaf.cucumber.context;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.QtafInitializer;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.helper.QtafTestExecutionConfigHelper;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.context.TestContextHelper;
import de.qytera.qtaf.core.guice.QtafModule;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.cucumber.entity.QTAFCucumberScenarioEntity;
import de.qytera.qtaf.cucumber.entity.QTAFCucumberScenarioEntityFactory;
import de.qytera.qtaf.testng.event_listener.TestNGEventListener;
import io.cucumber.java.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Cucumber Test Contexts.
 * Cucumber runner classes need to extend this class
 */
@Listeners({TestNGEventListener.class})
@Guice(modules = {QtafModule.class})
public class QtafTestNGCucumberContext extends AbstractTestNGCucumberTests implements IQtafTestContext {
    /**
     * Logger
     */
    private static final Logger logger = QtafFactory.getLogger();

    /**
     * Holds values from JSON configuration files
     */
    public static ConfigMap config;

    /**
     * Web driver instance (chrome, firefox, ...)
     */
    public static WebDriver driver = null;

    /**
     * Page object annotation reference
     */
    protected TestFeature testFeatureAnnotation;

    /**
     * Global log collection that holds all log messages from the tests
     */
    public static final TestSuiteLogCollection testSuiteLogCollection = QtafFactory.getTestSuiteLogCollection();

    /**
     * Flag for controlling if test context has been initialized
     */
    private boolean isInitialized = false;

    /**
     * Constructor with initialization
     */
    public QtafTestNGCucumberContext() {
        super();
        initialize();
    }

    /**
     * Constructor with optional initialization
     *
     * @param shallInitialize Whether test context should be initialized or not
     */
    public QtafTestNGCucumberContext(boolean shallInitialize) {
        super();

        if (shallInitialize) {
            initialize();
        }
    }

    /**
     * Restart browser
     */
    @Override
    public void restartDriver() {
        driver = DriverFactory.getDriver(true);
    }

    /**
     * Initialize test context
     */
    @Override
    public final QtafTestNGCucumberContext initialize() {
        if (isInitialized) {
            return this;
        }

        // Initialize the framework
        QtafInitializer.initialize();

        // Get configuration
        config = QtafFactory.getConfiguration();

        // Initialize driver
        driver = QtafFactory.getWebDriver();

        // Get Page object
        testFeatureAnnotation = this.getClass().getAnnotation(TestFeature.class);

        // Initialize log collection if class is a test case class (annotated with TestCase annotation)
        if (testFeatureAnnotation != null) {
            // Build test ID
            String testId = this.getClass().getName();

            // Create new logger for this test class and register it in the global TestSuiteLogger instance
            testFeatureLogCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(testId, testFeatureAnnotation);
            testSuiteLogCollection.addTestClassLogCollection(testFeatureLogCollection);
        }

        isInitialized = true;

        return this;
    }

    /**
     * Log collection for the current method
     */
    protected TestScenarioLogCollection logCollection;

    /**
     * Log collection for all methods in this class
     */
    protected TestFeatureLogCollection testFeatureLogCollection = null;

    @Override
    public TestScenarioLogCollection getLogCollection() {
        return logCollection;
    }

    @Override
    public IQtafTestContext setLogCollection(TestScenarioLogCollection collection) {
        this.logCollection = collection;
        return this;
    }

    @Override
    public void addLoggerToFieldsRecursively() {
        TestContextHelper.addLoggerToFieldsRecursively(this);
    }

    /**
     * Before hooks run before the first step of each scenario.
     */
    @Before
    public void initializeTest(Scenario scenario) {
        logger.debug("-- Cucumber: @Before");
    }

    /**
     * Method that check if a scenario shall run or not
     *
     * @param scenarioEntity Scenario entity object
     * @return true if scenario shall run, false otherwise
     */
    public boolean shallRun(QTAFCucumberScenarioEntity scenarioEntity) {
        List<String> configGroups = QtafTestExecutionConfigHelper.getTestGroupsFromConfiguration();

        // Groups were passed in configuration bit scenario has no groups
        if (
                configGroups == null                                            // no groups were given in configuration
                        || scenarioEntity.hasAnyGroupName(configGroups)         // scenario has all groups in the configuration
                        || scenarioEntity.belongsToAnyTestSet(configGroups)     // scenario entity belongs to all test sets given in the configuration
        ) {
            return true;
        }

        return false;
    }

    /**
     * Method that loads and provides Cucumber Scenario entities
     *
     * @return Cucumber scenario entities
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        Object[][] scenarios = super.scenarios();
        ArrayList<Object[]> filteredScenarios = new ArrayList<>();

        // Filter scenarios by group name
        for (Object[] o : scenarios) {
            QTAFCucumberScenarioEntity scenarioEntity = QTAFCucumberScenarioEntityFactory.getEntity(
                    (PickleWrapper) o[0],
                    (FeatureWrapper) o[1]
            );

            if (shallRun(scenarioEntity)) {
                filteredScenarios.add(o);
            }
        }

        Object[][] results = new Object[filteredScenarios.size()][2];

        for (int i = 0; i < filteredScenarios.size(); i++) {
            results[i] = filteredScenarios.get(i);
        }

        return results;
    }

    /**
     * TestNG test that executes all cucumber scenarios
     *
     * @param pickleWrapper  Cucumber scenario entity wrapper
     * @param featureWrapper Cucumber feature entity wrapper
     */
    @Test(
            groups = {"cucumber"},
            description = "Runs Cucumber Scenarios",
            dataProvider = "scenarios"
    )
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        QTAFCucumberScenarioEntity scenarioEntity = QTAFCucumberScenarioEntityFactory.getEntity(
                pickleWrapper,
                featureWrapper
        );

        onBeforeScenario(scenarioEntity);

        super.runScenario(pickleWrapper, featureWrapper);
    }

    /**
     * Method that runs before each scenario
     *
     * @param scenarioEntity Entity object that holds information about feature and scenario that gets executed
     */
    protected void onBeforeScenario(QTAFCucumberScenarioEntity scenarioEntity) {
        if (scenarioEntity.getFeatureTags().get("newDriver") != null) {
            restartDriver();
        } else if (scenarioEntity.getScenarioTags().get("newDriver") != null) {
            restartDriver();
        }

    }

    /**
     * Step hooks invoked before and after a step.
     */
    @BeforeStep
    public void beforeStep(Scenario scenario) {
        logger.debug("-- Cucumber: @BeforeStep - "
                + scenario.getName() + " : " + scenario.getId());
    }

    /**
     * Step hooks invoked before and after a step.
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        logger.debug("-- Cucumber: @AfterStep - "
                + scenario.getName() + " : " + scenario.getId());
    }

    /**
     * After hooks run after the last step of each scenario,
     * even when the step result is failed, undefined, pending, or skipped
     *
     * @param scenario
     */
    @After
    public void embedScreenshot(Scenario scenario) {
        logger.debug("-- Cucumber: @After - "
                + scenario.getName() + " : " + scenario.getId());

    }

}
