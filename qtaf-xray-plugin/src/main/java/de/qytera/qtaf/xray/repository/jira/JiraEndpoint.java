package de.qytera.qtaf.xray.repository.jira;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;

public interface JiraEndpoint {

    /**
     * Build the 'Authorization' header value for the Jira API.
     *
     * @return the authorization header value
     * @throws MissingConfigurationValueException if no Jira username or API token have been configured
     */
    default String getJiraAuthorizationHeaderValue() throws MissingConfigurationValueException {
        if (XrayConfigHelper.isXrayCloudService()) {
            return getHeaderCloud();
        }
        return getHeaderServer();
    }

    private static String getHeaderCloud() throws MissingConfigurationValueException {
        String username = XrayConfigHelper.getAuthenticationJiraUsername();
        if (username == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_JIRA_USERNAME,
                    QtafFactory.getConfiguration()
            );
        }
        String apiToken = XrayConfigHelper.getAuthenticationJiraAPIToken();
        if (apiToken == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_JIRA_API_TOKEN,
                    QtafFactory.getConfiguration());
        }
        String encoded = Base64Helper.encode(String.format("%s:%s", username, apiToken));
        return String.format("Basic %s", encoded);
    }

    private static String getHeaderServer() throws MissingConfigurationValueException {
        String bearerToken = XrayConfigHelper.getAuthenticationXrayBearerToken();
        if (bearerToken == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_XRAY_BEARER_TOKEN,
                    QtafFactory.getConfiguration());
        }
        return String.format("Bearer %s", bearerToken);
    }

}
