package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * This class is responsible for connecting to a local edge browser.
 */
public class EdgeDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "edge";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.edgedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.edge.EdgeDriver(getCapabilities());
    }

    @Override
    protected EdgeOptions getCapabilities() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        EdgeOptions options = new EdgeOptions();
        if (headless()) {
            options.addArguments(
                    "--headless",
                    "--disable-gpu",
                    "--ignore-certificate-errors",
                    "--disable-extensions",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
        }
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
