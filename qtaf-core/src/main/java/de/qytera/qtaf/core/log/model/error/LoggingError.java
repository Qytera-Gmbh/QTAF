package de.qytera.qtaf.core.log.model.error;

public class LoggingError extends ErrorLog {
    /**
     * Constructor
     *
     * @param e Error
     */
    public LoggingError(Throwable e) {
        super(e);
        this.type = "Logging Error";
    }
}
