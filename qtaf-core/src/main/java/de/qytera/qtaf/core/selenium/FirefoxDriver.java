package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * This class is responsible for connecting to a local firefox browser
 */
public class FirefoxDriver extends AbstractDriver {

    /**
     * Creates a new firefox driver.
     */
    public FirefoxDriver() {
        super(false);
    }

    @Override
    public String getName() {
        return "firefox";
    }

    @Override
    public WebDriver getDriverInstance() {
        WebDriverManager webDriverManager = WebDriverManager.firefoxdriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.firefox.FirefoxDriver(getCapabilities());
    }

    @Override
    protected FirefoxOptions getCapabilities() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        return new FirefoxOptions();
    }
}
