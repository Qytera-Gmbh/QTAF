package de.qytera.qtaf.core.config.entity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.PathNotFoundException;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.error.ConfigurationError;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Configuration entity
 */
public class ConfigMap extends HashMap<String, Object> {
    /**
     * Error log collection
     */
    private ErrorLogCollection errorLogCollection = ErrorLogCollection.getInstance();

    /**
     * Json context
     */
    private final DocumentContext documentContext;

    /**
     * Constructor
     * @param documentContext   json context
     */
    public ConfigMap(DocumentContext documentContext) {
        this.documentContext = documentContext;
    }

    /**
     * Logger
     */
    protected Logger logger = QtafFactory.getLogger();

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
            try {
                value = documentContext.read("$." + key);
            } catch (PathNotFoundException e) {
                return null;
            }
        }
        return value;
    }

    private <T> T getValue(String key, Class<T> clazz) {
        Object value = this.getValue(key);
        if (value != null) {
            try {
                return clazz.cast(value);
            } catch (ClassCastException e) {
                logger.error(String.format("Value '%s' of key '%s' could not be parsed as %s", value, key, clazz.getName()));
                this.errorLogCollection.addErrorLog(new ConfigurationError(e));
            }
        }
        return null;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link String}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value
     */
    public String getString(String key) {
        return this.getValue(key, String.class);
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link String}. If the value does not exist or is null,
     * returns {@code valueIfNull} instead.
     *
     * @param key         the key of the value to retrieve
     * @param valueIfNull the value to return if there is no value attached to the key
     * @return the key's value or the provided default value
     */
    public String getString(String key, String valueIfNull) {
        String value = this.getString(key);
        return value != null ? value : valueIfNull;
    }

    /**
     * Get value from environment variable
     * @param key   configuration key
     * @return  configuration value
     */
    public String getStringFromEnvironment(String key) {
        // Search for lower case and upper case keys
        String keyLower = key.trim().replace('.', '_').toLowerCase();
        String keyUpper = keyLower.toUpperCase();

        String s = System.getenv(keyLower);

        if (s != null) {
            return s;
        }

        return System.getenv(keyUpper);
    }

    /**
     * Get value from system properties
     * @param key   configuration key
     * @return  configuration value
     */
    public String getStringFromSystemProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * Set string value
     *
     * @param key     Key
     * @param value   Key
     * @return        self
     */
    public ConfigMap setString(String key, String value) {
        this.put(key, value);
        return this;
    }

    /**
     * Retrieves the value for the given key, interpreted as an {@link Integer}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value
     */
    public Integer getInt(String key) {
        return this.getValue(key, Integer.class);
    }

    /**
     * Retrieves the value for the given key, interpreted as an {@link Integer}. If the value does not exist or is null,
     * returns {@code valueIfNull} instead.
     *
     * @param key         the key of the value to retrieve
     * @param valueIfNull the value to return if there is no value attached to the key
     * @return the key's value or the provided default value
     */
    public Integer getInt(String key, Integer valueIfNull) {
        Integer value = getInt(key);
        return value == null ? valueIfNull : value;
    }

    /**
     * Set int value
     *
     * @param key     Key
     * @param value   Key
     * @return        self
     */
    public ConfigMap setInt(String key, Integer value) {
        this.put(key, value);
        return this;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Double}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or null if there is no value
     */
    public Double getDouble(String key) {
        return this.getValue(key, Double.class);
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Double}. If the value does not exist or is null,
     * returns {@code valueIfNull} instead.
     *
     * @param key         the key of the value to retrieve
     * @param valueIfNull the value to return if there is no value attached to the key
     * @return the key's value or the provided default value
     */
    public Double getDouble(String key, Double valueIfNull) {
        Double value = getDouble(key);
        return value == null ? valueIfNull : value;
    }

    /**
     * Set double value
     *
     * @param key     Key
     * @param value   Key
     * @return        self
     */
    public ConfigMap setDouble(String key, Double value) {
        this.put(key, value);
        return this;
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Boolean}.
     *
     * @param key the key of the value to retrieve
     * @return the key's value or the provided default value
     */
    public Boolean getBoolean(String key) {
        return this.getValue(key, Boolean.class);
    }

    /**
     * Retrieves the value for the given key, interpreted as a {@link Double}. If the value does not exist or is null,
     * returns {@code valueIfNull} instead.
     *
     * @param key         the key of the value to retrieve
     * @param valueIfNull the value to return if there is no value attached to the key
     * @return the key's value or the provided default value
     */
    public Boolean getBoolean(String key, Boolean valueIfNull) {
        Boolean value = getBoolean(key);
        return value == null ? valueIfNull : value;
    }

    /**
     * Set boolean value
     *
     * @param key     Key
     * @param value   Key
     * @return        self
     */
    public ConfigMap setBoolean(String key, Boolean value) {
        this.put(key, value);
        return this;
    }

    /**
     * Get array
     * @param key   Json path
     * @return      array
     */
    public List<?> getArray(String key) {
        return documentContext.read("$." + key);
    }
}
