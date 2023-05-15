package de.qytera.qtaf.core.config.helper;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.util.TokenSeparatedStringHelper;

import java.util.List;

/**
 * Class that provides methods for extracting information about the test execution from the configuration
 */
public class QtafTestExecutionConfigHelper {
    private QtafTestExecutionConfigHelper() {}
    /**
     * Holds values from JSON configuration files
     */
    protected static final ConfigMap config = QtafFactory.getConfiguration();

    /**
     * Get all groups that should run
     *
     * @return List of names of test groups
     */
    public static List<String> getTestGroupsFromConfiguration() {
        String configGroupString = config.getString("tests.groups");

        // Groups were passed in configuration bit scenario has no groups
        if (configGroupString == null) {
            return null;
        }

        // Parse config group names
        return TokenSeparatedStringHelper.toList(configGroupString, ",", true);
    }
}
