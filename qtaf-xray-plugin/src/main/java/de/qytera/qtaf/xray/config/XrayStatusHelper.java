package de.qytera.qtaf.xray.config;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;

/**
 * Utility class for converting QTAF statuses to Xray statuses.
 */
public class XrayStatusHelper {

    private XrayStatusHelper() {
        // Utility class, does not require a constructor.
    }

    /**
     * Returns the Xray status text of the test status, e.g. {@code "FAIL"} or {@code "SUCCESS"}.
     * Depends on the Xray instance configuration.
     *
     * @param status the test status
     * @return the status text
     */
    public static String statusToText(TestScenarioLogCollection.Status status) {
        switch (status) {
            case SUCCESS -> {
                return getTestStatusPassed();
            }
            case FAILURE -> {
                return getTestStatusFailed();
            }
            case PENDING -> {
                return getTestStatusPending();
            }
            case SKIPPED -> {
                return getTestStatusSkipped();
            }
            default -> throw new IllegalArgumentException(String.format("Unknown text for status %s", status));
        }
    }

    private static String getTestStatusPassed() {
        String status = XrayConfigHelper.getTestStatusPassed();
        if (status != null) {
            return status;
        }
        return XrayConfigHelper.isXrayCloudService() ? "PASSED" : "PASS";
    }

    private static String getTestStatusFailed() {
        String status = XrayConfigHelper.getTestStatusFailed();
        if (status != null) {
            return status;
        }
        return XrayConfigHelper.isXrayCloudService() ? "FAILED" : "FAIL";
    }

    private static String getTestStatusPending() {
        String status = XrayConfigHelper.getTestStatusPending();
        if (status != null) {
            return status;
        }
        return "EXECUTING";
    }

    private static String getTestStatusSkipped() {
        String status = XrayConfigHelper.getTestStatusSkipped();
        if (status != null) {
            return status;
        }
        return "TODO";
    }


    /**
     * Returns the Xray status text of the test step status, e.g. {@code "PASSED"} or {@code "FAIL"}.
     * Depends on the Xray instance configuration.
     *
     * @param status the test step status
     * @return the status text
     */
    public static String statusToText(StepInformationLogMessage.Status status) {
        switch (status) {
            case PASS -> {
                return getStepStatusPassed();
            }
            case ERROR -> {
                return getStepStatusFailed();
            }
            case PENDING -> {
                return getStepStatusPending();
            }
            case SKIPPED -> {
                return getStepStatusSkipped();
            }
            case UNDEFINED -> {
                return getStepStatusUndefined();
            }
            default -> throw new IllegalArgumentException(String.format("Unknown text for status %s", status));
        }
    }

    private static String getStepStatusPassed() {
        String status = XrayConfigHelper.getStepStatusPassed();
        if (status != null) {
            return status;
        }
        return XrayConfigHelper.isXrayCloudService() ? "PASSED" : "PASS";
    }

    private static String getStepStatusFailed() {
        String status = XrayConfigHelper.getStepStatusFailed();
        if (status != null) {
            return status;
        }
        return XrayConfigHelper.isXrayCloudService() ? "FAILED" : "FAIL";
    }

    private static String getStepStatusPending() {
        String status = XrayConfigHelper.getStepStatusPending();
        return status != null ? status : "EXECUTING";
    }

    private static String getStepStatusSkipped() {
        String status = XrayConfigHelper.getStepStatusSkipped();
        return status != null ? status : "TODO";
    }

    private static String getStepStatusUndefined() {
        String status = XrayConfigHelper.getStepStatusUndefined();
        return status != null ? status : "TODO";
    }

}
