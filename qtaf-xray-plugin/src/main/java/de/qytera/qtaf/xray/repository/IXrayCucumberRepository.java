package de.qytera.qtaf.xray.repository;

/**
 * Every repository class that downloads feature file definitions from Xray should implement this interface
 */
public interface IXrayCucumberRepository {
    /**
     * Get Tests as Cucumber Feature File
     *
     * @param testIDs the test issue IDs whose feature files to retrieve
     * @return Contents of feature file
     */
    String getFeatureFileDefinition(String[] testIDs);
}
