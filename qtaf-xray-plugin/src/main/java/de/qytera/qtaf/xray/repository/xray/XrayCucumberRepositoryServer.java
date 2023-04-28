package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Export Tests from Xray Server as Cucumber Feature File
 */
public class XrayCucumberRepositoryServer implements XrayCucumberRepository, XrayEndpoint {

    @Override
    public String getFeatureFileDefinition(String[] testIDs) throws URISyntaxException, MissingConfigurationValueException {
        RequestBuilder request = WebService.buildRequest(getURIExportFeatureFiles(testIDs));
        request.getBuilder()
                .header(HttpHeaders.AUTHORIZATION, getXrayAuthorizationHeaderValue())
                .accept(MediaType.APPLICATION_JSON_TYPE);
        try (Response response = WebService.get(request)) {
            return response.readEntity(String.class);
        }
    }

    private URI getURIExportFeatureFiles(String[] testIDs) throws URISyntaxException {
        String keys = StringUtils.join(testIDs, ";");
        return new URI(
                String.format(
                        "%s/export/test?keys=%s",
                        getXrayURL(),
                        keys
                )
        );
    }
}
