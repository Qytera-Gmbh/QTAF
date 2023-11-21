package de.qytera.qtaf.core.log.model.index;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Index that holds all log messages.
 */
public class LogMessageIndex {
    /**
     * Index that holds all LogMessage objects.
     */
    private final Map<Integer, LogMessage> index = Collections.synchronizedMap(new HashMap<>());

    /**
     * Singleton instance.
     */
    private static final LogMessageIndex instance = new LogMessageIndex();

    /**
     * Private constructor.
     */
    private LogMessageIndex() {
    }

    /**
     * Factory method.
     *
     * @return singleton instance
     */
    public static synchronized LogMessageIndex getInstance() {
        return instance;
    }

    /**
     * Get an object by its ID.
     *
     * @param id object ID
     * @return feature log collection
     */
    public synchronized LogMessage get(int id) {
        return index.get(id);
    }

    /**
     * Add a new object to the index.
     *
     * @param id  object's ID
     * @param obj object itself
     * @return the inserted object
     */
    public synchronized LogMessage put(int id, LogMessage obj) {
        QtafFactory.getLogger().debug(String.format(
                "[LogMessageIndex] Added log message: message_id=%s, message_hash=%s", id, obj.hashCode()
        ));

        index.put(id, obj);

        return obj;
    }

    /**
     * Get size of index (number of elements in the index).
     *
     * @return size of index
     */
    public synchronized int size() {
        return index.size();
    }

    /**
     * Clear the index.
     */
    public synchronized void clear() {
        index.clear();
    }

    /**
     * Get all log messages for a given feature ID.
     *
     * @param featureId Feature ID
     * @return List of log messages for the given feature ID
     */
    public List<LogMessage> getByFeatureId(String featureId) {
        return this.index
                .values()
                .stream()
                .filter(m -> m.getFeatureId().equals(featureId))
                .collect(Collectors.toList());
    }

    /**
     * Get all log messages for a given abstract scenario ID.
     *
     * @param abstractScenarioId Abstract Scenario ID
     * @return List of log messages for the given abstract scenario ID
     */
    public List<LogMessage> getByAbstractScenarioId(String abstractScenarioId) {
        return this.index
                .values()
                .stream()
                .filter(m -> m.getAbstractScenarioId().equals(abstractScenarioId))
                .collect(Collectors.toList());
    }

    /**
     * Get all log messages for a given scenario ID.
     *
     * @param scenarioId Scenario ID
     * @return List of log messages for the given scenario ID
     */
    public List<LogMessage> getByScenarioId(String scenarioId) {
        return this.index
                .values()
                .stream()
                .filter(m -> m.getScenarioId().equals(scenarioId))
                .collect(Collectors.toList());
    }

    /**
     * Get all log messages for a given scenario ID that are pending.
     *
     * @param scenarioId Scenario ID
     * @return List of log messages for the given scenario ID
     */
    public List<LogMessage> getByScenarioIdAndPending(String scenarioId) {
        return this.index
                .values()
                .stream()
                .filter(StepInformationLogMessage.class::isInstance)
                .filter(step -> step.getScenarioId().equals(scenarioId) && step.getStatus() == StepInformationLogMessage.Status.PENDING)
                .toList();
    }

    /**
     * Get all log messages for a given scenario ID that have passed.
     *
     * @param scenarioId Scenario ID
     * @return List of log messages for the given scenario ID
     */
    public List<LogMessage> getByScenarioIdAndPassed(String scenarioId) {
        return this.index
                .values()
                .stream()
                .filter(StepInformationLogMessage.class::isInstance)
                .filter(step -> step.getScenarioId().equals(scenarioId) && step.getStatus() == StepInformationLogMessage.Status.PASS)
                .toList();
    }

    /**
     * Get all log messages for a given scenario ID that have failed.
     *
     * @param scenarioId Scenario ID
     * @return List of log messages for the given scenario ID
     */
    public List<LogMessage> getByScenarioIdAndFailed(String scenarioId) {
        return this.index
                .values()
                .stream()
                .filter(StepInformationLogMessage.class::isInstance)
                .filter(step -> step.getScenarioId().equals(scenarioId) && step.getStatus() == StepInformationLogMessage.Status.ERROR)
                .toList();
    }
}
