package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * This class is responsible for connecting to a local edge browser
 */
public class EdgeDriver extends AbstractDriver {

    /**
     * Creates a new edge driver.
     */
    public EdgeDriver() {
        super(false);
    }

    @Override
    public String getName() {
        return "edge";
    }

    @Override
    public WebDriver getDriverInstance() {
        WebDriverManager webDriverManager = WebDriverManager.edgedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.edge.EdgeDriver(getCapabilities());
    }

    @Override
    protected EdgeOptions getCapabilities() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        return new EdgeOptions();
    }
}
