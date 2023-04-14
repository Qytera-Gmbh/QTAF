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
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PASSED);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_FAILED);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PENDING);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_SKIPPED);
        configMap.remove(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_UNDEFINED);
    }

    @Test
    public void testStatusServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "PASS");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAIL");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "PASSED");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAILED");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "EXECUTING");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "TODO");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "TODO");
    }

    @Test
    public void testStatusCustomCloud() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PASSED, "SUCCESS");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_FAILED, "FAILURE");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PENDING, "PENDING");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_SKIPPED, "SKIPPED");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_UNDEFINED, "UNDEFINED");
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "SUCCESS");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAILURE");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "PENDING");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "SKIPPED");
        entity = new XrayManualTestStepResultEntityCloud(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "UNDEFINED");
    }

    @Test
    public void testStatusCustomServer() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PASSED, "SUCCESS");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_FAILED, "FAILURE");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PENDING, "PENDING");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_SKIPPED, "SKIPPED");
        configMap.setString(XrayConfigHelper.RESULTS_UPLOAD_CUSTOM_STATUS_STEP_UNDEFINED, "UNDEFINED");
        configMap.setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayManualTestStepResultEntity entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.PASS);
        Assert.assertEquals(entity.getStatus(), "SUCCESS");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.ERROR);
        Assert.assertEquals(entity.getStatus(), "FAILURE");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.PENDING);
        Assert.assertEquals(entity.getStatus(), "PENDING");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.SKIPPED);
        Assert.assertEquals(entity.getStatus(), "SKIPPED");
        entity = new XrayManualTestStepResultEntityServer(StepInformationLogMessage.Status.UNDEFINED);
        Assert.assertEquals(entity.getStatus(), "UNDEFINED");
    }

}
