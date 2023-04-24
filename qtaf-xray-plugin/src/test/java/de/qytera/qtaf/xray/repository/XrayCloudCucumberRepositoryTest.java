package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayAuthCredentials;
import de.qytera.qtaf.xray.service.XrayCloudService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class XrayCloudCucumberRepositoryTest {

    @BeforeMethod
    public void setupCredentials() {
        XrayCloudService.getInstance().setAuthCredentials(
                new XrayAuthCredentials(
                        XrayConfigHelper.getAuthenticationXrayClientId(),
                        XrayConfigHelper.getAuthenticationXrayClientSecret()
                )
        );
    }

    /**
     * Test Download content of Cucumber ZIP file from Xray Cloud
     */
    @Test
    public void testCucumberZipFileDownload() {
        // Get file content
        XrayCloudCucumberRepository repo = new XrayCloudCucumberRepository();
        String fileContent = repo.getFeatureFileDefinition(new String[]{"QTAF-670"});
        Assert.assertNotEquals(fileContent, "");
    }

    /**
     * Test Download content of multiple test feature files as a ZIP file from Xray Cloud
     */
    @Test
    public void testCucumberZipFilesDownload() throws IOException {
        // Get file content
        XrayCloudCucumberRepository repo = new XrayCloudCucumberRepository();
        ArrayList<String> fileContents = repo.getFeatureFileDefinitions(new String[]{"QTAF-670"});
        Assert.assertTrue(fileContents.size() > 0, "There should be files in the downloaded ZIP file");
    }

    /**
     * Test Download content of multiple test feature files as a ZIP file from Xray Cloud
     */
    @Test
    public void testCucumberZipFilesDownloadAndStore() throws IOException {
        String dir = "$USER_DIR/src/test/resources";
        String filename = "1_QTAF-673_QTAF-721.feature";
        Path path = Path.of(DirectoryHelper.preparePath(dir + "/" + filename));

        Assert.assertFalse(
                Files.exists(path),
                String.format("There should be a no file named '%S' in the directory '%s' before downloading and storing ZIP file from Xray Cloud API", filename, dir)
        );
        // Get file content
        XrayCloudCucumberRepository repo = new XrayCloudCucumberRepository();
        repo.getAndStoreFeatureFileDefinitions(new String[]{"QTAF-670"}, dir);

        Assert.assertTrue(Files.exists(
                        Path.of(DirectoryHelper.preparePath(String.format("%s/%s", dir, filename)))),
                String.format("There should be a file named '%S' in the directory '%s' after downloading and storing ZIP file from Xray Cloud API", filename, dir)
        );

        Files.delete(path);
    }
}
