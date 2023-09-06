package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.request.xray.ImportExecutionResultsRequestDto;
import de.qytera.qtaf.xray.dto.response.steps.XrayTestStepResponseDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseCloudDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseServerDto;
import de.qytera.qtaf.xray.events.XrayEvents;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

/**
 * A class for interacting with Xray issues, such as retrieving test steps or creating test execution issues from
 * local test results.
 */
public interface XrayTestRepository extends XrayEndpoint {

    /**
     * Get an {@link XrayTestRepository} instance. The decision whether a cloud or server instance will be returned
     * depends on the configured {@link XrayConfigHelper#XRAY_SERVICE} value.
     *
     * @return the instance
     */
    static XrayTestRepository getInstance() {
        return XrayConfigHelper.isXrayCloudService() ? new XrayTestRepositoryCloud() : new XrayTestRepositoryServer();
    }

    private URI getURIImportExecutionResults() throws URISyntaxException {
        String endpoint;
        if (XrayConfigHelper.isXrayCloudService()) {
            endpoint = String.format("%s/import/execution", getXrayURL());
        } else {
            endpoint = String.format("%s/rest/raven/1.0/import/execution", getXrayURL());
        }
        return new URI(endpoint);
    }

    /**
     * Import test execution results using the Xray JSON format.
     *
     * @param body the import execution results DTO
     * @return the import execution response DTO
     * @throws URISyntaxException                 if any URLs used during import are invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results">Import Execution Results (Xray Server)</a>
     * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Import+Execution+Results+-+REST">Import Execution Results (Xray Cloud)</a>
     */
    default ImportExecutionResultsResponseDto importExecutionResults(ImportExecutionResultsRequestDto body) throws URISyntaxException, MissingConfigurationValueException {
        RequestBuilder request = WebService.buildRequest(getURIImportExecutionResults());
        request.getBuilder()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, getXrayAuthorizationHeaderValue());
        try (Response response = WebService.post(request, Entity.json(body))) {
            String responseData = response.readEntity(String.class);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                XrayEvents.uploadStatus.onNext(true);
            } else {
                XrayEvents.uploadStatus.onNext(false);
                String reason = String.format(
                        "%d %s: %s",
                        response.getStatus(),
                        response.getStatusInfo().getReasonPhrase(),
                        responseData
                );
                QtafFactory.getLogger().error(
                        String.format("[QTAF Xray Plugin] Failed to import test execution: %s", reason)
                );
            }
            XrayEvents.uploadResponseAvailable.onNext(response);
            if (XrayConfigHelper.isXrayServerService()) {
                return GsonFactory.getInstance().fromJson(responseData, ImportExecutionResultsResponseServerDto.class);
            } else if (XrayConfigHelper.isXrayCloudService()) {
                return GsonFactory.getInstance().fromJson(responseData, ImportExecutionResultsResponseCloudDto.class);
            }
        }
        return null;
    }

    /**
     * Retrieve all test steps for the given test issues.
     *
     * @param testIssueKeys the test issues to retrieve the steps for
     * @return a mapping of test issue keys to their steps
     * @throws URISyntaxException                 if any URLs used during step retrieval are invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    Map<String, XrayTestStepResponseDto[]> getTestSteps(Collection<String> testIssueKeys) throws URISyntaxException, MissingConfigurationValueException;

}
