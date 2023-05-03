package de.qytera.qtaf.xray.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XrayConfigHelperTest {

    @BeforeMethod
    public void clearConfig() {
        QtafFactory.getConfiguration().clear();
    }

    @Test
    public void testService() {
        ConfigMap configMap = QtafFactory.getConfiguration();

        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "server");

        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        Assert.assertEquals(XrayConfigHelper.getXrayService(), "cloud");
    }

    @Test
    public void testGetResultsUploadTestPlanKey() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        Assert.assertNull(XrayConfigHelper.getResultsUploadTestPlanKey());
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_TEST_PLAN_KEY, "PRJ-123");
        Assert.assertEquals(XrayConfigHelper.getResultsUploadTestPlanKey(), "PRJ-123");
    }
}
