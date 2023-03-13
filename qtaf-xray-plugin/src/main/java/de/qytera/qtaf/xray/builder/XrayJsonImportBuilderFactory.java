package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.xray.config.XrayConfigHelper;

/**
 * Factory class for Xray JSON Import Builder instances
 */
public class XrayJsonImportBuilderFactory {
    /**
     * Singleton instance of builder for Xray Server
     */
    private static XrayServerJsonImportBuilder builderServer = new XrayServerJsonImportBuilder();

    /**
     * Singleton instance of builder for Xray Cloud
     */
    private static XrayCloudJsonImportBuilder builderCloud = new XrayCloudJsonImportBuilder();

    /**
     * Get builder for Xray Server
     * @return  Xray Server Builder
     */
    public static XrayServerJsonImportBuilder getServerInstance() {
        return builderServer;
    }

    /**
     * Get builder for Xray Server
     * @return  Xray Server Builder
     */
    public static XrayCloudJsonImportBuilder getCloudInstance() {
        return builderCloud;
    }

    /**
     * Get instance of a builder based on which type of Xray instance (Server / Cloud) was specified in the configuration file
     * @return  Xray Json Import Builder instance
     */
    public static AbstractXrayJsonImportBuilder getInstance() {
        if (XrayConfigHelper.isXrayServerService()) {
            return getServerInstance();
        } else {
            return getCloudInstance();
        }
    }
}
