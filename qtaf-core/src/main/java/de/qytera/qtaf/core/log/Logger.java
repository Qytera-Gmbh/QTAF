package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * QTAF wrapper class for Log4J logger
 */
public class Logger {
    /**
     * Log4J logger
     */
    private org.apache.logging.log4j.Logger logger;

    /**
     * Error logs
     */
    private static final ErrorLogCollection errorLogCollection = ErrorLogCollection.getInstance();

    /**
     * Instances
     */
    private static Map<String, Logger> instances = new HashMap<>();

    /**
     * Constructor
     *
     * @param name logger name
     */
    private Logger(String name) {
        logger = LogManager.getLogger(name);
    }

    /**
     * Get QtafLogger instance
     *
     * @return ^logger instance
     */
    public static Logger getInstance() {
        return Logger.getInstance("de.qytera.qtaf.core");
    }

    /**
     * Get QtafLogger instance
     *
     * @param name logger name
     * @return logger  instance
     */
    public static Logger getInstance(String name) {
        if (instances.get(name) == null) {
            Logger logger = new Logger(name);
            instances.put(name, logger);
        }

        return instances.get(name);
    }

    /**
     * Log at trace level
     *
     * @param message Message
     * @param params  Message parameters
     */
    public void trace(String message, Object... params) {
        logger.trace(message, params);
    }

    /**
     * Log at debug level
     *
     * @param message Message
     * @param params  Message parameters
     */
    public void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    /**
     * Log at info level
     *
     * @param message Message
     * @param params  Message parameters
     */
    public void info(String message, Object... params) {
        logger.info(message, params);
    }

    /**
     * Log at warn level
     *
     * @param message Message
     * @param params  Message parameters
     */
    public void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    /**
     * Log at error level
     *
     * @param t      Throwable object
     * @param params Message parameters
     */
    public void error(Throwable t, Object... params) {
        logger.error(t.getMessage(), params);
        errorLogCollection.addErrorLog(t);
    }

    /**
     * Log at error level
     *
     * @param message Message
     * @param params  Message parameters
     */
    public void error(String message, Object... params) {
        logger.error(message, params);
        errorLogCollection.addErrorLog(message);
    }

    /**
     * Log at fatal level
     *
     * @param t      Throwable object
     * @param params Message parameters
     */
    public void fatal(Throwable t, Object... params) {
        logger.fatal(t.getMessage(), params);
        errorLogCollection.addErrorLog(t);
    }

    /**
     * Log at fatal level
     *
     * @param message Message
     * @param params  Message parameters
     */
    public void fatal(String message, Object... params) {
        logger.fatal(message, params);
        errorLogCollection.addErrorLog(message);
    }
}
