package de.qytera.qtaf.core.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

@Test(groups = {"edge"})
public class EdgeDriverTest {
    @Test(testName = "testGetDriver")
    public void testGetDriver() {
        EdgeDriver edgeDriver = new EdgeDriver();
        WebDriver driver = edgeDriver.getDriver();
        driver.quit();
        DriverFactory.clearDriver();
    }

    @Test(testName = "testIsRemoteDriver")
    public void testIsRemoteDriver() {
        EdgeDriver edgeDriver = new EdgeDriver();
        Assert.assertFalse(edgeDriver.isRemoteDriver());
    }
}
