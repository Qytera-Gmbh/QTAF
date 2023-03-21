package de.qytera.qtaf.core.event_subscriber.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.error.TestError;
import de.qytera.qtaf.core.log.service.LogFileWriter;
import de.qytera.qtaf.core.log.Logger;

/**
 * Event subscriber that subscribes to testing finished events and creates a log file when event is dispatched
 */
public class PersistLogFileSubscriber implements IEventSubscriber {
    /**
     * This collection contains error logs that were created during the test process
     */
    private final ErrorLogCollection errorLogCollection = ErrorLogCollection.getInstance();

    /**
     * Logger
     */
    private final Logger logger = QtafFactory.getLogger();

    @Override
    public void initialize() {
        // Subscribe to initialization events
        QtafEvents.frameworkInitialized.subscribe(
                v -> {},                                    // on success
                this::handleFrameworkInitializationErrors   // on error
        );

        // Handle driver initialization events
        QtafEvents.afterDriverInitialization.subscribe(
                v -> {},                                    // on success
                this::handleDriverInitializationErrors      // on error
        );

        // Subscribe to testing finished event
        QtafEvents.finishedTesting.subscribe(
                this::handleTestFinishedEvent,  // on success
                this::handleTestError           // on error
        );
    }

    /**
     * Handle the test finished event
     * @param iTestContext  test context
     */
    private synchronized void handleTestFinishedEvent(IQtafTestingContext iTestContext) {
        // Get test suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();

        logger.debug(String.format(
                "[QTAF LogFileSubscriber] suite_hash=%s",
                suiteLogCollection.hashCode()
        ));

        logger.debug(String.format(
                "[QTAF LogFileSubscriber] received event: features=%s",
                suiteLogCollection.getTestFeatureLogCollections().size())
        );

        logger.debug(String.format(
                "[QTAF LogFileSubscriber] log_message_index_size=%s",
                LogMessageIndex.getInstance().size()
        ));

        for (TestFeatureLogCollection featureLogCollection : suiteLogCollection.getTestFeatureLogCollections()) {
            logger.debug(String.format(
                    "[QTAF LogFileSubscriber] feature: id=%s, hash=%s, scenarios=%s",
                    featureLogCollection.getFeatureId(),
                    featureLogCollection.hashCode(),
                    featureLogCollection.getScenarioLogCollection().size()
            ));

            for (TestScenarioLogCollection scenarioLogCollection : featureLogCollection.getScenarioLogCollection()) {
                logger.debug(String.format(
                        "[QTAF LogFileSubscriber] scenario: id=%s, hash=%s, steps=%s, step_list_hash=%s",
                        scenarioLogCollection.getScenarioId(),
                        scenarioLogCollection.hashCode(),
                        scenarioLogCollection.getLogMessages().size(),
                        scenarioLogCollection.getLogMessages().hashCode()
                ));
            }
        }

        // Dispatch event to inform that logs are about to be persisted
        QtafEvents.beforeLogsPersisted.onNext(suiteLogCollection);

        // Persist log messages
        String path = LogFileWriter.persistLogs(suiteLogCollection);
        logger.info("[QTAF] Log files persisted");

        // Dispatch events
        QtafEvents.logsPersisted.onNext(path);
        QtafEvents.logsPersisted.onCompleted();
    }

    /**
     * Handle errors that occurred during the framework initialization process
     * @param e Error
     */
    private void handleFrameworkInitializationErrors(Throwable e) {
        LogFileWriter.persistErrorLogs(errorLogCollection);
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
     * Handle errors that occurred during the driver initialization process
     * @param e Error
     */
    private void handleDriverInitializationErrors(Throwable e) {
        LogFileWriter.persistErrorLogs(errorLogCollection);
    }
}
