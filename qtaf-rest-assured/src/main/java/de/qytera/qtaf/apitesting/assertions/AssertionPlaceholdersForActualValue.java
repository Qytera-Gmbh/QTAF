package de.qytera.qtaf.apitesting.assertions;

/**
 * This class contains an enum which is used as a placeholder
 * for later calculation of the actual value in assertion log messages.
 */
public class AssertionPlaceholdersForActualValue {
    /**
     * Types.
     */
    public enum Type{
        /**
         * Unknown actual value for body assertion.
         */
        UNKNOWN_ACTUAL_VALUE_forBodyAssertion,

        /**
         * Unknown actual value for status code assertion.
         */
        UNKNOWN_ACTUAL_VALUE_forStatusCodeAssertion,

        /**
         * Unknown actual value for time assertion.
         */
        UNKNOWN_ACTUAL_VALUE_forTimeAssertion,
    }
}
