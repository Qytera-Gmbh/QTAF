package de.qytera.qtaf.core.selenium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractAndroidDriver extends AbstractDriver {

    protected DesiredCapabilities getCapabilities() {
        DesiredCapabilities dc = new DesiredCapabilities();

        logInfo("[DesiredCapabilities] " + MobileCapabilityType.DEVICE_NAME + ": " + configMap.getString("appium.capabilities.deviceName"));
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, configMap.getString("appium.capabilities.deviceName"));

        logInfo("[DesiredCapabilities] " + MobileCapabilityType.PLATFORM_NAME + ": " + configMap.getString("appium.capabilities.platformName"));
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, configMap.getString("appium.capabilities.platformName"));

        return dc;
    }

    protected DesiredCapabilities getDesiredCapabilitiesBrowser(String browserName) {
        DesiredCapabilities dc = getCapabilities();

        logInfo("[DesiredCapabilities] " + MobileCapabilityType.BROWSER_NAME + ": " + browserName);
        dc.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);

        return dc;
    }

    protected AndroidDriver<AndroidElement> getAndroidDriver(DesiredCapabilities dc) {
        try {
            logInfo("[Android Driver] " + "URL" + ": " + configMap.getString("appium.driverSettings.url"));

            AndroidDriver<AndroidElement> androidDriver = new AndroidDriver<AndroidElement>(
                    new URL(configMap.getString("appium.driverSettings.url")), dc);

            return androidDriver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
