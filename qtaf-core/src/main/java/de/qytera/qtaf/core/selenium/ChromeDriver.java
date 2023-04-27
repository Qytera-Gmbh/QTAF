package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * This class is responsible for managing the selenium chrome driver
 */
public class ChromeDriver extends AbstractDriver {

    /**
     * Creates a new chrome driver.
     */
    public ChromeDriver() {
        super(false);
    }

    @Override
    public String getName() {
        return "chrome";
    }

    @Override
    public WebDriver getDriverInstance() {
        WebDriverManager webDriverManager = WebDriverManager.chromedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.chrome.ChromeDriver(getCapabilities());
    }

    @Override
    protected ChromeOptions getCapabilities() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeOptions options = new ChromeOptions();
        return options;
    }
}
