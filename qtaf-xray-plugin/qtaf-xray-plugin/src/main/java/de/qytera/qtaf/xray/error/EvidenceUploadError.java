package de.qytera.qtaf.xray.error;

import de.qytera.qtaf.core.log.model.error.ErrorLog;

/**
 * Error entity that is created when upload of Xray evidence fails
 */
public class EvidenceUploadError extends ErrorLog {
    /**
     * Evidence filepath
     */
    private String filepath;

    /**
     * Constructor
     *
     * @param e Error
     */
    public EvidenceUploadError(Throwable e) {
        super(e);
        this.type = "Xray Evidence Upload Error";
    }

    /**
     * Get filepath
     *
     * @return filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Set filepath
     *
     * @param filepath Filepath
     * @return this
     */
    public EvidenceUploadError setFilepath(String filepath) {
        this.filepath = filepath;
        return this;
    }
}
