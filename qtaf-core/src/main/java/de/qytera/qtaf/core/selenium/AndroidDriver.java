package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for managing the appium android driver.
 */
public class AndroidDriver extends AbstractDriver {

    private static final String KEY_APPIUM_DRIVERSETTINGS_URL = "appium.driverSettings.url";

    @Override
    public String getName() {
        return "android";
    }

    @Override
    public WebDriver getDriver() {
        String url = CONFIG.getString(KEY_APPIUM_DRIVERSETTINGS_URL);
        try {
            if (url == null) {
                throw new MalformedURLException(
                        "Failed to get Appium driver URL, configuration key '%s' is null".formatted(KEY_APPIUM_DRIVERSETTINGS_URL)
                );
            }
            return new io.appium.java_client.android.AndroidDriver(
                    new URL(url),
                    CapabilityFactory.getCapabilitiesAndroid()
            );
        } catch (MalformedURLException e) {
            QtafFactory.getLogger().fatal("The given Appium driver url is malformed: %s".formatted(url));
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }

}
