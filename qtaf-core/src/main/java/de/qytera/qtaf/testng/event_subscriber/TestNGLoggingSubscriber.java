package de.qytera.qtaf.testng.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import io.cucumber.testng.CucumberOptions;
import de.qytera.qtaf.core.log.Logger;
import org.testng.ITestResult;

import java.lang.annotation.Annotation;
import java.util.Date;

/**
 * Event subscriber that reacts to Qtaf lifecycle events and creates log messages
 */
public class TestNGLoggingSubscriber implements IEventSubscriber {

    /**
     * Reference to global log collection
     */
    private static final TestSuiteLogCollection testSuiteLogCollection = QtafFactory.getTestSuiteLogCollection();

    /**
     * Logger
     */
    private final Logger logger = QtafFactory.getLogger();

    /**
     * Subscribe to events and register event handlers
     */
    @Override
    public void initialize() {
        QtafEvents.testStarted.subscribe(
                this::onTestStarted,
                this::logError
        );
        QtafEvents.testSuccess.subscribe(
                this::onTestSuccess,
                this::logError
        );
        QtafEvents.testFailure.subscribe(
                this::onTestFailure,
                this::logError
        );
        QtafEvents.testSkipped.subscribe(
                this::onTestSkipped,
                this::logError
        );
        QtafEvents.testFailedButWithinSuccessPercentage.subscribe(this::onTestFailedButWithinSuccessPercentage);
    }

    /**
     * This method is called every time before a test is executed
     * @param iQtafTestEventPayload   Test context object
     * @deprecated
     */
    private void onTestStarted(IQtafTestEventPayload iQtafTestEventPayload) {
        // Check if this listener is responsible for this event
        if (!(iQtafTestEventPayload.getOriginalEvent() instanceof ITestResult)) {
            return;
        }

        TestScenarioLogCollection testScenarioLogCollection = TestScenarioLogCollection.fromQtafTestEventPayload(iQtafTestEventPayload);
        ITestResult iTestResult = (ITestResult) iQtafTestEventPayload.getOriginalEvent();

        TestFeatureLogCollection featureLogCollection = QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                iQtafTestEventPayload.getFeatureId(),
                iQtafTestEventPayload.getScenarioName()
        );

        this.log(iTestResult, "started");

        // Get instance of Test class, where test methods are defined
        IQtafTestContext testInstance = (IQtafTestContext) iTestResult.getInstance();

        // This is the class object of the class where the test methods are defined in
        Class<?> clazz = iTestResult.getTestClass().getRealClass();

        // Check if the class is annotated with a 'CucumberOptions' annotation
        Annotation cucumberOptions = clazz.getAnnotation(CucumberOptions.class);

        // If test class manages cucumber features then this method has nothing to do left.
        // QTAF provides own listener for cucumber tests.
        if (cucumberOptions != null) {
            return;
        }

        // Set logger
        /*
        testInstance
                .createAndSetNewLogCollection(
                        testScenarioLogCollection.getUniqueId(),
                        testScenarioLogCollection.getMethodId(),
                        testScenarioLogCollection.getTestId()
                );
         */

        TestScenarioLogCollection collection = TestScenarioLogCollection
                .fromQtafTestEventPayload(iQtafTestEventPayload);
        testInstance.setLogCollection(collection);
        featureLogCollection.addScenarioLogCollection(collection);

        testInstance.addLoggerToFieldsRecursively();
    }

    /**
     * This method is called every time after a test was executed successfully.
     * @param iQtafTestEventPayload   Test context object
     */
    private void onTestSuccess(IQtafTestEventPayload iQtafTestEventPayload) {
        // Check if this listener is responsible for this event
        if (!(iQtafTestEventPayload.getOriginalEvent() instanceof ITestResult)) {
            return;
        }

        ITestResult iTestResult = (ITestResult) iQtafTestEventPayload.getOriginalEvent();
        this.log(iTestResult, "success");

        // Get instance of Test class
        IQtafTestContext testInstance = (IQtafTestContext) iTestResult.getInstance();

        // If cucumber is used there will be no log collection at this stage.
        // Cucumber has its own Listener to create log collections
        if (testInstance.getLogCollection() != null) {
            // Add log information
            testInstance
                    .getLogCollection()
                    .setStatus(TestScenarioLogCollection.Status.SUCCESS)
                    .setEnd(new Date(iTestResult.getEndMillis()))
                    .setDuration(iTestResult.getEndMillis() - iTestResult.getStartMillis());
        }

        // Update end date
        testSuiteLogCollection.setEnd(new Date());
    }

    /**
     * This method is called every time after a test has failed
     * @param iQtafTestEventPayload   Test context object
     */
    private void onTestFailure(IQtafTestEventPayload iQtafTestEventPayload) {
        // Check if this listener is responsible for this event
        if (!(iQtafTestEventPayload.getOriginalEvent() instanceof ITestResult)) {
            return;
        }

        ITestResult iTestResult = (ITestResult) iQtafTestEventPayload.getOriginalEvent();

        this.log(iTestResult, "failure");

        // Get instance of Test class
        IQtafTestContext testInstance = (IQtafTestContext) iTestResult.getInstance();

        // Add log information
        testInstance
                .getLogCollection()
                .setStatus(TestScenarioLogCollection.Status.FAILURE)
                .setEnd(new Date(iTestResult.getEndMillis()))
                .setDuration(iTestResult.getEndMillis() - iTestResult.getStartMillis());

        // Update end date
        testSuiteLogCollection.setEnd(new Date());
    }

    /**
     * This method is called every time after a test has failed but its success value is within a given percentage
     * @param iQtafTestEventPayload   Test context object
     */
    private void onTestFailedButWithinSuccessPercentage(IQtafTestEventPayload iQtafTestEventPayload) {
        // Check if this listener is responsible for this event
        if (!(iQtafTestEventPayload.getOriginalEvent() instanceof ITestResult)) {
            return;
        }

        ITestResult iTestResult = (ITestResult) iQtafTestEventPayload.getOriginalEvent();

        this.log(iTestResult, "failure but within success percentage");

        // Update end date
        testSuiteLogCollection.setEnd(new Date());
    }

    /**
     * This method is called every time a test is skipped
     * @param iQtafTestEventPayload   Test context object
     */
    private void onTestSkipped(IQtafTestEventPayload iQtafTestEventPayload) {
        // Check if this listener is responsible for this event
        if (!(iQtafTestEventPayload.getOriginalEvent() instanceof ITestResult)) {
            return;
        }

        ITestResult iTestResult = (ITestResult) iQtafTestEventPayload.getOriginalEvent();

        this.log(iTestResult, "failure");

        // Get instance of Test class
        IQtafTestContext testInstance = (IQtafTestContext) iTestResult.getInstance();

        // Add log information
        testInstance
                .getLogCollection()
                .setStatus(TestScenarioLogCollection.Status.SKIPPED)
                .setEnd(new Date(iTestResult.getEndMillis()))
                .setDuration(iTestResult.getEndMillis() - iTestResult.getStartMillis());

        // Update end date
        testSuiteLogCollection.setEnd(new Date());
    }

    /**
     * Internal helper method for generating log messages
     * @param iTestResult   Test result object
     * @param message       Log message
     */
    private void log(ITestResult iTestResult, String message) {
        logger.info(
                "[Test] " +
                        "[" + iTestResult.hashCode() + "] " +
                        "[" + TestResultHelper.getTestContextInstance(iTestResult).getClass().getName() + "] " +
                        message
        );
    }

    /**
     * Log error
     * @param e Exception
     */
    private void logError(Throwable e) {
        QtafFactory.getLogger().error(e);
    }
}
