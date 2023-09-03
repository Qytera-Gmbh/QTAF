package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Interface that all classes that import Cucumber feature files from Xray should implement.
 */
public interface IXrayImporter {
    /**
     * Create feature file by Test Set ID.
     *
     * @param testSetID ID of test set
     * @param filePath  Feature file location
     * @throws IOException                        if the feature file cannot be created
     * @throws URISyntaxException                 if any URLs used during importing are invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    void createFeatureFileFromTestSetId(String testSetID, String filePath) throws IOException, URISyntaxException, MissingConfigurationValueException;

    /**
     * Create feature file by Test IDs.
     *
     * @param testIDs  Test IDs
     * @param filePath Feature file location
     * @throws IOException                        if the feature file cannot be created
     * @throws URISyntaxException                 if any URLs used during importing are invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    void createFeatureFileFromTestIds(String[] testIDs, String filePath) throws IOException, URISyntaxException, MissingConfigurationValueException;
}
