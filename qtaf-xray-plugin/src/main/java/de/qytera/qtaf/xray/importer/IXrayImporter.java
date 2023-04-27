package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Interface that all classes that import Cucumber feature files from Xray should implement
 */
public interface IXrayImporter {
    /**
     * Create feature file by Test Set ID
     *
     * @param testSetID ID of test set
     * @param filePath  Feature file location
     * @throws IOException if the feature file cannot be created
     */
    void createFeatureFileFromTestSetId(String testSetID, String filePath) throws IOException, URISyntaxException, MissingConfigurationValueException;

    /**
     * Create feature file by Test IDs
     *
     * @param testIDs  Test IDs
     * @param filePath Feature file location
     * @throws IOException if the feature files cannot be created
     */
    void createFeatureFileFromTestIds(String[] testIDs, String filePath) throws IOException, URISyntaxException, MissingConfigurationValueException;
}
