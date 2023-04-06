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
     * Feature ID
     */
    protected String featureId = "";

    /**
     * Abstract Scenario ID
     */
    protected String abstractScenarioId = "";

    /**
     * Scenario ID
     */
    protected String scenarioId = "";

    /**
     * Constructor
     *
     * @param level   log level
     * @param message log message
     */
    public LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }

    /**
     * Gte log level
     *
     * @return log level
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Set log level
     *
     * @param level log level
     * @return this
     */
    public LogMessage setLevel(LogLevel level) {
        this.level = level;
        return this;
    }

    /**
     * Get log message
     *
     * @return log message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set log message
     *
     * @param message log message
     * @return this
     */
    public LogMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get featureId
     *
     * @return featureId
     */
    public String getFeatureId() {
        return featureId;
    }

    /**
     * Set featureId
     *
     * @param featureId FeatureId
     * @return this
     */
    public LogMessage setFeatureId(String featureId) {
        this.featureId = featureId;
        return this;
    }

    /**
     * Get abstractScenarioId
     *
     * @return abstractScenarioId
     */
    public String getAbstractScenarioId() {
        return abstractScenarioId;
    }

    /**
     * Set abstractScenarioId
     *
     * @param abstractScenarioId AbstractScenarioId
     * @return this
     */
    public LogMessage setAbstractScenarioId(String abstractScenarioId) {
        this.abstractScenarioId = abstractScenarioId;
        return this;
    }

    /**
     * Get scenarioId
     *
     * @return scenarioId
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Set scenarioId
     *
     * @param scenarioId ScenarioId
     * @return this
     */
    public LogMessage setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
        return this;
    }
}
