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

public class SaucelabsDriverTest {

    @BeforeMethod
    public void clear() {
        QtafFactory.getConfiguration().clear();
    }

    @Test
    public void testGetName() {
        SaucelabsDriver driver = new SaucelabsDriver();
        Assert.assertEquals(driver.getName(), "sauce");
    }

    @Test
    public void testGetDriverFetchesSaucelabsCapabilities() {
        QtafFactory.getConfiguration().setString(SeleniumDriverConfigHelper.DRIVER_REMOTE_URL, "https://example.org");
        try (MockedStatic<CapabilityFactory> capabilityFactory = Mockito.mockStatic(CapabilityFactory.class)) {
            // Mock constructor to avoid setting up a real Saucelabs driver.
            try (MockedConstruction<RemoteWebDriver> mockConstructor = Mockito.mockConstruction(RemoteWebDriver.class)) {
                SaucelabsDriver driver = new SaucelabsDriver();
                driver.getDriver();
                Assert.assertEquals(mockConstructor.constructed().size(), 1);
            }
            capabilityFactory.verify(CapabilityFactory::getCapabilitiesSaucelabs, Mockito.times(1));
            capabilityFactory.verifyNoMoreInteractions();
        }
    }

    @Test
    public void testIsRemoteDriver() {
        SaucelabsDriver driver = new SaucelabsDriver();
        Assert.assertTrue(driver.isRemoteDriver());
    }
}