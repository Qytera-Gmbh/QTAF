package de.qytera.qtaf.xray.net.http;

import de.qytera.qtaf.core.net.http.HTTPJsonDao;
import de.qytera.qtaf.xray.commands.AuthenticationCommand;
import de.qytera.qtaf.xray.config.XrayConfigHelper;

/**
 * Factory class for HTTP Dao obejcts
 */
public class XrayHTTPDaoFactory {
    /**
     * Xray Server HTTP Dao
     */
    private static HTTPJsonDao xrayServerHttpDao = null;

    /**
     * Xray Cloud HTTP Dao
     */
    private static HTTPJsonDao xrayClientHttpDao = null;

    /**
     * Factory method for Xray HTTP Dao. THe configuration decides on which HTTP Dao is returned.
     * @return  Xray (Server | Client) HTTP Dao
     */
    public static HTTPJsonDao getInstance() {
        if (XrayConfigHelper.isXrayServerService()) {
            return xrayServerHttpDao;
        } else {
            return xrayClientHttpDao;
        }
    }

    /**
     * Factory method for Xray Server HTTP Dao
     * @return  Xray Server HTTP Dao
     */
    public static HTTPJsonDao getXrayServerHTTPJsonDao() {
        if (xrayServerHttpDao == null) {
            xrayServerHttpDao = new HTTPJsonDao(XrayConfigHelper.getServerUrl() + "/rest/raven/1.0");
            xrayServerHttpDao.setAuthorizationHeaderValue("Bearer " + XrayConfigHelper.getBearerToken());
        }

        return xrayServerHttpDao;
    }

    /**
     * Factory method for Xray Server HTTP Dao
     * @return  Xray Server HTTP Dao
     */
    public static HTTPJsonDao getXrayCloudHTTPDao() {
        if (xrayClientHttpDao == null) {
            AuthenticationCommand authenticationCommand = new AuthenticationCommand();
            authenticationCommand.execute();
            xrayClientHttpDao = new HTTPJsonDao(XrayUrls.XRAY_CLOUD_API_V1);
        }

        return xrayClientHttpDao;
    }
}
