package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class is responsible for connecting to a remote edge driver
 */
public class EdgeRemoteDriver extends AbstractDriver {

    /**
     * Creates a new edge-remote driver.
     */
    public EdgeRemoteDriver() {
        super(false);
    }

    @Override
    public String getName() {
        return "edge-remote";
    }

    @Override
    public WebDriver getDriverInstance() {
        return new RemoteWebDriver(SeleniumDriverConfigHelper.getRemoteUrl(), getCapabilities());
    }

    @Override
    protected Capabilities getCapabilities() {
        return new EdgeOptions();
    }
}
