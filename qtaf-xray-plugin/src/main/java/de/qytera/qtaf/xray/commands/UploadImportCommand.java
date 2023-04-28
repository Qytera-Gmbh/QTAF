package de.qytera.qtaf.xray.commands;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.patterns.Command;
import de.qytera.qtaf.xray.dto.request.xray.ImportExecutionResultsRequestDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import de.qytera.qtaf.xray.repository.xray.XrayTestRepository;

/**
 * Command to upload test results to Xray REST API
 */
public class UploadImportCommand implements Command {
    /**
     * Command to authenticate against Xray API
     */
    private AuthenticationCommand authenticationCommand = new AuthenticationCommand();

    /**
     * Payload for HTTP request
     */
    private ImportExecutionResultsRequestDto xrayImportRequestDto;

    /**
     * Payload sent by the Xray API
     */
    private ImportExecutionResultsResponseDto xrayImportResponseDto;

    /**
     * Get xrayImportRequestDto
     *
     * @return xrayImportRequestDto XrayImportRequestDto
     */
    public ImportExecutionResultsRequestDto getXrayImportRequestDto() {
        return xrayImportRequestDto;
    }

    /**
     * Set xrayImportRequestDto
     *
     * @param importExecutionResultsRequestDto XrayImportRequestDto
     * @return this
     */
    public UploadImportCommand setXrayImportRequestDto(ImportExecutionResultsRequestDto importExecutionResultsRequestDto) {
        this.xrayImportRequestDto = importExecutionResultsRequestDto;
        return this;
    }

    /**
     * Get xrayImportResponseDto
     *
     * @return xrayImportResponseDto XrayImportResponseDto
     */
    public ImportExecutionResultsResponseDto getXrayImportResponseDto() {
        return xrayImportResponseDto;
    }

    /**
     * Set xrayImportResponseDto
     *
     * @param importExecutionResultsResponseDto XrayImportResponseDto
     * @return this
     */
    public UploadImportCommand setXrayImportResponseDto(ImportExecutionResultsResponseDto importExecutionResultsResponseDto) {
        this.xrayImportResponseDto = importExecutionResultsResponseDto;
        return this;
    }

    @Override
    public void execute() {
        this.authenticationCommand.execute();
        try {
            XrayTestRepository.getInstance().importExecutionResults(xrayImportRequestDto);
        } catch (Exception e) {
            QtafFactory.getLogger().error(e.getMessage());
            e.printStackTrace();
        }
    }
}
