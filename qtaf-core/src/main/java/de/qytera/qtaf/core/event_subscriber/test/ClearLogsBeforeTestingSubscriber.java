package de.qytera.qtaf.core.event_subscriber.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.error.FrameworkInitializationErrorLog;
import de.qytera.qtaf.core.log.model.error.TestError;

public class ClearLogsBeforeTestingSubscriber implements IEventSubscriber {

    @Override
    public void initialize() {
        QtafEvents.frameworkInitialized.subscribe(
                this::onFrameworkInitialized,
                this::handleError
        );
    }

    /**
     * Handle onFrameworkInitialized event
     * @param x event payload
     */
    private void onFrameworkInitialized(Void x) {
        QtafFactory.getLogger().info("Clear log data");
        TestSuiteLogCollection.getInstance().clearCollection();
        TestFeatureLogCollection.clearIndex();
        TestScenarioLogCollection.clearIndex();
    }

    /**
     * Handle exceptions that occurred during invocation of subscriber method
     * @param e Exception object
     */
    private void handleError(Throwable e) {
        FrameworkInitializationErrorLog error = new FrameworkInitializationErrorLog(e);
        ErrorLogCollection errors = ErrorLogCollection.getInstance();
        errors.addErrorLog(error);
    }

}