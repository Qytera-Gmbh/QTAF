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
    public static final String STATUS_TEST_PASSED_SELECTOR = "xray.status.test.passed";
    public static final String STATUS_TEST_FAILED_SELECTOR = "xray.status.test.failed";
    public static final String STATUS_TEST_PENDING_SELECTOR = "xray.status.test.pending";
    public static final String STATUS_TEST_SKIPPED_SELECTOR = "xray.status.test.skipped";
    public static final String STATUS_STEP_PASSED_SELECTOR = "xray.status.step.passed";
    public static final String STATUS_STEP_FAILED_SELECTOR = "xray.status.step.failed";
    public static final String STATUS_STEP_PENDING_SELECTOR = "xray.status.step.pending";
    public static final String STATUS_STEP_SKIPPED_SELECTOR = "xray.status.step.skipped";
    public static final String STATUS_STEP_UNDEFINED_SELECTOR = "xray.status.step.undefined";
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
    public static String getTestStatusPassed() {
        return config.getString(STATUS_TEST_PASSED_SELECTOR);
    }

    /**
     * Get Xray status name for failed tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusFailed() {
        return config.getString(STATUS_TEST_FAILED_SELECTOR);
    }

    /**
     * Get Xray status name for pending tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusPending() {
        return config.getString(STATUS_TEST_PENDING_SELECTOR);
    }

    /**
     * Get Xray status name for skipped tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusSkipped() {
        return config.getString(STATUS_TEST_SKIPPED_SELECTOR);
    }

    /**
     * Get Xray status name for passed test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusPassed() {
        return config.getString(STATUS_STEP_PASSED_SELECTOR);
    }

    /**
     * Get Xray status name for failed test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusFailed() {
        return config.getString(STATUS_STEP_FAILED_SELECTOR);
    }

    /**
     * Get Xray status name for pending test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusPending() {
        return config.getString(STATUS_STEP_PENDING_SELECTOR);
    }

    /**
     * Get Xray status name for skipped test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusSkipped() {
        return config.getString(STATUS_STEP_SKIPPED_SELECTOR);
    }

    /**
     * Get Xray status name for undefined test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusUndefined() {
        return config.getString(STATUS_STEP_UNDEFINED_SELECTOR);
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

    /**
     * Returns the maximum allowed length of iteration parameter names.
     *
     * @return the maximum length or null if there is no maximum length
     */
    public static Integer getIterationParameterNameMaxLength() {
        return config.getInt(RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_NAME);
    }

    /**
     * Returns the maximum allowed length of iteration parameter values.
     *
     * @return the maximum length or null if there is no maximum length
     */
    public static Integer getIterationParameterValueMaxLength() {
        return config.getInt(RESULTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE);
    }
}
