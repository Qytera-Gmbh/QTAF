package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class is responsible for connecting to a remote firefox browser
 */
public class FirefoxRemoteDriver extends AbstractDriver {

    /**
     * Creates a new firefox-remote driver.
     */
    public FirefoxRemoteDriver() {
        super(true);
    }

    @Override
    public String getName() {
        return "firefox-remote";
    }

    @Override
    public WebDriver getDriverInstance() {
        return new RemoteWebDriver(SeleniumDriverConfigHelper.getRemoteUrl(), getCapabilities());
    }

    @Override
    protected Capabilities getCapabilities() {
        return new FirefoxOptions();
    }
}
