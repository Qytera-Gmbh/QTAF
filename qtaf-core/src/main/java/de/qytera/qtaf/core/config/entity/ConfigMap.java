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

    /**
     * Get string value from key
     *
     * @param key   Key
     * @return      Value
     */
    public String getString(String key) {
        // First check if this key has been overwritten
        String value = null;

        try {
            value = (String) this.get(key);
        } catch (ClassCastException ignored) {}

        // Then try to find key in system properties
        if (value == null) {
            value = this.getStringFromSystemProperty(key);
        }

        // Then try to find key in environment variables
        if (value == null || value.equals("")) {
            value = this.getStringFromEnvironment(key);

            if (value != null && !value.equals("")) {
                return value;
            }
        } else {
            return value;
        }

        // Finally try to find key in configuration file
        try {
            value = documentContext.read("$." + key);
        } catch (PathNotFoundException e) { // Key not found
            return value;
        } catch (ClassCastException e) { // Could not be casted to string (i.e. because it is a list)
            if (documentContext.read("$." + key) instanceof Boolean) {
                return documentContext.read("$." + key) ? "true" : "false";
            } else if (documentContext.read("$." + key) instanceof Integer) {
                return Integer.toString(documentContext.read("$." + key));
            } else if (documentContext.read("$." + key) instanceof Double) {
                return Double.toString(documentContext.read("$." + key));
            }

            logger.error("Configuration key '" + key + "' is not a string type");
            return value;
        }

        return value;
    }

    /**
     * Get string value from key
     *
     * @param key           Key
     * @param valueIfNull   Value that should be return if key cannot be found
     * @return      Value
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
     * Get int value from key
     *
     * @param key   Key
     * @return      Value
     */
    public Integer getInt(String key) {
        String s = this.getString(key);

        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            logger.error(
                    "Value '" + s + "' of key '" + key + "' could not be parsed as integer"
            );
            this.errorLogCollection.addErrorLog(new ConfigurationError(e));
            return 0;
        }
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
     * Get double value from key
     *
     * @param key   Key
     * @return      Value
     */
    public Double getDouble(String key) {
        String s = this.getString(key);

        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            logger.error(
                    "Value '" + s + "' of key '" + key + "' could not be parsed as integer"
            );
            this.errorLogCollection.addErrorLog(new ConfigurationError(e));
            return 0.0;
        }
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
     * Get boolean value from key
     *
     * @param key   Key
     * @return      Value
     */
    public Boolean getBoolean(String key) {
        if (this.get(key) != null && this.get(key) instanceof Boolean) {
            return (Boolean) this.get(key);
        }

        String s = this.getString(key);

        if (s == null ||
                s.trim().equals("") ||
                s.trim().equals("0") ||
                s.trim().equals("0.0") ||
                s.trim().toLowerCase().equals("false")
        ) {
            return false;
        }

        return true;
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
