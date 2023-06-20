package de.qytera.qtaf.core.selenium;

import org.openqa.selenium.WebDriver;

/**
 * This class manages the appium chrome android driver
 */
public class ChromeAndroidDriver extends AbstractAndroidDriver {
    @Override
    public String getName() {
        return "chrome-android";
    }

    @Override
    public WebDriver getDriver() {
        return getAndroidDriver(getDesiredCapabilitiesBrowser("Chrome"));
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
