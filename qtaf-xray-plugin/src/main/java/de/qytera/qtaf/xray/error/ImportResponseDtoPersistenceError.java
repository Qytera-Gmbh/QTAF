package de.qytera.qtaf.xray.error;

import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;

/**
 * An error log entry describing an error occurring when persisting test result import responses.
 */
public class ImportResponseDtoPersistenceError extends ErrorLog {
    /**
     * Import DTO.
     */
    ImportExecutionResultsResponseDto importExecutionResultsResponseDto;

    /**
     * Constructor.
     *
     * @param e Error
     */
    public ImportResponseDtoPersistenceError(Throwable e) {
        super(e);
    }

    /**
     * Get xrayImportResponseDto.
     *
     * @return xrayImportResponseDto
     */
    public ImportExecutionResultsResponseDto getXrayImportResponseDto() {
        return importExecutionResultsResponseDto;
    }

    /**
     * Set xrayImportResponseDto.
     *
     * @param importExecutionResultsResponseDto XrayImportResponseDto
     * @return this
     */
    public ImportResponseDtoPersistenceError setXrayImportResponseDto(ImportExecutionResultsResponseDto importExecutionResultsResponseDto) {
        this.importExecutionResultsResponseDto = importExecutionResultsResponseDto;
        return this;
    }
}
