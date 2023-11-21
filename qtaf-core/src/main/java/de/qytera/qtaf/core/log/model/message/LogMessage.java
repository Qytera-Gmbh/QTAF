package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.type.NullType;
import java.util.*;

/**
 * Log message class holds information about log messages.
 */
public class LogMessage {
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

    // Refactoring

    /**
     * Step error.
     * If an error occures during the step method execution it is stored in this attribute.
     */
    @Getter @Setter
    private ThrowableWrapper error = null;

    /**
     * Step status.
     */
    private Status status = Status.PENDING;

    /**
     * Get status.
     *
     * @return status Status
     */
    public Status getStatus() {
        return status;
    }



    /**
     * Computes the status of the test step.
     */
    public void computeStatus() {
        // Check if this step has an error object. If there is one the step has failed.
        if (this.error != null) {
            status = Status.ERROR;
            return;
        }

        // Check if there are any failed assertions. If there are any the step has failed.
        for (AssertionLogMessage assertion : assertions) {
            if (assertion.hasFailed()) {
                status = Status.ERROR;
                return;
            }
        }

        status = Status.PASS;
    }

    /**
     * Checks if step has failed.
     *
     * @return true if step has failed, false otherwise
     */
    public boolean hasFailed() {
        return status == Status.ERROR;
    }

    /**
     * Checks if step has passed.
     *
     * @return true if step has passed, false otherwise
     */
    public boolean hasPassed() {
        return status == Status.PASS;
    }


    /**
     * Checks if step is pending.
     *
     * @return true if step is pending, false otherwise
     */
    public boolean isPending() {
        return status == Status.PENDING;
    }

    /**
     * Checks if step is skipped.
     *
     * @return true if step is skipped, false otherwise
     */
    public boolean isSkipped() {
        return status == Status.SKIPPED;
    }

    public LogMessage setError(Throwable error) {
        this.error = new ThrowableWrapper(error);
        this.status = Status.ERROR;
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
        this.status = Status.ERROR;
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

    public LogMessage addAssertion(AssertionLogMessage assertion) {
        this.assertions.add(assertion);

        if (assertion.hasFailed()) {
            status = Status.ERROR;
        }

        return this;
    }

    // Error

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

    // Assertions

    /**
     * List of assertions that were checked in this step.
     */
    private List<AssertionLogMessage> assertions = Collections.synchronizedList(new ArrayList<>());

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

    // Date

    /**
     * Time when step was finished.
     */
    private Date end = null;

    /**
     * Time when step was started.
     */
    private Date start = null;


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
     * Unique id.
     */
    private final UUID uuid = UUID.randomUUID();

    /**
     * Get uuid.
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    // Screen shots

    /**
     * Set screenshotAfter.
     *
     * @param screenshotAfter ScreenshotAfter
     * @return this
     */
    public LogMessage setScreenshotAfter(String screenshotAfter) {
        this.screenshotAfter = screenshotAfter;
        return this;
    }

    /**
     * Path to screenshot file that was saved before execution of the step.
     */
    private String screenshotBefore = "";

    /**
     * Path to screenshot file that was saved after execution of the step.
     */
    private String screenshotAfter = "";

    /**
     * Set screenshotBefore.
     *
     * @param screenshotBefore ScreenshotBefore
     * @return this
     */
    public LogMessage setScreenshotBefore(String screenshotBefore) {
        this.screenshotBefore = screenshotBefore;
        return this;
    }


    /**
     * Get screenshotBefore.
     *
     * @return screenshotBefore
     */
    public String getScreenshotBefore() {
        return screenshotBefore;
    }



    /**
     * Get screenshotAfter.
     *
     * @return screenshotAfter
     */
    public String getScreenshotAfter() {
        return screenshotAfter;
    }


    // Step

    /**
     * Add a generic step parameter.
     *
     * @param <T>   the type of the value object
     * @param name  the name of the parameter
     * @param value the value of the parameter
     */
    public <T> void addStepParameter(String name, T value) {
        String className = value == null ? NullType.class.getName() : value.getClass().getSimpleName();
        this.stepParameters.add(new StepInformationLogMessage.StepParameter(name, className, value));
    }


    /**
     * The list of the step's method parameters.
     */
    @Getter
    @Setter
    private List<StepInformationLogMessage.StepParameter> stepParameters = new ArrayList<>();

    /**
     * Set step result.
     *
     * @param result step result
     * @return this
     */
    public LogMessage setResult(Object result) {
        this.result = result;
        return this;
    }

    /**
     * Step result.
     * If the step method returns an object it is stored in this attribute.
     */
    private Object result = null;


    /**
     * Get step result.
     *
     * @return step result
     */
    public Object getResult() {
        return result;
    }







    /**
     * Step status.
     */
    public enum Status {
        /**
         * The step is still pending execution.
         */
        PENDING,
        /**
         * The step was executed successfully.
         */
        PASS,
        /**
         * The step was executed successfully.
         */
        PASSED,
        /**
         * An assertion was not honoured. The step was executed unsuccessfully.
         */
        FAILED,
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
    }

}
