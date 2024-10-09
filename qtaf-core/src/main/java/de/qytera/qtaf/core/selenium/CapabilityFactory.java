package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
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
        return getCapabilitiesChromeRemote();
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

        Map<String, Object> prefs = SeleniumDriverConfigHelper.getDriverPreferences();
        parseDownloadDirectoryChrome(options, prefs);
        enableDownloadOnRemoteChromeDriver(options);
        setPreferencesForDownloadOnRemoteChromeDriver(prefs);

        return options;
    }

    /**
     * This method enables file downloads when using a remote chrome driver.
     * It sets the 'setEnableDownloads' property of the provided ChromiumOptions object to true.
     * This allows files to be downloaded when running tests on a remote driver which is needed to transfer the files to the local machine.
     * <a href="https://www.selenium.dev/documentation/webdriver/drivers/remote_webdriver/#:~:text=options.setEnableDownloads(true)%3B">Also see the Selenium documentation</a>
     *
     * @param options The ChromiumOptions object for the remote driver.
     */
    private static  void enableDownloadOnRemoteChromeDriver(ChromiumOptions<?> options){
        options.setEnableDownloads(true);
    }

    /**
     * This method sets preferences for file downloads when using a remote Chrome driver.
     * It modifies the provided map of preferences to disable various download prompts and popups, and to specify the types
     * of files that can be automatically downloaded. This should allow files to be downloaded when running tests on a remote driver without user
     * interactions. However, it may depend on the client and browser whether this ultimately works. If necessary, the prefs must be adapted to the specific case via the qtaf.json.
     *
     * @param prefs A map of preferences for the remote driver. This map is modified by the method.
     */
    private static void setPreferencesForDownloadOnRemoteChromeDriver(Map<String, Object> prefs){
        if (prefs instanceof Map<String, Object>) {
            prefs = new HashMap<>(prefs);
            if (prefs.get("download") instanceof Map<?, ?> && ((Map<?, ?>) prefs.get("download")).get("default_directory") instanceof String) {
                // Disable download prompts and popups
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.prompt_for_download", false);
                prefs.put("safebrowsing.enabled", false);
                prefs.put("browser.download.panel.shown", false);

                // Specify the types of files that can be automatically downloaded
                prefs.put("browser.helperApps.neverAsk.openFile","text/csv,application/vnd.ms-excel");
                prefs.put("browser.helperApps.neverAsk.saveToDisk", "application/msword, application/csv, application/ris, text/csv, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");

                // Disable various download manager settings
                prefs.put("browser.download.manager.showWhenStarting", false);
                prefs.put("browser.download.manager.alertOnEXEOpen", false);
                prefs.put("browser.download.manager.focusWhenStarting", false);
                prefs.put("browser.download.folderList", 2);
                prefs.put("browser.download.useDownloadDir", true);
                prefs.put("browser.helperApps.alwaysAsk.force", false);
                prefs.put("browser.download.manager.closeWhenDone", true);
                prefs.put("browser.download.manager.showAlertOnComplete", false);
                prefs.put("browser.download.manager.useWindow", false);
                prefs.put("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);

                // Disable the built-in PDF viewer
                prefs.put("pdfjs.disabled", true);
            }
        }
    }
    /**
     * This method sets the download directory for Chrome driver.
     *
     * @param options The ChromiumOptions object for the Chrome driver.
     * @param prefs   A map of preferences for the Chrome driver. This map is checked and potentially modified by the method.
     */
    private static void parseDownloadDirectoryChrome(ChromiumOptions<?> options, Map<String, Object> prefs) {
        if (prefs instanceof Map<String, Object>) {
            prefs = new HashMap<>(prefs);
            if (prefs.get("download") instanceof Map<?,?> && ((Map<?, ?>) prefs.get("download")).get("default_directory") instanceof String) {
                Map<String, Object> download = (Map<String, Object>) prefs.get("download");
                download = new HashMap<>(download);
                String defaultDirectory = (String) download.get("default_directory");
                defaultDirectory = DirectoryHelper.preparePath(defaultDirectory);
                download.put("default_directory", defaultDirectory);
                prefs.put("download", download);
            }
            options.setExperimentalOption("prefs", prefs);
        }
    }

    /**
     * Get Edge driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static EdgeOptions getCapabilitiesEdge() {
        useJdkHttpClient();
        return getCapabilitiesEdgeRemote();
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

        Map<String, Object> prefs = SeleniumDriverConfigHelper.getDriverPreferences();
        parseDownloadDirectoryChrome(options, prefs);

        return options;
    }

    /**
     * Get Firefox driver capabilities. Additional configurations will be read from the QTAF configuration file.
     *
     * @return the capabilities
     */
    public static FirefoxOptions getCapabilitiesFirefox() {
        useJdkHttpClient();
        return getCapabilitiesFirefoxRemote();
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
        Map<String, Object> prefs = (Map<String, Object>) ConfigurationFactory.getInstance().getValue("driver.preferences", Map.class);
        prefs = new HashMap<>(prefs);
        if (!prefs.isEmpty()) {
            String defaultDirectory = (String) prefs.get("browser.download.dir");
            defaultDirectory = DirectoryHelper.preparePath(defaultDirectory);
            prefs.put("browser.download.dir", defaultDirectory);
            FirefoxProfile profile = new FirefoxProfile();

            prefs.forEach(profile::setPreference);
            options.setProfile(profile);
        }
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
