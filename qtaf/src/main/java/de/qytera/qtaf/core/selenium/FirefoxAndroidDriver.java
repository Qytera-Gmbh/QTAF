package de.qytera.qtaf.core.selenium;

import org.openqa.selenium.WebDriver;

public class FirefoxAndroidDriver extends AbstractAndroidDriver {
    @Override
    public String getName() {
        return "firefox-android";
    }

    @Override
    public WebDriver getDriver() {
        return getAndroidDriver(getDesiredCapabilitiesBrowser("Firefox"));
    }
}
