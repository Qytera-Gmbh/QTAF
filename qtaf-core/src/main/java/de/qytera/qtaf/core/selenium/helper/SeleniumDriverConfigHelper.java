package de.qytera.qtaf.core.selenium.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for getting selenium driver configuration values.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeleniumDriverConfigHelper {

    /**
     * The implicit driver wait timeout.
     */
    public static final String DRIVER_IMPLICIT_WAIT_TIMEOUT = "driver.implicitWaitTimeout";
    /**
     * A driver remote URL.
     */
    public static final String DRIVER_REMOTE_URL = "driver.remoteUrl";
    /**
     * The driver's platform.
     */
    public static final String DRIVER_PLATFORM = "driver.platform";
    /**
     * The driver's version.
     */
    public static final String DRIVER_VERSION = "driver.version";
    /**
     * Additional driver options to consider during driver instantiation.
     */
    public static final String DRIVER_OPTIONS = "driver.options";
    /**
     * Additional driver capabilities to consider during driver instantiation.
     */
    public static final String DRIVER_CAPABILITIES = "driver.capabilities";
    /**
     * Additional driver preferences to consider during driver instantiation. When using Firefox, they will be inserted
     * into a {@link FirefoxProfile}. For Chromium, they will be inserted using
     * {@link ChromeOptions#setExperimentalOption(String, Object) experimental options}.
     */
    public static final String DRIVER_PREFERENCES = "driver.preferences";
    /**
     * Whether the driver should quit after testing.
     */
    public static final String DRIVER_QUIT_AFTER_TESTING = "driver.quitAfterTesting";
    /**
     * Whether Selenium should take a screenshot before each scenario.
     */
    public static final String SCREENSHOTS_BEFORE_SCENARIO = "driver.screenshots.beforeScenario";
    /**
     * Whether Selenium should take a screenshot after each scenario.
     */
    public static final String SCREENSHOTS_AFTER_SCENARIO = "driver.screenshots.afterScenario";
    /**
     * Whether Selenium should take a screenshot before each step.
     */
    public static final String SCREENSHOTS_BEFORE_STEP = "driver.screenshots.beforeStep";
    /**
     * Whether Selenium should take a screenshot after each step.
     */
    public static final String SCREENSHOTS_AFTER_STEP = "driver.screenshots.afterStep";
    /**
     * Whether Selenium should take a screenshot on step failure.
     */
    public static final String SCREENSHOTS_AFTER_STEP_FAILURE = "driver.screenshots.afterStepFailure";

    /**
     * Config.
     */
    private static ConfigMap config = QtafFactory.getConfiguration();

    /**
     * Retrieves the configured implicit timeout. Defaults to 30 seconds if no implicit timeout has been specified.
     *
     * @return the implicit timeout
     * @see <a href="https://www.selenium.dev/documentation/webdriver/waits/#implicit-wait">https://www.selenium.dev/documentation/webdriver/waits/#implicit-wait</a>
     */
    public static int getImplicitTimeout() {
        // Set implicit wait to 30 seconds by default.
        return config.getInt(DRIVER_IMPLICIT_WAIT_TIMEOUT, 30);
    }

    /**
     * Get remote driver URL.
     *
     * @return remote URL
     */
    public static URL getRemoteUrl() {
        String url = config.getString(DRIVER_REMOTE_URL);
        try {
            if (url == null) {
                throw new MalformedURLException(
                        "Failed to get remote driver URL, configuration key '%s' is null".formatted(DRIVER_REMOTE_URL)
                );
            }
            return new URL(url);
        } catch (MalformedURLException e) {
            QtafFactory.getLogger().fatal("The given remote driver url is malformed: %s".formatted(url));
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Get Platform Name.
     *
     * @return platform name
     */
    public static String getPlatformName() {
        return config.getString(DRIVER_PLATFORM);
    }

    /**
     * Get Driver version.
     *
     * @return driver version
     */
    public static String getDriverVersion() {
        return config.getString(DRIVER_VERSION);
    }

    /**
     * Returns the configured driver options to consider during driver instantiation.
     *
     * @return the driver options
     */
    public static List<String> getDriverOptions() {
        return config.getList(DRIVER_OPTIONS).stream()
                .filter(JsonPrimitive.class::isInstance)
                .map(JsonPrimitive.class::cast)
                .filter(JsonPrimitive::isString)
                .map(JsonPrimitive::getAsString)
                .toList();
    }

    /**
     * Returns the configured driver capabilities to consider during driver instantiation.
     *
     * @return the driver capabilities
     */
    public static Capabilities getDriverCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        toPrimitive(config.getMap(DRIVER_CAPABILITIES)).forEach(capabilities::setCapability);
        return ImmutableCapabilities.copyOf(capabilities);
    }

    /**
     * Returns the configured driver preferences to consider during driver instantiation.
     *
     * @return the driver preferences
     */
    public static Map<String, Object> getDriverPreferences() {
        return toPrimitive(config.getMap(DRIVER_PREFERENCES));
    }

    private static Map<String, Object> toPrimitive(Map<String, JsonElement> map) {
        Map<String, Object> primitiveMap = new HashMap<>();
        map.forEach((key, element) -> {
            if (element instanceof JsonPrimitive primitive) {
                primitiveMap.put(key, toPrimitive(primitive));
            } else if (element instanceof JsonArray array) {
                primitiveMap.put(key, toPrimitive(array.asList()));
            } else if (element instanceof JsonObject object) {
                primitiveMap.put(key, toPrimitive(object.asMap()));
            }
        });
        return primitiveMap;
    }

    private static List<Object> toPrimitive(List<JsonElement> array) {
        List<Object> primitiveArray = new ArrayList<>();
        array.forEach(element -> {
            if (element instanceof JsonPrimitive primitive) {
                primitiveArray.add(toPrimitive(primitive));
            } else if (element instanceof JsonArray nestedArray) {
                primitiveArray.add(toPrimitive(nestedArray.asList()));
            } else if (element instanceof JsonObject nestedObject) {
                primitiveArray.add(toPrimitive(nestedObject.asMap()));
            }
        });
        return primitiveArray;
    }

    private static Object toPrimitive(JsonPrimitive element) {
        if (element.isBoolean()) {
            return element.getAsBoolean();
        } else if (element.isNumber()) {
            try {
                return Long.parseLong(element.getAsString());
            } catch (NumberFormatException exception) {
                return Double.parseDouble(element.getAsString());
            }
        }
        return element.getAsString();
    }

    /**
     * Determine if QTAF should quit driver after testing.
     *
     * @return value from configuration
     */
    public static boolean shouldQuitDriverAfterTesting() {
        return config.getBoolean(DRIVER_QUIT_AFTER_TESTING);
    }

    /**
     * Determine if screenshots should be taken before a scenario.
     *
     * @return value from configuration
     */
    public static boolean shouldTakeScreenshotsBeforeScenario() {
        return config.getBoolean(SCREENSHOTS_BEFORE_SCENARIO);
    }

    /**
     * Determine if screenshots should be taken after a scenario.
     *
     * @return value from configuration
     */
    public static boolean shouldTakeScreenshotsAfterScenario() {
        return config.getBoolean(SCREENSHOTS_AFTER_SCENARIO);
    }

    /**
     * Determine if screenshots should be taken before a step.
     *
     * @return value from configuration
     */
    public static boolean shouldTakeScreenshotsBeforeStep() {
        return config.getBoolean(SCREENSHOTS_BEFORE_STEP);
    }

    /**
     * Determine if screenshots should be taken after a step.
     *
     * @return value from configuration
     */
    public static boolean shouldTakeScreenshotsAfterStep() {
        return config.getBoolean(SCREENSHOTS_AFTER_STEP);
    }

    /**
     * Determine if screenshots should be taken after a step failure.
     *
     * @return value from configuration
     */
    public static boolean shouldTakeScreenshotsAfterStepFailure() {
        return config.getBoolean(SCREENSHOTS_AFTER_STEP_FAILURE);
    }
}
