package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.error.ThrowableWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Log message for called steps
 */
public class StepInformationLogMessage extends LogMessage {
    /**
     * Unique id
     */
    private final UUID uuid = UUID.randomUUID();

    /**
     * Log message type
     */
    private final String type = "STEP_LOG";

    /**
     * Step name
     */
    private String methodName = "";

    /**
     * Step annotation
     */
    private Step step = new Step();

    /**
     * Step status
     */
    private Status status = Status.PENDING;

    /**
     * Time when step was started
     */
    private Date start = null;

    /**
     * Time when step was finished
     */
    private Date end = null;

    /**
     * Time needed for executing the step method
     */
    private long duration = 0L;

    /**
     * List of step method parameters
     */
    private ArrayList<StepParameter> stepParameters = new ArrayList<>();

    /**
     * Step result.
     * If the step method returns an object it is stored in this attribute.
     */
    private Object result = null;

    /**
     * Path to screenshot file that was saved before execution of the step
     */
    private String screenshotBefore = "";

    /**
     * Path to screenshot file that was saved after execution of the step
     */
    private String screenshotAfter = "";

    /**
     * Step error.
     * If an error occures during the step method execution it is stored in this attribute.
     */
    private ThrowableWrapper error = null;

    /**
     * Constructor
     *
     * @param methodName step name
     * @param message    log message
     */
    public StepInformationLogMessage(String methodName, String message) {
        super(LogLevel.INFO, message);
        this.methodName = methodName;
    }

    @Override
    protected void finalize() throws Throwable {
        QtafFactory.getLogger().warn("[StepLog] Destroying log message '" + getMessage() + "'");
        super.finalize();
    }

    /**
     * Get uuid
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get step annotation
     *
     * @return step annotation
     */
    public Step getStep() {
        return step;
    }

    /**
     * Set step annotation
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
     * Get step parameter list
     *
     * @return step parameter list
     */
    public ArrayList<StepParameter> getStepParameters() {
        return stepParameters;
    }

    /**
     * Set step parameter list
     *
     * @param stepParameters List of step parameters
     * @return step parameter list
     */
    public StepInformationLogMessage setStepParameters(ArrayList<StepParameter> stepParameters) {
        this.stepParameters = stepParameters;
        return this;
    }

    /**
     * App step parameter
     *
     * @param name  Name of the parameter
     * @param type  Type of the parameter
     * @param value Value of the parameter
     * @return step parameter
     */
    public StepInformationLogMessage addStepParameter(String name, String type, Object value) {
        this.stepParameters.add(new StepParameter(name, type, value));
        return this;
    }

    /**
     * Get status
     *
     * @return status Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Get step result
     *
     * @return step result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Set step result
     *
     * @param result step result
     * @return this
     */
    public StepInformationLogMessage setResult(Object result) {
        this.result = result;
        this.status = Status.PASS;
        return this;
    }

    /**
     * Get step error
     *
     * @return step error
     */
    public ThrowableWrapper getError() {
        return error;
    }

    /**
     * Check if an error occurred
     *
     * @return true if an error occurred during method execution, false otherwise
     */
    public boolean hasError() {
        return this.error != null;
    }

    /**
     * Set step error
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
     * Set step error
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
     * Get type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Get methodName
     *
     * @return methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set methodName
     *
     * @param methodName MethodName
     * @return this
     */
    public StepInformationLogMessage setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    /**
     * Set status
     *
     * @param status Status
     * @return this
     */
    public StepInformationLogMessage setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Get start
     *
     * @return start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Set start
     *
     * @param start Start
     * @return this
     */
    public StepInformationLogMessage setStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Get end
     *
     * @return end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Set end
     *
     * @param end End
     * @return this
     */
    public StepInformationLogMessage setEnd(Date end) {
        this.end = end;
        return this;
    }

    /**
     * Get duration
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
     * Set duration
     *
     * @param duration Duration
     * @return this
     */
    public StepInformationLogMessage setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Get screenshotBefore
     *
     * @return screenshotBefore
     */
    public String getScreenshotBefore() {
        return screenshotBefore;
    }

    /**
     * Set screenshotBefore
     *
     * @param screenshotBefore ScreenshotBefore
     * @return this
     */
    public StepInformationLogMessage setScreenshotBefore(String screenshotBefore) {
        this.screenshotBefore = screenshotBefore;
        return this;
    }

    /**
     * Get screenshotAfter
     *
     * @return screenshotAfter
     */
    public String getScreenshotAfter() {
        return screenshotAfter;
    }

    /**
     * Set screenshotAfter
     *
     * @param screenshotAfter ScreenshotAfter
     * @return this
     */
    public StepInformationLogMessage setScreenshotAfter(String screenshotAfter) {
        this.screenshotAfter = screenshotAfter;
        return this;
    }

    /**
     * Step status
     */
    public enum Status {
        PENDING,
        PASS,
        ERROR,
        SKIPPED,
        UNDEFINED,
    }

    /**
     * This data class holds the information from the Step annotation
     */
    public static class Step {
        /**
         * Step name
         */
        private String name = "";

        /**
         * Step description
         */
        private String description = "";

        /**
         * Get name
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Set name
         *
         * @param name Name
         * @return this
         */
        public Step setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Get description
         *
         * @return description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set description
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
     * Data class for step parameter information
     */
    public static class StepParameter {
        /**
         * Parameter name
         */
        private String name;

        /**
         * Parameter type
         */
        private String type;

        /**
         * Parameter value
         */
        private Object value;

        /**
         * Constructor
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
         * Get name
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Set name
         *
         * @param name Name
         * @return this
         */
        public StepParameter setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Get type
         *
         * @return type
         */
        public String getType() {
            return type;
        }

        /**
         * Set type
         *
         * @param type Type
         * @return this
         */
        public StepParameter setType(String type) {
            this.type = type;
            return this;
        }

        /**
         * Get value
         *
         * @return value
         */
        public Object getValue() {
            return value;
        }

        /**
         * Set value
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
