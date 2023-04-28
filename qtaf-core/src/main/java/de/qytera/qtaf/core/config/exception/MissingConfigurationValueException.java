package de.qytera.qtaf.core.config.exception;


import de.qytera.qtaf.core.config.entity.ConfigMap;

/**
 * Exception thrown when a configuration value has not been set.
 */
public class MissingConfigurationValueException extends Exception {

    /**
     * Construct a new exception indicating that no value was provided for the given key.
     *
     * @param key           the key whose value is missing
     * @param configuration the configuration which is missing the value
     */
    public MissingConfigurationValueException(String key, ConfigMap configuration) {
        super(
                String.format(
                        "failed to find non-null value in configuration %s for key: '%s'",
                        configuration.getLocation(),
                        key
                )
        );
    }
}
