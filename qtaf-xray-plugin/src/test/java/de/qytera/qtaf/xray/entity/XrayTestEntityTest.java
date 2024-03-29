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
        configMap.remove(XrayConfigHelper.XRAY_SERVICE);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PASSED);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_FAILED);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PENDING);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_SKIPPED);
    }

    @Test
    public void testStatusServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE, "server");
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(entity.getStatus(), "PASS");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(entity.getStatus(), "FAIL");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE, "cloud");
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(entity.getStatus(), "PASSED");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(entity.getStatus(), "FAILED");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCustom() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PASSED, "SUCCESS");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_FAILED, "FAILURE");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PENDING, "PENDING");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_TEST_SKIPPED, "SKIPPED");
        XrayTestEntity entity = new XrayTestEntity(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(entity.getStatus(), "SUCCESS");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(entity.getStatus(), "FAILURE");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "PENDING");
        entity = new XrayTestEntity(TestScenarioLogCollection.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "SKIPPED");
    }

}
