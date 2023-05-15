package de.qytera.qtaf.core.config;

import com.google.gson.JsonSyntaxException;
import com.google.inject.Provides;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.core.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Factory class for generating a configuration object from a JSON configuration file
 */
public class ConfigurationFactory {
    /**
     * Default configuration path which is used if no custom path is provided
     */
    public static final String FILE_PATH = "qtaf.json";

    /**
     * Default configuration resource location
     */
    public static final String CONFIGURATION_RESOURCE_URL = "/" + FILE_PATH;

    /**
     * Base resource directory
     */
    public static final String QTAF_CONFIG_RESOURCES_BASE_DIR = "$USER_DIR/src/test/resources";

    /**
     * Save config map
     */
    private static final Map<String, ConfigMap> configMaps = new HashMap<>();

    /**
     * Logger
     */
    private static final Logger logger = QtafFactory.getLogger();

    /**
     * Private constructor
     */
    private ConfigurationFactory() {
    }

    /**
     * Create default configuration object
     *
     * @return Configuration
     */
    @Provides
    public static ConfigMap getInstance() {
        // Check if configuration file was passed as an argument
        String config = System.getProperty("config");

        return ConfigurationFactory.getInstance(Objects.requireNonNullElse(config, FILE_PATH));
    }

    /**
     * Reads the default configuration file that is shipped with the QTAF JAR file
     *
     * @return Configuration file JSON content
     * @throws IOException File not found
     */
    public static String readDefaultConfigurationFileContent() throws IOException {
        InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(CONFIGURATION_RESOURCE_URL);

        if (inputStream == null) {
            throw new IOException("Error: Default configuration resource not found");
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * Create configuration file if it does not exist
     *
     * @return true on success, false otherwise
     * @throws IOException Error during file creation
     */
    public static boolean createConfigurationFileIfNotExists() throws IOException {
        return ConfigurationFactory.createConfigurationFileIfNotExists(
                QTAF_CONFIG_RESOURCES_BASE_DIR,
                FILE_PATH
        );
    }

    /**
     * Create configuration file if it does not exist
     *
     * @param qtafConfigResourcesBaseDir Directory where QTAF configuration files are stored
     * @param filePath                   relative path of the configuration file (relative to qtafConfigResourcesBaseDir)
     * @return true on success, false otherwise
     * @throws IOException Error during file creation
     */
    public static boolean createConfigurationFileIfNotExists(
            String qtafConfigResourcesBaseDir,
            String filePath
    ) throws IOException {
        return FileHelper.createFileIfNotExists(
                qtafConfigResourcesBaseDir + "/" + filePath,
                readDefaultConfigurationFileContent()
        );
    }

    /**
     * Create a new configuration object
     *
     * @param fileName Name of the file
     * @return Configuration
     */
    public static ConfigMap getInstance(String fileName) {
        if (configMaps.get(fileName) != null) {
            return configMaps.get(fileName);
        }

        Path location = Paths.get(DirectoryHelper.preparePath(QTAF_CONFIG_RESOURCES_BASE_DIR + "/") + fileName);

        String json = null;

        // Create configuration file if it does not exist
        try {
            if (!createConfigurationFileIfNotExists()) {
                logFatal("Could not create configuration file");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logFatal("Could not create configuration file");
        }

        // Read configuration file
        try {
            json = new String(Files.readAllBytes(location));
        } catch (IOException e) {
            try {
                json = ConfigurationFactory.readDefaultConfigurationFileContent();
            } catch (IOException ioException) {
                e.printStackTrace();
                logFatal("Configuration file not found: " + fileName);
            }
        }

        // Parse JSON
        try {
            DocumentContext documentContext = JsonPath.parse(json);
            ConfigMap configMap = new ConfigMap(documentContext, location.toAbsolutePath().toString());

            configMaps.put(fileName, configMap);
            return configMaps.get(fileName);
        } catch (JsonSyntaxException e) { // JSON syntax error
            e.printStackTrace();
            logFatal("Error: Syntax error in " + fileName);
        }

        return configMaps.get((fileName));
    }

    /**
     * Log an info message
     *
     * @param message Log message
     */
    private static void logFatal(String message) {
        logger.fatal("[ConfigurationFactory] " + message);
        System.exit(1);
    }
}
