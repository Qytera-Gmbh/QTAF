package de.qytera.qtaf.core.log.model.error;

/**
 * This class is a wrapper class for java.lang.Throwable, because GSON is not able to serialize this class.
 */
public class ThrowableWrapper {
    /**
     * Original object.
     */
    private transient Throwable throwable;

    /**
     * Constructor.
     *
     * @param t Throwable instance
     */
    public ThrowableWrapper(Throwable t) {
        throwable = t;
    }

    /**
     * Get message of throwable.
     *
     * @return message
     */
    public String getMessage() {
        return throwable != null ? throwable.getMessage() : "";
    }

    /**
     * Get classname of throwable.
     *
     * @return classname
     */
    public String getClassName() {
        return throwable != null ? throwable.getClass().getName() : "";
    }

    /**
     * Get stack trace.
     *
     * @return stack trace
     */
    public StackTraceElement[] getStackTrace() {
        return throwable != null ? throwable.getStackTrace() : new StackTraceElement[]{};
    }
}
