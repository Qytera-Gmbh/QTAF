package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.core.net.http.HTTPJsonDao;
import de.qytera.qtaf.xray.net.http.XrayHTTPDaoFactory;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MediaType;

/**
 * Export Tests from Xray Server as Cucumber Feature File
 */
public class XrayServerCucumberRepository implements IXrayCucumberRepository {

    /**
     * API Path for exporting tests as cucumber feature file (%s is replaced with semicolon-separated test IDs)
     */
    private static final String EXPORT_TEST_AS_CUCUMBER = "/export/test?keys=%s";

    /**
     * Data Access Object for Xray API
     */
    private final HTTPJsonDao dao = XrayHTTPDaoFactory.getXrayServerHTTPJsonDao();

    @Override
    public String getFeatureFileDefinition(String[] testIDs) {
        String keys = StringUtils.join(testIDs, ";");
        return dao.getAsString(String.format(EXPORT_TEST_AS_CUCUMBER, keys), MediaType.APPLICATION_JSON);
    }
}
