package de.qytera.qtaf.xray.service;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XrayServiceFactoryTest {
    /**
     * Test if Xray Service Factory produces different Services based on service values
     */
    @Test
    public void testServiceFactoryCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString("xray.service", "cloud");
        Assert.assertEquals(
                XrayServiceFactory.getInstance().getClass().getName(),
                "de.qytera.qtaf.xray.service.XrayCloudService"
        );
    }

    /**
     * Test if Xray Service Factory produces XrayServerService if service value is set to "server"
     */
    @Test
    public void testServiceFactoryServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString("xray.service", "server");
        Assert.assertEquals(
                XrayServiceFactory.getInstance().getClass().getName(),
                "de.qytera.qtaf.xray.service.XrayServerService"
        );
    }
}
