package de.qytera.qtaf.core.config.exception;


/**
 * Exception thrown when a configuration value has not been set.
 */
public class MissingConfigurationValueException extends RuntimeException {

    /**
     * Construct a new exception indicating that no value was provided for the given key.
     *
     * @param key the key whose value is missing
     */
    public MissingConfigurationValueException(String key) {
        super(String.format("failed to find non-null value in configuration for key: '%s'", key));
    }
}
