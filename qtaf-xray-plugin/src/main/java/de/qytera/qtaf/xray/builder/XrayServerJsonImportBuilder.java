package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayManualTestStepResultEntity;

/**
 * This service is responsible for building the Import DTO for the test execution for Xray Server
 */
public class XrayServerJsonImportBuilder extends AbstractXrayJsonImportBuilder {
    @Override
    public void setTestStatus(TestScenarioLogCollection scenarioLog, XrayTestEntity xrayTestEntity) {
        if (scenarioLog.getStatus() == TestScenarioLogCollection.Status.SUCCESS) {
            xrayTestEntity.setStatus(XrayTestEntity.Status.PASS);
        } else {
            xrayTestEntity.setStatus(XrayTestEntity.Status.FAIL);
        }
    }

    @Override
    public void setStepStatus(StepInformationLogMessage stepLog, XrayManualTestStepResultEntity xrayManualTestStepResultEntity) {
        if (stepLog.getStatus() == StepInformationLogMessage.Status.PASS) {
            xrayManualTestStepResultEntity.setStatus(XrayManualTestStepResultEntity.Status.PASS);
        } else if (stepLog.getStatus() == StepInformationLogMessage.Status.ERROR) {
            xrayManualTestStepResultEntity.setStatus(XrayManualTestStepResultEntity.Status.FAIL);
        } else if (stepLog.getStatus() == StepInformationLogMessage.Status.PENDING) {
            xrayManualTestStepResultEntity.setStatus(XrayManualTestStepResultEntity.Status.ABORTED);
        }
    }
}
