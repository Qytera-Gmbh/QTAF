package de.qytera.qtaf.core.guice.invokation;

import de.qytera.qtaf.core.guice.annotations.Step;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

/**
 * Information object that contains Step execution information
 */
public class StepExecutionInfo {
    private int id;
    private Step step;
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
    public StepExecutionInfo setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Get step
     *
     * @return step
     */
    public Step getStep() {
        return step;
    }

    /**
     * Set step
     *
     * @param step Step
     * @return this
     */
    public StepExecutionInfo setStep(Step step) {
        this.step = step;
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
    public StepExecutionInfo setMethodInvocation(MethodInvocation methodInvocation) {
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
    public StepExecutionInfo setResult(Object result) {
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
    public StepExecutionInfo setError(Throwable error) {
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
    public StepExecutionInfo setThread(Thread thread) {
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
    public StepExecutionInfo setStackTraceElements(StackTraceElement[] stackTraceElements) {
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
