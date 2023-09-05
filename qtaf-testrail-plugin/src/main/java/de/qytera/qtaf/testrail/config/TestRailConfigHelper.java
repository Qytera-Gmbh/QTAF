package de.qytera.qtaf.testrail.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * TestRail configuration parameters.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRailConfigHelper {
    /**
     * The selector for accessing the key used to decrypt encrypted configuration values.
     */
    public static final String SECURITY_KEY = "security.key";
    /**
     * The selector for accessing the key used to enable and disable the TestRail plugin.
     */
    public static final String TESTRAIL_ENABLED = "testrail.enabled";
    /**
     * The selector for accessing the TestRail URL.
     */
    public static final String TESTRAIL_URL = "testrail.url";
    /**
     * The selector for accessing the TestRail client ID.
     */
    public static final String TESTRAIL_AUTHENTICATION_CLIENT_ID = "testrail.authentication.clientId";
    /**
     * The selector for accessing the TestRail client secret.
     */
    public static final String TESTRAIL_AUTHENTICATION_CLIENT_SECRET = "testrail.authentication.clientSecret";
}
