package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for managing the appium android driver.
 */
public class AndroidDriver extends AbstractDriver {
    @Override
    public String getName() {
        return "android";
    }

    @Override
    public WebDriver getDriver() {
        try {
            logInfo("[Android Driver] " + "URL" + ": " + CONFIG.getString("appium.driverSettings.url"));
            return new io.appium.java_client.android.AndroidDriver(
                    new URL(CONFIG.getString("appium.driverSettings.url")),
                    getCapabilities()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected DesiredCapabilities getCapabilities() {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, CONFIG.getString("appium.capabilities.deviceName"));
        dc.setCapability(MobileCapabilityType.UDID, CONFIG.getString("appium.capabilities.udid"));
        dc.setCapability(CapabilityType.BROWSER_VERSION, CONFIG.getString("appium.capabilities.androidVersion"));
        dc.setCapability(CapabilityType.PLATFORM_NAME, CONFIG.getString("appium.capabilities.platformName"));
        dc.setCapability("appPackage", CONFIG.getString("appium.capabilities.appPackage"));
        dc.setCapability("appActivity", CONFIG.getString("appium.capabilities.appActivity"));
        dc = dc.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return dc;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }

}
