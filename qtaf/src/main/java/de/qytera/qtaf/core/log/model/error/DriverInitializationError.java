package de.qytera.qtaf.core.log.model.error;

public class DriverInitializationError extends ErrorLog {

    /**
     * Constructor
     *
     * @param e Error
     */
    public DriverInitializationError(Throwable e) {
        super(e);
        this.type = "Driver Initialization Error";
    }
}
