package de.qytera.qtaf.testng.event_listener;

import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestNGInvocationEventListener implements IInvokedMethodListener {
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
