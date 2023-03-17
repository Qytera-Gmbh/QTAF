package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayManualTestStepResultEntity;

/**
 * This service is responsible for building the Import DTO for the test execution for Xray Cloud
 */
public class XrayCloudJsonImportBuilder extends AbstractXrayJsonImportBuilder {
    @Override
    public void setTestStatus(TestScenarioLogCollection scenarioLog, XrayTestEntity xrayTestEntity) {
        if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.SUCCESS) {
            xrayTestEntity.setStatus(XrayTestEntity.Status.PASSED);
        } else {
            xrayTestEntity.setStatus(XrayTestEntity.Status.FAILED);
        }
    }

    @Override
    public void setStepStatus(StepInformationLogMessage stepLog, XrayManualTestStepResultEntity xrayTestStepEntity) {
        if (stepLog.getStatus() == StepInformationLogMessage.Status.PASS) {
            xrayTestStepEntity.setStatus(XrayManualTestStepResultEntity.Status.PASSED);
        } else if (stepLog.getStatus() == StepInformationLogMessage.Status.ERROR) {
            xrayTestStepEntity.setStatus(XrayManualTestStepResultEntity.Status.FAILED);
        } else if (stepLog.getStatus() == StepInformationLogMessage.Status.PENDING) {
            xrayTestStepEntity.setStatus(XrayManualTestStepResultEntity.Status.ABORTED);
        }
    }
}
