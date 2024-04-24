package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the log message index
 */
public class LogMessageIndexTest {

    /**
     * Test whether the insertion of log messages works
     */
    @Test
    public void testAddingLogMessages() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1");
        index.put(logMessage1.hashCode(), logMessage1);
        Assert.assertEquals(index.size(), 1, "There should be one log message in the index");

        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2");
        index.put(logMessage2.hashCode(), logMessage2);
        Assert.assertEquals(index.size(), 2, "There should be two log messages in the index");

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether the insertion of duplicates is forbidden
     */
    @Test
    public void testAddingDuplicateLogMessages() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1");
        index.put(logMessage1.hashCode(), logMessage1);
        Assert.assertEquals(index.size(), 1, "There should be one log message in the index");

        index.put(logMessage1.hashCode(), logMessage1);
        Assert.assertEquals(index.size(), 1, "There should be one log message in the index");

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether filtering by feature ID works
     */
    @Test
    public void testGroupLogMessagesByFeatureId() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setScenarioId("scenario1-iteration1");
        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setScenarioId("scenario1-iteration2");
        LogMessage logMessage3 = new LogMessage(LogLevel.INFO, "log message 3")
                .setFeatureId("feature2")
                .setAbstractScenarioId("scenario2")
                .setScenarioId("scenario2-iteration1");

        index.put(logMessage1.hashCode(), logMessage1);
        index.put(logMessage2.hashCode(), logMessage2);
        index.put(logMessage3.hashCode(), logMessage3);

        Assert.assertEquals(
                index.getByFeatureId("feature1").size(),
                2,
                "There should be two scenarios with the feature ID 'feature1'"
        );

        Assert.assertEquals(
                index.getByFeatureId("feature2").size(),
                1,
                "There should be one scenario with the feature ID 'feature2'"
        );

        Assert.assertEquals(
                index.getByFeatureId("feature3").size(),
                0,
                "There should be no scenario with the feature ID 'feature3'"
        );

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether filtering by abstract scenario ID works
     */
    @Test
    public void testGroupLogMessagesByAbstractScenarioId() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setScenarioId("scenario1-iteration1");
        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setScenarioId("scenario1-iteration2");
        LogMessage logMessage3 = new LogMessage(LogLevel.INFO, "log message 3")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario2")
                .setScenarioId("scenario2-iteration1");

        index.put(logMessage1.hashCode(), logMessage1);
        index.put(logMessage2.hashCode(), logMessage2);
        index.put(logMessage3.hashCode(), logMessage3);

        Assert.assertEquals(
                index.getByAbstractScenarioId("scenario1").size(),
                2,
                "There should be one scenario with the abstract scenario ID 'scenario1'"
        );

        Assert.assertEquals(
                index.getByAbstractScenarioId("scenario2").size(),
                1,
                "There should be one scenario with the abstract scenario ID 'scenario2'"
        );

        Assert.assertEquals(
                index.getByAbstractScenarioId("scenario3").size(),
                0,
                "There should be one scenario with the abstract scenario ID 'scenario3'"
        );

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether filtering by scenario ID works
     */
    @Test
    public void testGroupLogMessagesByScenarioId() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setScenarioId("scenario1-iteration1");
        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setScenarioId("scenario1-iteration2");
        LogMessage logMessage3 = new LogMessage(LogLevel.INFO, "log message 3")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario2")
                .setScenarioId("scenario2-iteration1");

        index.put(logMessage1.hashCode(), logMessage1);
        index.put(logMessage2.hashCode(), logMessage2);
        index.put(logMessage3.hashCode(), logMessage3);

        Assert.assertEquals(
                index.getByScenarioId("scenario1-iteration1").size(),
                1,
                "There should be one scenario with the scenario ID 'scenario1-iteration1'"
        );

        Assert.assertEquals(
                index.getByScenarioId("scenario1-iteration2").size(),
                1,
                "There should be one scenario with the scenario ID 'scenario1-iteration2'"
        );

