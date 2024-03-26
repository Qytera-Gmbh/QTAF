package de.qytera.qtaf.core.log.model.message;

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
        this.setStepName(step.name());
        this.setStepDescription(step.description());
        return this;
    }

    /**
     * Set step name.
     *
     * @param stepName step name
     * @return this
     */
    public StepInformationLogMessage setStepName(String stepName) {
        this.step.setName(stepName);
        return this;
    }

    /**
     * Set step description.
     *
     * @param stepDescription step description
     * @return this
     */
    public StepInformationLogMessage setStepDescription(String stepDescription) {
        this.step.setDescription(stepDescription);
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
