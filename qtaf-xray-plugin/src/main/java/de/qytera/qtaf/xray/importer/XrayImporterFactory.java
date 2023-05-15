package de.qytera.qtaf.xray.importer;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.xray.config.XrayConfigHelper;

/**
 * Factory class for Xray Importer objects
 */
public class XrayImporterFactory {
    private XrayImporterFactory() {}
    /**
     * Created instance of XrayServerCucumberImporter
     */
    private static XrayServerCucumberImporter xrayServerCucumberImporter = null;

    /**
     * Created instance of XrayCloudCucumberImporter
     */
    private static XrayCloudCucumberImporter xrayCloudCucumberImporter;

    /**
     * Get Cucumber Importer based on Configuration
     *
     * @return Xray Importer
     */
    public static IXrayImporter getCucumberImporter() {
        if (XrayConfigHelper.isXrayServerService()) {
            return getXrayServerCucumberImporter();
        } else if (XrayConfigHelper.isXrayCloudService()) {
            return getXrayCloudCucumberImporter();
        }

        return null;
    }

    /**
     * Get Xray Server Cucumber Importer
     *
     * @return Xray Server Cucumber Importer object
     */
    public static XrayServerCucumberImporter getXrayServerCucumberImporter() {
        if (xrayServerCucumberImporter == null) {
            xrayServerCucumberImporter = new XrayServerCucumberImporter();
        }

        return xrayServerCucumberImporter;
    }

    /**
     * Get Xray Cloud Cucumber Importer
     *
     * @return Xray Server Cucumber Importer object
     */
    public static XrayCloudCucumberImporter getXrayCloudCucumberImporter() {
        if (xrayCloudCucumberImporter == null) {
            xrayCloudCucumberImporter = new XrayCloudCucumberImporter();
        }

        return xrayCloudCucumberImporter;
    }
}
