package de.qytera.qtaf.testng.events.payload;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.testng.helper.TestResultHelper;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * TestNG event payload information class
 */
public class TestNGTestEventPayload extends QtafTestEventPayload {
    /**
     * Original event dispatched by TestNG
     */
    protected ITestResult originalEvent;

    /**
     * Method info
     */
    protected MethodInfo methodInfo;

    /**
     * Constructor
     * @param iTestResult   Original event
     * @throws NoSuchMethodException    Thrown if method is not found
     */
    public TestNGTestEventPayload(ITestResult iTestResult) throws NoSuchMethodException {
        this.originalEvent = iTestResult;
        this.handleTestNGTestResultObject(iTestResult);
        this.scenarioId = TestResultHelper.getTestMethodId(iTestResult);
        this.threadId = Thread.currentThread().getId();
        this.threadName = Thread.currentThread().getName();

        this.handleTestNGMethodObject(iTestResult.getMethod());

        // Handle reflective information about test method
        Class<?> realClass = this.getRealClass(iTestResult);
        this.methodInfo = this.getMethod(iTestResult, realClass);
        this.handleMethodInfo(this.getMethod(iTestResult, realClass));

        // Handle TestFeature annotation of test class
        TestFeature testFeatureAnnotation = realClass.getAnnotation(TestFeature.class);
        this.handleTestFeatureAnnotation(testFeatureAnnotation);

        // Handle test annotation of test method
        Test testNGTestAnnotation = this.getTestAnnotation(methodInfo.method);
        this.handleTestNGTestAnnotation(testNGTestAnnotation);
    }

    /**
     * Handle TestFeature annotation
     * @param testFeatureAnnotation TestFeature annotation
     */
    private void handleTestFeatureAnnotation(TestFeature testFeatureAnnotation) {
        this.featureName = testFeatureAnnotation.name();
        this.featureDescription = testFeatureAnnotation.description();
    }

    /**
     * Handle TestNG original event
     * @param iTestResult   original event
     */
    private void handleTestNGTestResultObject(ITestResult iTestResult) {
        this.featureId = iTestResult.hashCode();
        this.scenarioStart = new Date(iTestResult.getStartMillis());
        this.scenarioEnd = new Date(iTestResult.getEndMillis());
    }

    private void handleTestNGMethodObject(ITestNGMethod testNGMethod) {
        this.scenarioDescription = testNGMethod.getDescription();
        this.groupDependencies = testNGMethod.getGroupsDependedUpon();
        this.methodDependencies = testNGMethod.getMethodsDependedUpon();
    }

    private Class<?> getRealClass(ITestResult iTestResult) {
        return iTestResult.getTestClass().getRealClass();
    }

    private MethodInfo getMethod(ITestResult iTestResult, Class<?> clazz) throws NoSuchMethodException {
        // Java method name of the test
        String methodName = iTestResult.getName();

        // These are the parameters passed to the test methods
        Object[] methodParamValues = iTestResult.getParameters();

        // These are the types of the parameters passed to the test method
        Class<?>[] methodParamTypes = new Class[methodParamValues.length];

        for (int i = 0; i < methodParamValues.length; i++) {
            methodParamTypes[i] = methodParamValues[i].getClass();
        }

        Method method = clazz.getMethod(methodName, methodParamTypes);

        return new MethodInfo(method, methodParamTypes, methodParamValues);
    }

    private void handleMethodInfo(MethodInfo methodInfo) {
        this.scenarioParameters = methodInfo.method.getParameters();
        this.parameterValues = methodInfo.methodParamValues;
    }

    private Test getTestAnnotation(Method method) {
        return method.getAnnotation(Test.class);
    }

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
    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    /**
     * Set methodInfo
     *
     * @param methodInfo MethodInfo
     * @return this
     */
    public TestNGTestEventPayload setMethodInfo(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
        return this;
    }

    class MethodInfo {
        private Method method;
        private Class<?>[] methodParamTypes;
        private Object[] methodParamValues;

        public MethodInfo(Method method, Class<?>[] methodParamTypes, Object[] methodParamValues) {
            this.method = method;
            this.methodParamTypes = methodParamTypes;
            this.methodParamValues = methodParamValues;
        }

        /**
         * Get method
         *
         * @return method
         */
        public Method getMethod() {
            return method;
        }

        /**
         * Set method
         *
         * @param method Method
         * @return this
         */
        public MethodInfo setMethod(Method method) {
            this.method = method;
            return this;
        }

        /**
         * Get methodParamTypes
         *
         * @return methodParamTypes
         */
        public Class<?>[] getMethodParamTypes() {
            return methodParamTypes;
        }

        /**
         * Set methodParamTypes
         *
         * @param methodParamTypes MethodParamTypes
         * @return this
         */
        public MethodInfo setMethodParamTypes(Class<?>[] methodParamTypes) {
            this.methodParamTypes = methodParamTypes;
            return this;
        }

        /**
         * Get methodParamValues
         *
         * @return methodParamValues
         */
        public Object[] getMethodParamValues() {
            return methodParamValues;
        }

        /**
         * Set methodParamValues
         *
         * @param methodParamValues MethodParamValues
         * @return this
         */
        public MethodInfo setMethodParamValues(Object[] methodParamValues) {
            this.methodParamValues = methodParamValues;
            return this;
        }
    }
}
