package de.qytera.qtaf.core.log.service;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import org.junit.Assert;
import org.testng.annotations.Test;

public class LogFileWriterTest {
    @Test
    public void testLogFileWriter() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();

        // Mock feature logs
        TestFeatureLogCollection f1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestFeatureLogCollection f2 = slc.createFeatureIfNotExists("feature2", "feature2");

        // Mock scenario logs
        TestScenarioLogCollection c1 = f1.createScenarioIfNotExists(f1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        TestScenarioLogCollection c2 = f1.createScenarioIfNotExists(f1.getFeatureId(), "scenario2", "instance1", "Scenario 2");
        TestScenarioLogCollection c3 = f2.createScenarioIfNotExists(f2.getFeatureId(), "scenario3", "instance1", "Scenario 3");
        TestScenarioLogCollection c4 = f2.createScenarioIfNotExists(f2.getFeatureId(), "scenario4", "instance1", "Scenario 4");

        LogMessage logMessage = new LogMessage(LogLevel.INFO, "Hello");
        c1.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        c1.addLogMessage(logMessage);

        String filePath = LogFileWriter.persistLogs(slc);

        Assert.assertNotNull(filePath);

        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }
}
