package de.qytera.qtaf.core.log.model.index;

/**
 * Helper class that provides useful methods for working with the log indices.
 */
public class IndexHelper {
    private IndexHelper() {
    }

    /**
     * Clear all indices.
     */
    public static void clearAllIndices() {
        FeatureLogCollectionIndex.getInstance().clear();
        ScenarioLogCollectionIndex.getInstance().clear();
        LogMessageIndex.getInstance().clear();
    }
}
