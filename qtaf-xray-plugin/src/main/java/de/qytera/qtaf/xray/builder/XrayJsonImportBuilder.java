package de.qytera.qtaf.xray.builder;

import com.google.inject.Singleton;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.test.XrayTestEntityBuilder;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestExecutionInfoEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Transforms log collection into Xray Execution Import DTO
 */
@Singleton
@RequiredArgsConstructor
public class XrayJsonImportBuilder {

    /**
     * An exception thrown when a test suite did not execute any test marked with {@link XrayTest}.
     */
    public static class NoXrayTestException extends Exception {
        /**
         * Constructs a new exception with a predefined error message.
         */
        public NoXrayTestException() {
            super("Cannot build import execution request because no test linked to Xray was executed");
        }
    }

    @NonNull
    private TestSuiteLogCollection collection;

    /**
     * Creates an execution import DTO based on the test suite logs.
     *
     * @return the execution import DTO
     */
    public XrayImportRequestDto buildRequest() throws NoXrayTestException {
        XrayImportRequestDto xrayImportRequestDto = new XrayImportRequestDto();
        xrayImportRequestDto.setInfo(buildTestExecutionInfoEntity());
        xrayImportRequestDto.setTests(buildTestEntities());
        if (xrayImportRequestDto.getTests().isEmpty()) {
            throw new NoXrayTestException();
        }
        return xrayImportRequestDto;
    }

    private XrayTestExecutionInfoEntity buildTestExecutionInfoEntity() {
        XrayTestExecutionInfoEntity entity = new XrayTestExecutionInfoEntity();
        if (collection.getStart() != null) {
            String startDate = XrayJsonHelper.isoDateString(collection.getStart());
            entity.setStartDate(startDate);
        }
        if (collection.getEnd() != null) {
            String finishDate = XrayJsonHelper.isoDateString(collection.getEnd());
            entity.setFinishDate(finishDate);
        }
        entity.addTestEnvironment(collection.getOsName());
        entity.addTestEnvironment(collection.getDriverName());
        return entity;
    }

    private List<XrayTestEntity> buildTestEntities() {
        List<XrayTestEntity> entities = new ArrayList<>();
        for (TestFeatureLogCollection testFeatureLogCollection : collection.getTestFeatureLogCollections()) {
            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();
            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    XrayTestEntity entity = XrayTestEntityBuilder.buildFrom(collection, entry.getValue());
                    if (entity != null) {
                        entities.add(entity);
                    }
                }
            }
        }
        return entities;
    }

}
