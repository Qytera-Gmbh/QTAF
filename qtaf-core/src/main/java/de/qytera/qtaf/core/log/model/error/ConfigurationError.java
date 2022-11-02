package de.qytera.qtaf.core.log.model.error;

/**
 * Log message for configuration errors
 */
public class ConfigurationError extends ErrorLog {

    /**
     * Constructor
     *
     * @param e Error
     */
    public ConfigurationError(Throwable e) {
        super(e);
        this.type = "Driver Initialization Error";
    }

}
