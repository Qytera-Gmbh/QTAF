package de.qytera.qtaf.testng.events.payload;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.events.payload.MethodInfoEntity;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.reflection.ClassHelper;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * TestNG event payload information class
 */
public class TestNGTestEventPayload extends QtafTestEventPayload {
    /**
     * Original event dispatched by TestNG
     */
    protected ITestResult originalEvent;

    /**
     * Constructor
     *
     * @param iTestResult Original event
     * @throws NoSuchMethodException Thrown if method is not found
     */
    public TestNGTestEventPayload(ITestResult iTestResult) throws NoSuchMethodException {
        this.originalEvent = iTestResult;
        this.originalTestInstance = iTestResult.getInstance();
        this.instanceId = iTestResult.id();

        // Handle annotations
        Class<?> realClass = iTestResult.getTestClass().getRealClass();
        this.realClass = realClass;
        this.realClassAnnotations = realClass.getAnnotations();

        this.handleTestNGTestResultObject(iTestResult);
        this.abstractScenarioId = TestResultHelper.getTestMethodId(iTestResult);
        this.instanceId = iTestResult.id();
        this.scenarioId = abstractScenarioId + "-" + instanceId;
        this.threadId = Thread.currentThread().getId();
        this.threadName = Thread.currentThread().getName();

        this.handleTestNGMethodObject(iTestResult.getMethod());

        // Handle reflective information about test method
        this.featureClassName = realClass.getName();
        this.featurePackageName = realClass.getPackageName();
        this.scenarioMethodName = iTestResult.getMethod().getMethodName();
        this.methodInfo = this.getMethodInfoEntity(iTestResult, realClass);
        this.handleMethodInfo(this.getMethodInfoEntity(iTestResult, realClass));

        // Handle test annotation of test method
        Test testNGTestAnnotation = this.getTestAnnotation(methodInfo.getMethod());

        assert testNGTestAnnotation != null;
        this.handleTestNGTestAnnotation(testNGTestAnnotation);
    }

    /**
     * Handle TestNG original event
     *
     * @param iTestResult original event
     */
    private void handleTestNGTestResultObject(ITestResult iTestResult) {
        this.featureId = iTestResult.getTestClass().getRealClass().getName();
        this.scenarioStart = new Date(iTestResult.getStartMillis());
        this.scenarioEnd = new Date(iTestResult.getEndMillis());
    }

    /**
     * Initialize class atributes based on a testNGMethod object
     *
     * @param testNGMethod TestNGMethod object
     */
    private void handleTestNGMethodObject(ITestNGMethod testNGMethod) {
        this.scenarioDescription = testNGMethod.getDescription();
        this.groupDependencies = testNGMethod.getGroupsDependedUpon();
        this.methodDependencies = testNGMethod.getMethodsDependedUpon();
    }

    /**
     * Get a method entity object from the test execution info object
     *
     * @param iTestResult Info object that contains information about the test
     * @param clazz       Real class that contains the scenario method
     * @return MethodInfoEntity object
     * @throws NoSuchMethodException Thrown of original method cannot be found
     */
    private MethodInfoEntity getMethodInfoEntity(ITestResult iTestResult, Class<?> clazz) throws NoSuchMethodException {
        // Java method name of the test
        String methodName = iTestResult.getName();

        // These are the parameters passed to the test methods
        Object[] methodParamValues = iTestResult.getParameters();

        // These are the types of the parameters passed to the test method
        Class<?>[] methodParamTypes = new Class[methodParamValues.length];

        // Get the classes of the parameters
        for (int i = 0; i < methodParamValues.length; i++) {
            methodParamTypes[i] = methodParamValues[i].getClass();
        }

        // We need the method reflection object for the test scenario (that is the method that contains the test logic)
        Method method;

        try {
            // First try to use the Java API to get the method object of the test scenario
            method = clazz.getMethod(methodName, methodParamTypes);
        } catch (NoSuchMethodException e) {
            // Otherwise use ClassHelper to find the correct method object
            List<Method> suitableMethods = ClassHelper.findSuitableMethods(clazz, methodParamValues, methodName);

            // If there were no methods found matching the method's name and the parameters raise an exception
            if (suitableMethods.isEmpty()) {
                throw e;
            }

            // Use the first matching method object for logging
            method = suitableMethods.get(0);
        }

        // Get annotations of method
        Annotation[] annotations = method.getAnnotations();

        return new MethodInfoEntity(method, methodParamTypes, methodParamValues, annotations);
    }

    /**
     * Initialize class attributes based on a MethodInfoEntity object
     *
     * @param methodInfo MethodInfoEntity object
     */
    private void handleMethodInfo(MethodInfoEntity methodInfo) {
        this.scenarioParameters = methodInfo.getMethod().getParameters();
        this.parameterValues = methodInfo.getMethodParamValues();
    }

    /**
     * Get the test annotation from the test method
     *
     * @param method Method reflection object
     * @return Test annotation object
     */
    private Test getTestAnnotation(Method method) {
        return method.getAnnotation(Test.class);
    }

    /**
     * Method to initialize class attributes based on the Test annotation from TestNG
     *
     * @param testAnnotation Test annotation of the scenario
     */
    private void handleTestNGTestAnnotation(Test testAnnotation) {
        this.scenarioName = testAnnotation.testName();
    }

    /**
     * Get originalEvent
     *
     * @return originalEvent
     */
    @Override
    public ITestResult getOriginalEvent() {
        return originalEvent;
    }

    /**
     * Set originalEvent
     *
     * @param originalEvent OriginalEvent
     * @return this
     */
    public TestNGTestEventPayload setOriginalEvent(ITestResult originalEvent) {
        this.originalEvent = originalEvent;
        return this;
    }

    /**
     * Get methodInfo
     *
     * @return methodInfo
     */
    @Override
    public MethodInfoEntity getMethodInfo() {
        return methodInfo;
    }

    /**
     * Set methodInfo
     *
     * @param methodInfo MethodInfo
     * @return this
     */
    @Override
    public TestNGTestEventPayload setMethodInfo(MethodInfoEntity methodInfo) {
        this.methodInfo = methodInfo;
        return this;
    }
}
