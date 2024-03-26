package de.qytera.qtaf.core.config.helper;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.util.TokenSeparatedStringHelper;

import java.util.List;

/**
 * Class that provides methods for extracting information about the test execution from the configuration.
 */
public class QtafTestExecutionConfigHelper {
    private QtafTestExecutionConfigHelper() {
    }

    /**
     * Holds values from JSON configuration files.
     */
    protected static final ConfigMap config = QtafFactory.getConfiguration();

    private static final String TEST_GROUPS = "tests.groups";
    private static final String TEST_ASSERTION_BEHAVIOUR_ON_FAILURE = "tests.continueOnAssertionFailure";
    /**
     * Flag for toggling step logging on or off.
     */
    public static final String LOGGING_LOG_STEPS = "logging.logSteps";

    /**
     * Get all groups that should run.
     *
     * @return List of names of test groups
     */
    public static List<String> getTestGroupsFromConfiguration() {
        String configGroupString = config.getString(TEST_GROUPS);

        // Groups were passed in configuration bit scenario has no groups
        if (configGroupString == null) {
            return null;
        }

        // Parse config group names
        return TokenSeparatedStringHelper.toList(configGroupString, ",", true);
    }

    /**
     * A boolean value that indicates weather failed assertions should finish a running test immediately or if the test should continue.
     *
     * @return true if tests should continue when assertions fail, false otherwise
     */
    public static boolean continueOnAssertionFailure() {
        return config.getBoolean(TEST_ASSERTION_BEHAVIOUR_ON_FAILURE);
    }


    /**
     * QTAF users have the option to configure if the test steps should get logged to the console. This is useful if
     * the user wants shorter logs.
     *
     * @return true if step logging is wanted and false if logging is unwanted
     */
    public static boolean isStepLoggingEnabled() {
        ConfigMap config = QtafFactory.getConfiguration();
        return config.getBoolean(LOGGING_LOG_STEPS);
    }
}
