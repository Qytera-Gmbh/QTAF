package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;
import lombok.Getter;

import java.util.*;

/**
 * Log message class holds information about log messages.
 */
public class LogMessage {

    /**
     * Step status.
     */
    @Getter
    protected Status status = Status.PENDING;

    /**
     * Unique id.
     */

    protected final UUID uuid = UUID.randomUUID();

    /**
     * List of assertions that were checked in this step.
     */
    protected List<AssertionLogMessage> assertions = Collections.synchronizedList(new ArrayList<>());


    /**
     * Time when step was started.
     */
    protected Date start = null;

    /**
     * Time when step was finished.
     */
    protected Date end = null;

    /**
     * Time needed for executing the step method.
     */
    // private long duration = 0L; TODO

    /**
     * Step error.
     * If an error occures during the step method execution it is stored in this attribute.
     */
    protected ThrowableWrapper error = null;


    /**
     * Log level.
     */
    protected LogLevel level;

    /**
     * Log message.
     */
    protected String message = "";

    /**
     * Feature ID.
     */
    protected String featureId = "";

    /**
     * Abstract Scenario ID.
     */
    protected String abstractScenarioId = "";

    /**
     * Scenario ID.
     */
    protected String scenarioId = "";

    /**
     * Constructor.
     *
     * @param level   log level
     * @param message log message
     */
    public LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }


    /**
     * Get uuid.
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Checks if step has failed.
     *
     * @return true if step has failed, false otherwise
     */
    public boolean hasFailed() {
        // return status == Status.ERROR;
        return getStatus() == Status.ERROR;
    }

    /**
     * Checks if step has passed.
     *
     * @return true if step has passed, false otherwise
     */
    public boolean hasPassed() {
        // return status == Status.PASS;
        return getStatus() == Status.PASS;
    }

    /**
     * Checks if step is pending.
     *
     * @return true if step is pending, false otherwise
     */
    public boolean isPending() {
        // return status == Status.PENDING;
        return getStatus() == Status.PENDING;
    }

    /**
     * Checks if step is skipped.
     *
     * @return true if step is skipped, false otherwise
     */
    public boolean isSkipped() {
        // return status == Status.SKIPPED;
        return getStatus() == Status.SKIPPED;

    }


    /**
     * Get step error.
     *
     * @return step error
     */
    public ThrowableWrapper getError() {
        return error;
    }

    /**
     * Check if an error occurred.
     *
     * @return true if an error occurred during method execution, false otherwise
     */
    public boolean hasError() {
        return this.error != null;
    }

    /**
     * Set step error.
     *
     * @param error step error
     * @return this
     */
    public LogMessage setError(Throwable error) {
        this.error = new ThrowableWrapper(error);
        this.status = Status.ERROR;
        // setStatus(Status.ERROR); TODO
        return this;
    }

    /**
     * Set step error.
     *
     * @param error step error
     * @return this
     */
    public LogMessage setError(ThrowableWrapper error) {
        this.error = error;
        // this.status = Status.ERROR;
        setStatus(Status.ERROR);
        return this;
    }

    /**
     * Get start.
     *
     * @return start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Set start.
     *
     * @param start Start
     * @return this
     */
    public LogMessage setStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Get end.
     *
     * @return end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Set end.
     *
     * @param end End
     * @return this
     */
    public LogMessage setEnd(Date end) {
        this.end = end;
        computeStatus();
        return this;
    }

    /**
     * Get duration.
     *
     * @return duration
     */
    public long getDuration() {
        if (this.end != null && this.getStart() != null) {
            return this.end.getTime() - this.getStart().getTime();
        }

        return 0;
    }

    /**
     * Get assertions.
     *
     * @return list of assertions
     */
    public List<AssertionLogMessage> getAssertions() {
        return assertions;
    }

    /**
     * Set list of assertions.
     *
     * @param assertions list of assertions
     * @return this
     */
    public LogMessage setAssertions(List<AssertionLogMessage> assertions) {
        this.assertions = assertions;
        return this;
    }

    /**
     * Add an assertion to the list.
     *
     * @param assertion Assertion
     * @return this
     */
    public LogMessage addAssertion(AssertionLogMessage assertion) {
        this.assertions.add(assertion);

        if (assertion.hasFailed()) {
            // status = Status.ERROR;
            setStatus(Status.ERROR);
        }

        return this;
    }








    /**
     * Computes the status of the test step.
     */
    public void computeStatus() {
        if (this.error != null) {
            setStatus(Status.ERROR);
            return;
        }

        // Check if there are any failed assertions. If there are any the step has failed.
        for (AssertionLogMessage assertion : assertions) {
            if (assertion.hasFailed()) {
                // status = Status.ERROR;
                setStatus(Status.ERROR);
                return;
            }
        }

        // status = Status.PASS;
        setStatus(Status.PASS);
    }

    /**
     * Gte log level.
     *
     * @return log level
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Set log level.
     *
     * @param level log level
     * @return this
     */
    public LogMessage setLevel(LogLevel level) {
        this.level = level;
        return this;
    }


    /**
     * Set status.
     *
     * @param status Status
     * @return this
     */
    public LogMessage setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Get log message.
     *
     * @return log message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set log message.
     *
     * @param message log message
     * @return this
     */
    public LogMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get featureId.
     *
     * @return featureId
     */
    public String getFeatureId() {
        return featureId;
    }

    /**
     * Set featureId.
     *
     * @param featureId FeatureId
     * @return this
     */
    public LogMessage setFeatureId(String featureId) {
        this.featureId = featureId;
        return this;
    }

    /**
     * Get abstractScenarioId.
     *
     * @return abstractScenarioId
     */
    public String getAbstractScenarioId() {
        return abstractScenarioId;
    }

    /**
     * Set abstractScenarioId.
     *
     * @param abstractScenarioId AbstractScenarioId
     * @return this
     */
    public LogMessage setAbstractScenarioId(String abstractScenarioId) {
        this.abstractScenarioId = abstractScenarioId;
        return this;
    }

    /**
     * Get scenarioId.
     *
     * @return scenarioId
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Set scenarioId.
     *
     * @param scenarioId ScenarioId
     * @return this
     */
    public LogMessage setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
        return this;
    }

    /**
     * Step status.
     */
    public enum Status {

        //TODO: Clean up status: (Failed vs. Failure vs. Error, Passed vs. Pass ...)
        /**
         * The step is still pending execution.
         */
        PENDING,
        /**
         * The step was executed successfully.
         */
        PASS,
        /**
         * There were errors during step execution.
         */
        ERROR,
        /**
         * The step's execution was skipped.
         */
        SKIPPED,
        /**
         * The step status could not be determined.
         */
        UNDEFINED,

        // From Assertion LogMessage
        /**
         * Status of passed assertions.
         */
        PASSED,
        /**
         * Status of failed assertions.
         */
        FAILED,

        FAILURE

    }
}
