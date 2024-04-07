package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * The sauce driver class.
 */
public class SaucelabsDriver extends AbstractDriver {

    @Override
    public String getName() {
        return "sauce";
    }

    @Override
    public WebDriver getDriver() {
        return new RemoteWebDriver(SeleniumDriverConfigHelper.getRemoteUrl(), getCapabilities());
    }

    @Override
    protected Capabilities getCapabilities() {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", SeleniumDriverConfigHelper.getSaucelabUsername());
        sauceOptions.setCapability("accesskey", SeleniumDriverConfigHelper.getSaucelabAccessKey());

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", SeleniumDriverConfigHelper.getSaucelabBrowserName());
        capabilities.setCapability("browserVersion", SeleniumDriverConfigHelper.getDriverVersion());
        capabilities.setCapability("platformName", SeleniumDriverConfigHelper.getPlatformName());
        capabilities.setCapability("sauce:options", sauceOptions);

        SeleniumDriverConfigHelper.getDriverCapabilities().forEach((key, value) ->
                capabilities.setCapability(key, value.getAsString())
        );
        return capabilities;
    }

    @Override
    protected boolean isRemoteDriver() {
        return false;
    }
}
