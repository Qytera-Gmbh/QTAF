package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = {"ie"})
public class InternetExplorerDriverTest {

    @BeforeMethod
    public void clear() {
        QtafFactory.getConfiguration().clear();
    }

    @Test
    public void testGetName() {
        InternetExplorerDriver driver = new InternetExplorerDriver();
        Assert.assertEquals(driver.getName(), "ie");
    }

    @Test
    public void testGetDriverFetchesInternetExplorerCapabilities() {
        QtafFactory.getConfiguration().setString(SeleniumDriverConfigHelper.DRIVER_REMOTE_URL, "https://example.org");
        try (MockedStatic<CapabilityFactory> capabilityFactory = Mockito.mockStatic(CapabilityFactory.class)) {
            // Mock constructor to avoid setting up a real driver.
            try (MockedConstruction<org.openqa.selenium.ie.InternetExplorerDriver> mockConstructor = Mockito.mockConstruction(org.openqa.selenium.ie.InternetExplorerDriver.class)) {
                InternetExplorerDriver driver = new InternetExplorerDriver();
                driver.getDriver();
                Assert.assertEquals(mockConstructor.constructed().size(), 1);
            }
            capabilityFactory.verify(CapabilityFactory::getCapabilitiesInternetExplorer, Mockito.times(1));
            capabilityFactory.verifyNoMoreInteractions();
        }
    }

    @Test
    public void testIsRemoteDriver() {
        InternetExplorerDriver driver = new InternetExplorerDriver();
        Assert.assertFalse(driver.isRemoteDriver());
    }
}