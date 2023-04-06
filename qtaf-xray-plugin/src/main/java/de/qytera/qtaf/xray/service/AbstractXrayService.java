package de.qytera.qtaf.xray.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayCloudImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayServerImportResponseDto;
import de.qytera.qtaf.xray.events.QtafXrayEvents;

import javax.ws.rs.core.MediaType;

public abstract class AbstractXrayService {

    /**
     * JWT auth token that was fetched from the API
     */
    protected String jwtToken = null;

    /**
     * Jersey HTTP Client
     */
    protected final Client client = new Client();

    /**
     * Error logger
     */
    protected final ErrorLogCollection errorLogs = ErrorLogCollection.getInstance();

    /**
     * Get Xray URL
     *
     * @return Xray URL
     */
    public abstract String getXrayURL();

    /**
     * Get Xray REST Import Path
     *
     * @return Xray REST Import Path
     */
    public abstract String getImportPath();

    /**
     * Authentication against Xray PAI
     *
     * @return Bearer Token
     */
    public abstract String authenticate();

    /**
     * Get JWT token
     *
     * @return jwt token
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Set jwtToken
     *
     * @param jwtToken JwtToken
     * @return this
     */
    public AbstractXrayService setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    /**
     * Import a test execution
     *
     * @param xrayImportDTO Test execution DTO
     * @return Test execution response DTO
     */
    public XrayImportResponseDto importExecution(
            XrayImportRequestDto xrayImportDTO
    ) {
        // Create HTTP client
        WebResource webResource = client.resource(getXrayURL() + getImportPath());
        QtafXrayEvents.webResourceAvailable.onNext(webResource);

        // Create JSON string that gets uplaoded
        Gson gson = GsonFactory.getInstance();
        String json = gson.toJson(xrayImportDTO);

        // Send request
        ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getJwtToken())
                .post(ClientResponse.class, json);

        // Check response
        if (response.getStatus() == 200) {
            QtafXrayEvents.uploadStatus.onNext(true);
        } else {
            QtafXrayEvents.uploadStatus.onNext(false);
        }

        QtafXrayEvents.uploadResponseAvailable.onNext(response);

        // Create response entity
        XrayImportResponseDto responseDto = null;

        if (XrayConfigHelper.isXrayServerService()) {
            responseDto = gson.fromJson(
                    response.getEntity(String.class),
                    XrayServerImportResponseDto.class
            );
        } else if (XrayConfigHelper.isXrayCloudService()) {
            responseDto = gson.fromJson(
                    response.getEntity(String.class),
                    XrayCloudImportResponseDto.class
            );
        }

        return responseDto;
    }

}
