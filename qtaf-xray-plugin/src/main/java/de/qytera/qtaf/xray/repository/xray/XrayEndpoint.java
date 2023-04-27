package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayRestPaths;

import java.net.URISyntaxException;

public interface XrayEndpoint {

    /**
     * Build the 'Authorization' header value for the Xray API.
     *
     * @return the authorization header value
     * @throws MissingConfigurationValueException if no Jira username or API token have been configured
     */
    default String getXrayAuthorizationHeaderValue() throws MissingConfigurationValueException, URISyntaxException {
        if (XrayConfigHelper.isXrayCloudService()) {
            return getHeaderCloud();
        }
        return getHeaderServer();
    }

    /**
     * Return the base URL for all Xray endpoints.
     *
     * @return the base URL
     */
    default String getXrayURL() {
        if (XrayConfigHelper.isXrayCloudService()) {
            return XrayRestPaths.XRAY_CLOUD_API_V2;
        }
        return XrayConfigHelper.getServerUrl();
    }

    private static String getHeaderCloud() throws MissingConfigurationValueException, URISyntaxException {
        return XrayCloudAuthenticator.getXrayAuthorizationHeaderValue();
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
