package de.qytera.qtaf.core;

import com.google.gson.Gson;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.selenium.DriverFactory;
import org.openqa.selenium.WebDriver;

/**
 * Factory Class
 */
public class QtafFactory {
    private QtafFactory() {}
    /**
     * Get configuration
     *
     * @return Configuration
     */
    public static ConfigMap getConfiguration() {
        return ConfigurationFactory.getInstance();
    }

    /**
     * Get Test suite log collection
     *
     * @return test suite log collection
     */
    public static synchronized TestSuiteLogCollection getTestSuiteLogCollection() {
        return TestSuiteLogCollection.getInstance();
    }

    /**
     * Get Selenium Web Driver instance
     *
     * @return Selenium Web Driver
     */
    public static WebDriver getWebDriver() {
        return DriverFactory.getDriver();
    }

    /**
     * Get logger
     *
     * @return logger
     */
    public static Logger getLogger() {
        return Logger.getInstance();
    }

    /**
     * Get GSON instance
     *
     * @return GSON instance
     */
    public static Gson getGson() {
        return GsonFactory.getInstance();
    }
}
