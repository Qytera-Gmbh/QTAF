package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XrayManualTestStepResultEntityTest {

    @BeforeMethod
    public void clearConfigMap() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.remove(XrayConfigHelper.XRAY_SERVICE_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_STEP_PASSED_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_STEP_FAILED_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_STEP_PENDING_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_STEP_SKIPPED_SELECTOR);
        configMap.remove(XrayConfigHelper.STATUS_STEP_UNDEFINED_SELECTOR);
    }

    @Test
    public void testStatusServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "PASS");
        entity.setStatus(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAIL");
        entity.setStatus(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity.setStatus(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
        entity.setStatus(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "PASSED");
        entity.setStatus(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAILED");
        entity.setStatus(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity.setStatus(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
        entity.setStatus(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCustomCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.STATUS_STEP_PASSED_SELECTOR, "SUCCESS");
        configMap.setString(XrayConfigHelper.STATUS_STEP_FAILED_SELECTOR, "FAILURE");
        configMap.setString(XrayConfigHelper.STATUS_STEP_PENDING_SELECTOR, "PENDING");
        configMap.setString(XrayConfigHelper.STATUS_STEP_SKIPPED_SELECTOR, "SKIPPED");
        configMap.setString(XrayConfigHelper.STATUS_STEP_UNDEFINED_SELECTOR, "UNDEFINED");
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "SUCCESS");
        entity.setStatus(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAILURE");
        entity.setStatus(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "PENDING");
        entity.setStatus(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "SKIPPED");
        entity.setStatus(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "UNDEFINED");
    }

    @Test
    public void testStatusCustomServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.STATUS_STEP_PASSED_SELECTOR, "SUCCESS");
        configMap.setString(XrayConfigHelper.STATUS_STEP_FAILED_SELECTOR, "FAILURE");
        configMap.setString(XrayConfigHelper.STATUS_STEP_PENDING_SELECTOR, "PENDING");
        configMap.setString(XrayConfigHelper.STATUS_STEP_SKIPPED_SELECTOR, "SKIPPED");
        configMap.setString(XrayConfigHelper.STATUS_STEP_UNDEFINED_SELECTOR, "UNDEFINED");
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "SUCCESS");
        entity.setStatus(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAILURE");
        entity.setStatus(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "PENDING");
        entity.setStatus(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "SKIPPED");
        entity.setStatus(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "UNDEFINED");
    }

}
