package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class is responsible for connecting to a remote chrome browser.
 */
public class ChromeRemoteDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "chrome-remote";
    }

    @Override
    public WebDriver getDriver() {
        return new RemoteWebDriver(SeleniumDriverConfigHelper.getRemoteUrl(), getCapabilities());
    }

    @Override
    protected Capabilities getCapabilities() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
