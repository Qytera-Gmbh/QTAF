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
    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    // Keys
    public static final String XRAY_SERVICE_SELECTOR = "xray.service";
    public static final String URL_XRAY_SELECTOR = "xray.url.xray";
    public static final String URL_JIRA_SELECTOR = "xray.url.jira";
    public static final String AUTHENTICATION_XRAY_CLIENT_ID = "xray.authentication.xray.clientId";
    public static final String AUTHENTICATION_XRAY_CLIENT_SECRET = "xray.authentication.xray.clientSecret";
    public static final String AUTHENTICATION_XRAY_BEARER_TOKEN = "xray.authentication.xray.bearerToken";
    public static final String AUTHENTICATION_JIRA_USERNAME = "xray.authentication.jira.username";
    public static final String AUTHENTICATION_JIRA_API_TOKEN = "xray.authentication.jira.apiToken";
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
    public static final String RESULTS_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_NAME = "xray.results.tests.iterations.parameters.maxLength.name";
    public static final String RESULTS_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE = "xray.results.tests.iterations.parameters.maxLength.value";
    public static final String RESULTS_TESTS_INFO_STEPS_UPDATE_ON_SINGLE_ITERATION = "xray.results.tests.info.steps.updateOnSingleIteration";
    public static final String RESULTS_TESTS_INFO_STEPS_MERGE_ON_MULTIPLE_ITERATIONS = "xray.results.tests.info.steps.mergeOnMultipleIterations";

    private XrayConfigHelper() {
        // Hide constructor.
    }

    // Values
    private static String XRAY_SERVICE_CLOUD = "cloud";
    private static String XRAY_SERVICE_SERVER = "server";

    /**
     * Returns the bearer token for authenticating against Xray server.
     *
     * @return the bearer token or null if undefined
     */
    public static String getAuthenticationXrayBearerToken() {
        return CONFIG.getString(AUTHENTICATION_XRAY_BEARER_TOKEN);
    }

    /**
     * Returns the client id for authenticating against Xray cloud.
     *
     * @return the client id or null if undefined
     */
    public static String getAuthenticationXrayClientId() {
        return CONFIG.getString(AUTHENTICATION_XRAY_CLIENT_ID);
    }

    /**
     * Returns the client secret for authenticating against Xray cloud.
     *
     * @return the client secret or null if undefined
     */
    public static String getAuthenticationXrayClientSecret() {
        return CONFIG.getString(AUTHENTICATION_XRAY_CLIENT_SECRET);
    }

    /**
     * Returns the username for authenticating against the Jira instance's REST API.
     *
     * @return the username or null if undefined
     */
    public static String getAuthenticationJiraUsername() {
        return CONFIG.getString(AUTHENTICATION_JIRA_USERNAME);
    }

    /**
     * Returns the API token used during authentication against the Jira instance's REST API.
     *
     * @return the API token or null if undefined
     */
    public static String getAuthenticationJiraAPIToken() {
        return CONFIG.getString(AUTHENTICATION_JIRA_API_TOKEN);
    }

    /**
     * Get Xray server URL
     *
     * @return Xray server URL
     */
    public static String getServerUrl() {
        return CONFIG.getString(URL_XRAY_SELECTOR);
    }

    /**
     * Returns the URL of the underlying Jira instance.
     *
     * @return the Jira URL or null if undefined
     */
    public static String getJiraUrl() {
        return CONFIG.getString(URL_JIRA_SELECTOR);
    }

    /**
     * Get Xray status name for passed tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusPassed() {
        return CONFIG.getString(STATUS_TEST_PASSED_SELECTOR);
    }

    /**
     * Get Xray status name for failed tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusFailed() {
        return CONFIG.getString(STATUS_TEST_FAILED_SELECTOR);
    }

    /**
     * Get Xray status name for pending tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusPending() {
        return CONFIG.getString(STATUS_TEST_PENDING_SELECTOR);
    }

    /**
     * Get Xray status name for skipped tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusSkipped() {
        return CONFIG.getString(STATUS_TEST_SKIPPED_SELECTOR);
    }

    /**
     * Get Xray status name for passed test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusPassed() {
        return CONFIG.getString(STATUS_STEP_PASSED_SELECTOR);
    }

    /**
     * Get Xray status name for failed test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusFailed() {
        return CONFIG.getString(STATUS_STEP_FAILED_SELECTOR);
    }

    /**
     * Get Xray status name for pending test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusPending() {
        return CONFIG.getString(STATUS_STEP_PENDING_SELECTOR);
    }

    /**
     * Get Xray status name for skipped test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusSkipped() {
        return CONFIG.getString(STATUS_STEP_SKIPPED_SELECTOR);
    }

    /**
     * Get Xray status name for undefined test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusUndefined() {
        return CONFIG.getString(STATUS_STEP_UNDEFINED_SELECTOR);
    }

    /**
     * Get xray service (cloud | server)
     *
     * @return xray service (cloud | server)
     */
    public static String getXrayService() {
        String service = CONFIG.getString(XRAY_SERVICE_SELECTOR);

        if (service == null || !service.equals(XRAY_SERVICE_SERVER)) {
            return XRAY_SERVICE_CLOUD;
        }

        return service;
    }

    /**
     * Check if Xray server is enabled
     *
     * @return true if enabled, false otherwise
     */
    public static boolean isXrayServerService() {
        return getXrayService().equals(XRAY_SERVICE_SERVER);
    }

    /**
     * Check if Xray cloud is enabled
     *
     * @return true if enabled, false otherwise
     */
    public static boolean isXrayCloudService() {
        return getXrayService().equals(XRAY_SERVICE_CLOUD);
    }

    /**
     * Check if HTML report should be attached as evidence to Xray test execution import
     *
     * @return true if enabled, false otherwise
     */
    public static Boolean isScenarioReportEvidenceEnabled() {
        return CONFIG.getBoolean(SCENARIO_REPORT_EVIDENCE);
    }

    /**
     * Check if images should be attached as evidence to Xray test execution import
     *
     * @return true if enabled, false otherwise
     */
    public static Boolean isScenarioImageEvidenceEnabled() {
        return CONFIG.getBoolean(SCENARIO_IMAGE_EVIDENCE);
    }

    /**
     * Returns the maximum allowed length of iteration parameter names.
     *
     * @return the maximum length or null if there is no maximum length
     */
    public static Integer getTestsIterationsParametersNameMaxLength() {
        return CONFIG.getInt(RESULTS_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_NAME);
    }

    /**
     * Returns the maximum allowed length of iteration parameter values.
     *
     * @return the maximum length or null if there is no maximum length
     */
    public static Integer getTestsIterationsParametersValueMaxLength() {
        return CONFIG.getInt(RESULTS_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE);
    }

    /**
     * Returns whether test issue steps should be updated during results upload of tests consisting of single
     * iterations.
     *
     * @return whether they should be updated or null if not defined
     */
    public static Boolean getResultsTestsInfoStepsUpdateOnSingleIteration() {
        return CONFIG.getBoolean(RESULTS_TESTS_INFO_STEPS_UPDATE_ON_SINGLE_ITERATION);
    }

    /**
     * Returns whether test issue steps should be merged during results upload of tests consisting of multiple
     * iterations.
     *
     * @return whether they should be updated or null if not defined
     */
    public static Boolean getResultsTestsInfoStepsMergeOnMultipleIterations() {
        return CONFIG.getBoolean(RESULTS_TESTS_INFO_STEPS_MERGE_ON_MULTIPLE_ITERATIONS);
    }
}
