package de.qytera.qtaf.core.log.model.error;

/**
 * Error entity class for framework initialization errors
 */
public class FrameworkInitializationErrorLog extends ErrorLog {
    /**
     * Constructor
     *
     * @param e Error
     */
    public FrameworkInitializationErrorLog(Throwable e) {
        super(e);
        this.type = "Framework Initialization Error";
    }
}
