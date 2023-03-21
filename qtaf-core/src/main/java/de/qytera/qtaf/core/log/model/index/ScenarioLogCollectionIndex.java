package de.qytera.qtaf.core.log.model.index;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Index that holds all scenario log collections
 */
public class ScenarioLogCollectionIndex {
    /**
     * Index that holds all TestScenarioLogCollection objects
     */
    private final Map<String, TestScenarioLogCollection> index = Collections.synchronizedMap(new HashMap<>());

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
        QtafFactory.getLogger().debug(String.format(
                "[ScenarioIndex] Added Scenario log: id=%s, scenario_id=%s, abstract_scenario=%s, instance_id=%s, hash=%s",
                id,
                obj.getScenarioId(),
                obj.getAbstractScenarioId(),
                obj.getInstanceId(),
                obj.hashCode()
        ));

        index.put(id, obj);

        return obj;
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
