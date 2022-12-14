package de.qytera.qtaf.core.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.io.DirectoryHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ConfigurationTest {
    /**
     * Instantiation of configuration should create a configuration file if it does not exist
     */
    @Test
    public void testCreationOfConfigurationFile() throws IOException {
        String resourcesPath = DirectoryHelper.preparePath("$USER_DIR/src/test/resources/de/qytera/qtaf/core/config");
        String configFilePath = DirectoryHelper.preparePath(resourcesPath);

        File configFile = new File(configFilePath);

        // First delete existing resources
        DirectoryHelper.deleteDirectory(resourcesPath);

        Assert.assertFalse(configFile.exists());

        // Instantiation of configuration should create configuration file
        ConfigurationFactory.createConfigurationFileIfNotExists(
                resourcesPath,
                "configuration.json"
        );

        Assert.assertTrue(configFile.exists());
    }

    /**
     * Configuration object should be available
     */
    @Test
    public void testConfigurationAvailable() {
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertNotNull(config);
    }

    /**
     * If configuration factory is called multiple times it should always return the same (singleton) object
     */
    @Test
    public void testConfigurationObjectIsSingleton() {
        ConfigMap configMap1 = QtafFactory.getConfiguration();
        ConfigMap configMap2 = QtafFactory.getConfiguration();

        Assert.assertEquals(configMap1, configMap2);
    }

    /**
     * If configuration factory is called multiple times it should always return the same (singleton) object
     */
    @Test
    public void testConfigurationObjectIsSingleton2() {
        ConfigMap configMap1 = QtafFactory.getConfiguration();
        ConfigMap configMap2 = QtafFactory.getConfiguration();

        configMap1.setString("a", "c");

        Assert.assertEquals(configMap2.getString("a"), "c");
    }

    /**
     * Test default driver configuration
     */
    @Test
    public void testDriverConfiguration() {
        ConfigMap config = ConfigurationFactory.getInstance();

        // Driver configuration
        Assert.assertEquals(config.getString("driver.name"), "chrome");
        Assert.assertNull(config.getString("driver.remoteUrl"));
        Assert.assertTrue(config.getBoolean("driver.quitAfterTesting"));
    }

    /**
     * Test default logging configuration
     */
    @Test
    public void testLoggingConfiguration() {
        ConfigMap config = ConfigurationFactory.getInstance();

        // Logging configuration
        Assert.assertTrue(config.getBoolean("logging.enabled"));
    }

    /**
     * Test default Xray configuration
     */
    @Test
    public void testXrayConfiguration() {
        ConfigMap config = ConfigurationFactory.getInstance();

        // Xray configuration
        Assert.assertFalse(config.getBoolean("xray.enabled"));
        Assert.assertNull(config.getString("xray.authentication.client_id"));
        Assert.assertNull(config.getString("xray.authentication.client_secret"));
    }

    /**
     * Test default HTML configuration
     */
    @Test
    public void testHtmlReportConfiguration() {
        ConfigMap config = ConfigurationFactory.getInstance();

        // HTML report
        Assert.assertTrue(config.getBoolean("htmlReport.enabled"));
    }

    /**
     * Test default framework configuration
     */
    @Test
    public void testFrameworkConfiguration() {
        ConfigMap config = ConfigurationFactory.getInstance();

        // Framework
        Assert.assertEquals(config.getArray("framework.packageNames").size(), 0);
    }
}
