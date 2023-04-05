package de.qytera.qtaf.xray.importer;

import java.io.IOException;

/**
 * Interface that all classes that import Cucumber feature files from Xray should implement
 */
public interface IXrayImporter {
    /**
     * Create feature file by Test Set ID
     *
     * @param testSetID ID of test set
     * @param filePath  Feature file location
     */
    void createFeatureFileFromTestSetId(String testSetID, String filePath) throws IOException;

    /**
     * Create feature file by Test IDs
     *
     * @param testIDs  Test IDs
     * @param filePath Feature file location
     */
    void createFeatureFileFromTestIds(String[] testIDs, String filePath) throws IOException;
}
