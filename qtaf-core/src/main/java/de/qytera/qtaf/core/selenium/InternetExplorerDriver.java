package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

/**
 * This class is responsible for connecting to a local Internet Explorer browser.
 */
public class InternetExplorerDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "ie";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager.iedriver().setup();
        return new org.openqa.selenium.ie.InternetExplorerDriver(CapabilityFactory.getCapabilitiesInternetExplorer());
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
