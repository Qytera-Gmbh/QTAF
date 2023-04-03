package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XrayTestEntityTest {

    @BeforeMethod
    public void clearConfigMap() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.remove(XrayConfigHelper.XRAY_SERVICE_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_PASSED_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_FAILED_SELECTOR);
    }

    @Test
    public void testStatusServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        Assert.assertEquals(XrayTestEntity.Status.failed().text, "FAIL");
        Assert.assertEquals(XrayTestEntity.Status.passed().text, "PASS");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        Assert.assertEquals(XrayTestEntity.Status.failed().text, "FAILED");
        Assert.assertEquals(XrayTestEntity.Status.passed().text, "PASSED");
    }

    @Test
    public void testStatusCustom() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.STATUS_PASSED_SELECTOR, "SUCCESS");
        configMap.setString(XrayConfigHelper.STATUS_FAILED_SELECTOR, "FAILURE");
        Assert.assertEquals(XrayTestEntity.Status.failed().text, "FAILURE");
        Assert.assertEquals(XrayTestEntity.Status.passed().text, "SUCCESS");
    }

}
