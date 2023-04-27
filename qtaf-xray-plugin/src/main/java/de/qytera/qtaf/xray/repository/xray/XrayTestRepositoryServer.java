package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.dto.response.steps.XrayServerTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class XrayTestRepositoryServer implements XrayTestRepository {

    @Override
    public Map<String, XrayTestStepResponseDto[]> getTestSteps(Collection<String> testIssueKeys) throws URISyntaxException, MissingConfigurationValueException {
        Map<String, XrayTestStepResponseDto[]> stepsByIssue = new HashMap<>();
        Map<String, String> issueIds = JiraIssueRepository.getInstance().getIssueIds(testIssueKeys);
        for (String testIssueKey : issueIds.keySet()) {
            RequestBuilder request = WebService.buildRequest(getURITestSteps(testIssueKey));
            request.getBuilder()
                    .header(HttpHeaders.AUTHORIZATION, getXrayAuthorizationHeaderValue())
                    .accept(MediaType.APPLICATION_JSON_TYPE);
            try (Response response = WebService.get(request)) {
                String responseData = response.readEntity(String.class);
                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    String reason = String.format(
                            "%d %s: %s",
                            response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            responseData
                    );
                    QtafFactory.getLogger().error(
                            String.format(
                                    "[QTAF Xray Plugin] Failed to retrieve test steps for issue %s: %s",
                                    testIssueKey,
                                    reason
                            )
                    );
                    continue;
                }
                XrayTestStepResponseDto[] steps = GsonFactory.getInstance().fromJson(
                        responseData,
                        XrayServerTestStepResponseDto[].class
                );
                stepsByIssue.put(testIssueKey, steps);
            }
        }
        return stepsByIssue;
    }


    private URI getURITestSteps(String testIssueKey) throws URISyntaxException {
        return new URI(String.format("%s/rest/raven/1.0/api/test/%s/step", getXrayURL(), testIssueKey));
    }

}
