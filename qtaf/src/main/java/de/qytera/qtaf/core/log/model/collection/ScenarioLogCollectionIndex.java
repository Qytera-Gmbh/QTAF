package de.qytera.qtaf.core.log.model.collection;

import java.util.HashMap;

/**
 * Index that holds all scenario log collections
 */
public class ScenarioLogCollectionIndex extends HashMap<String, TestScenarioLogCollection> {
    /**
     * Singleton instance
     */
    private static final ScenarioLogCollectionIndex instance = new ScenarioLogCollectionIndex();

    /**
     * Private constructor
     */
    private ScenarioLogCollectionIndex() {}

    /**
     * Factory method
     * @return  singleton instance
     */
    public static ScenarioLogCollectionIndex getInstance() {
        return instance;
    }

}
