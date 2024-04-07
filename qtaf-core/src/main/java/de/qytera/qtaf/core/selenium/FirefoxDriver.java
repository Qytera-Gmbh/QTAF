package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

/**
 * This class is responsible for connecting to a local firefox browser.
 */
public class FirefoxDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "firefox";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.firefoxdriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.firefox.FirefoxDriver(CapabilityFactory.getCapabilitiesFirefox());
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
