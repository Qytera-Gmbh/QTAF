package de.qytera.qtaf.selenium;

import de.qytera.qtaf.core.selenium.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test instantiation of selenium drivers
 */
public class DriverTest {
    /**
     * Test if the driver gets cached
     */
    @Test(groups = {"driver"})
    public void testDriverCache() {
        WebDriver webDriver1 = DriverFactory.getDriver("chrome");
        webDriver1.quit();
        WebDriver webDriver2 = DriverFactory.getDriver("firefox");
        webDriver1.quit();
        Assert.assertEquals(webDriver1.hashCode(), webDriver2.hashCode());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"driver"})
    public void testChromeDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("chrome");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), ChromeDriver.class.getName());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"driver"})
    public void testFirefoxDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("firefox");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), FirefoxDriver.class.getName());
        DriverFactory.clearDriver();
    }

    //@Test(groups = {"driver"})
    public void testOperaDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("opera");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), OperaDriver.class.getName());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"driver"})
    public void testEdgeDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("edge");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), EdgeDriver.class.getName());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"driver"})
    public void testIEDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("ie");
        webDriver.close();
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), InternetExplorerDriver.class.getName());
        DriverFactory.clearDriver();
    }
}
