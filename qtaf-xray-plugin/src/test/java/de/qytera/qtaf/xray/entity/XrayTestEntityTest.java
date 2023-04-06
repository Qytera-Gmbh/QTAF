package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XrayTestEntityTest {

    @BeforeMethod
    public void clearConfigMap() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.remove(XrayConfigHelper.XRAY_SERVICE_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_TEST_PASSED_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_TEST_FAILED_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_TEST_PENDING_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_TEST_SKIPPED_SELECTOR);
    }

    @Test
    public void testStatusServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayTestEntity entity = new XrayTestEntity();
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.SUCCESS).getStatus(), "PASS");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.FAILURE).getStatus(), "FAIL");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.PENDING).getStatus(), "EXECUTING");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.SKIPPED).getStatus(), "TODO");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayTestEntity entity = new XrayTestEntity();
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.SUCCESS).getStatus(), "PASSED");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.FAILURE).getStatus(), "FAILED");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.PENDING).getStatus(), "EXECUTING");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.SKIPPED).getStatus(), "TODO");
    }

    @Test
    public void testStatusCustom() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.STATUS_TEST_PASSED_SELECTOR, "SUCCESS");
        configMap.setString(XrayConfigHelper.STATUS_TEST_FAILED_SELECTOR, "FAILURE");
        configMap.setString(XrayConfigHelper.STATUS_TEST_PENDING_SELECTOR, "PENDING");
        configMap.setString(XrayConfigHelper.STATUS_TEST_SKIPPED_SELECTOR, "SKIPPED");
        XrayTestEntity entity = new XrayTestEntity();
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.SUCCESS).getStatus(), "SUCCESS");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.FAILURE).getStatus(), "FAILURE");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.PENDING).getStatus(), "PENDING");
        Assert.assertEquals(entity.setStatus(TestScenarioLogCollection.Status.SKIPPED).getStatus(), "SKIPPED");
    }

}
