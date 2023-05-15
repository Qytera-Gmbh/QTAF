package de.qytera.qtaf.core.log.model.error;

import java.util.Arrays;
import java.util.List;

/**
 * Error object class for error log messages
 */
public class ErrorLog {
    /**
     * Error object
     */
    protected Throwable error;

    /**
     * Error type
     */
    protected String type = "";

    /**
     * Error message
     */
    protected String message;

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
        this.message = message;
    }

    /**
     * Constructor
     *
     * @param e Error
     */
    public ErrorLog(Throwable e) {
        this.setError(e);
        this.message = e.getMessage();
        this.stack = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
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
     * Get message
     * @return  message
     */
    public String message() {
        return message;
    }

    /**
     * Set message
     * @param message   Message
     * @return  this
     */
    public ErrorLog setMessage(String message) {
        this.message = message;
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
