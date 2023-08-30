package de.qytera.qtaf.core.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EdgeDriverTest {
    @Test(testName = "testGetDriver", groups =  {"edge"})
    public void testGetDriver(){
        EdgeDriver edgeDriver = new EdgeDriver();
        WebDriver driver = edgeDriver.getDriver();
        driver.quit();
        DriverFactory.clearDriver();
    }
    @Test(testName = "testGetCapabilities", groups =  {"edge"})
    public void testGetCapabilities() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EdgeDriver edgeDriver = new EdgeDriver();
        Class<EdgeDriver> clazz = (Class<EdgeDriver>) edgeDriver.getClass();
        Method method = clazz.getDeclaredMethod("getCapabilities");
        // make method public
        method.setAccessible(true);
        Assert.assertTrue(method.invoke(edgeDriver) instanceof EdgeOptions);

        // make method procteced
        method.setAccessible(false);
    }
    @Test(testName = "testIsRemoteDriver", groups =  {"edge"})
    public void testIsRemoteDriver() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EdgeDriver edgeDriver = new EdgeDriver();
        Class<EdgeDriver> clazz = (Class<EdgeDriver>) edgeDriver.getClass();
        Method method = clazz.getDeclaredMethod("isRemoteDriver");
        // make method public
        method.setAccessible(true);
        Assert.assertFalse((Boolean) method.invoke(edgeDriver));

        // make method procteced
        method.setAccessible(false);
    }
}
