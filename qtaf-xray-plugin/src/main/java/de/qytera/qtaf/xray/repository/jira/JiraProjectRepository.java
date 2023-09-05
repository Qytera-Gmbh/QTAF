package de.qytera.qtaf.xray.repository.jira;

import com.google.gson.reflect.TypeToken;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.ProjectCloudDto;
import de.qytera.qtaf.xray.dto.jira.ProjectDto;
import de.qytera.qtaf.xray.dto.jira.ProjectServerDto;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
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
     * @param <P>            the project type
     * @return the project details
     * @throws URISyntaxException                 if any URLs used project retrieval are invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    public <P extends ProjectDto<?, ?, ?>> P getProject(String projectIdOrKey) throws URISyntaxException, MissingConfigurationValueException {
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
            Type type;
            if (XrayConfigHelper.isXrayCloudService()) {
                type = TypeToken.get(ProjectCloudDto.class).getType();
            } else {
                type = TypeToken.get(ProjectServerDto.class).getType();
            }
            return GsonFactory.getInstance().fromJson(
                    responseData,
                    type
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
