package de.qytera.qtaf.core.event_subscriber.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.error.LoggingError;
import de.qytera.qtaf.core.log.model.error.TestError;
import de.qytera.qtaf.core.log.service.LogFileWriter;
import de.qytera.qtaf.core.selenium.AbstractDriver;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestStepEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;

import java.util.Date;

/**
 * This subscriber is responsible for adding log messages to the corresponding log collections
 */
public class QtafScenarioLoggingSubscriber implements IEventSubscriber {
    /**
     * Reference to global log collection
     */
    private static final TestSuiteLogCollection testSuiteLogCollection = QtafFactory.getTestSuiteLogCollection();

    @Override
    public void initialize() {
        QtafEvents.afterDriverInitialization.subscribe(
                this::onDriverInitialized,
                this::handleTestError
        );
        QtafEvents.startTesting.subscribe(
                this::onStartTesting,
                this::handleTestError
        );
        QtafEvents.testStarted.subscribe(
                this::onTestStarted,
                this::handleTestError
        );
        QtafEvents.testSuccess.subscribe(
                this::onTestSuccess,
                this::handleTestError
        );
        QtafEvents.testFailure.subscribe(
                this::onTestFailure,
                this::handleTestError
        );
        QtafEvents.testSkipped.subscribe(
                this::onTestSkipped,
                this::handleTestError
        );
        QtafEvents.stepLog.subscribe(
                this::onStepLog,
                this::handleTestError
        );
        QtafEvents.finishedTesting.subscribe(
                this::onFinishTesting,
                this::handleTestError
        );
        QtafEvents.beforeLogsPersisted.subscribe(
                this::onBeforeLogsPersisted,
                this::handleLoggingError
        );
    }

    /**
     * Handle step event
     * @param iQtafTestStepEventPayload event payload
     */
    private void onStepLog(IQtafTestStepEventPayload iQtafTestStepEventPayload) {
        QtafFactory.getLogger().debug(String.format("--- Step log received: scenario=%s", iQtafTestStepEventPayload.getScenarioId()));
        // Get Scenario log collection
        TestScenarioLogCollection scenarioLogCollection = ScenarioLogCollectionIndex
                .getInstance()
                .get(iQtafTestStepEventPayload.getScenarioId());

        // Add log message to scenario log collection
        scenarioLogCollection.addLogMessage(iQtafTestStepEventPayload.getLogMessage());
    }

    /**
     * React to driver initialization
     * @param abstractDriver    driver information
     */
    private void onDriverInitialized(AbstractDriver abstractDriver) {
        // Add log information
        testSuiteLogCollection
                .setDriverName(abstractDriver.getName());
    }


    /**
     * React to start of the testing process. This method is called before the first test execution.
     * @param testContext  Test context object
     */
    private void onStartTesting(IQtafTestingContext testContext) {
        // Add log information
        testSuiteLogCollection
                .setStart(testContext.getStartDate())
                .setEnd(testContext.getEndDate());

        TestSuiteLogCollection.SuiteInfo suiteInfo = testSuiteLogCollection.getSuiteInfo();

        suiteInfo
                .setName(testContext.getSuiteName())
                .setOutputDir(testContext.getLogDirectory());
    }

    /**
     * This method is executed when a test was executed successfully
     * @param iQtafTestEventPayload event payload
     */
    private void onTestStarted(IQtafTestEventPayload iQtafTestEventPayload) {
        TestFeatureLogCollection featureLogCollection = QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                iQtafTestEventPayload.getFeatureId(),
                iQtafTestEventPayload.getFeatureName()
        );

        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection
                .fromQtafTestEventPayload(iQtafTestEventPayload)
                .setStatus(TestScenarioLogCollection.Status.PENDING)
                .setEnd(new Date());

        featureLogCollection.addScenarioLogCollection(scenarioLogCollection);
    }

    /**
     * This method is executed when a test was executed successfully
     * @param iQtafTestEventPayload event payload
     */
    private void onTestSuccess(IQtafTestEventPayload iQtafTestEventPayload) {
        TestFeatureLogCollection featureLogCollection = QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                iQtafTestEventPayload.getFeatureId(),
                iQtafTestEventPayload.getFeatureName()
        );

        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection
                .fromQtafTestEventPayload(iQtafTestEventPayload)
                .setStatus(TestScenarioLogCollection.Status.SUCCESS)
                .setEnd(new Date());

        featureLogCollection.addScenarioLogCollection(scenarioLogCollection);
    }

    /**
     * This method is executed when a test failed
     * @param iQtafTestEventPayload event payload
     */
    private void onTestFailure(IQtafTestEventPayload iQtafTestEventPayload) {
        TestFeatureLogCollection featureLogCollection = QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                iQtafTestEventPayload.getFeatureId(),
                iQtafTestEventPayload.getScenarioName()
        );

        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection
                .fromQtafTestEventPayload(iQtafTestEventPayload)
                .setStatus(TestScenarioLogCollection.Status.FAILURE)
                .setEnd(new Date());

        featureLogCollection.addScenarioLogCollection(scenarioLogCollection);
    }

    /**
     * This method is executed when a test was skipped
     * @param iQtafTestEventPayload event payload
     */
    private void onTestSkipped(IQtafTestEventPayload iQtafTestEventPayload) {
        TestFeatureLogCollection featureLogCollection = QtafFactory.getTestSuiteLogCollection().createFeatureIfNotExists(
                iQtafTestEventPayload.getFeatureId(),
                iQtafTestEventPayload.getScenarioName()
        );

        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection
                .fromQtafTestEventPayload(iQtafTestEventPayload)
                .setStatus(TestScenarioLogCollection.Status.SKIPPED)
                .setEnd(new Date());

        featureLogCollection.addScenarioLogCollection(scenarioLogCollection);
    }

    /**
     * This method is executed before the created log messages of QTAF are persisted.
     */
    private void onFinishTesting(IQtafTestingContext testingContext) {
        // Add test duration information
        testSuiteLogCollection
                .setDuration(
                        testSuiteLogCollection.getEnd().getTime()
                                - testSuiteLogCollection.getStart().getTime()
                );

        ErrorLogCollection errorLogCollection = ErrorLogCollection.getInstance();

        if (!errorLogCollection.isEmpty()) {
            LogFileWriter.persistErrorLogs(errorLogCollection);
        }
    }

    /**
     * This method is executed before the created log messages of QTAF are persisted.
     */
    private void onBeforeLogsPersisted(TestSuiteLogCollection c) {
        QtafFactory.getLogger().debug(String.format("[%s] onBeforeLogsPersisted", this.getClass().getName()));
        // Add test duration information
        testSuiteLogCollection
                .setDuration(
                        new Date().getTime()
                                - testSuiteLogCollection.getStart().getTime()
                );
    }

    /**
     * Handle exceptions that occurred during invocation of subscriber method
     * @param e Exception object
     */
    private void handleTestError(Throwable e) {
        TestError testError = new TestError(e);
        ErrorLogCollection errors = ErrorLogCollection.getInstance();
        errors.addErrorLog(testError);
    }

    /**
     * Handle exceptions that occurred during invocation of subscriber method
     * @param e Exception object
     */
    private void handleLoggingError(Throwable e) {
        LoggingError loggingError = new LoggingError(e);
        ErrorLogCollection errors = ErrorLogCollection.getInstance();
        errors.addErrorLog(loggingError);
    }
}
