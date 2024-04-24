package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;

/**
 * Entity class for assertion log messages.
 */
public class AssertionLogMessage extends LogMessage {

    // TODO: Refacor Class with lombok
    /**
     * Type of assertion.
     */
    protected AssertionLogMessageType type;

    /**
     * Step of assertion.
     * This attribute needs to be transient so that it is ignored by JSON serializers.
     * Otherwise, this causes a stack overflow, because it is a circular reference.
     */
    protected transient StepInformationLogMessage step;

    /**
     * Assertion condition.
     */
    protected boolean condition;

    /**
     * Actual object.
     */
    protected Object actual;

    /**
     * Expected object.
     */
    protected Object expected;

    /**
     * Constructor.
     *
     * @param level   log level
     * @param message log message
     */
    public AssertionLogMessage(LogLevel level, String message) {
        super(level, message);
    }

    /**
     * Returns the assertion's type.
     *
     * @return the type
     */
    public AssertionLogMessageType type() {
        return type;
    }

    /**
     * Set the assertion's type.
     *
     * @param type the type
     * @return the current assertion
     */
    public AssertionLogMessage setType(AssertionLogMessageType type) {
        this.type = type;
        return this;
    }

    /**
     * Returns the assertion's step.
     *
     * @return the step
     */
    public StepInformationLogMessage step() {
        return step;
    }

    /**
     * Set the step where the assertion was executed.
     *
     * @param step step log message
     * @return this
     */
    public AssertionLogMessage setStep(StepInformationLogMessage step) {
        this.step = step;
        if (step != null) {
            step.addAssertion(this);
        }
        return this;
    }

    /**
     * Returns the assertion's condition.
     *
     * @return the condition
     */
    public boolean condition() {
        return condition;
    }

    /**
     * Set the assertion's condition.
     *
     * @param condition the condition
     * @return the current assertion
     */
    public AssertionLogMessage setCondition(boolean condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Returns the assertion's actual object.
     *
     * @return the actual object
     */
    public Object actual() {
        return actual;
    }

    /**
     * Set the actual object.
     *
     * @param actual the actual object
     * @return the current assertion
     */
    public AssertionLogMessage setActual(Object actual) {
        this.actual = actual;
        return this;
    }

    /**
     * Returns the assertion's expected object.
     *
     * @return the expected object
     */
    public Object expected() {
        return expected;
    }

    /**
     * Set the expected object.
     *
     * @param expected the expected object
     * @return the current assertion
     */
    public AssertionLogMessage setExpected(Object expected) {
        this.expected = expected;
        return this;
    }

    /**
     * Returns the assertion's error.
     *
     * @return the error
     */
    public ThrowableWrapper error() {
        return error;
    }

    /**
     * Set the assertion's error.
     *
     * @param error the error
     * @return the current assertion
     */
    // TODO: soll es diese zwei unterschieldichen setter für Error geben (vgl. LogMessage)?
    public AssertionLogMessage setError(AssertionError error) {
        if (error != null) {
            this.error = new ThrowableWrapper(error);
        } else {
            this.error = null;
        }
        return this;
    }

    /**
     * Returns whether the assertion was actually executed.
     *
     * @return true if it was executed, false otherwise
     */
    public boolean wasExecuted() {
        // return status != null;
        return getStatus() != Status.PENDING;
    }

    /**
     * Returns whether the assertion has passed.
     *
     * @return true if it passed, false otherwise
     */
    public boolean hasPassed() {
        // return status == Status.PASSED;
        return getStatus() == Status.PASSED;
    }

    /**
     * Returns whether the assertion has failed.
     *
     * @return true if it failed, false otherwise
     */
    public boolean hasFailed() {
        // return status == Status.FAILED;
        return getStatus() == Status.FAILED;
    }

    /**
     * Set the assertion status to passed.
     *
     * @return the current assertion
     */
    public AssertionLogMessage setStatusToPassed() {
        // this.status = Status.PASSED;
        setStatus(Status.PASSED);
        return this;
    }

    /**
     * Set the assertion status to failed.
     *
     * @return the current assertion
     */
    public AssertionLogMessage setStatusToFailed() {
        // this.status = Status.FAILED;
        setStatus(Status.FAILED);
        return this;
    }
}
