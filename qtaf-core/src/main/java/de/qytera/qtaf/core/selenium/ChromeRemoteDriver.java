package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.WebDriver;
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
        return new RemoteWebDriver(
                SeleniumDriverConfigHelper.getRemoteUrl(),
                CapabilityFactory.getCapabilitiesChromeRemote()
        );
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
