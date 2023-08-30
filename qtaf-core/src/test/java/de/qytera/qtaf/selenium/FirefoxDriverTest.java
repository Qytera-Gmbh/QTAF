package de.qytera.qtaf.selenium;

import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.core.selenium.FirefoxDriver;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FirefoxDriverTest {
    @Test(testName = "testGetDriver", groups =  {"firefox"})
    public void testGetDriver(){
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        WebDriver driver = firefoxDriver.getDriver();
        driver.quit();
        DriverFactory.clearDriver();
    }
    @Test(testName = "testGetCapabilities", groups =  {"firefox"})
    public void testGetCapabilities() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        Class<FirefoxDriver> clazz = (Class<FirefoxDriver>) firefoxDriver.getClass();
        Method method = clazz.getDeclaredMethod("getCapabilities");
        // make method public
        method.setAccessible(true);
        Assert.assertTrue(method.invoke(firefoxDriver) instanceof FirefoxOptions);

        // make method procteced
        method.setAccessible(false);
    }
    @Test(testName = "testIsRemoteDriver", groups =  {"firefox"})
    public void testIsRemoteDriver() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        Class<FirefoxDriver> clazz = (Class<FirefoxDriver>) firefoxDriver.getClass();
        Method method = clazz.getDeclaredMethod("isRemoteDriver");
        // make method public
        method.setAccessible(true);
        Assert.assertFalse((Boolean) method.invoke(firefoxDriver));

        // make method procteced
        method.setAccessible(false);
    }
}
