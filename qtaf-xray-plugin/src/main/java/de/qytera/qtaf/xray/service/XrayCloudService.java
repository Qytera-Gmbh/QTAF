package de.qytera.qtaf.xray.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayRestPaths;
import de.qytera.qtaf.xray.dto.response.steps.XrayServerTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.entity.XrayAuthCredentials;
import de.qytera.qtaf.xray.events.QtafXrayEvents;
import de.qytera.qtaf.xray.log.XrayAuthenticationErrorLog;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Xray API Rest Client
 */
public class XrayCloudService extends AbstractXrayService {
    /**
     * Xray API URL
     */
    private final String XRAY_CLOUD_URL = XrayRestPaths.XRAY_CLOUD_API_V2;

    /**
     * API authentication path
     */
    private final String PATH_AUTHENTICATION = "/authenticate";

    /**
     * API import path
     */
    private final String PATH_IMPORT = "/import/execution";

    /**
     * Xray authentication credentials
     */
    private XrayAuthCredentials xrayAuthCredentials;

    /**
     * Singleton instance
     */
    private static XrayCloudService instance = null;

    /**
     * Factory method
     *
     * @return singleton instance
     */
    public static XrayCloudService getInstance() {
        if (instance == null) {
            instance = new XrayCloudService();
            instance.setAuthCredentials(new XrayAuthCredentials(
                    XrayConfigHelper.getAuthenticationXrayClientId(),
                    XrayConfigHelper.getAuthenticationXrayClientSecret()
            ));
        }

        return instance;
    }

    @Override
    public String getXrayURL() {
        return XRAY_CLOUD_URL;
    }

    @Override
    public String getImportPath() {
        return PATH_IMPORT;
    }

    @Override
    public String getIssueSearchPath() {
        return "/rest/api/3/search";
    }

    @Override
    public List<XrayTestStepResponseDto[]> getTestSteps(String... testKeys) {
        List<XrayTestStepResponseDto[]> responses = new ArrayList<>(testKeys.length);
        for (String testKey : testKeys) {

            String path = String.format("%s/rest/raven/1.0/api/test/%s/step", getXrayURL(), testKey);
            WebResource webResource = client.resource(path);
            QtafXrayEvents.webResourceAvailable.onNext(webResource);
            ClientResponse response = webResource
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", authorizationHeaderXray())
                    .get(ClientResponse.class);
            XrayServerTestStepResponseDto[] steps = GsonFactory.getInstance().fromJson(
                    response.getEntity(String.class),
                    XrayServerTestStepResponseDto[].class
            );
            responses.add(steps);
        }
        return responses;
    }

    /**
     * Set authentication credentials
     *
     * @param xrayAuthCredentials Authentication credentials
     */
    public void setAuthCredentials(XrayAuthCredentials xrayAuthCredentials) {
        this.xrayAuthCredentials = xrayAuthCredentials;
    }

    /**
     * Authenticate against Xray API
     *
     * @return Bearer token
     */
    public String authorizationHeaderXray() {
        // Check if bearer token is available, if not try to get one by clientId and clientSecret
        if (jwtToken == null) {
            // Build HTTP Request
            WebResource webResource = client.resource(getXrayURL() + PATH_AUTHENTICATION);

            // Build HTTP Request Payload
            Gson gson = GsonFactory.getInstance();
            String json = gson.toJson(xrayAuthCredentials);

            // Send HTTP Request
            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, json);

            QtafXrayEvents.authenticationResponseAvailable.onNext(response);

            // Check response
            if (response.getStatus() != 200) {
                String text = response.getEntity(String.class);
                QtafXrayEvents.authenticationSuccess.onNext(false);
                ErrorLog authErrorLog = new XrayAuthenticationErrorLog(new Exception(text))
                        .setErrorMessage(response.getStatusInfo().getReasonPhrase())
                        .setStatusCode(response.getStatus());
                errorLogs.addErrorLog(authErrorLog);
            } else {
                QtafXrayEvents.authenticationSuccess.onNext(true);
            }

            jwtToken = gson.fromJson(response.getEntity(String.class), String.class);
        }

        return String.format("Bearer %s", jwtToken);
    }

    @Override
    public String authorizationHeaderJira() {
        String username = XrayConfigHelper.getAuthenticationJiraUsername();
        String apiToken = XrayConfigHelper.getAuthenticationJiraAPIToken();
        String encoded = Base64Helper.encode(String.format("%s:%s", username, apiToken));
        return String.format("Basic %s", encoded);
    }

    /**
     * Get JWT token
     *
     * @return jwt token
     */
    public String getJwtToken() {
        return jwtToken;
    }
}
