package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestStepEntity;

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
    public void setStepStatus(StepInformationLogMessage stepLog, XrayTestStepEntity xrayTestStepEntity) {
        if (stepLog.getStatus() == StepInformationLogMessage.Status.PASS) {
            xrayTestStepEntity.setStatus(XrayTestStepEntity.Status.PASS);
        } else if (stepLog.getStatus() == StepInformationLogMessage.Status.ERROR) {
            xrayTestStepEntity.setStatus(XrayTestStepEntity.Status.FAIL);
        } else if (stepLog.getStatus() == StepInformationLogMessage.Status.PENDING) {
            xrayTestStepEntity.setStatus(XrayTestStepEntity.Status.ABORTED);
        }
    }
}
