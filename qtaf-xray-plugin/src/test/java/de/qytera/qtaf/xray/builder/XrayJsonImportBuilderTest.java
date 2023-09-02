package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayStatusHelper;
import de.qytera.qtaf.xray.dto.request.xray.ImportExecutionResultsRequestDto;
import de.qytera.qtaf.xray.entity.XrayIterationParameterEntity;
import de.qytera.qtaf.xray.entity.XrayIterationResultEntity;
import de.qytera.qtaf.xray.entity.XrayManualTestStepResultEntity;
import de.qytera.qtaf.xray.entity.XrayTestStepEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class XrayJsonImportBuilderTest {

    @BeforeMethod(description = "clear the entire configuration and set appropriate values for testing")
    public void clearSuite() {
        TestSuiteLogCollection.getInstance().clear();
        TestFeatureLogCollection.clearIndex();
        TestScenarioLogCollection.clearIndex();
        QtafFactory.getConfiguration().clear();
        QtafFactory.getConfiguration().setString(XrayConfigHelper.PROJECT_KEY, "QTAF");
        // We're not actually uploading anything, no need to query Jira for actual issue summaries during testing.
        QtafFactory.getConfiguration().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_KEEP_JIRA_SUMMARY, false);
    }

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

    @Test(description = "whether long parameters are truncated to the proper length")
    public void testLongParameterValueTruncation() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setInt(XrayConfigHelper.RESULTS_UPLOAD_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE, 3);

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

        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
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

    @Test(description = "whether test iteration statuses are correctly set")
    public void testIterationStatus() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {

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

        // Build import request.
        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
        List<XrayIterationResultEntity> iterations = dto.getTests().get(0).getIterations();
        Assert.assertEquals(iterations.get(0).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.SUCCESS));
        Assert.assertEquals(iterations.get(1).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.FAILURE));
        Assert.assertEquals(iterations.get(2).getStatus(), XrayStatusHelper.statusToText(TestScenarioLogCollection.Status.FAILURE));
    }

    @Test(description = "whether screenshot evidence can be attached to a single iteration")
    public void testSingleRunScreenshotEvidence() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
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
        // Build import request.
        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "server");
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
        List<XrayManualTestStepResultEntity> steps = dto.getTests().get(0).getSteps();
        Assert.assertTrue(steps.get(0).getAllEvidence().isEmpty());
        Assert.assertTrue(steps.get(1).getAllEvidence().isEmpty());
        Assert.assertEquals(steps.get(2).getAllEvidence().size(), 2);
        Assert.assertEquals(steps.get(2).getAllEvidence().get(0).getContentType(), "image/png");
        Assert.assertEquals(steps.get(2).getAllEvidence().get(0).getFilename(), "qytera.png");
        Assert.assertEquals(steps.get(2).getAllEvidence().get(1).getContentType(), "image/png");
        Assert.assertEquals(steps.get(2).getAllEvidence().get(1).getFilename(), "turtle.png");
    }

    @Test(description = "whether screenshot evidence can be attached to multiple iterations")
    public void testIterationsScreenshotEvidence() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
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
        // Build import request.
        ConfigurationFactory.getInstance().setString(XrayConfigHelper.XRAY_SERVICE_SELECTOR, "cloud");
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
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
            description = "whether single test iterations throw an exception if the scenarios are not Xray-annotated",
            expectedExceptions = {
                    XrayJsonImportBuilder.NoXrayTestException.class
            }
    )
    public void testSuiteWithoutXrayTestSingleIterations() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
        TestScenarioLogCollection scenarioCollection = scenarioWithoutAnnotation(1, "feature-generic");
        scenarioCollection.addLogMessage(successfulStep("doWhatever"));
        scenarioCollection.addLogMessage(failingStep("clickSomething", "path/to/something.png", "path/to/whatever.png"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        scenarioCollection = scenarioWithoutAnnotation(1, "feature-unrelated");
        scenarioCollection.addLogMessage(successfulStep("successfulStep"));
        scenarioCollection.addLogMessage(failingStep("failingStep"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        // Build import request.
        new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
    }

    @Test(
            description = "whether multiple test iterations throw an exception if the scenario is not Xray-annotated",
            expectedExceptions = {
                    XrayJsonImportBuilder.NoXrayTestException.class
            }
    )
    public void testSuiteWithoutXrayTestMultipleIterations() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
        TestScenarioLogCollection scenarioCollection = scenarioWithoutAnnotation(1, "feature-iterated");
        scenarioCollection.addLogMessage(successfulStep("doWhatever"));
        scenarioCollection.addLogMessage(failingStep("clickSomething", "path/to/something.png", "path/to/whatever.png"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        scenarioCollection = scenarioWithoutAnnotation(2, "feature-iterated");
        scenarioCollection.addLogMessage(successfulStep("successStep"));
        scenarioCollection.addLogMessage(failingStep("clickSomething", "path/to/something.png"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        // Build import request.
        new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
    }

    @Test(description = "whether an upload without step updates enabled contains step information to update")
    public void testXrayTestInfoWithoutUpdate() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
        TestScenarioLogCollection scenarioCollection = scenario(1, "feature-no-step-update");
        StepInformationLogMessage step = successfulStep("step1");
        scenarioCollection.addLogMessage(step);
        step = successfulStep("step2");
        scenarioCollection.addLogMessage(step);
        step = failingStep("failingStep");
        step.addStepParameter("array", new String[]{"hello", "there"});
        scenarioCollection.addLogMessage(step);
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        // Build import request.
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, false);
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
        Assert.assertEquals(dto.getTests().size(), 1);
        Assert.assertNull(dto.getTests().get(0).getTestInfo());
    }

    @Test(description = "whether an upload with one iteration updates test step information with step updates enabled")
    public void testSingleRunStepUpdate() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
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
        // Build import request.
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, true);
        // Step merging should not have any effect on single runs.
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, true);
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
        Assert.assertEquals(dto.getTests().size(), 1);
        List<XrayTestStepEntity> steps = dto.getTests().get(0).getTestInfo().getSteps();
        Assert.assertEquals(steps.size(), 3);
        Assert.assertNull(steps.get(0).getData());
        Assert.assertEquals(steps.get(1).getData(), "x=42\ny=45.0\nnull=null\nnull=null");
        Assert.assertEquals(steps.get(2).getData(), "array=[hello, there]\ncomplex={a=15, b=\"Jeff\", c=[]}");
    }

    @Test(description = "whether multiple test iterations (DDT) are merged into a single step if merging is enabled")
    public void testMultipleRunStepMerge() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
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
        // Build import request.
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, true);
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, true);
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
        Assert.assertEquals(dto.getTests().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getTestInfo().getSteps().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getIterations().size(), 2);
        Assert.assertEquals(dto.getTests().get(0).getIterations().get(0).getSteps().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getIterations().get(1).getSteps().size(), 1);
        Assert.assertEquals(dto.getTests().get(0).getIterations().get(0).getSteps().get(0).getAllEvidence().size(), 3);
    }

    @Test(description = "whether a configured test plan key will be included in the import request")
    public void testTestPlanKey() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
        TestScenarioLogCollection scenarioCollection = scenario(1, "feature-test-plan-key");
        scenarioCollection.addLogMessage(successfulStep("stepWithoutParameters"));
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        // Build import request.
        ConfigurationFactory.getInstance().setString(XrayConfigHelper.RESULTS_UPLOAD_TEST_PLAN_KEY, "QTAF-765");
        ImportExecutionResultsRequestDto dto = new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).build();
        Assert.assertEquals(dto.getInfo().getTestPlanKey(), "QTAF-765");
    }

    @Test(description = "empty step logs should not result in errors for multiple iterations")
    public void testTestEmptyStepLogsMultipleIterations() throws XrayJsonImportBuilder.NoXrayTestException, URISyntaxException, MissingConfigurationValueException {
        TestScenarioLogCollection scenarioCollection = scenario(1, "feature-test-plan-key");
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        scenarioCollection = scenario(2, "feature-test-plan-key");
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.FAILURE);
        // Build import request.
        ConfigurationFactory.getInstance().setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, true);
        Assert.assertNotNull(new XrayJsonImportBuilder(TestSuiteLogCollection.getInstance()).buildRequest());
    }

}
