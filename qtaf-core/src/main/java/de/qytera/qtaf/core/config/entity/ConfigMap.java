package de.qytera.qtaf.core.config.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.PathNotFoundException;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.log.model.error.ConfigurationError;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Stream;

/**
 * Configuration entity.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConfigMap extends HashMap<String, Object> {
    /**
     * Error log collection.
     */
    private static final ErrorLogCollection ERROR_LOG_COLLECTION = ErrorLogCollection.getInstance();

    /**
     * Json context.
     * transient because hashMap need to be serializable
     */
    private final transient DocumentContext documentContext;

    /**
     * The path of the underlying document used to build the configuration map.
     */
    @Getter
    private final String location;

    private Object getValue(String key) {
        // First check if this key has been overwritten
        Object value = this.get(key);
        // Then try to find key in system properties
        if (value == null) {
            value = this.getStringFromSystemProperty(key);
        }
        // Then try to find key in environment variables
        if (value == null || value.equals("")) {
            value = this.getStringFromEnvironment(key);
        }
        // Finally try to find key in configuration file
        if (value == null || value.equals("")) {
            value = documentContext.read("$." + key);
        }
        return value;
    }

    private <T> T getValue(String key, Class<T> clazz) {
        Object value = this.getValue(key);
        if (value != null) {
            try {
                return clazz.cast(value);
            } catch (ClassCastException exception) {
                QtafFactory.getLogger().error(
                        String.format("Value '%s' of key '%s' could not be parsed as %s", value, key, clazz.getName())
                );
                ERROR_LOG_COLLECTION.addErrorLog(new ConfigurationError(exception));
            }
        }
        return null;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link String}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value or if the value cannot be interpreted as a {@code String}
     */
    public String getString(String key) {
        try {
            return this.getValue(key, String.class);
        } catch (PathNotFoundException exception) {
            logMissingKey(key);
        }
        return null;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link String}. Returns {@code valueIfNull} if the value
     * does not exist, cannot be interpreted as a {@code String} or is null.
     *
     * @param key         the key of the value to retrieve if possible
     * @param valueIfNull the value to return if retrieval was unsuccessful
     * @return the key's value or the provided default value
     */
    public String getString(String key, String valueIfNull) {
        String value = this.getString(key);
        return value != null ? value : valueIfNull;
    }

    /**
     * Retrieves the value for the given key from environment variables.
     *
     * @param key configuration key
     * @return the environment variable's value or null if the environment variable has not been set
     */
    public String getStringFromEnvironment(String key) {
        // Search for lower case and upper case keys
        String environmentVariable = keyAsEnvironmentVariable(key);
        String s = System.getenv(environmentVariable);
        if (s == null) {
            s = System.getenv(environmentVariable.toLowerCase());
        }
        return s;
    }

    private String keyAsEnvironmentVariable(String key) {
        return key.trim().replace('.', '_').toUpperCase();
    }

    /**
     * Get value from system properties.
     *
     * @param key configuration key
     * @return configuration value
     */
    public String getStringFromSystemProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * Set the value for the given key to the provided {@link String}.
     *
     * @param key   the key of the value to set
     * @param value the value to set
     */
    public void setString(String key, String value) {
        this.put(key, value);
    }

    /**
     * Retrieves the value for the given key, interpreted as an {@link Integer}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value or if the value cannot be interpreted as an {@code Integer}
     */
    public Integer getInt(String key) {
        try {
            return this.getValue(key, Integer.class);
        } catch (PathNotFoundException exception) {
            logMissingKey(key);
        }
        return null;
    }

    /**
     * Retrieves the value for the given key, interpreted as an {@link Integer}. Returns {@code valueIfNull} if the
     * value does not exist, cannot be interpreted as an {@code Integer} or is null.
     *
     * @param key         the key of the value to retrieve if possible
     * @param valueIfNull the value to return if retrieval was unsuccessful
     * @return the key's value or the provided default value
     */
    public Integer getInt(String key, Integer valueIfNull) {
        Integer value = getInt(key);
        return value == null ? valueIfNull : value;
    }

    /**
     * Set the value for the given key to the provided {@link Integer}.
     *
     * @param key   the key of the value to set
     * @param value the value to set
     */
    public void setInt(String key, Integer value) {
        this.put(key, value);
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Double}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value or if the value cannot be interpreted as a {@code Double}
     */
    public Double getDouble(String key) {
        try {
            return this.getValue(key, Double.class);
        } catch (PathNotFoundException exception) {
            logMissingKey(key);
        }
        return null;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Double}. Returns {@code valueIfNull} if the value
     * does not exist, cannot be interpreted as a {@code Double} or is null.
     *
     * @param key         the key of the value to retrieve if possible
     * @param valueIfNull the value to return if retrieval was unsuccessful
     * @return the key's value or the provided default value
     */
    public Double getDouble(String key, Double valueIfNull) {
        Double value = getDouble(key);
        return value == null ? valueIfNull : value;
    }

    /**
     * Set the value for the given key to the provided {@link Double}.
     *
     * @param key   the key of the value to set
     * @param value the value to set
     */
    public void setDouble(String key, Double value) {
        this.put(key, value);
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Boolean}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value or if the value cannot be interpreted as a {@code Boolean}
     */
    public Boolean getBoolean(String key) {
        try {
            Object value = getValue(key);
            if (value instanceof String s) {
                if (Stream.of("1", "true", "y").anyMatch(v -> v.equalsIgnoreCase(s))) {
                    return true;
                } else if (Stream.of("0", "false", "n").anyMatch(v -> v.equalsIgnoreCase(s))) {
                    return false;
                }
            }
            if (value instanceof Integer n) {
                if (n == 0) {
                    return false;
                }
                if (n == 1) {
                    return true;
                }
            }
            if (value instanceof Boolean b) {
                return b;
            }
        } catch (PathNotFoundException | NullPointerException exception) {
            logMissingKey(key);
        }
        return null;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Boolean}. Returns {@code valueIfNull} if the value
     * does not exist, cannot be interpreted as a {@code Boolean} or is null.
     *
     * @param key         the key of the value to retrieve if possible
     * @param valueIfNull the value to return if retrieval was unsuccessful
     * @return the key's value or the provided default value
     */
    public Boolean getBoolean(String key, Boolean valueIfNull) {
        Boolean value = getBoolean(key);
        return value == null ? valueIfNull : value;
    }

    /**
     * Set the value for the given key to the provided {@link Boolean}.
     *
     * @param key   the key of the value to set
     * @param value the value to set
     */
    public void setBoolean(String key, Boolean value) {
        this.put(key, value);
    }

    /**
     * Retrieves a list of values for the given key.
     *
     * @param key the key of the array to retrieve
     * @return the list of JSON elements or an empty list if:
     * <ul>
     *   <li>there is no value</li>
     *   <li>the value cannot be interpreted as a JSON array</li>
     * </ul>
     */
    public List<JsonElement> getList(String key) {
        Object value = null;
        try {
            value = getValue(key);
            if (value != null) {
                return GsonFactory.getInstanceWithoutCustomSerializers()
                        .fromJson(value.toString(), JsonArray.class)
                        .asList();
            }
        } catch (PathNotFoundException exception) {
            logMissingKey(key);
        } catch (JsonSyntaxException exception) {
            QtafFactory.getLogger().error(
                    String.format(
                            "Value '%s' of key '%s' could not be parsed as a list (%s)",
                            value,
                            key,
                            exception
                    )
            );
            ERROR_LOG_COLLECTION.addErrorLog(new ConfigurationError(exception));
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves a JSON object for the given key.
     *
     * @param key the key of the JSON object to retrieve
     * @return a map representing the JSON object or an empty map if:
     * <ul>
     *   <li>there is no value</li>
     *   <li>the value cannot be interpreted as a JSON object</li>
     * </ul>
     */
    public Map<String, JsonElement> getMap(String key) {
        Object value = null;
        try {
            value = getValue(key);
            if (value != null) {
                return GsonFactory.getInstanceWithoutCustomSerializers()
                        .fromJson(value.toString(), JsonObject.class)
                        .asMap();
            }
        } catch (PathNotFoundException exception) {
            logMissingKey(key);
        } catch (JsonSyntaxException exception) {
            QtafFactory.getLogger().error(
                    String.format(
                            "Value '%s' of key '%s' could not be parsed as a JSON object (%s)",
                            value,
                            key,
                            exception
                    )
            );
            ERROR_LOG_COLLECTION.addErrorLog(new ConfigurationError(exception));
        }
        return Collections.emptyMap();
    }

    private void logMissingKey(String key) {
        QtafFactory.getLogger().warn(
                String.format(
                        "Failed to find key '%s' in JVM arguments (-D%s), environment variables (%s) or configuration file %s",
                        key,
                        key,
                        keyAsEnvironmentVariable(key),
                        location
                )
        );
    }

    /**
     * Logs an error describing that a value was null (a missing key) and that a fallback value will be used instead.
     *
     * @param key           the missing key
     * @param fallbackValue the value that will be used instead
     * @param <T>           the fallback value type
     * @return the fallback value
     */
    public final <T> T logMissingValue(String key, T fallbackValue) {
        QtafFactory.getLogger().warn(
                String.format(
                        "Value for '%s' was null, defaulting to '%s'.",
                        key,
                        fallbackValue
                )
        );
        return fallbackValue;
    }

    /**
     * Logs an error that an unknown value was encountered for the given key. An optional array of known values can
     * be provided to provide more detail.
     *
     * @param key           the key for which an unknown value was retrieved
     * @param unknownValue  the unknown value
     * @param fallbackValue the value that will be used instead
     * @param knownValues   the array of known values
     * @param <T>           the value type
     * @return the fallback value
     */
    @SafeVarargs
    public final <T> T logUnknownValue(String key, T unknownValue, T fallbackValue, T... knownValues) {
        if (knownValues.length == 0) {
            QtafFactory.getLogger().error(
                    String.format(
                            "Unknown value for '%s': '%s'. Defaulting to '%s'.",
                            key,
                            unknownValue,
                            fallbackValue
                    )
            );
        } else {
            QtafFactory.getLogger().error(
                    String.format(
                            "Unknown value for '%s': '%s' (known values: '%s'). Defaulting to '%s'.",
                            key,
                            unknownValue,
                            Arrays.toString(knownValues),
                            fallbackValue
                    )
            );
        }
        return fallbackValue;
    }

}
