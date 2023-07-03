package de.qytera.qtaf.core.context;

import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;

/**
 * Interface that all test context classes have to implement
 */
public interface IQtafTestContext {
    /**
     * Get current log collection
     *
     * @return log collection
     */
    TestScenarioLogCollection getLogCollection();

    /**
     * Set the current log collection
     *
     * @param collection log collection
     * @return this
     */
    IQtafTestContext setLogCollection(TestScenarioLogCollection collection);

    /**
     * Add logger to all instance fields
     */
    void addLoggerToFieldsRecursively();

    /**
     * Destroys the current browser and starts a new one
     */
    void restartDriver();

    /**
     * Initialize test context
     *
     * @return this
     */
    IQtafTestContext initialize();
}
