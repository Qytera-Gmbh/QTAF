package de.qytera.qtaf.xray.service;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XrayServiceFactoryTest {

    @BeforeMethod
    public void resetConfig() {
        QtafFactory.getConfiguration().clear();
    }

    /**
     * Test if Xray Service Factory produces different Services based on service values
     */
    @Test
    public void testServiceFactoryCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        configMap.setString(XrayConfigHelper.AUTHENTICATION_XRAY_CLIENT_ID, "abc");
        configMap.setString(XrayConfigHelper.AUTHENTICATION_XRAY_CLIENT_SECRET, "def");
        Assert.assertEquals(XrayServiceFactory.getInstance().getClass(), XrayCloudService.class);
    }

    /**
     * Test if Xray Service Factory produces XrayServerService if service value is set to "server"
     */
    @Test
    public void testServiceFactoryServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        configMap.setString(XrayConfigHelper.AUTHENTICATION_XRAY_BEARER_TOKEN, "api");
        Assert.assertEquals(XrayServiceFactory.getInstance().getClass(), XrayServerService.class);
    }
}
