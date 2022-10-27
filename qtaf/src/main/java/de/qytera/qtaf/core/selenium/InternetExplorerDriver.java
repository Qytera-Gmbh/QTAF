package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

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

    public InternetExplorerOptions getCapabilities() {
        InternetExplorerOptions caps = new InternetExplorerOptions();
        caps.setCapability("ignoreZoomSetting", true);
        return caps;
    }
}
