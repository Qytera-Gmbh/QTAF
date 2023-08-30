package de.qytera.qtaf.selenium;

import de.qytera.qtaf.core.selenium.ChromeDriver;
import de.qytera.qtaf.core.selenium.DriverFactory;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChromeDriverTest {
    @Test(testName = "testGetDriver")
    public void testGetDriver(){
        ChromeDriver chromeDriver = new ChromeDriver();
        WebDriver driver = chromeDriver.getDriver();
        driver.quit();
        DriverFactory.clearDriver();
    }
    @Test(testName = "testGetCapabilities")
    public void testGetCapabilities() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ChromeDriver chromeDriver = new ChromeDriver();
        Class<ChromeDriver> clazz = (Class<ChromeDriver>) chromeDriver.getClass();
        Method method = clazz.getDeclaredMethod("getCapabilities");
        // make method public
        method.setAccessible(true);
        Assert.assertTrue(method.invoke(chromeDriver) instanceof ChromeOptions);

        // make method procteced
        method.setAccessible(false);
    }
    @Test(testName = "testIsRemoteDriver")
    public void testIsRemoteDriver() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ChromeDriver chromeDriver = new ChromeDriver();
        Class<ChromeDriver> clazz = (Class<ChromeDriver>) chromeDriver.getClass();
        Method method = clazz.getDeclaredMethod("isRemoteDriver");
        // make method public
        method.setAccessible(true);
        Assert.assertFalse((Boolean) method.invoke(chromeDriver));

        // make method procteced
        method.setAccessible(false);
    }
}
