package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestIterationParameterEntity;
import de.qytera.qtaf.xray.entity.XrayTestIterationResultEntity;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AbstractXrayJsonImportBuilderTest {

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
        scenarioCollection.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        scenarioCollection.setAnnotations(new Annotation[]{ANNOTATION});
        return scenarioCollection;
    }

    @DataProvider(name = "longParameterValueProvider")
    private Object[][] longParameterValueProvider() {
        List<List<String>> expectedParameterValues = new ArrayList<>();

        TestFeatureLogCollection featureCollection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("feature-1", "feature-xray");

        TestScenarioLogCollection scenarioCollection = scenarioWithId("1");
        List<String> parameters = Arrays.asList(
                "a",
                "abcd",
                "abcdefghijklmnop",
                "abcdefghijklmnopqrstuvwxyz01234567891011121314151617181920212223"
        );
        scenarioCollection.addParameters(parameters.toArray());
        featureCollection.addScenarioLogCollection(scenarioCollection);
        expectedParameterValues.add(Arrays.asList("a", "abc", "abc", "abc"));

        scenarioCollection = scenarioWithId("2");
        parameters = Arrays.asList(
                "ab",
                "abcdefghi",
                "abcdefghijklmnopqrstuvwxyz012345"
        );
        scenarioCollection.addParameters(parameters.toArray());
        featureCollection.addScenarioLogCollection(scenarioCollection);
        expectedParameterValues.add(Arrays.asList("ab", "abc", "abc"));

        List<Object[]> data = new ArrayList<>();
        for (AbstractXrayJsonImportBuilder builder : Arrays.asList(XrayJsonImportBuilderFactory.getServerInstance(), XrayJsonImportBuilderFactory.getCloudInstance())) {
            data.add(new Object[]{
                    builder,
                    featureCollection,
                    3,
                    expectedParameterValues
            });
        }
        return data.toArray(Object[][]::new);
    }

    @Test(dataProvider = "longParameterValueProvider")
    public void testLongParameterValueTruncation(AbstractXrayJsonImportBuilder builder, TestFeatureLogCollection featureCollection, int maxLength, List<List<String>> expectedParameterValues) {
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setString(XrayConfigHelper.RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE, String.valueOf(maxLength));

        TestSuiteLogCollection suiteCollection = TestSuiteLogCollection.getInstance();
        suiteCollection.addTestClassLogCollection(featureCollection);
        XrayImportRequestDto dto = builder.buildFromTestSuiteLogs(suiteCollection);
        XrayTestEntity test = dto.getTests().get(0);
        List<List<String>> actualValues = new ArrayList<>();
        for (XrayTestIterationResultEntity iteration : test.getIterations()) {
            List<String> values = new ArrayList<>();
            iteration.getParameters().stream()
                    .map(XrayTestIterationParameterEntity::getValue)
                    .forEach(values::add);
            actualValues.add(values);
        }
        Assert.assertEquals(actualValues, expectedParameterValues);
    }

}
