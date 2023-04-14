package de.qytera.qtaf.xray.builder.test.info;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntity;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntityCloud;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntityServer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Builds an {@link XrayTestInfoEntity} objects for a single test iteration.
 */
@RequiredArgsConstructor
public class SingleIterationXrayTestInfoEntityBuilder extends XrayTestInfoEntityBuilder {

    /**
     * The scenario log to use for constructing the test information.
     */
    @NonNull
    private TestScenarioLogCollection scenarioLog;

    @Override
    public XrayTestInfoEntity buildTestInfo(XrayTest xrayTest) {
        XrayTestInfoEntity entity = null;
        if (Boolean.TRUE.equals(XrayConfigHelper.getResultsTestsInfoStepsUpdateOnSingleIteration())) {
            String projectKey = xrayTest.key().substring(0, xrayTest.key().indexOf("-"));
            if (XrayConfigHelper.isXrayCloudService()) {
                entity = new XrayTestInfoEntityCloud("", projectKey, "Manual");
            } else {
                entity = new XrayTestInfoEntityServer("", projectKey, "Manual");
            }
            for (LogMessage logMessage : scenarioLog.getLogMessages()) {
                if (logMessage instanceof StepInformationLogMessage stepLog) {
                    entity.getSteps().add(buildTestStepEntity(stepLog));
                }
            }
        }
        return entity;
    }

}
