package de.qytera.qtaf.testng.helper;

import de.qytera.qtaf.core.context.IQtafTestContext;
import org.testng.ITestResult;

/**
 * Helper class for extracting attributes from TestResult objects.
 */
public class TestResultHelper {
    private TestResultHelper() {
    }

    /**
     * Get test context object.
     *
     * @param iTestResult test result
     * @return test context
     */
    public static IQtafTestContext getTestContextInstance(ITestResult iTestResult) {
        return (IQtafTestContext) iTestResult.getInstance();
    }


    /**
     * Build Test ID from test class and test method name.
     *
     * @param iTestResult Test result context
     * @return ID of the test
     */
    public static String getTestClassId(ITestResult iTestResult) {
        // Get class name of the test case
        return iTestResult.getTestClass().getRealClass().getName();
    }

    /**
     * Get the abstract scenario ID from a TestNG test result.
     * The abstract scenario ID is the name of the class where the test case was defined in plus the method name.
     *
     * @param iTestResult Test result context
     * @return abstract scenario ID
     */
    public static String getAbstractScenarioId(ITestResult iTestResult) {
        // Get method name of the test case
        String className = getTestClassId(iTestResult);
        String testName = iTestResult.getMethod().getMethodName();
        return className + "." + testName;
    }

    /**
     * Get the scenario instance ID from a TestNG test result.
     * The instance ID helps to distinguish multiple executions of the same scenario.
     *
     * @param iTestResult Test result context
     * @return scenario instance ID
     */
    public static String getScenarioInstanceId(ITestResult iTestResult) {
        return iTestResult.id();
    }

    /**
     * Get the scenario ID that identifies the execution of an abstract scenario.
     * The scenario ID is the abstract scenario ID plus the instance ID of a concrete execution.
     *
     * @param iTestResult Test result context
     * @return scenario instance ID
     */
    public static String getScenarioId(ITestResult iTestResult) {
        return getAbstractScenarioId(iTestResult) + "-" + getScenarioInstanceId(iTestResult);
    }
}
