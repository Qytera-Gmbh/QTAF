package de.qytera.qtaf.xray.error;

import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.xray.dto.request.xray.ImportExecutionResultsRequestDto;

/**
 * An error log entry describing an error occurring when persisting test result import requests.
 */
public class ImportRequestDtoPersistenceError extends ErrorLog {
    /**
     * Import DTO.
     */
    ImportExecutionResultsRequestDto importExecutionResultsRequestDto;

    /**
     * Constructor.
     *
     * @param e Error
     */
    public ImportRequestDtoPersistenceError(Throwable e) {
        super(e);
    }

    /**
     * Get xrayImportRequestDto.
     *
     * @return xrayImportRequestDto
     */
    public ImportExecutionResultsRequestDto getXrayImportRequestDto() {
        return importExecutionResultsRequestDto;
    }

    /**
     * Set xrayImportRequestDto.
     *
     * @param importExecutionResultsRequestDto XrayImportRequestDto
     * @return this
     */
    public ImportRequestDtoPersistenceError setXrayImportRequestDto(ImportExecutionResultsRequestDto importExecutionResultsRequestDto) {
        this.importExecutionResultsRequestDto = importExecutionResultsRequestDto;
        return this;
    }
}
