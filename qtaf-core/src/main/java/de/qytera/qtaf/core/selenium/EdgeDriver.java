package de.qytera.qtaf.core.selenium;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
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
        SeleniumDriverConfigHelper.getDriverCapabilities().forEach((key, value) ->
                options.setCapability(key, value.getAsString())
        );
        options.addArguments(
                SeleniumDriverConfigHelper.getDriverOptions().stream()
                        .map(JsonElement::getAsString)
                        .toArray(String[]::new)
        );
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
