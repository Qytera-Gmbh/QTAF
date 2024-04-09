package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

/**
 * This class is responsible for managing the selenium chrome driver.
 */
public class ChromeDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "chrome";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.chromedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.chrome.ChromeDriver(CapabilityFactory.getCapabilitiesChrome());
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
