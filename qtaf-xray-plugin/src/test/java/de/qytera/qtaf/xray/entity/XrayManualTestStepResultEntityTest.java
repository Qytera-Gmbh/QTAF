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
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntity();
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.PASS).getStatus(), "PASS");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.ERROR).getStatus(), "FAIL");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.PENDING).getStatus(), "EXECUTING");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.SKIPPED).getStatus(), "TODO");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.UNDEFINED).getStatus(), "TODO");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntity();
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.PASS).getStatus(), "PASSED");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.ERROR).getStatus(), "FAILED");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.PENDING).getStatus(), "EXECUTING");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.SKIPPED).getStatus(), "TODO");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.UNDEFINED).getStatus(), "TODO");
    }

    @Test
    public void testStatusCustom() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.STATUS_STEP_PASSED_SELECTOR, "SUCCESS");
        configMap.setString(XrayConfigHelper.STATUS_STEP_FAILED_SELECTOR, "FAILURE");
        configMap.setString(XrayConfigHelper.STATUS_STEP_PENDING_SELECTOR, "PENDING");
        configMap.setString(XrayConfigHelper.STATUS_STEP_SKIPPED_SELECTOR, "SKIPPED");
        configMap.setString(XrayConfigHelper.STATUS_STEP_UNDEFINED_SELECTOR, "UNDEFINED");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntity();
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.PASS).getStatus(), "SUCCESS");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.ERROR).getStatus(), "FAILURE");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.PENDING).getStatus(), "PENDING");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.SKIPPED).getStatus(), "SKIPPED");
        Assert.assertEquals(entity.setStatus(StepInformationLogMessage.Status.UNDEFINED).getStatus(), "UNDEFINED");
    }

}
