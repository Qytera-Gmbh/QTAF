package de.qytera.qtaf.xray.error;

import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;

public class ImportRequestDtoPersistenceError extends ErrorLog {
    /**
     * Import DTO
     */
    XrayImportRequestDto xrayImportRequestDto;

    /**
     * Constructor
     *
     * @param e Error
     */
    public ImportRequestDtoPersistenceError(Throwable e) {
        super(e);
    }

    /**
     * Get xrayImportRequestDto
     *
     * @return xrayImportRequestDto
     */
    public XrayImportRequestDto getXrayImportRequestDto() {
        return xrayImportRequestDto;
    }

    /**
     * Set xrayImportRequestDto
     *
     * @param xrayImportRequestDto XrayImportRequestDto
     * @return this
     */
    public ImportRequestDtoPersistenceError setXrayImportRequestDto(XrayImportRequestDto xrayImportRequestDto) {
        this.xrayImportRequestDto = xrayImportRequestDto;
        return this;
    }
}
