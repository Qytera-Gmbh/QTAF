package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

/**
 * This class is responsible for connecting to a local edge browser.
 */
public class EdgeDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "edge";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.edgedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.edge.EdgeDriver(CapabilityFactory.getCapabilitiesEdge());
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
