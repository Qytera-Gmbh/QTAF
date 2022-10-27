package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaOptions;

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
