package de.qytera.qtaf.core.log.model.index;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Index that holds all FeatureLogCollection objects
 */
public class FeatureLogCollectionIndex {
    /**
     * Feature log index that holds all FeatureLogCollection objects
     */
    private final Map<String, TestFeatureLogCollection> index = Collections.synchronizedMap(new HashMap<>());

    /**
     * Singleton instance
     */
    private static final FeatureLogCollectionIndex instance = new FeatureLogCollectionIndex();

    /**
     * Private constructor
     */
    private FeatureLogCollectionIndex() {}

    /**
     * Factory method
     * @return  singleton instance
     */
    public static synchronized FeatureLogCollectionIndex getInstance() {
        return instance;
    }

    /**
     * Get an object by its ID
     * @param id    object ID
     * @return  feature log collection
     */
    public synchronized TestFeatureLogCollection get(String id) {
        return index.get(id);
    }

    /**
     * Add a new object to the index
     * @param id    object's ID
     * @param obj   object itself
     * @return  the inserted object
     */
    public synchronized TestFeatureLogCollection put(String id, TestFeatureLogCollection obj) {
        QtafFactory.getLogger().debug(String.format(
                "[FeatureIndex] Added Feature log: feature_id=%s, feature_hash=%s", id, obj.hashCode()
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
