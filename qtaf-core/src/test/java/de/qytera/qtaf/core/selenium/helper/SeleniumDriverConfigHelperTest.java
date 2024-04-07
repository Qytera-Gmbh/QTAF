package de.qytera.qtaf.core.selenium.helper;

import de.qytera.qtaf.core.QtafFactory;
import org.openqa.selenium.MutableCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SeleniumDriverConfigHelperTest {

    @BeforeMethod
    public void clearConfiguration() {
        QtafFactory.getConfiguration().clear();
    }

    @AfterMethod
    public void clear() {
        System.clearProperty(SeleniumDriverConfigHelper.DRIVER_CAPABILITIES);
        System.clearProperty(SeleniumDriverConfigHelper.DRIVER_OPTIONS);
    }

    @Test
    public void testGetDriverOptionsDefault() {
        Assert.assertEquals(SeleniumDriverConfigHelper.getDriverOptions(), Collections.emptyList());
    }

    @Test
    public void testGetDriverOptionsHappyPath() {
        System.setProperty(SeleniumDriverConfigHelper.DRIVER_OPTIONS, """
                [
                  "a",
                  "b",
                  "c",
                  "d"
                ]
                """
        );
        Assert.assertEquals(
                SeleniumDriverConfigHelper.getDriverOptions(),
                List.of(
                        "a",
                        "b",
                        "c",
                        "d"
                )
        );
    }

    @Test
    public void testGetDriverOptionsFiltersStrings() {
        System.setProperty(SeleniumDriverConfigHelper.DRIVER_OPTIONS, """
                [
                  "a",
                  456,
                  true,
                  null,
                  [1, 2, 3],
                  {
                    "b": true
                  }
                ]
                """
        );
        Assert.assertEquals(
                SeleniumDriverConfigHelper.getDriverOptions(),
                List.of(
                        "a"
                )
        );
    }

    @Test
    public void testGetDriverCapabilitiesDefault() {
        Assert.assertEquals(SeleniumDriverConfigHelper.getDriverOptions(), Collections.emptyList());
    }

    @Test
    public void testGetDriverCapabilitiesHappyPath() {
        System.setProperty(SeleniumDriverConfigHelper.DRIVER_CAPABILITIES, """
                {
                  "a": "good morning",
                  "b": 3,
                  "c": false,
                  "d": [8080, 443],
                  "e": {
                    "f": true,
                    "g": {
                      "h": [1, 2.14, 3],
                      "i": null
                    }
                  }
                }
                """
        );
        MutableCapabilities expectedCapabilities = new MutableCapabilities();
        expectedCapabilities.setCapability("a", "good morning");
        expectedCapabilities.setCapability("b", 3L);
        expectedCapabilities.setCapability("c", false);
        expectedCapabilities.setCapability("d", List.of(8080L, 443L));
        expectedCapabilities.setCapability("e", Map.of(
                "f", true,
                "g", Map.of(
                        "h", List.of(1L, 2.14D, 3L)
                )
        ));
        Assert.assertEquals(SeleniumDriverConfigHelper.getDriverCapabilities(), expectedCapabilities);
    }
}