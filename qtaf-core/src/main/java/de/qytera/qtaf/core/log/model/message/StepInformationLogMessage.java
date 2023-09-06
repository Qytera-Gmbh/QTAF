package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.type.NullType;
import java.util.*;

/**
 * Log message for called steps.
 */
public class StepInformationLogMessage extends LogMessage {
    /**
     * Unique id.
     */
    private final UUID uuid = UUID.randomUUID();

    /**
     * Log message type.
     */
    private static final String TYPE = "STEP_LOG";

    /**
     * Step name.
     */
    private String methodName = "";

    /**
     * Step annotation.
     */
    private final Step step = new Step();

    /**
     * Step status.
     */
    private Status status = Status.PENDING;

    /**
     * Time when step was started.
     */
    private Date start = null;

    /**
     * Time when step was finished.
     */
    private Date end = null;

    /**
     * Time needed for executing the step method.
     */
    private long duration = 0L;

    /**
     * List of assertions that were checked in this step.
     */
    private List<AssertionLogMessage> assertions = Collections.synchronizedList(new ArrayList<>());

    /**
     * The list of the step's method parameters.
     */
    @Getter
    @Setter
    private List<StepParameter> stepParameters = new ArrayList<>();

    /**
     * Step result.
     * If the step method returns an object it is stored in this attribute.
     */
    private Object result = null;

    /**
     * Path to screenshot file that was saved before execution of the step.
     */
    private String screenshotBefore = "";

    /**
     * Path to screenshot file that was saved after execution of the step.
     */
    private String screenshotAfter = "";

    /**
     * Step error.
     * If an error occures during the step method execution it is stored in this attribute.
     */
    private ThrowableWrapper error = null;

    /**
     * Constructor.
     *
     * @param methodName step name
     * @param message    log message
     */
    public StepInformationLogMessage(String methodName, String message) {
        super(LogLevel.INFO, message);
        this.methodName = methodName;
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
     * Get step annotation.
     *
     * @return step annotation
     */
    public Step getStep() {
        return step;
    }

    /**
     * Set step annotation.
     *
     * @param step step annotation
     * @return this
     */
    public StepInformationLogMessage setStep(de.qytera.qtaf.core.guice.annotations.Step step) {
        this.step.setName(step.name());
        this.step.setDescription(step.description());
        return this;
    }

    /**
     * Add a generic step parameter.
     *
     * @param <T>   the type of the value object
     * @param name  the name of the parameter
     * @param value the value of the parameter
     */
    public <T> void addStepParameter(String name, T value) {
        String className = value == null ? NullType.class.getName() : value.getClass().getSimpleName();
        this.stepParameters.add(new StepParameter(name, className, value));
    }

    /**
     * Get status.
     *
     * @return status Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Compute the status of the test step
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
     * Check if step has failed
     * @return true if step has failed, false otherwise
     */
    public boolean hasFailed() {
        return status == Status.ERROR;
    }

    /**
     * Check if step has passed
     * @return true if step has passed, false otherwise
     */
    public boolean hasPassed() {
        return status == Status.PASS;
    }

    /**
     * Check if step is pending
     * @return true if step is pending, false otherwise
     */
    public boolean isPending() {
        return status == Status.PENDING;
    }

    /**
     * Check if step is skipped
     * @return true if step is skipped, false otherwise
     */
    public boolean isSkipped() {
        return status == Status.SKIPPED;
    }

    /**
     * Get step result.
     *
     * @return step result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Set step result.
     *
     * @param result step result
     * @return this
     */
    public StepInformationLogMessage setResult(Object result) {
        this.result = result;
        return this;
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
    public StepInformationLogMessage setError(Throwable error) {
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
    public StepInformationLogMessage setError(ThrowableWrapper error) {
        this.error = error;
        this.status = Status.ERROR;
        return this;
    }

    /**
     * Get type.
     *
     * @return type
     */
    public String getTYPE() {
        return TYPE;
    }

    /**
     * Get methodName.
     *
     * @return methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set methodName.
     *
     * @param methodName MethodName
     * @return this
     */
    public StepInformationLogMessage setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    /**
     * Set status.
     *
     * @param status Status
     * @return this
     */
    public StepInformationLogMessage setStatus(Status status) {
        this.status = status;
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
    public StepInformationLogMessage setStart(Date start) {
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
    public StepInformationLogMessage setEnd(Date end) {
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
     * Set duration.
     *
     * @param duration Duration
     * @return this
     */
    public StepInformationLogMessage setDuration(long duration) {
        this.duration = duration;
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
     * Set screenshotBefore.
     *
     * @param screenshotBefore ScreenshotBefore
     * @return this
     */
    public StepInformationLogMessage setScreenshotBefore(String screenshotBefore) {
        this.screenshotBefore = screenshotBefore;
        return this;
    }

    /**
     * Get screenshotAfter.
     *
     * @return screenshotAfter
     */
    public String getScreenshotAfter() {
        return screenshotAfter;
    }

    /**
     * Set screenshotAfter.
     *
     * @param screenshotAfter ScreenshotAfter
     * @return this
     */
    public StepInformationLogMessage setScreenshotAfter(String screenshotAfter) {
        this.screenshotAfter = screenshotAfter;
        return this;
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
    public StepInformationLogMessage setAssertions(List<AssertionLogMessage> assertions) {
        this.assertions = assertions;
        return this;
    }

    /**
     * Add an assertion to the list.
     *
     * @param assertion Assertion
     * @return this
     */
    public StepInformationLogMessage addAssertion(AssertionLogMessage assertion) {
        this.assertions.add(assertion);

        if (assertion.hasFailed()) {
            status = Status.ERROR;
        }

        return this;
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

    /**
     * This data class holds the information from the Step annotation.
     */
    public static class Step {
        /**
         * Step name.
         */
        private String name = "";

        /**
         * Step description.
         */
        private String description = "";

        /**
         * Get name.
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Set name.
         *
         * @param name Name
         * @return this
         */
        public Step setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Get description.
         *
         * @return description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set description.
         *
         * @param description Description
         * @return this
         */
        public Step setDescription(String description) {
            this.description = description;
            return this;
        }
    }

    /**
     * Data class for step parameter information.
     */
    public static class StepParameter {
        /**
         * Parameter name.
         */
        private String name;

        /**
         * Parameter type.
         */
        private String type;

        /**
         * Parameter value.
         */
        private Object value;

        /**
         * Constructor.
         *
         * @param name  parameter name
         * @param type  parameter type
         * @param value parameter value
         */
        public StepParameter(String name, String type, Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }

        /**
         * Get name.
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Set name.
         *
         * @param name Name
         * @return this
         */
        public StepParameter setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Get type.
         *
         * @return type
         */
        public String getType() {
            return type;
        }

        /**
         * Set type.
         *
         * @param type Type
         * @return this
         */
        public StepParameter setType(String type) {
            this.type = type;
            return this;
        }

        /**
         * Get value.
         *
         * @return value
         */
        public Object getValue() {
            return value;
        }

        /**
         * Set value.
         *
         * @param value Value
         * @return this
         */
        public StepParameter setValue(Object value) {
            this.value = value;
            return this;
        }
    }
}
