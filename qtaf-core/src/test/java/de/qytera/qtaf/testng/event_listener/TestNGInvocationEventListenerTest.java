package de.qytera.qtaf.testng.event_listener;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.SampleTestResult;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;


public class TestNGInvocationEventListenerTest {
    private void manipulateLogIndex(SampleTestResult testResult, LogMessage.Status desiredStatus){
        String scenarioId = TestResultHelper.getScenarioId(testResult);

        LogMessageIndex index = LogMessageIndex.getInstance();
        Assert.assertEquals(LogMessageIndex.getInstance().size(), 0, "The log message index should be empty");

        LogMessage logMessage1 = new LogMessage(LogLevel.INFO, "log message 1");

        logMessage1.setScenarioId(scenarioId);
        logMessage1.setStatus(desiredStatus);

        index.put(logMessage1.hashCode(), logMessage1);
        Assert.assertEquals(LogMessageIndex.getInstance().size(), 1, "There should be one log message in the index");
        Assert.assertEquals(LogMessageIndex.getInstance().getByScenarioId(scenarioId).size(), 1, "There should be one log message in the index with the scenario ID");
    }
    @Test(description = "a status of success gets changed to failure when afterInvocation gets called and there is a failed log message in the index")
    public void testStatusOfSuccessGetsChangedToFailureWhenFailed() {
        // Test Preparation
        IndexHelper.clearAllIndices();
        TestNGInvocationEventListener listener = new TestNGInvocationEventListener(); // dummy listener
        SampleTestResult testResult = new SampleTestResult(); // dummy testResult

        manipulateLogIndex(testResult, LogMessage.Status.FAILED);

        testResult.setStatus(ITestResult.SUCCESS);
        listener.afterInvocation(null, testResult);

        Assert.assertEquals(testResult.getStatus(), ITestResult.FAILURE);

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(LogMessageIndex.getInstance().size(), 0, "The log message index should be empty");
    }

    @Test(description = "a status of failure stays at failure when afterInvocation gets called and there is a failed log message in the index")
    public void testStatusOfFailureStaysFailureWhenFailed() {
        // Test Preparation
        IndexHelper.clearAllIndices();
        TestNGInvocationEventListener listener = new TestNGInvocationEventListener(); // dummy listener
        SampleTestResult testResult = new SampleTestResult(); // dummy testResult

        manipulateLogIndex(testResult, LogMessage.Status.FAILED);

        testResult.setStatus(ITestResult.FAILURE);
        listener.afterInvocation(null, testResult);

        Assert.assertEquals(testResult.getStatus(), ITestResult.FAILURE);

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(LogMessageIndex.getInstance().size(), 0, "The log message index should be empty");
    }

    @Test(description = "a status of failure stays at failure when afterInvocation gets called and there is a successful log message in the index")
    public void testStatusOfFailureStaysFailureWhenPassed() {
        // Test Preparation
        IndexHelper.clearAllIndices();
        TestNGInvocationEventListener listener = new TestNGInvocationEventListener(); // dummy listener
        SampleTestResult testResult = new SampleTestResult(); // dummy testResult

        manipulateLogIndex(testResult, LogMessage.Status.PASSED);

        testResult.setStatus(ITestResult.FAILURE);
        listener.afterInvocation(null, testResult);

        Assert.assertEquals(testResult.getStatus(), ITestResult.FAILURE);

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(LogMessageIndex.getInstance().size(), 0, "The log message index should be empty");
    }

    @Test()
    public void testStatusOfSuccessfulStaysSuccessfulWhenPassed() {
        // Test Preparation
        IndexHelper.clearAllIndices();
        TestNGInvocationEventListener listener = new TestNGInvocationEventListener(); // dummy listener
        SampleTestResult testResult = new SampleTestResult(); // dummy testResult

        manipulateLogIndex(testResult, LogMessage.Status.PASSED);

        testResult.setStatus(ITestResult.SUCCESS);
        listener.afterInvocation(null, testResult);

        Assert.assertEquals(testResult.getStatus(), ITestResult.SUCCESS);

        // Tidy up after testing
        IndexHelper.clearAllIndices();
        Assert.assertEquals(LogMessageIndex.getInstance().size(), 0, "The log message index should be empty");
    }
}
