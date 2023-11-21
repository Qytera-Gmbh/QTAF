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
     * Time needed for executing the step method.
     */
    private long duration = 0L;









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
        this.step.setName(step.name());
        this.step.setDescription(step.description());
        return this;
    }



















    /**
     * Set step error.
     *
     * @param error step error
     * @return this
     */




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
     * Add an assertion to the list.
     *
     * @param assertion Assertion
     * @return this
     */




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
