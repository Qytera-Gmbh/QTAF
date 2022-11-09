package de.qytera.qtaf.xray.service;

import de.qytera.qtaf.xray.commands.AuthenticationCommand;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayAuthCredentials;

/**
 * Xray Service Factory class
 */
public class XrayServiceFactory {
    /**
     * Get Xray Service instance
     * @return  Xray Service instance
     */
    public static AbstractXrayService getInstance() {
        AbstractXrayService xrayService;

        if (XrayConfigHelper.isXrayServerService()) {
            xrayService = XrayServerService.getInstance();
            ((XrayServerService) xrayService).setJwtToken(XrayConfigHelper.getBearerToken());
        } else {
            xrayService = XrayCloudService.getInstance();

            // Set Authentication credentials
            ((XrayCloudService) xrayService).setAuthCredentials(new XrayAuthCredentials(
                    XrayConfigHelper.getClientId(),
                    XrayConfigHelper.getClientSecret()
            ));
        }

        return xrayService;
    }
}
