package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.builder.test.MultipleIterationsXrayTestEntityBuilder;
import de.qytera.qtaf.xray.builder.test.XrayTestEntityBuilder;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntityCloud;
import de.qytera.qtaf.xray.entity.XrayTestInfoEntityServer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * This test feature tests the XrayTestEntityBuilder class
 */
public class XrayTestEntityBuilderTest {
    /**
     * This is a method for building a dummy scenario
     * @param scenarioIteration scenario iteration number
     * @param featureName       name of the feature the scenario belongs to
     * @param annotation        A list of test annotations
     * @return  a dummy scenario
     */
    private static TestScenarioLogCollection scenario(int scenarioIteration, String featureName, XrayTest annotation) {
        TestScenarioLogCollection scenarioCollection = TestScenarioLogCollection.createTestScenarioLogCollection(
                featureName,
                "SomeClass.doesSomething",
                String.valueOf(scenarioIteration),
                "a test scenario"
        );
        scenarioCollection.setStart(Date.from(Instant.now().minusSeconds(3600)));
        scenarioCollection.setEnd(Date.from(Instant.now()));
        scenarioCollection.setAnnotations(new Annotation[] {annotation});
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

    /**
     * This test case tests the building of the XrayTestEntity for the following configuration:
     * - use xray cloud
     * - steps should not be merged
     * - test info should be updated
     */
    @Test
    public void testBuildXrayCloudTestInfoStepEntities() throws NoSuchMethodException {
        // Get the QTAF configuration
        ConfigMap configMap = QtafFactory.getConfiguration();

        // Set merge steps value in config to false
        String xrayServiceOldValue = configMap.getString(XrayConfigHelper.XRAY_SERVICE, "cloud");
        boolean mergeStepsOldValue = configMap.getBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, false);
        boolean stepUpdateOOldValue = configMap.getBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, true);
        configMap.setString(XrayConfigHelper.XRAY_SERVICE, "cloud");
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, false);
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, true);

        // Get an annotation object needed for the test entity builder
        XrayTest xrayTestAnnotation = DemoTest.class.getMethod("foo").getAnnotation(XrayTest.class);

        // Get the suite log collection needed to the entity builder
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();

        // The entity builder
        XrayTestEntityBuilder<List<TestScenarioLogCollection>> builder = new MultipleIterationsXrayTestEntityBuilder(
                suiteLogCollection,
                new HashMap<>()
        );

        // Get dummy scenario log collections
        TestScenarioLogCollection s1 = scenario(1, "scenario1", xrayTestAnnotation);
        TestScenarioLogCollection s2 = scenario(1, "scenario2", xrayTestAnnotation);
        List<TestScenarioLogCollection> sList = List.of(s1, s2);

        // Build the xray upload entity for the dummy scenarios
        XrayTestEntity xrayTestEntity = builder.buildTestEntity(xrayTestAnnotation, sList);

        // Test info entity should be built for Xray Cloud
        Assert.assertTrue(xrayTestEntity.getTestInfo() instanceof XrayTestInfoEntityCloud);

        // Set merge steps value in config to old value
        configMap.setString(XrayConfigHelper.XRAY_SERVICE, xrayServiceOldValue);
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, mergeStepsOldValue);
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, stepUpdateOOldValue);
    }

    /**
     * This test case tests the building of the XrayTestEntity for the following configuration:
     * - use xray server
     * - steps should not be merged
     * - test info should be updated
     */
    @Test
    public void testBuildXrayServerTestInfoStepEntities() throws NoSuchMethodException {
        // Get the QTAF configuration
        ConfigMap configMap = QtafFactory.getConfiguration();

        // Set merge steps value in config to false
        String xrayServiceOldValue = configMap.getString(XrayConfigHelper.XRAY_SERVICE, "cloud");
        boolean mergeStepsOldValue = configMap.getBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, false);
        boolean stepUpdateOOldValue = configMap.getBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, true);
        configMap.setString(XrayConfigHelper.XRAY_SERVICE, "server");
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, false);
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, true);

        // Get an annotation object needed for the test entity builder
        XrayTest xrayTestAnnotation = DemoTest.class.getMethod("foo").getAnnotation(XrayTest.class);

        // Get the suite log collection needed to the entity builder
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();

        // The entity builder
        XrayTestEntityBuilder<List<TestScenarioLogCollection>> builder = new MultipleIterationsXrayTestEntityBuilder(
                suiteLogCollection,
                new HashMap<>()
        );

        // Get dummy scenario log collections
        TestScenarioLogCollection s1 = scenario(1, "scenario1", xrayTestAnnotation);
        TestScenarioLogCollection s2 = scenario(1, "scenario2", xrayTestAnnotation);
        List<TestScenarioLogCollection> sList = List.of(s1, s2);

        // Build the xray upload entity for the dummy scenarios
        XrayTestEntity xrayTestEntity = builder.buildTestEntity(xrayTestAnnotation, sList);

        // Test info entity should be built for Xray Cloud
        Assert.assertTrue(xrayTestEntity.getTestInfo() instanceof XrayTestInfoEntityServer);

        // Set merge steps value in config to old value
        configMap.setString(XrayConfigHelper.XRAY_SERVICE, xrayServiceOldValue);
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, mergeStepsOldValue);
        configMap.setBoolean(XrayConfigHelper.RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, stepUpdateOOldValue);
    }
}

/**
 * This is a demo test case that is used by the tests above
 */
class DemoTest {
    @XrayTest(key = "foo")
    public void foo() {

    }
}
