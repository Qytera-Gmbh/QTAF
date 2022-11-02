package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public class ChromeDriver extends AbstractDriver {
    @Override
    public String getName() {
        return "chrome";
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager webDriverManager = WebDriverManager.chromedriver();
        initWebDriverManager(webDriverManager);
        return new org.openqa.selenium.chrome.ChromeDriver();
    }

    @Override
    protected Capabilities getCapabilities() {
        return null;
    }
}
