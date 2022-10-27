package de.qytera.qtaf.core.selenium.helper;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper class for getting selenium driver configuration values
 */
public class SeleniumDriverConfigHelper {

    /**
     * Config keys
     */
    public static final String DRIVER_IMPLICIT_WAIT_TIMEOUT = "driver.implicitWaitTimeout";
    public static final String DRIVER_REMOTE_URL = "driver.remoteUrl";
    public static final String DRIVER_PLATFORM = "driver.platform";
    public static final String DRIVER_VERSION = "driver.version";
    public static final String DRIVER_QUIT_AFTER_TESTING = "driver.quitAfterTesting";
    public static final String SCREENSHOTS_BEFORE_SCENARIO = "driver.screenshots.beforeScenario";
    public static final String SCREENSHOTS_AFTER_SCENARIO = "driver.screenshots.afterScenario";
    public static final String SCREENSHOTS_BEFORE_STEP = "driver.screenshots.beforeStep";
    public static final String SCREENSHOTS_AFTER_STEP = "driver.screenshots.afterStep";
    public static final String SAUCE_USERNAME = "sauce.username";
    public static final String SAUCE_ACCESS_KEY = "sauce.accessKey";
    public static final String SAUCE_BROWSER_NAME = "sauce.browserName";

    /**
     * Config
     */
    private static ConfigMap config = QtafFactory.getConfiguration();

    /**
     * Configure driver properties
     * @return  Driver object
     */
    public static int getImplicitTimeout() {
        // Set implicit wait
        int implicitTimeout = 30;

        try {
            implicitTimeout = config.getInt(DRIVER_IMPLICIT_WAIT_TIMEOUT);
        } catch (Throwable ignored) {}

        return implicitTimeout;
    }

    /**
     * Get remote driver URL
     * @return  remote URL
     */
    public static URL getRemoteUrl() {
        try {
            return new URL(config.getString(DRIVER_REMOTE_URL));
        } catch (MalformedURLException e) {
            QtafFactory.getLogger().fatal("The given driver url is malformed");
            System.exit(1);
        }

        return null;
    }

    /**
     * Get Platform Name
     * @return  platform name
     */
    public static String getPlatformName() {
        return config.getString(DRIVER_PLATFORM);
    }

    /**
     * Get Driver version
     * @return  driver version
     */
    public static String getDriverVersion() {
        return config.getString(DRIVER_VERSION);
    }

    /**
     * Get Saucelab Browser Name
     * @return  Saucelab Browser Name
     */
    public static String getSaucelabBrowserName() {
        return config.getString(SAUCE_BROWSER_NAME);
    }

    /**
     * Get Saucelab Username
     * @return  Saucelab Username
     */
    public static String getSaucelabUsername() {
        return config.getString(SAUCE_USERNAME);
    }

    /**
     * Get Saucelab Access Key
     * @return  Saucelab Access Key
     */
    public static String getSaucelabAccessKey() {
        return config.getString(SAUCE_ACCESS_KEY);
    }

    /**
     * Determine if QTAF should quit driver after testing
     * @return  value from configuration
     */
    public static boolean shouldQuitDriverAfterTesting() {
        return config.getBoolean(DRIVER_QUIT_AFTER_TESTING);
    }

    /**
     * Determine if screenshots should be taken before a scenario
     * @return  value from configuration
     */
    public static boolean shouldTakeScreenshotsBeforeScenario() {
        return config.getBoolean(SCREENSHOTS_BEFORE_SCENARIO);
    }

    /**
     * Determine if screenshots should be taken after a scenario
     * @return  value from configuration
     */
    public static boolean shouldTakeScreenshotsAfterScenario() {
        return config.getBoolean(SCREENSHOTS_AFTER_SCENARIO);
    }

    /**
     * Determine if screenshots should be taken before a step
     * @return  value from configuration
     */
    public static boolean shouldTakeScreenshotsBeforeStep() {
        return config.getBoolean(SCREENSHOTS_BEFORE_STEP);
    }

    /**
     * Determine if screenshots should be taken after a step
     * @return  value from configuration
     */
    public static boolean shouldTakeScreenshotsAfterStep() {
        return config.getBoolean(SCREENSHOTS_AFTER_STEP);
    }
}
