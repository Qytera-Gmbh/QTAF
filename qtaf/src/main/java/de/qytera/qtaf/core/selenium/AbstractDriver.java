package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import de.qytera.qtaf.core.log.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

/**
 * Abstract driver class that all driver classes inherit from
 */
public abstract class AbstractDriver {

    /**
     * Configuration
     */
    protected ConfigMap configMap = QtafFactory.getConfiguration();

    /**
     * Logger
     */
    protected static Logger logger = QtafFactory.getLogger();

    /**
     * Get Driver name
     * @return driver name
     */
    public abstract String getName();

    /**
     * Get Selenium WebDriver object
     * @return  selenium web driver object
     */
    public abstract WebDriver getDriver();

    /**
     * Log an info message
     * @param message   Log message
     */
    static void logInfo(String message) {
        logger.info("[DriverFactory] " + message);
    }

    /**
     * Get driver capabilities
     * @return driver capabilities
     */
    protected abstract Capabilities getCapabilities();

    /**
     * Initialize web driver manager
     * @param webDriverManager  web driver manager
     */
    protected void initWebDriverManager(WebDriverManager webDriverManager) {
        setDriverVersion(webDriverManager);
        webDriverManager.setup();
    }

    /**
     * Set web driver version
     * @param webDriverManager  web driver object
     */
    protected void setDriverVersion(WebDriverManager webDriverManager) {
        // Set driver version
        if (SeleniumDriverConfigHelper.getDriverVersion() != null) {
            webDriverManager.driverVersion(SeleniumDriverConfigHelper.getDriverVersion());
        }
    }
}
