package de.qytera.qtaf.xray.builder.test.info;

import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.entity.XrayManualTestStepResultEntity;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntity;
import de.qytera.qtaf.xray.entity.XrayTestStepEntity;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A class for building {@link XrayTestInfoEntity} objects. The way these objects are built heavily depends on the
 * test suite configuration.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
public abstract class XrayTestInfoEntityBuilder {

    /**
     * Builds a test information entity for a test. Returns null if the configuration has not been configured to build
     * test information entities.
     *
     * @param xrayTest the {@link XrayTest} annotation of the test
     * @return the test information entity
     */
    public abstract XrayTestInfoEntity buildTestInfo(XrayTest xrayTest);

    /**
     * Converts a {@link StepInformationLogMessage} into an {@link XrayTestStepEntity}.
     * <p>
     * <b>Note</b>: these are different from {@link XrayManualTestStepResultEntity}.
     * </p>
     *
     * @param stepLog the step information to convert
     * @return the converted Xray test step
     */
    protected XrayTestStepEntity buildTestStepEntity(StepInformationLogMessage stepLog) {
        XrayTestStepEntity entity = new XrayTestStepEntity(stepLog.getStep().getName());
        String data = stepLog.getStepParameters().stream()
                .map(p -> {
                    Object value = p.getValue();
                    if (value instanceof Object[] array) {
                        value = Arrays.toString(array);
                    }
                    return String.format("%s=%s", p.getName(), value);
                })
                .collect(Collectors.joining("\n"));
        if (!data.isBlank()) {
            entity.setData(data);
        }
        if (stepLog.getResult() != null) {
            entity.setResult(stepLog.getResult().toString());
        }
        return entity;
    }

}
