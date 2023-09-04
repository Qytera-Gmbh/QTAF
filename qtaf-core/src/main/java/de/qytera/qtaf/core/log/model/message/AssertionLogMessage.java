package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;

/**
 * Entity class for assertion log messages
 */
public class AssertionLogMessage extends LogMessage {
    /**
     * Type of assertion
     */
    protected AssertionLogMessageType type;

    /**
     * Step of assertion
     * This attribute needs to be transient so that it is ignored by JSON serializers.
     * Otherwise, this causes a stack overflow, because it is a circular reference.
     */
    protected transient StepInformationLogMessage step;

    /**
     * Assertion status
     */
    protected Status status;

    /**
     * Assertion condition
     */
    protected boolean condition;

    /**
     * Actual object
     */
    protected Object actual;

    /**
     * Expected object
     */
    protected Object expected;

    /**
     * Assertion Error
     */
    protected ThrowableWrapper error;

    /**
     * Constructor
     *
     * @param level   log level
     * @param message log message
     */
    public AssertionLogMessage(LogLevel level, String message) {
        super(level, message);
    }

    /**
     * Get the assertion type
     * @return assertion type
     */
    public AssertionLogMessageType type() {
        return type;
    }

    /**
     * Set the assertion type
     * @param type  assertion type
     * @return  this
     */
    public AssertionLogMessage setType(AssertionLogMessageType type) {
        this.type = type;
        return this;
    }

    /**
     * Get the step where the assertion was executed
     * @return step
     */
    public StepInformationLogMessage step() {
        return step;
    }

    /**
     * Set the step where the assertion was executed
     * @param step
     * @return  this
     */
    public AssertionLogMessage setStep(StepInformationLogMessage step) {
        this.step = step;
        if (step != null) {
            step.addAssertion(this);
        }
        return this;
    }

    /**
     * Get the condition
     * @return condition
     */
    public boolean condition() {
        return condition;
    }

    /**
     * Set the condition
     * @param condition condition
     * @return  this
     */
    public AssertionLogMessage setCondition(boolean condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Get the actual value
     * @return actual value
     */
    public Object actual() {
        return actual;
    }

    /**
     * Set the actual value
     * @param actual    actual value
     * @return this
     */
    public AssertionLogMessage setActual(Object actual) {
        this.actual = actual;
        return this;
    }

    /**
     * Get the expected value
     * @return  expected value
     */
    public Object expected() {
        return expected;
    }

    /**
     * Set the expected value
     * @param expected  expected value
     * @return this
     */
    public AssertionLogMessage setExpected(Object expected) {
        this.expected = expected;
        return this;
    }

    /**
     * Get the error
     * @return this
     */
    public ThrowableWrapper error() {
        return error;
    }

    /**
     * Set the error
     * @param error Exception object
     * @return this
     */
    public AssertionLogMessage setError(AssertionError error) {
        if (error != null) {
            this.error = new ThrowableWrapper(error);
        } else {
            this.error = null;
        }
        return this;
    }

    /**
     * Check if this assertion was executed
     * @return true if it was executed, false otherwise
     */
    public boolean wasExecuted() {
        return status != null;
    }

    /**
     * Check if assertion has passed
     * @return true if exception has passed, false otherwise
     */
    public boolean hasPassed() {
        return status == Status.PASSED;
    }

    /**
     * Check if assertion has failed
     * @return true if it has failed, false otherwise
     */
    public boolean hasFailed() {
        return status == Status.FAILED;
    }

    /**
     * Set status to passed
     * @return this
     */
    public AssertionLogMessage setStatusToPassed() {
        this.status = Status.PASSED;
        return this;
    }

    /**
     * Set status to failed
     * @return this
     */
    public AssertionLogMessage setStatusToFailed() {
        this.status = Status.FAILED;
        return this;
    }


    /**
     * Step status
     */
    private enum Status {
        /**
         * Status of passed assertions
         */
        PASSED,
        /**
         * Status of failed assertions
         */
        FAILED,
    }

}
