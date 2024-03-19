package de.qytera.qtaf.core.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.io.DirectoryHelper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationTest {

    private static final Path CONFIG_PATH = Paths.get(
            DirectoryHelper.preparePath(ConfigurationFactory.QTAF_CONFIG_RESOURCES_BASE_DIR),
            ConfigurationFactory.CONFIGURATION_RESOURCE_URL
    );

    @BeforeMethod
    @AfterMethod
    public void deleteConfiguration() {
        File configFile = CONFIG_PATH.toFile();
        if (configFile.exists()) {
            Assert.assertTrue(configFile.delete(), String.format("failed to delete configuration file %s", configFile));
        }
    }

    @Test
    public void testCreationOfConfigurationFile() throws IOException {
        File configFile = CONFIG_PATH.toFile();
        Assert.assertFalse(configFile.exists());
        ConfigurationFactory.createConfigurationFileIfNotExists(
                ConfigurationFactory.QTAF_CONFIG_RESOURCES_BASE_DIR,
                ConfigurationFactory.CONFIGURATION_RESOURCE_URL
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
        Assert.assertEquals(config.getString("driver.name"), "edge");
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
        Assert.assertEquals(config.getList("framework.packageNames").size(), 0);
    }

    @Test
    public void testLocation() {
        ConfigMap config = ConfigurationFactory.getInstance();
        Assert.assertEquals(config.getLocation(), CONFIG_PATH.toAbsolutePath().toString());
    }

}
