package de.qytera.qtaf.aws_devicefarm.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Configuration Helper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AWSDeviceFarmConfigHelper {
    /**
     * QTAF Configuration.
     */
    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    /**
     * The selector for accessing the configured AWS device farm project ARN.
     */
    public static final String PROJECT_ARN = "aws.deviceFarm.projectArn";
    /**
     * The selector for accessing the configured AWS device farm project region.
     */
    public static final String PROJECT_REGION = "aws.deviceFarm.region";

    /**
     * Get the configured AWS device farm project ARN.
     *
     * @return the ARN
     */
    public static String getProjectArn() {
        return CONFIG.getString(PROJECT_ARN);
    }

    /**
     * Get the configured AWS device farm project region.
     *
     * @return the region
     */
    public static String getProjectRegion() {
        return CONFIG.getString(PROJECT_REGION);
    }
}
