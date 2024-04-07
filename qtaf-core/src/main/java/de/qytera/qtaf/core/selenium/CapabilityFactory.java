package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

/**
 * A factory class for constructing {@link org.openqa.selenium.Capabilities} for different browser drivers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CapabilityFactory {

    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    /**
     * Get Chrome driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static ChromeOptions getCapabilitiesChrome() {
        useJdkHttpClient();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    /**
     * Get Chrome remote driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static ChromeOptions getCapabilitiesChromeRemote() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    /**
     * Get Edge driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static EdgeOptions getCapabilitiesEdge() {
        useJdkHttpClient();
        EdgeOptions options = new EdgeOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    /**
     * Get Edge remote driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static EdgeOptions getCapabilitiesEdgeRemote() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    /**
     * Get Firefox driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static FirefoxOptions getCapabilitiesFirefox() {
        useJdkHttpClient();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    /**
     * Get Firefox remote driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static FirefoxOptions getCapabilitiesFirefoxRemote() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    /**
     * Get Internet Explorer driver capabilities. Additional configurations will be read from the QTAF configuration
     * file.
     *
     * @return the capabilities
     */
    public static InternetExplorerOptions getCapabilitiesInternetExplorer() {
        InternetExplorerOptions caps = new InternetExplorerOptions();
        caps.setCapability("ignoreZoomSetting", true);
        caps.addCommandSwitches(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        caps = caps.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return caps;
    }

    /**
     * Get Android driver capabilities. Additional configurations will be read from the QTAF configuration
     * file.
     *
     * @return the capabilities
     */
    public static Capabilities getCapabilitiesAndroid() {
        DesiredCapabilities dc = new DesiredCapabilities(
                Map.of(
                        MobileCapabilityType.DEVICE_NAME, CONFIG.getString("appium.capabilities.deviceName"),
                        MobileCapabilityType.UDID, CONFIG.getString("appium.capabilities.udid"),
                        CapabilityType.BROWSER_VERSION, CONFIG.getString("appium.capabilities.androidVersion"),
                        CapabilityType.PLATFORM_NAME, CONFIG.getString("appium.capabilities.platformName"),
                        "appPackage", CONFIG.getString("appium.capabilities.appPackage"),
                        "appActivity", CONFIG.getString("appium.capabilities.appActivity")
                )
        );
        dc = dc.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return dc;
    }

    /**
     * Get Saucelabs driver capabilities. Additional configurations will be read from the QTAF configuration
     * file.
     *
     * @return the capabilities
     */
    public static Capabilities getCapabilitiesSaucelabs() {
        MutableCapabilities capabilities = new MutableCapabilities(
                Map.of(
                        "browserName", CONFIG.get("sauce.browserName"),
                        "browserVersion", SeleniumDriverConfigHelper.getDriverVersion(),
                        "platformName", SeleniumDriverConfigHelper.getPlatformName(),
                        "sauce:options", Map.of(
                                "username", CONFIG.getString("sauce.username"),
                                "accesskey", CONFIG.getString("sauce.accessKey")
                        )
                )
        );
        capabilities = capabilities.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return capabilities;
    }

    private static void useJdkHttpClient() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
    }

}
