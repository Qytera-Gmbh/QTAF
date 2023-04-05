package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.xray.repository.XrayServerCucumberRepository;

import java.io.IOException;

/**
 * This class provides methods for importing Tests as Cucumber feature files from Xray Server
 */
public class XrayServerCucumberImporter implements IXrayImporter {
    /**
     * Xray Cucumber Server Repository
     */
    private static final XrayServerCucumberRepository repo = new XrayServerCucumberRepository();

    /**
     * Create feature file by Test Set ID
     *
     * @param testSetID ID of test set
     * @param filePath  Feature file location
     */
    public void createFeatureFileFromTestSetId(String testSetID, String filePath) throws IOException {
        String fileContent = repo.getFeatureFileDefinition(new String[]{testSetID});
        FileHelper.writeFile(filePath, fileContent);
    }

    /**
     * Create feature file by Test IDs
     *
     * @param testIDs  Test IDs
     * @param filePath Feature file location
     */
    public void createFeatureFileFromTestIds(String[] testIDs, String filePath) throws IOException {
        String fileContent = repo.getFeatureFileDefinition(testIDs);
        FileHelper.writeFile(filePath, fileContent);
    }
}
