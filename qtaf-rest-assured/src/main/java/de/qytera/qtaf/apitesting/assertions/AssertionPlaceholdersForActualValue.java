package de.qytera.qtaf.apitesting.assertions;

/**
 * This class contains an enum which is used as a placeholder
 * for later calculation of the actual value in assertion log messages.
 */

public class AssertionPlaceholdersForActualValue {
    public enum Type{
        UNKNOWN_ACTUAL_VALUE_forBodyAssertion,
        UNKNOWN_ACTUAL_VALUE_forStatusCodeAssertion,
        UNKNOWN_ACTUAL_VALUE_forTimeAssertion,
    }
}
