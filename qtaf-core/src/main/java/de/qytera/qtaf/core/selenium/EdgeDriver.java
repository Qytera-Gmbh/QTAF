package de.qytera.qtaf.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public class EdgeDriver extends AbstractDriver {
    @Override
    public String getName() {
        return "edge";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.edgedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.edge.EdgeDriver();
    }

    @Override
    protected Capabilities getCapabilities() {
        return null;
    }
}
