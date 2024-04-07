package de.qytera.qtaf.core.selenium;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
        return new org.openqa.selenium.chrome.ChromeDriver(getCapabilities());
    }

    @Override
    protected ChromeOptions getCapabilities() {
        // Make selenium use the selenium-http-jdk-client package
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                SeleniumDriverConfigHelper.getDriverOptions().stream()
                        .map(JsonElement::getAsString)
                        .toArray(String[]::new)
        );
        options = options.merge(SeleniumDriverConfigHelper.getDriverCapabilities());
        return options;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
