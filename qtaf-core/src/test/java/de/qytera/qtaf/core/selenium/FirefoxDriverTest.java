package de.qytera.qtaf.core.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

@Test(groups = {"firefox"})
public class FirefoxDriverTest {
    @Test(testName = "testGetDriver")
    public void testGetDriver() {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        WebDriver driver = firefoxDriver.getDriver();
        driver.quit();
        DriverFactory.clearDriver();
    }

    @Test(testName = "testIsRemoteDriver")
    public void testIsRemoteDriver() {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        Assert.assertFalse(firefoxDriver.isRemoteDriver());
    }
}
