package de.qytera.qtaf.core.log.model.error;

import java.util.ArrayList;

/**
 * Collection for error log messages
 */
public class ErrorLogCollection {

    /**
     * Singleton instance of ErrorLogCollection
     */
    private static ErrorLogCollection instance = new ErrorLogCollection();

    /**
     * Error logs
     */
    private final ArrayList<ErrorLog> errorLogs = new ArrayList<>();

    /**
     * Private constructor
     */
    private ErrorLogCollection() {}

    /**
     * Get singleton instance of ErrorLogCollection
     * @return  ErrorLogCollection
     */
    public static ErrorLogCollection getInstance() {
        return instance;
    }

    /**
     * Get errorLogs
     *
     * @return errorLogs
     */
    public ArrayList<ErrorLog> getErrorLogs() {
        return errorLogs;
    }

    /**
     * Add error log
     * @param t  Throwable exception
     */
    public void addErrorLog(Throwable t) {
        errorLogs.add(new ErrorLog(t));
    }

    /**
     * Add error log
     * @param errorLog  Error log
     */
    public void addErrorLog(ErrorLog errorLog) {
        errorLogs.add(errorLog);
    }

    /**
     * Add error log
     * @param message  Error message
     */
    public void addErrorLog(String message) {
        errorLogs.add(new ErrorLog(message));
    }

    /**
     * Check if there are no error logs
     * @return  True if there are no error logs, false otherwise
     */
    public boolean isEmpty() {
        return errorLogs.isEmpty();
    }
}
