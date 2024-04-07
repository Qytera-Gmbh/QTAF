package de.qytera.qtaf.core.selenium;

import com.google.gson.JsonElement;
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
        SeleniumDriverConfigHelper.getDriverCapabilities().forEach((key, value) ->
                options.setCapability(key, value.getAsString())
        );
        options.addArguments(
                SeleniumDriverConfigHelper.getDriverOptions().stream()
                        .map(JsonElement::getAsString)
                        .toArray(String[]::new)
        );
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