        Assert.assertEquals(
                index.getByScenarioId("scenario2-iteration1").size(),
                1,
                "There should be one scenario with the scenario ID 'scenario2-iteration1'"
        );

        Assert.assertEquals(
                index.getByScenarioId("scenario2-iteration2").size(),
                0,
                "There should be no scenario with the scenario ID 'scenario2-iteration2'"
        );

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether filtering by scenario status PENDING works
     */
    @Test
    public void testGroupLogMessagesByScenarioStatusPending() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario1-iteration1");
        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario1-iteration2");
        LogMessage logMessage3 = new LogMessage(LogLevel.INFO, "log message 3")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario2")
                .setStatus(LogMessage.Status.PENDING)
                .setScenarioId("scenario2-iteration1");

        index.put(logMessage1.hashCode(), logMessage1);
        index.put(logMessage2.hashCode(), logMessage2);
        index.put(logMessage3.hashCode(), logMessage3);

        Assert.assertEquals(
                index.getByScenarioIdAndPending("scenario2-iteration1").size(),
                1,
                "There should be one log message with status PENDING in \"scenario2-iteration1\""
        );
        Assert.assertEquals(
                index.getByScenarioIdAndPassed("scenario2-iteration1").size(),
                0,
                "There should be no log message with status PASSED in \"scenario2-iteration1\""
        );
        Assert.assertEquals(
                index.getByScenarioIdAndFailed("scenario2-iteration1").size(),
                0,
                "There should be no log message with status FAILED in \"scenario2-iteration1\""
        );

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether filtering by scenario status PASSED works
     */
    @Test
    public void testGroupLogMessagesByScenarioStatusPassed() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario1-iteration1");
        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario1-iteration2");
        LogMessage logMessage3 = new LogMessage(LogLevel.INFO, "log message 3")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario2")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario2-iteration1");

        index.put(logMessage1.hashCode(), logMessage1);
        index.put(logMessage2.hashCode(), logMessage2);
        index.put(logMessage3.hashCode(), logMessage3);

        Assert.assertEquals(
                index.getByScenarioIdAndPending("scenario2-iteration1").size(),
                0,
                "There should be no log message with status PENDING in \"scenario2-iteration1\""
        );
        Assert.assertEquals(
                index.getByScenarioIdAndPassed("scenario2-iteration1").size(),
                1,
                "There should be one log message with status PASSED in \"scenario2-iteration1\""
        );
        Assert.assertEquals(
                index.getByScenarioIdAndFailed("scenario2-iteration1").size(),
                0,
                "There should be no log message with status FAILED in \"scenario2-iteration1\""
        );

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

    /**
     * Test whether filtering by scenario status FAILED works
     */
    @Test
    public void testGroupLogMessagesByScenarioStatusFailed() {
        // First clear all entries in the indices
        IndexHelper.clearAllIndices();

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario1-iteration1");
        LogMessage logMessage2 = new LogMessage(LogLevel.INFO, "log message 2")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setStatus(LogMessage.Status.PASSED)
                .setScenarioId("scenario1-iteration2");
        LogMessage logMessage3 = new LogMessage(LogLevel.INFO, "log message 3")
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario2")
                .setStatus(LogMessage.Status.FAILED)
                .setScenarioId("scenario2-iteration1");

        index.put(logMessage1.hashCode(), logMessage1);
        index.put(logMessage2.hashCode(), logMessage2);
        index.put(logMessage3.hashCode(), logMessage3);

        Assert.assertEquals(
                index.getByScenarioIdAndPending("scenario2-iteration1").size(),
                0,
                "There should be no log message with status PENDING in \"scenario2-iteration1\""
        );
        Assert.assertEquals(
                index.getByScenarioIdAndPassed("scenario2-iteration1").size(),
                0,
                "There should be no log message with status PASSED in \"scenario2-iteration1\""
        );
        Assert.assertEquals(
                index.getByScenarioIdAndFailed("scenario2-iteration1").size(),
                1,
                "There should be one log message with status FAILED in \"scenario2-iteration1\""
        );

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(index.size(), 0, "The log message index should be empty");
    }

}
