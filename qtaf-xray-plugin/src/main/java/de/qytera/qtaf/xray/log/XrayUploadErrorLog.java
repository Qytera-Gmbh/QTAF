package de.qytera.qtaf.xray.log;

import de.qytera.qtaf.core.log.model.error.ErrorLog;

public class XrayUploadErrorLog extends ErrorLog {
    protected String statusCode = "";
    protected String errorMessage = "";

    /**
     * Constructor
     *
     * @param e Error
     */
    public XrayUploadErrorLog(Throwable e) {
        super(e);
        this.type = "Xray Upload Error";
    }

    /**
     * Get statusCode
     *
     * @return statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     *
     * @param statusCode StatusCode
     * @return this
     */
    public XrayUploadErrorLog setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * Get errorMessage
     *
     * @return errorMessage
     */
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set errorMessage
     *
     * @param errorMessage ErrorMessage
     * @return this
     */
    public XrayUploadErrorLog setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
