package de.qytera.qtaf.core.log.model.collection;

import java.util.HashMap;

/**
 * Index that holds all FeatureLogCollection objects
 */
public class FeatureLogCollectionIndex extends HashMap<Integer, TestFeatureLogCollection> {
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
    public static FeatureLogCollectionIndex getInstance() {
        return instance;
    }
}
