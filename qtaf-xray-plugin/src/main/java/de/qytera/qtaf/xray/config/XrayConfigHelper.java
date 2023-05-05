package de.qytera.qtaf.xray.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.annotations.Test;

/**
 * Xray configuration helper
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XrayConfigHelper {
    /**
     * QTAF Configuration
     */
    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    // Keys
    public static final String ENABLED_SELECTOR = "xray.enabled";
    public static final String XRAY_SERVICE_SELECTOR = "xray.service";
    public static final String URL_XRAY_SELECTOR = "xray.url.xray";
    public static final String URL_JIRA_SELECTOR = "xray.url.jira";
    public static final String PROJECT_KEY = "xray.projectKey";
    public static final String AUTHENTICATION_XRAY_CLIENT_ID = "xray.authentication.xray.clientId";
    public static final String AUTHENTICATION_XRAY_CLIENT_SECRET = "xray.authentication.xray.clientSecret";
    public static final String AUTHENTICATION_XRAY_BEARER_TOKEN = "xray.authentication.xray.bearerToken";
    public static final String AUTHENTICATION_JIRA_USERNAME = "xray.authentication.jira.username";
    public static final String AUTHENTICATION_JIRA_API_TOKEN = "xray.authentication.jira.apiToken";
    public static final String RESULTS_UPLOAD_TEST_PLAN_KEY = "xray.resultsUpload.testPlanKey";
    public static final String RESULTS_UPLOAD_SCENARIO_REPORT_EVIDENCE = "xray.resultsUpload.scenarioReportEvidence";
    public static final String RESULTS_UPLOAD_SCENARIO_IMAGE_EVIDENCE = "xray.resultsUpload.scenarioImageEvidence";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PASSED = "xray.resultsUpload.customStatus.test.passed";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_TEST_FAILED = "xray.resultsUpload.customStatus.test.failed";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PENDING = "xray.resultsUpload.customStatus.test.pending";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_TEST_SKIPPED = "xray.resultsUpload.customStatus.test.skipped";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PASSED = "xray.resultsUpload.customStatus.step.passed";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_STEP_FAILED = "xray.resultsUpload.customStatus.step.failed";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PENDING = "xray.resultsUpload.customStatus.step.pending";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_STEP_SKIPPED = "xray.resultsUpload.customStatus.step.skipped";
    public static final String RESULTS_UPLOAD_CUSTOM_STATUS_STEP_UNDEFINED = "xray.resultsUpload.customStatus.step.undefined";
    public static final String RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE = "xray.resultsUpload.tests.info.steps.update";
    public static final String RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE = "xray.resultsUpload.tests.info.steps.merge";
    public static final String RESULTS_UPLOAD_TESTS_INFO_KEEP_JIRA_SUMMARY = "xray.resultsUpload.tests.info.keepJiraSummary";
    public static final String RESULTS_UPLOAD_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_NAME = "xray.resultsUpload.tests.iterations.parameters.maxLength.name";
    public static final String RESULTS_UPLOAD_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE = "xray.resultsUpload.tests.iterations.parameters.maxLength.value";

    // Values
    private static final String XRAY_SERVICE_CLOUD = "cloud";
    private static final String XRAY_SERVICE_SERVER = "server";

    /**
     * Returns whether the Xray plugin is enabled. Defaults to false if
     * the value has not been specified.
     *
     * @return whether the plugin is enabled or false by default
     */
    public static boolean isEnabled() {
        return CONFIG.getBoolean(ENABLED_SELECTOR, false);
    }

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
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PASSED);
    }

    /**
     * Get Xray status name for failed tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusFailed() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_TEST_FAILED);
    }

    /**
     * Get Xray status name for pending tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusPending() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_TEST_PENDING);
    }

    /**
     * Get Xray status name for skipped tests.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getTestStatusSkipped() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_TEST_SKIPPED);
    }

    /**
     * Get Xray status name for passed test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusPassed() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PASSED);
    }

    /**
     * Get Xray status name for failed test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusFailed() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_STEP_FAILED);
    }

    /**
     * Get Xray status name for pending test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusPending() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_STEP_PENDING);
    }

    /**
     * Get Xray status name for skipped test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusSkipped() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_STEP_SKIPPED);
    }

    /**
     * Get Xray status name for undefined test steps.
     *
     * @return the status name if configured, otherwise null
     */
    public static String getStepStatusUndefined() {
        return CONFIG.getString(RESULTS_UPLOAD_CUSTOM_STATUS_STEP_UNDEFINED);
    }

    /**
     * Get xray service (cloud | server)
     *
     * @return xray service (cloud | server)
     */
    public static String getXrayService() {
        String service = CONFIG.getString(XRAY_SERVICE_SELECTOR);
        if (service == null) {
            CONFIG.logMissingValue(XRAY_SERVICE_SELECTOR, XRAY_SERVICE_CLOUD);
            return XRAY_SERVICE_CLOUD;
        }
        if (service.equals(XRAY_SERVICE_SERVER) || service.equals(XRAY_SERVICE_CLOUD)) {
            return service;
        }
        return CONFIG.logUnknownValue(
                XRAY_SERVICE_SELECTOR,
                service,
                XRAY_SERVICE_CLOUD,
                XRAY_SERVICE_SERVER,
                XRAY_SERVICE_CLOUD
        );
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
    public static boolean isScenarioReportEvidenceEnabled() {
        Boolean value = CONFIG.getBoolean(RESULTS_UPLOAD_SCENARIO_REPORT_EVIDENCE);
        if (value == null) {
            return CONFIG.logMissingValue(RESULTS_UPLOAD_SCENARIO_REPORT_EVIDENCE, false);
        }
        return value;
    }

    /**
     * Check if images should be attached as evidence to Xray test execution import
     *
     * @return true if enabled, false otherwise
     */
    public static boolean isScenarioImageEvidenceEnabled() {
        Boolean value = CONFIG.getBoolean(RESULTS_UPLOAD_SCENARIO_IMAGE_EVIDENCE);
        if (value == null) {
            return CONFIG.logMissingValue(RESULTS_UPLOAD_SCENARIO_IMAGE_EVIDENCE, false);
        }
        return value;
    }

    /**
     * Returns the maximum allowed length of iteration parameter names.
     *
     * @return the maximum length or null if there is no maximum length
     */
    public static Integer getTestsIterationsParametersNameMaxLength() {
        return CONFIG.getInt(RESULTS_UPLOAD_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_NAME);
    }

    /**
     * Returns the maximum allowed length of iteration parameter values.
     *
     * @return the maximum length or null if there is no maximum length
     */
    public static Integer getTestsIterationsParametersValueMaxLength() {
        return CONFIG.getInt(RESULTS_UPLOAD_TESTS_ITERATIONS_PARAMETERS_MAX_LENGTH_VALUE);
    }

    /**
     * Returns whether test issue steps should be merged into a single step during results upload.
     *
     * @return whether they should be merged
     */
    public static boolean shouldResultsUploadTestsInfoStepsMerge() {
        Boolean value = CONFIG.getBoolean(RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE);
        if (value == null) {
            return CONFIG.logMissingValue(RESULTS_UPLOAD_TESTS_INFO_STEPS_MERGE, false);
        }
        return value;
    }

    /**
     * Returns whether test issue steps should be updated during results upload of tests.
     *
     * @return whether they should be updated
     */
    public static boolean shouldResultsUploadTestsInfoStepsUpdate() {
        Boolean value = CONFIG.getBoolean(RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE);
        if (value == null) {
            return CONFIG.logMissingValue(RESULTS_UPLOAD_TESTS_INFO_STEPS_UPDATE, false);
        }
        return value;
    }

    /**
     * Returns whether test issue summaries should be kept as they are in Jira instead of replacing them using
     * {@link Test#testName()} during results upload.
     *
     * @return whether summaries should be kept
     */
    public static boolean shouldResultsUploadTestsInfoKeepJiraSummary() {
        Boolean value = CONFIG.getBoolean(RESULTS_UPLOAD_TESTS_INFO_KEEP_JIRA_SUMMARY);
        if (value == null) {
            return CONFIG.logMissingValue(RESULTS_UPLOAD_TESTS_INFO_KEEP_JIRA_SUMMARY, false);
        }
        return value;
    }

    /**
     * Returns the project key.
     *
     * @return the configured project key
     */
    public static String getProjectKey() {
        return CONFIG.getString(PROJECT_KEY);
    }

    /**
     * Returns the test plan key to attach execution results to.
     *
     * @return the configured test plan key or null if no test plan has been defined
     */
    public static String getResultsUploadTestPlanKey() {
        return CONFIG.getString(RESULTS_UPLOAD_TEST_PLAN_KEY);
    }
}
