package de.qytera.qtaf.xray.service;

import de.qytera.qtaf.xray.config.XrayConfigHelper;

public class XrayServerService extends AbstractXrayService {

    @Override
    public String getXrayURL() {
        return XrayConfigHelper.getServerUrl();
    }

    @Override
    public String getImportPath() {
        return "/rest/raven/1.0/import/execution";
    }

    @Override
    public String authenticate() {
        return XrayConfigHelper.getBearerToken();
    }

    /**
     * Singleton instance
     */
    private static XrayServerService instance = null;

    /**
     * Factory method
     *
     * @return singleton instance
     */
    public static XrayServerService getInstance() {
        if (instance == null) {
            instance = new XrayServerService();
        }

        return instance;
    }
}
