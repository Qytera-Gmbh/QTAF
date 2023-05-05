package de.qytera.qtaf.xray.repository.jira;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.response.jira.ProjectDto;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * A class for interacting with Jira projects, such as retrieving project administration data.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JiraProjectRepository implements JiraEndpoint {

    private static final JiraProjectRepository INSTANCE = new JiraProjectRepository();

    /**
     * Retrieve a {@link JiraProjectRepository} instance.
     *
     * @return an instance to work with
     */
    public static JiraProjectRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the project details for a project.
     *
     * @param projectIdOrKey the project ID or project key (case-sensitive)
     * @return the project details
     */
    public ProjectDto getProject(String projectIdOrKey) throws URISyntaxException, MissingConfigurationValueException {
        RequestBuilder request = WebService.buildRequest(getProjectPathURI(projectIdOrKey));
        request.getBuilder()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, getJiraAuthorizationHeaderValue());
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
                                "[QTAF Xray Plugin] Failed to get project details for project '%s': %s",
                                projectIdOrKey,
                                reason
                        )
                );
                return null;
            }
            return GsonFactory.getInstance().fromJson(
                    responseData,
                    ProjectDto.class
            );
        }
    }

    private static URI getProjectPathURI(String projectIdOrKey) throws URISyntaxException {
        if (XrayConfigHelper.isXrayCloudService()) {
            return new URI(String.format("%s/rest/api/3/project/%s", XrayConfigHelper.getJiraUrl(), projectIdOrKey));
        }
        return new URI(String.format("%s/rest/api/2/project/%s", XrayConfigHelper.getJiraUrl(), projectIdOrKey));
    }

}
