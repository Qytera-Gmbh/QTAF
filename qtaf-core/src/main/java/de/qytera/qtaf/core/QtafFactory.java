package de.qytera.qtaf.core;

import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import org.openqa.selenium.WebDriver;

/**
 * Factory Class
 */
public class QtafFactory {
    /**
     * Get configuration
     * @return  Configuration
     */
    public static ConfigMap getConfiguration() {
        return ConfigurationFactory.getInstance();
    }

    /**
     * Get Test suite log collection
     * @return  test suite log collection
     */
    public static TestSuiteLogCollection getTestSuiteLogCollection() {
        return TestSuiteLogCollection.getInstance();
    }

    /**
     * Get Selenium Web Driver instance
     * @return  Selenium Web Driver
     */
    public static WebDriver getWebDriver() {
        return DriverFactory.getDriver();
    }

    /**
     * Get logger
     * @return logger
     */
    public static Logger getLogger() {
        return Logger.getInstance();
    }
}
