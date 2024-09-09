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
 * Event listener for TestNG events. This class connects TestNG with the QTAF event system.
 */
public class TestNGEventListener implements ITestListener {

    /**
     * Parameter that indicates if the test start event was already dispatched.
     */
    private static boolean testsStartedEventDispatched = false;

    /**
     * Parameter that indicates if the test finish event was already dispatched.
     */
    private static boolean testsFinishedEventDispatched = false;

    /**
     * Map that contains test results.
     */
    private static final Map<Integer, ITestResult> testResultIdMap = new HashMap<>();
    /**
     * Logger.
     */
    private static Logger logger = QtafFactory.getLogger();

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

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        // TestNG cannot cover QTAF's own assertion methods. So we have to check if there were any failed steps manually here.
        //String scenarioId = TestResultHelper.getScenarioId(iTestResult);
        // If there are no failed steps the scenario has passed
        //boolean hasScenarioPassed = LogMessageIndex.getInstance().getByScenarioIdAndFailed(scenarioId).isEmpty();

        try {
            QtafEvents.testSuccess.onNext(new TestNGTestEventPayload(iTestResult));
        } catch (NoSuchMethodException e) { // Can be caused by cucumber
            return;
        }
        // Dispatch events
        /*
        try {
            if (hasScenarioPassed) {
                QtafEvents.testSuccess.onNext(new TestNGTestEventPayload(iTestResult));
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);
                QtafEvents.testFailure.onNext(new TestNGTestEventPayload(iTestResult));
            }
        } catch (NoSuchMethodException e) { // Can be caused by cucumber
            return;
        }
         */
    }

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

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        // Dispatch events
        try {
            QtafEvents.testSkipped.onNext(new TestNGTestEventPayload(iTestResult));
        } catch (NoSuchMethodException e) { // Can be caused by cucumber
            return;
        }
    }

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
