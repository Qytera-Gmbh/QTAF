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
import de.qytera.qtaf.xray.entity.XrayIterationParameterEntity;
import de.qytera.qtaf.xray.entity.XrayIterationResultEntity;
import de.qytera.qtaf.xray.entity.XrayManualTestStepResultEntity;
import de.qytera.qtaf.xray.entity.XrayTestStepEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.*;
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

    private static TestScenarioLogCollection scenarioWithoutAnnotation(int scenarioIteration, String featureName) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureName,
                "SomeClass.doesSomething",
                String.valueOf(scenarioIteration),
                "a test scenario"
        );
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[]{/* empty */});
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
    public void testLongParameterValueTruncation() throws XrayJsonImportBuilder.NoXrayTestException {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setInt(XrayConfigHelper.RESULTS_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE, 3);

        TestScenarioLogCollection scenarioCollection = scenario(0, "feature-x");
        scenarioCollection.addParameters(new String[]{
                "a",
                "abcd",
                "abcdefghijklmnop",
                "abcdefghijklmnopqrstuvwxyz01234567891011121314151617181920212223"
        });

        scenarioCollection = scenario(1, "feature-x");
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
    public void testIterationStatus() throws XrayJsonImportBuilder.NoXrayTestException {

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
    public void testSingleRunScreenshotEvidence() throws XrayJsonImportBuilder.NoXrayTestException {

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
        Assert.assertTrue(steps.get(0).getAllEvidence().isEmpty());
        Assert.assertTrue(steps.get(1).getAllEvidence().isEmpty());
        Assert.assertEquals(steps.get(2).getAllEvidence().size(), 2);
        Assert.assertEquals(steps.get(2).getAllEvidence().get(0).getContentType(), "image/png");
        Assert.assertEquals(steps.get(2).getAllEvidence().get(0).getFilename(), "qytera.png");
        Assert.assertEquals(steps.get(2).getAllEvidence().get(1).getContentType(), "image/png");
        Assert.assertEquals(steps.get(2).getAllEvidence().get(1).getFilename(), "turtle.png");
    }

    @Test
    public void testIterationsScreenshotEvidence() throws XrayJsonImportBuilder.NoXrayTestException {

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

        Assert.assertTrue(iterations.get(0).getSteps().get(0).getAllEvidence().isEmpty());
        Assert.assertTrue(iterations.get(0).getSteps().get(1).getAllEvidence().isEmpty());
        Assert.assertEquals(iterations.get(0).getSteps().get(2).getAllEvidence().size(), 1);
        Assert.assertEquals(iterations.get(0).getSteps().get(2).getAllEvidence().get(0).getContentType(), "image/png");
        Assert.assertEquals(iterations.get(0).getSteps().get(2).getAllEvidence().get(0).getFilename(), "turtle.png");

        Assert.assertTrue(iterations.get(1).getSteps().get(0).getAllEvidence().isEmpty());
        Assert.assertEquals(iterations.get(1).getSteps().get(1).getAllEvidence().size(), 1);
        Assert.assertEquals(iterations.get(1).getSteps().get(1).getAllEvidence().get(0).getContentType(), "image/png");
        Assert.assertEquals(iterations.get(1).getSteps().get(1).getAllEvidence().get(0).getFilename(), "turtle.png");
    }

    @Test(
            expectedExceptions = {
                    XrayJsonImportBuilder.NoXrayTestException.class
            }
    )
    public void testSuiteWithoutXrayTest() throws XrayJsonImportBuilder.NoXrayTestException {
        TestScenarioLogCollection scenarioCollection = scenarioWithoutAnnotation(1, "feature-generic");
        scenarioCollection.addLogMessage(successfulStep("doWhatever"));
        scenarioCollection.addLogMessage(failingStep("clickSomething", "path/to/something.png", "path/to/whatever.png"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        scenarioCollection = scenarioWithoutAnnotation(1, "feature-unrelated");
        scenarioCollection.addLogMessage(successfulStep("successfulStep"));
        scenarioCollection.addLogMessage(failingStep("failingStep"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
    }

    @Test(
            expectedExceptions = {
                    XrayJsonImportBuilder.NoXrayTestException.class
            }
    )
    public void testSuiteWithMissingXrayTest() throws XrayJsonImportBuilder.NoXrayTestException {
        TestScenarioLogCollection scenarioCollection = scenarioWithoutAnnotation(1, "feature-iterated");
        scenarioCollection.addLogMessage(successfulStep("doWhatever"));
        scenarioCollection.addLogMessage(failingStep("clickSomething", "path/to/something.png", "path/to/whatever.png"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        scenarioCollection = scenarioWithoutAnnotation(2, "feature-iterated");
        scenarioCollection.addLogMessage(successfulStep("successStep"));
        scenarioCollection.addLogMessage(failingStep("clickSomething", "path/to/something.png"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
    }

    @Test
    public void testSingleRunStepUpdate() throws XrayJsonImportBuilder.NoXrayTestException {
        TestScenarioLogCollection scenarioCollection = scenario(1, "feature-step-update");
        StepInformationLogMessage step = successfulStep("stepWithoutParameters");
        scenarioCollection.addLogMessage(step);
        step = successfulStep("stepWithParameters");
        step.addStepParameter("x", 42);
        step.addStepParameter("y", 45.0);
        step.addStepParameter(null, "null");
        step.addStepParameter("null", null);
        scenarioCollection.addLogMessage(step);
        step = failingStep("failingStepWithParameters");
        step.addStepParameter("array", new String[]{"hello", "there"});
        // Use a LinkedHashMap to enforce insertion ordering.
        Map<String, Object> complexParameter = new LinkedHashMap<>();
        complexParameter.put("a", 15);
        complexParameter.put("b", "\"Jeff\"");
        complexParameter.put("c", new ArrayList<>());
        step.addStepParameter("complex", complexParameter);
        scenarioCollection.addLogMessage(step);
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_TESTS_INFO_STEPS_UPDATE_ON_SINGLE_ITERATION, true);
        XrayImportRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
        Assert.assertEquals(dto.getTests().size(), 1);
        List<XrayTestStepEntity> steps = dto.getTests().get(0).getTestInfo().getSteps();
        Assert.assertEquals(steps.size(), 3);
        Assert.assertNull(steps.get(0).getData());
        Assert.assertEquals(steps.get(1).getData(), "x=42\ny=45.0\nnull=null\nnull=null");
        Assert.assertEquals(steps.get(2).getData(), "array=[hello, there]\ncomplex={a=15, b=\"Jeff\", c=[]}");
    }

    @Test
    public void testMultipleRunStepMerge() throws XrayJsonImportBuilder.NoXrayTestException {
        // First iteration.
        TestScenarioLogCollection scenarioCollection = scenario(1, "feature-step-merge");
        StepInformationLogMessage step = successfulStep("stepWithoutParameters");
        scenarioCollection.addLogMessage(step);
        step = successfulStep("stepWithParameters");
        step.setScreenshotBefore("src/test/resources/images/qytera.png");
        step.addStepParameter("x", 42);
        step.addStepParameter("y", 45.0);
        step.addStepParameter(null, "null");
        step.addStepParameter("null", null);
        scenarioCollection.addLogMessage(step);
        step = failingStep(
                "failingStepWithParameters",
                "src/test/resources/images/turtle.png",
                "src/test/resources/images/qytera.png"
        );
        step.addStepParameter("array", new String[]{"hello", "there"});
        scenarioCollection.addLogMessage(step);
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        // Second iteration.
        scenarioCollection = scenario(2, "feature-step-merge");
        step = successfulStep("stepWithoutParameters");
        scenarioCollection.addLogMessage(step);
        step = successfulStep("stepWithParameters");
        step.addStepParameter("x", -747);
        step.addStepParameter("y", null);
        step.addStepParameter(null, "null");
        step.addStepParameter("null", "green cabbage");
        scenarioCollection.addLogMessage(step);
        step = failingStep("failingStepWithParameters");
        step.addStepParameter("array", new String[]{"knock", "knock", "who's", "there?"});
        scenarioCollection.addLogMessage(step);
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_TESTS_INFO_STEPS_MERGE_ON_MULTIPLE_ITERATIONS, true);
        XrayImportRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest();
        Assert.assertEquals(dto.getTests().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getTestInfo().getSteps().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getIterations().size(), 2);
        Assert.assertEquals(dto.getTests().get(0).getIterations().get(0).getSteps().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getIterations().get(1).getSteps().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getIterations().get(0).getSteps().get(0).getAllEvidence().size(), 3);
    }

}
