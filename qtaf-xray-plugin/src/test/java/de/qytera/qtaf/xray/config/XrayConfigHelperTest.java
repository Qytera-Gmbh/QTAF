package de.qytera.qtaf.xray.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XrayConfigHelperTest {
    @Test
    public void testService() {
        ConfigMap configMap = QtafFactory.getConfiguration();

        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "server");

        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "cloud");
    }
}
