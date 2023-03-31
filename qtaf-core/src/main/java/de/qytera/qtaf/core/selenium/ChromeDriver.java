package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * This class is responsible for managing the selenium chrome driver
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
        return new org.openqa.selenium.chrome.ChromeDriver(getCapabilities());
    }

    @Override
    protected ChromeOptions getCapabilities() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeOptions options = new ChromeOptions();
        options.setCapability("remote-allow-origins", "");
        return options;
    }
}
