package de.qytera.qtaf.core.selenium;

import org.openqa.selenium.WebDriver;

/**
 * This class is responsible for connecting to a firefox browser on an android device
 */
public class FirefoxAndroidDriver extends AbstractAndroidDriver {
    @Override
    public String getName() {
        return "firefox-android";
    }

    @Override
    public WebDriver getDriverInstance() {
        return getAndroidDriver(getDesiredCapabilitiesBrowser("Firefox"));
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
