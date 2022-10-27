package de.qytera.qtaf.xray.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XrayConfigHelperTest {
    @Test
    public void testServiceDefaultValue() {
        // Default value
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "cloud");
    }

    @Test
    public void testService() {
        ConfigMap configMap = QtafFactory.getConfiguration();

        configMap.setString("xray.service", "server");
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "server");

        configMap.setString("xray.service", "cloud");
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "cloud");
    }
}
