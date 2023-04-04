package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.entity.XrayTestIterationParameterEntity;
import de.qytera.qtaf.xray.entity.XrayTestIterationResultEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class XrayJsonImportBuilderTest {

    private static final XrayTest ANNOTATION = new XrayTest() {

        @Override
        public String key() {
            return "QTAF-42";
        }

        @Override
        public boolean scenarioReport() {
            return false;
        }

        @Override
        public boolean screenshots() {
            return false;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return XrayTest.class;
        }
    };

    private static TestScenarioLogCollection scenarioWithId(String id) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection("feature-1", "scenario-1", id, "debug-scenario");
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[]{ANNOTATION});
        return scenarioCollection;
    }

    @Test
    public void testLongParameterValueTruncation() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setInt(XrayConfigHelper.RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE, 3);

        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("feature-1", "feature-xray");
        TestSuiteLogCollection suiteCollection = TestSuiteLogCollection.getInstance();
        suiteCollection.addTestClassLogCollection(featureCollection);

        TestScenarioLogCollection scenarioCollection = scenarioWithId("0");
        scenarioCollection.addParameters(new String[]{
                "a",
                "abcd",
                "abcdefghijklmnop",
                "abcdefghijklmnopqrstuvwxyz01234567891011121314151617181920212223"
        });
        featureCollection.addScenarioLogCollection(scenarioCollection);

        scenarioCollection = scenarioWithId("1");
        scenarioCollection.addParameters(new String[]{
                "ab",
                "abcdefghi",
                "abcdefghijklmnopqrstuvwxyz012345"
        });
        featureCollection.addScenarioLogCollection(scenarioCollection);

        XrayImportRequestDto dto = new XrayJsonImportBuilder().buildFromTestSuiteLogs(suiteCollection);
        List<XrayTestIterationResultEntity> iterations = dto.getTests().get(0).getIterations();
        Assert.assertEquals(
                iterations.get(0).getParameters().stream().map(XrayTestIterationParameterEntity::getValue).collect(Collectors.toList()),
                Arrays.asList("a", "abc", "abc", "abc")
        );
        Assert.assertEquals(
                iterations.get(1).getParameters().stream().map(XrayTestIterationParameterEntity::getValue).collect(Collectors.toList()),
                Arrays.asList("ab", "abc", "abc")
        );
    }

    private static StepInformationLogMessage successfulStep(String methodName) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.PASS);
        return message;
    }

    private static StepInformationLogMessage failingStep(String methodName) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.ERROR);
        return message;
    }

    @Test
    public void testIterationStatus() {

        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("feature-1", "feature-xray");
        TestSuiteLogCollection suiteCollection = TestSuiteLogCollection.getInstance();
        suiteCollection.addTestClassLogCollection(featureCollection);

        // Iteration with no failing step and an assumed successful assertion.
        TestScenarioLogCollection scenarioCollection = scenarioWithId("0");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(successfulStep("clickLogin"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        featureCollection.addScenarioLogCollection(scenarioCollection);

        // Iteration with no failing step and an assumed failed assertion.
        scenarioCollection = scenarioWithId("1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(successfulStep("clickLogin"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        featureCollection.addScenarioLogCollection(scenarioCollection);

        // Iteration with a failing step.
        scenarioCollection = scenarioWithId("2");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(failingStep("clickLogin"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        featureCollection.addScenarioLogCollection(scenarioCollection);

        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayImportRequestDto dto = new XrayJsonImportBuilder().buildFromTestSuiteLogs(suiteCollection);
        List<XrayTestIterationResultEntity> iterations = dto.getTests().get(0).getIterations();
        Assert.assertEquals(iterations.get(0).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.SUCCESS));
        Assert.assertEquals(iterations.get(1).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.FAILURE));
        Assert.assertEquals(iterations.get(2).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.FAILURE));
    }

}
