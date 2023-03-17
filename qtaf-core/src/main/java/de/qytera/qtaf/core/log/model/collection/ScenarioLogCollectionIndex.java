package de.qytera.qtaf.core.log.model.collection;

import java.util.HashMap;

/**
 * Index that holds all scenario log collections
 */
public class ScenarioLogCollectionIndex {
    /**
     * Index that holds all TestScenarioLogCollection objects
     */
    private final HashMap<String, TestScenarioLogCollection> index = new HashMap<>();

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
    public static synchronized ScenarioLogCollectionIndex getInstance() {
        return instance;
    }

    /**
     * Get an object by its ID
     * @param id    object ID
     * @return  feature log collection
     */
    public synchronized TestScenarioLogCollection get(String id) {
        return index.get(id);
    }

    /**
     * Add a new object to the index
     * @param id    object's ID
     * @param obj   object itself
     * @return  the inserted object
     */
    public synchronized TestScenarioLogCollection put(String id, TestScenarioLogCollection obj) {
        return index.put(id, obj);
    }

    /**
     * Get size of index (number of elements in the index)
     * @return size of index
     */
    public synchronized int size() {
        return index.size();
    }

    /**
     * Clear the index
     */
    public synchronized void clear() {
        index.clear();
    }
}
