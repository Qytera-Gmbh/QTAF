package de.qytera.qtaf.testng.event_listener;


import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.QtafInitializer;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.testng.events.payload.TestNGTestContextPayload;
import de.qytera.qtaf.testng.events.payload.TestNGTestEventPayload;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is an event listener for TestNG events. It connects TestNG with the QTAF event system.
 * It implements the ITestListener interface from TestNG, which provides methods for handling test events
 * and triggers corresponding QTAF events.
 */
public class TestNGEventListener implements ITestListener {

    /**
     * A flag indicating whether the test start event has already been dispatched.
     */
    private static boolean testsStartedEventDispatched = false;

    /**
     * A flag indicating whether the test finish event has already been dispatched.
     */
    private static boolean testsFinishedEventDispatched = false;

    /**
     * Map that contains test results.
     */
    private static final Map<Integer, ITestResult> testResultIdMap = new HashMap<>();

    /**
     * A logger instance from the QTAF factory.
     */
    private static Logger logger = QtafFactory.getLogger();

    /**
     * This method is called when a TestNG test run starts.
     * It initializes the QTAF system, logs a start message, and dispatches a start testing event.
     *
     * @param iTestContext The test context for the current test run.
     */
    @Override
    public void onStart(ITestContext iTestContext) {
        QtafInitializer.initialize();
        logger.info("[QTAF] - testing started");

        // Dispatch test started event
        if (!testsStartedEventDispatched) {
            QtafEvents.startTesting.onNext(new TestNGTestContextPayload(iTestContext));
            QtafEvents.startTesting.onCompleted();
            testsStartedEventDispatched = true;
        }
    }

    /**
     * This method is called when a TestNG test run finishes.
     * It logs a finish message and dispatches a finish testing event.
     *
     * @param iTestContext The test context for the current test run.
     */
    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("[QTAF] - testing finished");

        // Dispatch test finished event
        if (!testsFinishedEventDispatched) {
            QtafEvents.finishedTesting.onNext(new TestNGTestContextPayload(iTestContext));
            QtafEvents.finishedTesting.onCompleted();
            testsFinishedEventDispatched = true;
        }
    }

    /**
     * This method is called when a TestNG test starts.
     * It dispatches a test started event if it has not been dispatched before for the current test.
     *
     * @param iTestResult The result of the current test.
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        // Dispatch event if it has not been dispatched before
        if (testResultIdMap.get(iTestResult.hashCode()) == null) {
            QtafTestEventPayload testEventPayload;

            try {
                testEventPayload = new TestNGTestEventPayload(iTestResult);
                QtafEvents.testStarted.onNext(testEventPayload);
            } catch (NoSuchMethodException e) { // Can be caused by cucumber
                return;
            }

            testResultIdMap.put(iTestResult.hashCode(), iTestResult);
        }
    }

    /**
     * This method is called when a TestNG test succeeds.
     * It dispatches a test success event.
     *
     * @param iTestResult The result of the current test.
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        // Dispatch events
        try {
            QtafEvents.testSuccess.onNext(new TestNGTestEventPayload(iTestResult));
        } catch (NoSuchMethodException e) { // Can be caused by cucumber
            return;
        }
    }
    /**
     * This method is called when a TestNG test fails.
     * It dispatches a test failure event.
     * Important note: TestNG does not support changing the status of the test result after a test terminates.
     * An InvocationEventListener can be used for this purpose.
     * See also: TestNGInvocationEventListener.java
     *
     * @param iTestResult The result of the current test.
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        // Dispatch events
        try {
            QtafEvents.testFailure.onNext(new TestNGTestEventPayload(iTestResult));
        } catch (NoSuchMethodException e) { // Can be caused by cucumber
            return;
        }
        try {
            // generalHelperFunctions.TakeScreenShort(TestResultHelper.getTestMethodName(iTestResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a TestNG test is skipped.
     * It dispatches a test skipped event.
     *
     * @param iTestResult The result of the current test.
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        // Dispatch events
        try {
            QtafEvents.testSkipped.onNext(new TestNGTestEventPayload(iTestResult));
        } catch (NoSuchMethodException e) { // Can be caused by cucumber
            return;
        }
    }

    /**
     * This method is called when a TestNG test fails but is within the success percentage.
     * It dispatches a test failed but within success percentage event.
     *
     * @param iTestResult The result of the current test.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // Dispatch events
        try {
            QtafEvents.testFailedButWithinSuccessPercentage.onNext(
                    new TestNGTestEventPayload(iTestResult)
            );
        } catch (NoSuchMethodException e) {
            return;
        }
    }
}
