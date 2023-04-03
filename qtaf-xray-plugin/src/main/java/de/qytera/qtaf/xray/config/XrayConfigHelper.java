package de.qytera.qtaf.xray.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;

/**
 * Xray configuration helper
 */
public class XrayConfigHelper {
    /**
     * QTAF Configuration
     */
    private static ConfigMap config = QtafFactory.getConfiguration();

    // Keys
    public static final String XRAY_SERVICE_SELECTOR = "xray.service";
    private static String XRAY_SERVER_URL_SELECTOR = "xray.url";
    private static String BEARER_TOKEN_SELECTOR = "xray.authentication.bearerToken";
    private static String CLIENT_ID_SELECTOR = "xray.authentication.clientId";
    private static String CLIENT_SECRET_SELECTOR = "xray.authentication.clientSecret";
    private static String SCENARIO_REPORT_EVIDENCE = "xray.scenarioReportEvidence";
    private static String SCENARIO_IMAGE_EVIDENCE = "xray.scenarioImageEvidence";
    public static final String STATUS_PASSED_SELECTOR = "xray.status.passed";
    public static final String STATUS_FAILED_SELECTOR = "xray.status.failed";
    public static final String RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_NAME = "xray.results.iterations.parameters.maxLength.name";
    public static final String RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE = "xray.results.iterations.parameters.maxLength.value";

    // Values
    private static String XRAY_SERVICE_CLOUD = "cloud";
    private static String XRAY_SERVICE_SERVER = "server";

    /**
     * Get bearer token
     * @return  bearer token
     */
    public static String getBearerToken() {
        return config.getString(BEARER_TOKEN_SELECTOR);
    }

    /**
     * Get client id
     * @return  client id
     */
    public static String getClientId() {
        return config.getString(CLIENT_ID_SELECTOR);
    }

    /**
     * Get client secret
     * @return  client secret
     */
    public static String getClientSecret() {
        return config.getString(CLIENT_SECRET_SELECTOR);
    }

    /**
     * Get Xray server URL
     * @return  Xray server URL
     */
    public static String getServerUrl() {
        return config.getString(XRAY_SERVER_URL_SELECTOR);
    }

    /**
     * Get Xray status name for passed tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStatusPassed() {
        return config.getString(STATUS_PASSED_SELECTOR);
    }

    /**
     * Get Xray status name for failed tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStatusFailed() {
        return config.getString(STATUS_FAILED_SELECTOR);
    }

    /**
     * Get xray service (cloud | server)
     * @return  xray service (cloud | server)
     */
    public static String getXrayService() {
        String service = config.getString(XRAY_SERVICE_SELECTOR);

        if (service == null || !service.equals(XRAY_SERVICE_SERVER)) {
            return XRAY_SERVICE_CLOUD;
        }

        return service;
    }

    /**
     * Check if Xray server is enabled
     * @return  true if enabled, false otherwise
     */
    public static boolean isXrayServerService() {
        return getXrayService().equals(XRAY_SERVICE_SERVER);
    }

    /**
     * Check if Xray cloud is enabled
     * @return  true if enabled, false otherwise
     */
    public static boolean isXrayCloudService() {
        return getXrayService().equals(XRAY_SERVICE_CLOUD);
    }

    /**
     * Check if HTML report should be attached as evidence to Xray test execution import
     * @return  true if enabled, false otherwise
     */
    public static boolean isScenarioReportEvidenceEnabled() {
        return config.getBoolean(SCENARIO_REPORT_EVIDENCE);
    }

    /**
     * Check if images should be attached as evidence to Xray test execution import
     * @return  true if enabled, false otherwise
     */
    public static boolean isScenarioImageEvidenceEnabled() {
        return config.getBoolean(SCENARIO_IMAGE_EVIDENCE);
    }
}
