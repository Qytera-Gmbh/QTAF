package de.qytera.qtaf.core.selenium;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
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
    public WebDriver getDriverInstance() {
        return getAndroidDriver(getCapabilities());
    }

    /**
     * Get capabilities
     *
     * @return capabilities
     */
    @Override
    protected DesiredCapabilities getCapabilities() {
        DesiredCapabilities dc = super.getCapabilities();
        logDesiredCapability(MobileCapabilityType.UDID, CONFIG.getString("appium.capabilities.udid"));
        logDesiredCapability(CapabilityType.BROWSER_VERSION, CONFIG.getString("appium.capabilities.androidVersion"));
        logDesiredCapability("appPackage", CONFIG.getString("appium.capabilities.appPackage"));
        logDesiredCapability("appActivity", CONFIG.getString("appium.capabilities.appActivity"));
        dc.setCapability(MobileCapabilityType.UDID, CONFIG.getString("appium.capabilities.udid"));
        dc.setCapability(CapabilityType.BROWSER_VERSION, CONFIG.getString("appium.capabilities.androidVersion"));
        dc.setCapability("appPackage", CONFIG.getString("appium.capabilities.appPackage"));
        dc.setCapability("appActivity", CONFIG.getString("appium.capabilities.appActivity"));
        return dc;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }

}
