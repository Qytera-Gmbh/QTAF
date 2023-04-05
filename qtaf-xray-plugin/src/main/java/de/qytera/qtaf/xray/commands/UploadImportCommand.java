package de.qytera.qtaf.xray.commands;

import de.qytera.qtaf.core.patterns.Command;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import de.qytera.qtaf.xray.service.AbstractXrayService;
import de.qytera.qtaf.xray.service.XrayServiceFactory;

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
    private XrayImportRequestDto xrayImportRequestDto;

    /**
     * Payload sent by the Xray API
     */
    private XrayImportResponseDto xrayImportResponseDto;

    /**
     * Get xrayImportRequestDto
     *
     * @return xrayImportRequestDto XrayImportRequestDto
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
    public UploadImportCommand setXrayImportRequestDto(XrayImportRequestDto xrayImportRequestDto) {
        this.xrayImportRequestDto = xrayImportRequestDto;
        return this;
    }

    /**
     * Get xrayImportResponseDto
     *
     * @return xrayImportResponseDto XrayImportResponseDto
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
    public UploadImportCommand setXrayImportResponseDto(XrayImportResponseDto xrayImportResponseDto) {
        this.xrayImportResponseDto = xrayImportResponseDto;
        return this;
    }

    @Override
    public void execute() {
        // Authenticate
        this.authenticationCommand.execute();

        // Get service instance
        AbstractXrayService xrayService = XrayServiceFactory.getInstance();

        // Upload test execution data and store results
        this.setXrayImportResponseDto(xrayService.importExecution(xrayImportRequestDto));
    }
}
