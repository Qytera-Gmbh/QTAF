package de.qytera.qtaf.core.selenium;

import org.openqa.selenium.WebDriver;


public class ChromeAndroidDriver extends AbstractAndroidDriver {
    @Override
    public String getName() {
        return "chrome-android";
    }

    @Override
    public WebDriver getDriver() {
        return getAndroidDriver(getDesiredCapabilitiesBrowser("Chrome"));
    }
}
