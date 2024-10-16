package de.qytera.qtaf.core.selenium;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test instantiation of selenium drivers
 */
@Test(groups = {"driver"})
public class DriverTest {

    /**
     * Test if the driver gets cached
     */
    @Test
    public void testDriverCache() {
        WebDriver webDriver1 = DriverFactory.getDriver("chrome");
        webDriver1.quit();
        WebDriver webDriver2 = DriverFactory.getDriver("firefox");
        webDriver2.quit();
        WebDriver webDriver3 = DriverFactory.getDriver("edge");
        webDriver3.quit();
        Assert.assertEquals(webDriver1.hashCode(), webDriver2.hashCode());
        Assert.assertEquals(webDriver1.hashCode(), webDriver3.hashCode());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"chrome"})
    public void testChromeDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("chrome");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), org.openqa.selenium.chrome.ChromeDriver.class.getName());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"firefox"})
    public void testFirefoxDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("firefox");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), org.openqa.selenium.firefox.FirefoxDriver.class.getName());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"edge"})
    public void testEdgeDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("edge");
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), org.openqa.selenium.edge.EdgeDriver.class.getName());
        DriverFactory.clearDriver();
    }

    @Test(groups = {"ie"})
    public void testIEDriverInstantiation() {
        DriverFactory.clearDriver();
        WebDriver webDriver = DriverFactory.getDriver("ie");
        webDriver.close();
        webDriver.quit();
        Assert.assertEquals(webDriver.getClass().getName(), org.openqa.selenium.ie.InternetExplorerDriver.class.getName());
        DriverFactory.clearDriver();
    }
}
