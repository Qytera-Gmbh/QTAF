package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * The sauce driver class.
 */
public class SaucelabsDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "sauce";
    }

    @Override
    public WebDriver getDriver() {
        return new RemoteWebDriver(
                SeleniumDriverConfigHelper.getRemoteUrl(),
                CapabilityFactory.getCapabilitiesSaucelabs()
        );
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
