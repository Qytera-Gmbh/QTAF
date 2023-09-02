package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Abstract driver class that all driver classes inherit from
 */
public abstract class AbstractDriver {

    /**
     * Configuration
     */
    protected static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    /**
     * Logger
     */
    protected static final Logger LOGGER = QtafFactory.getLogger();

    /**
     * Get Driver name
     *
     * @return driver name
     */
    public abstract String getName();

    /**
     * Get Selenium WebDriver object
     *
     * @return selenium web driver object
     */
    public final WebDriver getDriverInstance() {
        WebDriver driver = getDriver();
        if (isRemoteDriver()) {
            // See: https://www.selenium.dev/documentation/webdriver/drivers/remote_webdriver/#local-file-detector
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        }
        return driver;
    }

    /**
     * Get the concrete {@link WebDriver} instance.
     *
     * @return the instance
     */
    protected abstract WebDriver getDriver();

    /**
     * Log an info message
     *
     * @param message Log message
     */
    static void logInfo(String message) {
        LOGGER.info("[DriverFactory] " + message);
    }

    /**
     * Get driver capabilities
     *
     * @return driver capabilities
     */
    protected abstract Capabilities getCapabilities();

    /**
     * Initialize web driver manager
     *
     * @param webDriverManager web driver manager
     */
    protected void initWebDriverManager(WebDriverManager webDriverManager) {
        setDriverVersion(webDriverManager);
        webDriverManager.setup();
    }

    /**
     * Set web driver version
     *
     * @param webDriverManager web driver object
     */
    protected void setDriverVersion(WebDriverManager webDriverManager) {
        // Set driver version
        if (SeleniumDriverConfigHelper.getDriverVersion() != null) {
            webDriverManager.driverVersion(SeleniumDriverConfigHelper.getDriverVersion());
        }
    }

    /**
     * Whether the driver runs on a different machine, e.g. when using chrome-remote or firefox-remote.
     *
     * @return whether the driver runs on a different machine
     */
    protected abstract boolean isRemoteDriver();

    /**
     * Check if driver should be started in headless mode
     *
     * @return true if headless, false otherwise
     */
    protected boolean headless() {
        return ConfigurationFactory.getInstance().getBoolean("driver.headless");
    }
}
