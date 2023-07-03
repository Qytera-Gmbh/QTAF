package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class is responsible for connecting to a remote chrome browser
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
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--headless");
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
