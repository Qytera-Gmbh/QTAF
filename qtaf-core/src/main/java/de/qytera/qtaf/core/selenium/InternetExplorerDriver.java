package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

/**
 * This class is responsible for connecting to a local Internet Explorer browser
 */
public class InternetExplorerDriver extends AbstractDriver {

    /**
     * Creates a new ie driver.
     */
    public InternetExplorerDriver() {
        super(false);
    }

    @Override
    public String getName() {
        return "ie";
    }

    @Override
    public WebDriver getDriverInstance() {
        WebDriverManager.iedriver().setup();
        return new org.openqa.selenium.ie.InternetExplorerDriver(this.getCapabilities());
    }

    /**
     * Get capabilities
     *
     * @return capabilities
     */
    public InternetExplorerOptions getCapabilities() {
        InternetExplorerOptions caps = new InternetExplorerOptions();
        caps.setCapability("ignoreZoomSetting", true);
        return caps;
    }
}
