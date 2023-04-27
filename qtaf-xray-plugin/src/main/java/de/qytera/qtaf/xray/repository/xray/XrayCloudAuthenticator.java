package de.qytera.qtaf.xray.repository.xray;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.config.XrayRestPaths;
import de.qytera.qtaf.xray.events.XrayEvents;
import de.qytera.qtaf.xray.log.XrayAuthenticationErrorLog;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * A utility class for authenticating to Xray Cloud.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XrayCloudAuthenticator {

    private static String JWT_TOKEN;

    /**
     * Return the authorization header value based on the configured Xray Cloud credentials.
     *
     * @return the authorization header value
     * @throws MissingConfigurationValueException if the Xray Cloud credentials have not been configured
     */
    public static String getXrayAuthorizationHeaderValue() throws MissingConfigurationValueException {
        if (JWT_TOKEN == null) {
            try {
                RequestBuilder request = WebService.buildRequest(new URI(XrayRestPaths.XRAY_CLOUD_API_V2 + "/authenticate"));
                try (Response response = WebService.post(request, GsonFactory.getInstance().toJsonTree(getAuthenticationBody()))) {
                    String responseData = response.readEntity(String.class);
                    XrayEvents.authenticationResponseAvailable.onNext(response);
                    if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                        XrayEvents.authenticationSuccess.onNext(false);
                        ErrorLog authErrorLog = new XrayAuthenticationErrorLog(new Exception(responseData))
                                .setErrorMessage(response.getStatusInfo().getReasonPhrase())
                                .setStatusCode(response.getStatus());
                        ErrorLogCollection.getInstance().addErrorLog(authErrorLog);
                    } else {
                        XrayEvents.authenticationSuccess.onNext(true);
                        JWT_TOKEN = responseData.replaceAll("(^\")|(\"$)", "");
                    }
                }
            } catch (URISyntaxException exception) {
                XrayEvents.authenticationSuccess.onNext(false);
                ErrorLog authErrorLog = new ErrorLog(exception);
                ErrorLogCollection.getInstance().addErrorLog(authErrorLog);
            }
        }
        return String.format("Bearer %s", JWT_TOKEN);
    }

    private static JsonElement getAuthenticationBody() throws MissingConfigurationValueException {
        String clientId = XrayConfigHelper.getAuthenticationXrayClientId();
        if (clientId == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_XRAY_CLIENT_ID,
                    QtafFactory.getConfiguration()
            );
        }
        String clientSecret = XrayConfigHelper.getAuthenticationXrayClientSecret();
        if (clientSecret == null) {
            throw new MissingConfigurationValueException(
                    XrayConfigHelper.AUTHENTICATION_XRAY_CLIENT_SECRET,
                    QtafFactory.getConfiguration()
            );
        }
        Map<String, String> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret
        );
        return GsonFactory.getInstance().toJsonTree(body);
    }


}
