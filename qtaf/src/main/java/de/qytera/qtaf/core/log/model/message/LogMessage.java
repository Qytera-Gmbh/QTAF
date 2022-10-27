package de.qytera.qtaf.core.log.model.message;

import de.qytera.qtaf.core.log.model.LogLevel;

/**
 * Log message class holds information about log messages
 */
public class LogMessage {
    /**
     * Log level
     */
    protected LogLevel level;

    /**
     * Log message
     */
    protected String message = "";

    /**
     * Constructor
     * @param level     log level
     * @param message   log message
     */
    public LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }

    /**
     * Gte log level
     * @return  log level
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Set log level
     * @param level log level
     * @return      this
     */
    public LogMessage setLevel(LogLevel level) {
        this.level = level;
        return this;
    }

    /**
     * Get log message
     * @return  log message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set log message
     * @param message   log message
     * @return          this
     */
    public LogMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
