package de.qytera.qtaf.core.selenium;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CapabilityFactoryTest {

    @BeforeMethod
    public void clear() {
        QtafFactory.getConfiguration().clear();
    }

    private static final List<String> OPTIONS = List.of(
            "good morning",
            "good afternoon",
            "good evening"
    );

    private static final Capabilities CAPABILITIES = new ImmutableCapabilities(
            Map.of(
                    "a", "hi",
                    "b", "there",
                    "c", "goodbye"
            )
    );

    private static final Map<String, Object> PREFERENCES = Map.of(
            "f", true,
            "g", Map.of(
                    "h", List.of(1L, 2.14D, 3L, 5L),
                    "j", List.of(List.of(10L), Map.of("k", true))
            ),
            "download", Map.of("default_directory", "abc"),
            "browser", Map.of("download", Map.of("dir", "abc"))
    );

    @Test
    public void testGetCapabilitiesChrome() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            ChromeOptions actualOptions = CapabilityFactory.getCapabilitiesChrome();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));

            ChromeOptions expectedOptions = new ChromeOptions();
            expectedOptions.addArguments(OPTIONS);
            expectedOptions = expectedOptions.merge(CAPABILITIES);
            expectedOptions.setExperimentalOption("prefs", new HashMap<>());

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesChromeWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            ChromeOptions actualOptions = CapabilityFactory.getCapabilitiesChrome();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(1));

            ChromeOptions expectedOptions = new ChromeOptions();
            expectedOptions.setExperimentalOption("prefs", PREFERENCES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesChromeRemote() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            ChromeOptions actualOptions = CapabilityFactory.getCapabilitiesChromeRemote();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));

            ChromeOptions expectedOptions = new ChromeOptions();
            expectedOptions.addArguments(OPTIONS);
            expectedOptions = expectedOptions.merge(CAPABILITIES);
            expectedOptions.setExperimentalOption("prefs", new HashMap<>());

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesChromeRemoteWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            ChromeOptions actualOptions = CapabilityFactory.getCapabilitiesChromeRemote();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(1));

            ChromeOptions expectedOptions = new ChromeOptions();
            expectedOptions.setExperimentalOption("prefs", PREFERENCES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesEdge() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            EdgeOptions actualOptions = CapabilityFactory.getCapabilitiesEdge();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));

            EdgeOptions expectedOptions = new EdgeOptions();
            expectedOptions.addArguments(OPTIONS);
            expectedOptions = expectedOptions.merge(CAPABILITIES);
            expectedOptions.setExperimentalOption("prefs", new HashMap<>());

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesEdgeWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            EdgeOptions actualOptions = CapabilityFactory.getCapabilitiesEdge();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(1));

            EdgeOptions expectedOptions = new EdgeOptions();
            expectedOptions.setExperimentalOption("prefs", PREFERENCES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesEdgeRemote() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            EdgeOptions actualOptions = CapabilityFactory.getCapabilitiesEdgeRemote();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));

            EdgeOptions expectedOptions = new EdgeOptions();
            expectedOptions.addArguments(OPTIONS);
            expectedOptions = expectedOptions.merge(CAPABILITIES);
            expectedOptions.setExperimentalOption("prefs", new HashMap<>());

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesEdgeRemoteWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            EdgeOptions actualOptions = CapabilityFactory.getCapabilitiesEdgeRemote();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(1));

            EdgeOptions expectedOptions = new EdgeOptions();
            expectedOptions.setExperimentalOption("prefs", PREFERENCES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesFirefox() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            FirefoxOptions actualOptions = CapabilityFactory.getCapabilitiesFirefox();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));

            FirefoxOptions expectedOptions = new FirefoxOptions();
            expectedOptions.addArguments(OPTIONS);
            expectedOptions = expectedOptions.merge(CAPABILITIES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesFirefoxWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            FirefoxOptions actualOptions = CapabilityFactory.getCapabilitiesFirefox();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(0));

            FirefoxOptions expectedOptions = new FirefoxOptions();
            FirefoxProfile profile = new FirefoxProfile();
            PREFERENCES.forEach(profile::setPreference);
            Assert.assertEquals(actualOptions, expectedOptions);

            expectedOptions.setProfile(profile);
        }
    }

    @Test
    public void testGetCapabilitiesFirefoxRemote() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            FirefoxOptions actualOptions = CapabilityFactory.getCapabilitiesFirefoxRemote();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));

            FirefoxOptions expectedOptions = new FirefoxOptions();
            expectedOptions.addArguments(OPTIONS);
            expectedOptions = expectedOptions.merge(CAPABILITIES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesFirefoxRemoteWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            FirefoxOptions actualOptions = CapabilityFactory.getCapabilitiesFirefoxRemote();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(0));

            FirefoxOptions expectedOptions = new FirefoxOptions();
            FirefoxProfile profile = new FirefoxProfile();
            PREFERENCES.forEach(profile::setPreference);
            Assert.assertEquals(actualOptions, expectedOptions);
            expectedOptions.setProfile(profile);
            Assert.assertNotNull(actualOptions.getProfile());
        }
    }

    @Test
    public void testGetCapabilitiesInternetExplorer() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverOptions).thenReturn(OPTIONS);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            InternetExplorerOptions actualOptions = CapabilityFactory.getCapabilitiesInternetExplorer();
            helper.verify(SeleniumDriverConfigHelper::getDriverOptions, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));
            helper.verifyNoMoreInteractions();

            InternetExplorerOptions expectedOptions = new InternetExplorerOptions();
            expectedOptions.addCommandSwitches(OPTIONS.toArray(String[]::new));
            expectedOptions.setCapability("ignoreZoomSetting", true);
            expectedOptions = expectedOptions.merge(CAPABILITIES);

            Assert.assertEquals(actualOptions, expectedOptions);
        }
    }

    @Test
    public void testGetCapabilitiesInternetExplorerWithPreferences() {
        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            CapabilityFactory.getCapabilitiesInternetExplorer();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(0));
        }
    }

    @Test
    public void testGetCapabilitiesAndroid() {

        QtafFactory.getConfiguration().setString("appium.capabilities.deviceName", "Alpha");
        QtafFactory.getConfiguration().setString("appium.capabilities.udid", "Beta");
        QtafFactory.getConfiguration().setString("appium.capabilities.androidVersion", "Gamma");
        QtafFactory.getConfiguration().setString("appium.capabilities.platformName", "Delta");
        QtafFactory.getConfiguration().setString("appium.capabilities.appPackage", "Epsilon");
        QtafFactory.getConfiguration().setString("appium.capabilities.appActivity", "Zeta");

        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);

            Capabilities actualCapabilities = CapabilityFactory.getCapabilitiesAndroid();
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));
            helper.verifyNoMoreInteractions();

            MutableCapabilities expectedCapabilities = new MutableCapabilities(
                    Map.of(
                            "deviceName", "Alpha",
                            "udid", "Beta",
                            "browserVersion", "Gamma",
                            "platformName", "Delta",
                            "appPackage", "Epsilon",
                            "appActivity", "Zeta"
                    )
            );
            expectedCapabilities = expectedCapabilities.merge(CAPABILITIES);

            Assert.assertEquals(actualCapabilities, expectedCapabilities);
        }
    }

    @Test
    public void testGetCapabilitiesAndroidWithPreferences() {

        QtafFactory.getConfiguration().setString("appium.capabilities.deviceName", "Alpha");
        QtafFactory.getConfiguration().setString("appium.capabilities.udid", "Beta");
        QtafFactory.getConfiguration().setString("appium.capabilities.androidVersion", "Gamma");
        QtafFactory.getConfiguration().setString("appium.capabilities.platformName", "Delta");
        QtafFactory.getConfiguration().setString("appium.capabilities.appPackage", "Epsilon");
        QtafFactory.getConfiguration().setString("appium.capabilities.appActivity", "Zeta");

        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());

            CapabilityFactory.getCapabilitiesAndroid();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(0));
        }
    }

    @Test
    public void testGetCapabilitiesSaucelabs() {

        QtafFactory.getConfiguration().setString("sauce.username", "Alpha");
        QtafFactory.getConfiguration().setString("sauce.accessKey", "Beta");
        QtafFactory.getConfiguration().setString("sauce.browserName", "Gamma");

        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(CAPABILITIES);
            helper.when(SeleniumDriverConfigHelper::getDriverVersion).thenReturn("123.456.789");
            helper.when(SeleniumDriverConfigHelper::getPlatformName).thenReturn("Unix");

            Capabilities actualCapabilities = CapabilityFactory.getCapabilitiesSaucelabs();
            helper.verify(SeleniumDriverConfigHelper::getDriverCapabilities, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getDriverVersion, Mockito.times(1));
            helper.verify(SeleniumDriverConfigHelper::getPlatformName, Mockito.times(1));
            helper.verifyNoMoreInteractions();

            MutableCapabilities expectedCapabilities = new MutableCapabilities(
                    Map.of(
                            "browserName", "Gamma",
                            "browserVersion", "123.456.789",
                            "platformName", "Unix",
                            "sauce:options", Map.of(
                                    "username", "Alpha",
                                    "accesskey", "Beta"
                            )
                    )
            );
            expectedCapabilities = expectedCapabilities.merge(CAPABILITIES);

            Assert.assertEquals(actualCapabilities, expectedCapabilities);
        }
    }

    @Test
    public void testGetCapabilitiesSaucelabsWithPreferences() {

        QtafFactory.getConfiguration().setString("sauce.username", "Alpha");
        QtafFactory.getConfiguration().setString("sauce.accessKey", "Beta");
        QtafFactory.getConfiguration().setString("sauce.browserName", "Gamma");

        try (MockedStatic<SeleniumDriverConfigHelper> helper = Mockito.mockStatic(SeleniumDriverConfigHelper.class)) {
            helper.when(SeleniumDriverConfigHelper::getDriverPreferences).thenReturn(PREFERENCES);
            helper.when(SeleniumDriverConfigHelper::getDriverCapabilities).thenReturn(new MutableCapabilities());
            helper.when(SeleniumDriverConfigHelper::getDriverVersion).thenReturn("123.456.789");
            helper.when(SeleniumDriverConfigHelper::getPlatformName).thenReturn("Unix");

            CapabilityFactory.getCapabilitiesSaucelabs();
            helper.verify(SeleniumDriverConfigHelper::getDriverPreferences, Mockito.times(0));
        }
    }

}