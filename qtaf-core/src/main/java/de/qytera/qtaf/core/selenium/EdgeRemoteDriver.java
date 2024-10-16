package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class is responsible for connecting to a remote edge driver.
 */
public class EdgeRemoteDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "edge-remote";
    }

    @Override
    public WebDriver getDriver() {
        return new RemoteWebDriver(
                SeleniumDriverConfigHelper.getRemoteUrl(),
                CapabilityFactory.getCapabilitiesEdgeRemote()
        );
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
