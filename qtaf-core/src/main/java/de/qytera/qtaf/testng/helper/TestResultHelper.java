package de.qytera.qtaf.testng.helper;

import de.qytera.qtaf.core.context.IQtafTestContext;
import org.testng.ITestResult;

/**
 * Helper class for extracting attributes from TestResult objects
 */
public class TestResultHelper {
    private TestResultHelper() {}
    /**
     * Get test context object
     *
     * @param iTestResult test result
     * @return test context
     */
    public static IQtafTestContext getTestContextInstance(ITestResult iTestResult) {
        return (IQtafTestContext) iTestResult.getInstance();
    }


    /**
     * Build Test ID from test class and test method name
     *
     * @param iTestResult Test result context
     * @return ID of the test
     */
    public static String getTestClassId(ITestResult iTestResult) {
        // Get class name of the test case
        return iTestResult.getTestClass().getRealClass().getName();
    }

    /**
     * Build Test ID from test class and test method name
     *
     * @param iTestResult Test result context
     * @return ID of the test
     */
    public static String getTestMethodId(ITestResult iTestResult) {
        // Get method name of the test case
        String className = getTestClassId(iTestResult);
        String testName = iTestResult.getMethod().getMethodName();
        return className + "." + testName;
    }

}
