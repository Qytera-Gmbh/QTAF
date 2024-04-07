package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

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
        return new org.openqa.selenium.ie.InternetExplorerDriver(this.getCapabilities());
    }

    /**
     * Get capabilities.
     *
     * @return capabilities
     */
    public InternetExplorerOptions getCapabilities() {
        InternetExplorerOptions caps = new InternetExplorerOptions();
        caps.setCapability("ignoreZoomSetting", true);
        caps.addCommandSwitches(SeleniumDriverConfigHelper.getDriverOptions().toArray(String[]::new));
        caps = caps.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return caps;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
