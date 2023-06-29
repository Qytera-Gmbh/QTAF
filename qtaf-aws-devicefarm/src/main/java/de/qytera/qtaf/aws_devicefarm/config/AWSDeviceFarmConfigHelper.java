package de.qytera.qtaf.aws_devicefarm.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;

/**
 * Configuration Helper
 */
public class AWSDeviceFarmConfigHelper {
    /**
     * QTAF Configuration
     */
    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    public static final String PROJECT_ARN = "aws.deviceFarm.projectArn";
    public static final String PROJECT_REGION = "aws.deviceFarm.region";

    public static String getProjectArn() {
        return PROJECT_ARN;
    }

    public static String getProjectRegion() {
        return PROJECT_REGION;
    }
}
