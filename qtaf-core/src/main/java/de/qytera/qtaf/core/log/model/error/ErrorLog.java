package de.qytera.qtaf.core.log.model.error;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Error object class for error log messages
 */
public class ErrorLog {
    /**
     * Error object
     */
    protected transient Throwable error;

    /**
     * Error type
     */
    protected String type = "";

    /**
     * Error type
     */
    protected List<String> stack = null;

    /**
     * Constructor
     *
     * @param message Error message
     */
    public ErrorLog(String message) {
        this.setError(new Exception(message));
        this.stack = null;
    }

    /**
     * Constructor
     *
     * @param e Error
     */
    public ErrorLog(Throwable e) {
        this.setError(e);
        this.stack = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
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
    public ErrorLog setError(Throwable error) {
        this.error = error;
        return this;
    }

    /**
     * Get stack trace
     *
     * @return stack trace
     */
    public List<String> getStackTrace() {
        return this.stack;
    }

    /**
     * Get error message
     *
     * @return error message
     */
    public String getErrorMessage() {
        return this.getError().getMessage();
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
    public ErrorLog setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get stack
     *
     * @return stack
     */
    public List<String> getStack() {
        return stack;
    }
}
