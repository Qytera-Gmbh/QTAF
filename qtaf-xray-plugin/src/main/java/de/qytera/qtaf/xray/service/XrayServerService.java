package de.qytera.qtaf.xray.service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.response.steps.XrayServerTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.events.QtafXrayEvents;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class XrayServerService extends AbstractXrayService {

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
        return String.format("Bearer %s", XrayConfigHelper.getAuthenticationXrayBearerToken());
    }

    @Override
    public String authorizationHeaderJira() {
        return String.format("Bearer %s", XrayConfigHelper.getAuthenticationJiraAPIToken());
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
     * Singleton instance
     */
    private static XrayServerService instance = null;

    /**
     * Factory method
     *
     * @return singleton instance
     */
    public static XrayServerService getInstance() {
        if (instance == null) {
            instance = new XrayServerService();
        }

        return instance;
    }
}
