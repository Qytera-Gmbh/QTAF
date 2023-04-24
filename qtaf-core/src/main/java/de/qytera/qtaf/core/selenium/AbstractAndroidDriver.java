package de.qytera.qtaf.core.selenium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for managing android devices
 */
public abstract class AbstractAndroidDriver extends AbstractDriver {

    /**
     * Creates a new android driver.
     */
    protected AbstractAndroidDriver() {
        super(false);
    }

    /**
     * Get driver capabilities
     *
     * @return driver capabilities
     */
    protected DesiredCapabilities getCapabilities() {
        DesiredCapabilities dc = new DesiredCapabilities();
        logDesiredCapability(MobileCapabilityType.DEVICE_NAME, CONFIG.getString("appium.capabilities.deviceName"));
        logDesiredCapability(CapabilityType.PLATFORM_NAME, CONFIG.getString("appium.capabilities.platformName"));
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, CONFIG.getString("appium.capabilities.deviceName"));
        dc.setCapability(CapabilityType.PLATFORM_NAME, CONFIG.getString("appium.capabilities.platformName"));
        return dc;
    }

    /**
     * Creates a log message describing the desired capability and its desired value.
     *
     * @param capability the desired capability
     * @param value      the desired value
     */
    protected void logDesiredCapability(String capability, String value) {
        logInfo(String.format("[DesiredCapabilities] %s: %s", capability, value));
    }

    /**
     * Get browser capabilities
     *
     * @param browserName name of the browser
     * @return browser capabilities
     */
    protected DesiredCapabilities getDesiredCapabilitiesBrowser(String browserName) {
        DesiredCapabilities dc = getCapabilities();
        logDesiredCapability(CapabilityType.BROWSER_NAME, browserName);
        dc.setCapability(CapabilityType.BROWSER_NAME, browserName);

        return dc;
    }

    /**
     * Get android driver
     *
     * @param dc capabilities
     * @return android capabilities
     */
    protected AndroidDriver getAndroidDriver(DesiredCapabilities dc) {
        try {
            logInfo("[Android Driver] " + "URL" + ": " + CONFIG.getString("appium.driverSettings.url"));
            return new AndroidDriver(new URL(CONFIG.getString("appium.driverSettings.url")), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
