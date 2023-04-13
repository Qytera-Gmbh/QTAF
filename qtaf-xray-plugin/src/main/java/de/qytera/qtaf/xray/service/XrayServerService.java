package de.qytera.qtaf.xray.service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.response.steps.XrayServerTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.events.QtafXrayEvents;

import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class XrayServerService extends AbstractXrayService {

    /**
     * Singleton instance.
     */
    private static final XrayServerService INSTANCE = new XrayServerService();

    /**
     * Returns the singleton instance of the {@code XrayServerService}.
     *
     * @return the instance
     */
    public static XrayServerService getInstance() {
        return INSTANCE;
    }

    private XrayServerService() {
        // Hide constructor.
    }

    @Override
    public String getXrayURL() {
        return XrayConfigHelper.getServerUrl();
    }

    @Override
    public String getImportPath() {
        return "/rest/raven/1.0/import/execution";
    }

    @Override
    public String getIssueSearchPath() {
        return "/rest/api/2/search";
    }

    @Override
    public String authorizationHeaderXray() {
        String bearerToken = XrayConfigHelper.getAuthenticationXrayBearerToken();
        if (bearerToken == null) {
            throw new MissingConfigurationValueException(XrayConfigHelper.AUTHENTICATION_XRAY_BEARER_TOKEN);
        }
        return String.format("Bearer %s", bearerToken);
    }

    @Override
    public String authorizationHeaderJira() {
        String bearerToken = XrayConfigHelper.getAuthenticationJiraAPIToken();
        if (bearerToken == null) {
            throw new MissingConfigurationValueException(XrayConfigHelper.AUTHENTICATION_JIRA_API_TOKEN);
        }
        return String.format("Bearer %s", bearerToken);
    }

    private XrayServerTestStepResponseDto[] parseTestStepResponse(String testKey, ClientResponse response) {
        if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            return GsonFactory.getInstance().fromJson(
                    response.getEntity(String.class),
                    XrayServerTestStepResponseDto[].class
            );
        }
        String reason = String.format(
                "%d %s",
                response.getStatus(),
                response.getStatusInfo().getReasonPhrase()
        );
        String message = String.format("Failed to retrieve test steps for issue %s: %s", testKey, reason);
        logger.error(message);
        errorLogs.addErrorLog(new IllegalStateException(message));
        return new XrayServerTestStepResponseDto[0];
    }

    @Override
    public Map<String, XrayTestStepResponseDto[]> getTestSteps(Collection<String> testIssueKeys) {
        Map<String, XrayTestStepResponseDto[]> stepsByIssue = new HashMap<>();
        Map<String, String> issueIds = getIssueIds(testIssueKeys);
        issueIds.forEach((testKey, issueId) -> {
            String path = String.format("%s/rest/raven/1.0/api/test/%s/step", getXrayURL(), testKey);
            WebResource webResource = client.resource(path);
            QtafXrayEvents.webResourceAvailable.onNext(webResource);
            ClientResponse response = webResource
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", authorizationHeaderXray())
                    .get(ClientResponse.class);
            XrayTestStepResponseDto[] steps = parseTestStepResponse(testKey, response);
            stepsByIssue.put(testKey, steps);
        });
        return stepsByIssue;
    }
}
