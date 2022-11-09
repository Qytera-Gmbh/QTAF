package de.qytera.qtaf.core.guice.invokation;

import de.qytera.qtaf.core.guice.annotations.Step;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Information object that contains Step execution information
 */
public class StepExecutionInfo {
    private int id;
    private Step step;
    private MethodInvocation methodInvocation;
    private Object result = null;
    private Throwable error = null;

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
}
