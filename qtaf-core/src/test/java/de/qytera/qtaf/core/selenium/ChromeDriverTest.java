package de.qytera.qtaf.core.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

@Test(groups = {"chrome"})
public class ChromeDriverTest {
    @Test(testName = "testGetDriver")
    public void testGetDriver() {
        ChromeDriver chromeDriver = new ChromeDriver();
        WebDriver driver = chromeDriver.getDriver();
        driver.quit();
        DriverFactory.clearDriver();
    }

    @Test(testName = "testIsRemoteDriver")
    public void testIsRemoteDriver() {
        ChromeDriver chromeDriver = new ChromeDriver();
        Assert.assertFalse(chromeDriver.isRemoteDriver());
    }
}
