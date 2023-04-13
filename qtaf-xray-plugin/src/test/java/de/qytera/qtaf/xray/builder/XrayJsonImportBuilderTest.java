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
import de.qytera.qtaf.xray.entity.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
            return true;
        }

        @Override
        public boolean screenshots() {
            return true;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return XrayTest.class;
        }
    };

    private static TestScenarioLogCollection scenario(int scenarioIteration, String featureName) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureName,
                "SomeClass.doesSomething",
                String.valueOf(scenarioIteration),
                "a test scenario"
        );
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[]{ANNOTATION});
        // Add scenario to its corresponding feature collection.
        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                featureName,
                "a test feature"
        );
        featureCollection.addScenarioLogCollection(scenarioCollection);
        // Add feature to suite collection.
        TestSuiteLogCollection.getInstance().addTestClassLogCollection(featureCollection);
        return scenarioCollection;
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

    private static StepInformationLogMessage failingStep(
            String methodName,
            String screenshotPathBefore
    ) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.ERROR);
        message.setScreenshotBefore(screenshotPathBefore);
        return message;
    }

    private static StepInformationLogMessage failingStep(
            String methodName,
            String screenshotPathBefore,
            String screenshotPathAfter
    ) {
        StepInformationLogMessage message = new StepInformationLogMessage(methodName, "?");
        message.setStatus(StepInformationLogMessage.Status.ERROR);
        message.setScreenshotBefore(screenshotPathBefore);
        message.setScreenshotAfter(screenshotPathAfter);
        return message;
    }

    @BeforeMethod
    public void clearSuite() {
        TestSuiteLogCollection.getInstance().clear();
        TestFeatureLogCollection.clearIndex();
        TestScenarioLogCollection.clearIndex();
    }

    @Test
    public void testLongParameterValueTruncation() {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setInt(XrayConfigHelper.RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE, 3);

        TestScenarioLogCollection scenarioCollection = scenario(0, "feature-1");
        scenarioCollection.addParameters(new String[]{
                "a",
                "abcd",
                "abcdefghijklmnop",
                "abcdefghijklmnopqrstuvwxyz01234567891011121314151617181920212223"
        });

        scenarioCollection = scenario(1, "feature-1");
        scenarioCollection.addParameters(new String[]{
                "ab",
                "abcdefghi",
                "abcdefghijklmnopqrstuvwxyz012345"
        });

        XrayImportRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
        List<XrayIterationResultEntity> iterations = dto.getTests().get(0).getIterations();
        Assert.assertEquals(
                iterations.get(0).getParameters().stream().map(XrayIterationParameterEntity::getValue).collect(Collectors.toList()),
                Arrays.asList("a", "abc", "abc", "abc")
        );
        Assert.assertEquals(
                iterations.get(1).getParameters().stream().map(XrayIterationParameterEntity::getValue).collect(Collectors.toList()),
                Arrays.asList("ab", "abc", "abc")
        );
    }

    @Test
    public void testIterationStatus() {

        // Iteration with no failing step and an assumed successful assertion.
        TestScenarioLogCollection scenarioCollection = scenario(0, "feature-1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(successfulStep("clickLogin"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);

        // Iteration with no failing step and an assumed failed assertion.
        scenarioCollection = scenario(1, "feature-1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(successfulStep("clickLogin"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);

        // Iteration with a failing step.
        scenarioCollection = scenario(2, "feature-1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(failingStep("clickLogin"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);

        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayImportRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
        List<XrayIterationResultEntity> iterations = dto.getTests().get(0).getIterations();
        Assert.assertEquals(iterations.get(0).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.SUCCESS));
        Assert.assertEquals(iterations.get(1).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.FAILURE));
        Assert.assertEquals(iterations.get(2).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.FAILURE));
    }

    @Test
    public void testSingleRunScreenshotEvidence() {

        // Iteration with no failing step and an assumed successful assertion.
        TestScenarioLogCollection scenarioCollection = scenario(0, "feature-1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(failingStep(
                "clickLogout",
                "src/test/resources/images/qytera.png",
                "src/test/resources/images/turtle.png")
        );
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);

        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        XrayImportRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
        List<XrayManualTestStepResultEntity> steps = dto.getTests().get(0).getSteps();
        Assert.assertTrue(((XrayManualTestStepResultEntityServer) steps.get(0)).getEvidences().isEmpty());
        Assert.assertTrue(((XrayManualTestStepResultEntityServer) steps.get(1)).getEvidences().isEmpty());
        Assert.assertEquals(((XrayManualTestStepResultEntityServer) steps.get(2)).getEvidences().size(), 2);
        Assert.assertEquals(((XrayManualTestStepResultEntityServer) steps.get(2)).getEvidences().get(0).getContentType(), "image/png");
        Assert.assertEquals(((XrayManualTestStepResultEntityServer) steps.get(2)).getEvidences().get(0).getFilename(), "qytera.png");
        Assert.assertEquals(((XrayManualTestStepResultEntityServer) steps.get(2)).getEvidences().get(1).getContentType(), "image/png");
        Assert.assertEquals(((XrayManualTestStepResultEntityServer) steps.get(2)).getEvidences().get(1).getFilename(), "turtle.png");
    }

    @Test
    public void testIterationsScreenshotEvidence() {

        // Iteration with no failing step and an assumed successful assertion.
        TestScenarioLogCollection scenarioCollection = scenario(0, "feature-1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(successfulStep("enterPassword"));
        scenarioCollection.addLogMessage(failingStep(
                "clickLogout",
                "",
                "src/test/resources/images/turtle.png")
        );
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);

        scenarioCollection = scenario(1, "feature-1");
        scenarioCollection.addLogMessage(successfulStep("enterUsername"));
        scenarioCollection.addLogMessage(failingStep(
                "clickLogout",
                "src/test/resources/images/turtle.png")
        );
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);

        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        XrayImportRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
        List<XrayIterationResultEntity> iterations = dto.getTests().get(0).getIterations();

        Assert.assertTrue(((XrayManualTestStepResultEntityCloud) iterations.get(0).getSteps().get(0)).getEvidence().isEmpty());
        Assert.assertTrue(((XrayManualTestStepResultEntityCloud) iterations.get(0).getSteps().get(1)).getEvidence().isEmpty());
        Assert.assertEquals(((XrayManualTestStepResultEntityCloud) iterations.get(0).getSteps().get(2)).getEvidence().size(), 1);
        Assert.assertEquals(((XrayManualTestStepResultEntityCloud) iterations.get(0).getSteps().get(2)).getEvidence().get(0).getContentType(), "image/png");
        Assert.assertEquals(((XrayManualTestStepResultEntityCloud) iterations.get(0).getSteps().get(2)).getEvidence().get(0).getFilename(), "turtle.png");

        Assert.assertTrue(((XrayManualTestStepResultEntityCloud) iterations.get(1).getSteps().get(0)).getEvidence().isEmpty());
        Assert.assertEquals(((XrayManualTestStepResultEntityCloud) iterations.get(1).getSteps().get(1)).getEvidence().size(), 1);
        Assert.assertEquals(((XrayManualTestStepResultEntityCloud) iterations.get(1).getSteps().get(1)).getEvidence().get(0).getContentType(), "image/png");
        Assert.assertEquals(((XrayManualTestStepResultEntityCloud) iterations.get(1).getSteps().get(1)).getEvidence().get(0).getFilename(), "turtle.png");
    }

}
