package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AndroidDriverTest {

    @BeforeMethod
    public void clear() {
        QtafFactory.getConfiguration().clear();
    }

    @Test
    public void testGetName() {
        AndroidDriver driver = new AndroidDriver();
        Assert.assertEquals(driver.getName(), "android");
    }

    @Test
    public void testGetDriverFetchesAndroidCapabilities() {
        QtafFactory.getConfiguration().setString("appium.driverSettings.url", "https://example.org");
        try (MockedStatic<CapabilityFactory> capabilityFactory = Mockito.mockStatic(CapabilityFactory.class)) {
            // Mock constructor to avoid setting up a real Android Driver.
            try (MockedConstruction<io.appium.java_client.android.AndroidDriver> mockConstructor = Mockito.mockConstruction(io.appium.java_client.android.AndroidDriver.class)) {
                AndroidDriver driver = new AndroidDriver();
                driver.getDriver();
                Assert.assertEquals(mockConstructor.constructed().size(), 1);
            }
            capabilityFactory.verify(CapabilityFactory::getCapabilitiesAndroid, Mockito.times(1));
            capabilityFactory.verifyNoMoreInteractions();
        }
    }

    @Test
    public void testGetDriverInvalidUrl() {
        AndroidDriver driver = new AndroidDriver();
        Assert.assertThrows(IllegalArgumentException.class, driver::getDriver);
    }

    @Test
    public void testIsRemoteDriver() {
        AndroidDriver driver = new AndroidDriver();
        Assert.assertFalse(driver.isRemoteDriver());
    }
}