package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = {"firefox"})
public class FirefoxRemoteDriverTest {

    @BeforeMethod
    public void clear() {
        QtafFactory.getConfiguration().clear();
    }

    @Test
    public void testGetName() {
        FirefoxRemoteDriver driver = new FirefoxRemoteDriver();
        Assert.assertEquals(driver.getName(), "firefox-remote");
    }

    @Test
    public void testGetDriverFetchesFirefoxRemoteCapabilities() {
        QtafFactory.getConfiguration().setString(SeleniumDriverConfigHelper.DRIVER_REMOTE_URL, "https://example.org");
        try (MockedStatic<CapabilityFactory> capabilityFactory = Mockito.mockStatic(CapabilityFactory.class)) {
            // Mock constructor to avoid setting up a real remote driver.
            try (MockedConstruction<RemoteWebDriver> mockConstructor = Mockito.mockConstruction(RemoteWebDriver.class)) {
                FirefoxRemoteDriver driver = new FirefoxRemoteDriver();
                driver.getDriver();
                Assert.assertEquals(mockConstructor.constructed().size(), 1);
            }
            capabilityFactory.verify(CapabilityFactory::getCapabilitiesFirefoxRemote, Mockito.times(1));
            capabilityFactory.verifyNoMoreInteractions();
        }
    }

    @Test
    public void testIsRemoteDriver() {
        FirefoxRemoteDriver driver = new FirefoxRemoteDriver();
        Assert.assertTrue(driver.isRemoteDriver());
    }
}