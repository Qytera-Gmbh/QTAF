package de.qytera.qtaf.allure;

import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import io.qameta.allure.model.TestResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

public class AllureReportGeneratorTest {
    @Test
    public void testResult() {
        TestSuiteLogCollection suiteLogCollection = TestSuiteLogCollection.getInstance();
        suiteLogCollection.setDriverName("chrome");
        TestScenarioLogCollection scenarioLogCollection1 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature1",
                        "scenario1",
                        "iteration1",
                        "test1"
                )
                .setDescription("QTAF-001")
                .setStart(new Date())
                .setEnd(new Date());

        TestResult testResult = AllureTestResultGenerator.fromQtafTestScenario(scenarioLogCollection1);

        Assert.assertEquals(testResult.getHistoryId(), "scenario1");
    }

    @Test
    public void testGenerateAllureReport() throws IOException {
        String path = DirectoryHelper.preparePath("$USER_DIR/allure-results");
        DirectoryHelper.createDirectoryIfNotExists(path);

        TestSuiteLogCollection testSuiteLogCollection = TestSuiteLogCollection.getInstance();
        testSuiteLogCollection.setDriverName("chrome");

        TestScenarioLogCollection scenarioLogCollection1 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature1",
                        "scenario1",
                        "iteration1",
                        "test1"
                )
                .setDescription("QTAF-001")
                .setStart(new Date())
                .setEnd(new Date());

        TestResult testResult = AllureTestResultGenerator.fromQtafTestScenario(scenarioLogCollection1);

        AllureReportGenerator.generateReportFile(testSuiteLogCollection, testResult, path);
    }
}
