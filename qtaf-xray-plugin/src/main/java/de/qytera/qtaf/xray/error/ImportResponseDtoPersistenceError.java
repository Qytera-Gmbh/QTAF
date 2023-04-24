package de.qytera.qtaf.xray.error;

import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;

/**
 * An error log entry describing an error occurring when persisting test result import responses.
 */
public class ImportResponseDtoPersistenceError extends ErrorLog {
    /**
     * Import DTO
     */
    XrayImportResponseDto xrayImportResponseDto;

    /**
     * Constructor
     *
     * @param e Error
     */
    public ImportResponseDtoPersistenceError(Throwable e) {
        super(e);
    }

    /**
     * Get xrayImportResponseDto
     *
     * @return xrayImportResponseDto
     */
    public XrayImportResponseDto getXrayImportResponseDto() {
        return xrayImportResponseDto;
    }

    /**
     * Set xrayImportResponseDto
     *
     * @param xrayImportResponseDto XrayImportResponseDto
     * @return this
     */
    public ImportResponseDtoPersistenceError setXrayImportResponseDto(XrayImportResponseDto xrayImportResponseDto) {
        this.xrayImportResponseDto = xrayImportResponseDto;
        return this;
    }
}
