package de.qytera.qtaf.testng.event_listener;

import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * A custom TestNG listener that implements the {@link IInvokedMethodListener} interface to listen to
 * method invocation events during test execution. This listener specifically checks whether any failed
 * steps occurred in the test scenario, using QTAF's own assertion methods, and manually updates the
 * test result status.
 */
public class TestNGInvocationEventListener implements IInvokedMethodListener {
    /**
     * This method is called after a test method has been invoked. It checks whether the test scenario has
     * any failed steps by manually verifying the log messages associated with the scenario. If any failed
     * steps are detected, the test result is marked as failed.
     * This procedure is required because the QTAF assertions are not visible for TestNG, as the QTAF assertions
     * typically do not throw an exception and are therefore not recognized by TestNG as a failed test.
     *
     * @param method      The invoked method information.
     * @param testResult  The result of the test method invocation, which contains information such as
     *                    the test method, its result, status, and execution details.
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        // TestNG cannot cover QTAF's own assertion methods. So we have to check if there were any failed steps manually here.
        String scenarioId = TestResultHelper.getScenarioId(testResult);

        // If there are no failed steps the scenario has passed
        boolean hasScenarioPassed = LogMessageIndex.getInstance().getByScenarioIdAndFailed(scenarioId).isEmpty();

        // Dispatch events
        if (!hasScenarioPassed) {
            testResult.setStatus(ITestResult.FAILURE);
        }
    }
}
