package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;

import java.net.URISyntaxException;

/**
 * Every repository class that downloads feature file definitions from Xray should implement this interface.
 */
public interface XrayCucumberRepository {
    /**
     * Get Tests as Cucumber Feature File
     *
     * @param testIDs the test issue IDs whose feature files to retrieve
     * @return Contents of feature file
     * @throws URISyntaxException                 if the download URLs are invalid
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    String getFeatureFileDefinition(String[] testIDs) throws URISyntaxException, MissingConfigurationValueException;
}
