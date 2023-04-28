package de.qytera.qtaf.core.event_subscriber.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.error.LoggingError;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.WebDriver;

/**
 * A subscriber listening for driver quits.
 */
public class QuitDriverSubscriber implements IEventSubscriber {
    /**
     * Configuration
     */
    ConfigMap configMap = QtafFactory.getConfiguration();

    @Override
    public void initialize() {
        QtafEvents.finishedTesting.subscribe(
                this::onFinishedTesting,
                this::handleLoggingError
        );
    }

    /**
     * onFinishedTesting event handler
     *
     * @param iTestContext event payload
     */
    private void onFinishedTesting(IQtafTestingContext iTestContext) {
        // Quit the driver after testing
        if (SeleniumDriverConfigHelper.shouldQuitDriverAfterTesting()) {
            WebDriver driver = QtafFactory.getWebDriver();

            try {
                driver.manage().deleteAllCookies();
            } catch (Exception e) {
            }

            driver.quit();
        }
    }

    /**
     * Handle exceptions that occurred during invocation of subscriber method
     *
     * @param e Exception object
     */
    private void handleLoggingError(Throwable e) {
        LoggingError loggingError = new LoggingError(e);
        ErrorLogCollection errors = ErrorLogCollection.getInstance();
        errors.addErrorLog(loggingError);
    }
}
