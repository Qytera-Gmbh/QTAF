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
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(entity.getStatus(), "PASS");
        entity.setStatus(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(entity.getStatus(), "FAIL");
        entity.setStatus(TestScenarioLogCollection.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity.setStatus(TestScenarioLogCollection.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(entity.getStatus(), "PASSED");
        entity.setStatus(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(entity.getStatus(), "FAILED");
        entity.setStatus(TestScenarioLogCollection.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity.setStatus(TestScenarioLogCollection.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCustom() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.STATUS_TEST_PASSED_SELECTOR, "SUCCESS");
        configMap.setString(XrayConfigHelper.STATUS_TEST_FAILED_SELECTOR, "FAILURE");
        configMap.setString(XrayConfigHelper.STATUS_TEST_PENDING_SELECTOR, "PENDING");
        configMap.setString(XrayConfigHelper.STATUS_TEST_SKIPPED_SELECTOR, "SKIPPED");
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(entity.getStatus(), "SUCCESS");
        entity.setStatus(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(entity.getStatus(), "FAILURE");
        entity.setStatus(TestScenarioLogCollection.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "PENDING");
        entity.setStatus(TestScenarioLogCollection.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "SKIPPED");
    }

}
