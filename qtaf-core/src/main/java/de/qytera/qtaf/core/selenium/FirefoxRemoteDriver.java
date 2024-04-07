package de.qytera.qtaf.core.selenium;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class is responsible for connecting to a remote firefox browser.
 */
public class FirefoxRemoteDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "firefox-remote";
    }

    @Override
    public WebDriver getDriver() {
        return new RemoteWebDriver(SeleniumDriverConfigHelper.getRemoteUrl(), getCapabilities());
    }

    @Override
    protected Capabilities getCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(
                SeleniumDriverConfigHelper.getDriverOptions().stream()
                        .map(JsonElement::getAsString)
                        .toArray(String[]::new)
        );
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
