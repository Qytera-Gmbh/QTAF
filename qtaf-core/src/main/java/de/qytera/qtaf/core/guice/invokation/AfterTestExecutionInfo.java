package de.qytera.qtaf.core.guice.invokation;

import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Arrays;

/**
 * Information object that contains Step execution information
 */
public class AfterTestExecutionInfo {
    private int id;
    private AfterTest afterTestAnnotation;
    private MethodInvocation methodInvocation;
    private Object result = null;
    private Throwable error = null;
    private Thread thread;
    private StackTraceElement[] stackTraceElements;

    /**
     * Get id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id Id
     * @return this
     */
    public AfterTestExecutionInfo setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Set beforeTestAnnotation
     *
     * @param afterTestAnnotation AfterTest Annotation
     * @return this
     */
    public AfterTestExecutionInfo setAfterTestAnnotation(AfterTest afterTestAnnotation) {
        this.afterTestAnnotation = afterTestAnnotation;
        return this;
    }

    /**
     * Get methodInvocation
     *
     * @return methodInvocation
     */
    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    /**
     * Set methodInvocation
     *
     * @param methodInvocation MethodInvocation
     * @return this
     */
    public AfterTestExecutionInfo setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
        return this;
    }

    /**
     * Get result
     *
     * @return result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Set result
     *
     * @param result Result
     * @return this
     */
    public AfterTestExecutionInfo setResult(Object result) {
        this.result = result;
        return this;
    }

    /**
     * Get error
     *
     * @return error
     */
    public Throwable getError() {
        return error;
    }

    /**
     * Set error
     *
     * @param error Error
     * @return this
     */
    public AfterTestExecutionInfo setError(Throwable error) {
        this.error = error;
        return this;
    }

    /**
     * Get thread
     *
     * @return thread
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Set thread
     *
     * @param thread Thread
     * @return this
     */
    public AfterTestExecutionInfo setThread(Thread thread) {
        this.thread = thread;
        return this;
    }

    /**
     * Get stackTraceElements
     *
     * @return stackTraceElements
     */
    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    /**
     * Set stackTraceElements
     *
     * @param stackTraceElements StackTraceElements
     * @return this
     */
    public AfterTestExecutionInfo setStackTraceElements(StackTraceElement[] stackTraceElements) {
        // We want to filter out the irrelevant parts of the stack trace
        this.stackTraceElements = Arrays.stream(stackTraceElements)
                .filter(
                        el -> !el.getClassName().startsWith("java")
                                && !el.getClassName().startsWith("jdk")
                                && !el.getClassName().startsWith("com.google")
                                && !el.getClassName().startsWith("org.testng")
                                && !el.getClassName().startsWith("de.qytera.qtaf.core")
                                && !el.getClassName().startsWith("de.qytera.qtaf.testng")
                )
                .toArray(StackTraceElement[]::new);
        return this;
    }
}
