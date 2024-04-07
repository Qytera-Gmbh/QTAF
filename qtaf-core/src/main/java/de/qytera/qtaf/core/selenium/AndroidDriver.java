package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for managing the appium android driver.
 */
public class AndroidDriver extends AbstractDriver {
    @Override
    public String getName() {
        return "android";
    }

    @Override
    public WebDriver getDriver() {
        try {
            logInfo("[Android Driver] " + "URL" + ": " + CONFIG.getString("appium.driverSettings.url"));
            return new io.appium.java_client.android.AndroidDriver(
                    new URL(CONFIG.getString("appium.driverSettings.url")),
                    getCapabilities()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected DesiredCapabilities getCapabilities() {
        DesiredCapabilities dc = new DesiredCapabilities();
        SeleniumDriverConfigHelper.getDriverCapabilities().forEach((key, value) ->
                dc.setCapability(key, value.getAsString())
        );
        return dc;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }

}
