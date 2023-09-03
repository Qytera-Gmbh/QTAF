package de.qytera.qtaf.xray.log;

import de.qytera.qtaf.core.log.model.error.ErrorLog;

/**
 * An error log entry representing Xray authentication errors.
 */
public class XrayAuthenticationErrorLog extends ErrorLog {
    /**
     * HTTP Response status code.
     */
    protected int statusCode;

    /**
     * HTTP error message.
     */
    protected String errorMessage = "";

    /**
     * Error description.
     */
    protected String description = "Xray API Authentication failed";

    /**
     * Constructor.
     *
     * @param e Error
     */
    public XrayAuthenticationErrorLog(Throwable e) {
        super(e);
        this.type = "Xray Authentication Error";
    }

    /**
     * Get statusCode.
     *
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode.
     *
     * @param statusCode StatusCode
     * @return this
     */
    public XrayAuthenticationErrorLog setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * Get errorMessage.
     *
     * @return errorMessage
     */
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set errorMessage.
     *
     * @param errorMessage ErrorMessage
     * @return this
     */
    public XrayAuthenticationErrorLog setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
