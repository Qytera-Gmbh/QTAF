package de.qytera.qtaf.testrail.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * TestRail configuration parameters
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRailConfigHelper {
    public static final String SECURITY_KEY = "security.key";
    public static final String TESTRAIL_ENABLED = "testrail.enabled";
    public static final String TESTRAIL_URL = "testrail.url";
    public static final String TESTRAIL_AUTHENTICATION_CLIENT_ID = "testrail.authentication.clientId";
    public static final String TESTRAIL_AUTHENTICATION_CLIENT_SECRET = "testrail.authentication.clientSecret";
}
