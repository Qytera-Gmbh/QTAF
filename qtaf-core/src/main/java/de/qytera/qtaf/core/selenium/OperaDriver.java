package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaOptions;

/**
 * This class is responsible for connecting to a local Opera browser
 */
public class OperaDriver extends AbstractDriver {
    @Override
    public String getName() {
        return "opera";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.operadriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.opera.OperaDriver();
    }

    public Capabilities getCapabilities() {
        return new OperaOptions();
    }
}
