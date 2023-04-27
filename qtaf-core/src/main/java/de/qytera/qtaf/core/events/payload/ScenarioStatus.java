package de.qytera.qtaf.core.events.payload;

/**
 * All statuses a test scenario can assume.
 */
public enum ScenarioStatus {
    /**
     * A scenario that is still pending execution.
     */
    PENDING,
    /**
     * The scenario run was successful.
     */
    SUCCESS,
    /**
     * The scenario run resulted in errors.
     */
    FAILURE,
    /**
     * The scenario was skipped.
     */
    SKIPPED,
}