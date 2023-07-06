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
     */
    protected StepInformationLogMessage step;

    /**
     * Assertion status
     */
    protected Status status;

    /**
     * Assertion object
     */
    protected Object object;

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

    public AssertionLogMessageType type() {
        return type;
    }

    public AssertionLogMessage setType(AssertionLogMessageType type) {
        this.type = type;
        return this;
    }

    public StepInformationLogMessage step() {
        return step;
    }

    public AssertionLogMessage setStep(StepInformationLogMessage step) {
        this.step = step;
        if (step != null) {
            step.addAssertion(this);
        }
        return this;
    }

    public boolean condition() {
        return condition;
    }

    public AssertionLogMessage setCondition(boolean condition) {
        this.condition = condition;
        return this;
    }

    public Object object() {
        return object;
    }

    public AssertionLogMessage setObject(Object object) {
        this.object = object;
        return this;
    }

    public Object actual() {
        return actual;
    }

    public AssertionLogMessage setActual(Object actual) {
        this.actual = actual;
        return this;
    }

    public Object expected() {
        return expected;
    }

    public AssertionLogMessage setExpected(Object expected) {
        this.expected = expected;
        return this;
    }

    public ThrowableWrapper error() {
        return error;
    }

    public AssertionLogMessage setError(AssertionError error) {
        if (error != null) {
            this.error = new ThrowableWrapper(error);
        } else {
            this.error = null;
        }
        return this;
    }

    public boolean hasPassed() {
        return status == Status.PASSED;
    }

    public boolean hasFailed() {
        return status == Status.FAILED;
    }

    public AssertionLogMessage setStatusToPassed() {
        this.status = Status.PASSED;
        return this;
    }

    public AssertionLogMessage setStatusToFailed() {
        this.status = Status.FAILED;
        return this;
    }


    /**
     * Step status
     */
    private enum Status {
        PASSED,
        FAILED,
    }

}
