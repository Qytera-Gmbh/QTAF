package de.qytera.qtaf.core.selenium;

import com.google.inject.Provides;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.log.model.error.DriverInitializationError;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.reflection.ClassLoader;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import de.qytera.qtaf.core.log.Logger;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

/**
 * Driver factory class
 */
public class DriverFactory {
    /**
     * Selenium Web Driver object
     */
    private static WebDriver driver;

    /**
     * Error logs
     */
    private static final ErrorLogCollection errorLogCollection = ErrorLogCollection.getInstance();

    /**
     * Configuration dictionary
     */
    private static final ConfigMap config = QtafFactory.getConfiguration();

    /**
     * Logger
     */
    private static Logger logger = QtafFactory.getLogger();

    /**
     * Get default web driver instance. The default driver name is fetched from the QTAF configuration.
     * @return  web driver
     */
    @Provides
    public static WebDriver getDriver() {
        // Get driver name from configuration
        String driverName = config.getString("driver.name", "chrome");
        return DriverFactory.getDriver(driverName);
    }

    /**
     * Get web driver instance
     * @param driverName    Driver name
     * @return  Selenium WebDriver instance
     */
    @Provides
    public static WebDriver getDriver(String driverName) {
        // Try to load driver from internal cache
        if (driver != null) {
            return driver;
        }

        // Get instances of all driver factory classes
        Object[] lst = ClassLoader.getInstancesOfDirectSubtypesOf(AbstractDriver.class);
        AbstractDriver d = null;

        // Look for a driver class that matches the given driver name
        try {
            for (Object o : lst) {
                // Cast driver object
                d = (AbstractDriver) o;

                if (d.getName() != null && d.getName().equals(driverName)) {
                    driver = d.getDriver();
                    break;
                }
            }
        } catch (Throwable e) {
            // Add error log
            errorLogCollection.addErrorLog(new DriverInitializationError(e));

            // Send error event
            QtafEvents.afterDriverInitialization.onError(e);

            // Shut down process
            logError("Error: Driver initialization failed");
            e.printStackTrace();
            System.exit(1);
        }

        logInfo("Use driver " + driverName);

        // Initialize driver
        if (driver != null){
            logInfo("Driver initialized: " + driverName);
            QtafEvents.afterDriverInitialization.onNext(d);
        } else {
            logError("Driver could not be initialized");
        }

        return configureDriver(driver);
    }

    /**
     * Restart web driver instance, use default driver name
     * @param restart       true if new driver object should be created
     * @return  Selenium WebDriver instance
     */
    @Provides
    public static WebDriver getDriver(boolean restart) {
        // Check if driver was
        if (driver != null) {
            driver.quit();
            driver = null;
        }

        return getDriver();
    }

    /**
     * Driver builder function, configures properties of driver object
     * @param d Driver object
     * @return  Driver object
     */
    protected static WebDriver configureDriver(WebDriver d) {
        // Check if driver is null
        assert driver != null;

        // Set implicit wait
        d.manage().timeouts().implicitlyWait(SeleniumDriverConfigHelper.getImplicitTimeout(), TimeUnit.SECONDS);

        logInfo("Driver configured");

        return d;
    }


    /**
     * Restart web driver instance
     * @param driverName    Driver name
     * @param restart       true if new driver object should be created
     * @return  Selenium WebDriver instance
     */
    @Provides
    public static WebDriver getDriver(String driverName, boolean restart) {
        // Check if driver was
        if (driver != null) {
            driver.quit();
            driver = null;
        }

        return getDriver(driverName);
    }

    /**
     * Clear driver instance
     */
    public static void clearDriver() {
        driver = null;
    }

    /**
     * Log an info message
     * @param message   Log message
     */
    private static void logInfo(String message) {
        logger.info("[DriverFactory] " + message);
    }

    /**
     * Log an info message
     * @param message   Log message
     */
    private static void logError(String message) {
        logger.error("[DriverFactory] " + message);
    }

}
