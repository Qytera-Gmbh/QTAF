package de.qytera.qtaf.core.selenium;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This class is responsible for managing the appium android driver
 */
public class AndroidDriver extends AbstractAndroidDriver {
    @Override
    public String getName() {
        return "android";
    }

    @Override
    public WebDriver getDriver() {
        DesiredCapabilities dc = getCapabilities();
        WebDriver driver = getAndroidDriver(dc);
        return driver;
    }

    /**
     * Get capabilities
     * @return  capabilities
     */
    protected DesiredCapabilities getCapabilities() {
        DesiredCapabilities dc = getCapabilities();

        logInfo("[DesiredCapabilities] " + MobileCapabilityType.UDID + ": " + configMap.getString("appium.capabilities.udid"));
        dc.setCapability(MobileCapabilityType.UDID, configMap.getString("appium.capabilities.udid"));

        logInfo("[DesiredCapabilities] " + MobileCapabilityType.VERSION + ": " + configMap.getString("appium.capabilities.androidVersion"));
        dc.setCapability(MobileCapabilityType.VERSION, configMap.getString("appium.capabilities.androidVersion"));

        logInfo("[DesiredCapabilities] " + "appPackage" + ": " + configMap.getString("appium.capabilities.appPackage"));
        dc.setCapability("appPackage", configMap.getString("appium.capabilities.appPackage"));

        logInfo("[DesiredCapabilities] " + "appActivity" + ": " + configMap.getString("appium.capabilities.appActivity"));
        dc.setCapability("appActivity", configMap.getString("appium.capabilities.appActivity"));

        return dc;
    }

}
