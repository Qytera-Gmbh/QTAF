package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.http.HTTPJsonDao;
import de.qytera.qtaf.xray.dto.XrayTestDto;
import de.qytera.qtaf.xray.dto.XrayTestDtoCollection;
import de.qytera.qtaf.xray.net.http.XrayHTTPDaoFactory;

/**
 * Repository class that is responsible for fetching and updating Test Entities via the Xray API
 */
public class XrayServerTestRepository implements IXrayTestRepository {
    /**
     * API Path for tests of a given test set (%s is replaced with test set ID)
     */
    private static final String BY_TEST_SET_ID_PATH = "/api/testset/%s/test";

    /**
     * API Path for tests
     */
    private static final String BY_TEST_ID_PATH = "/api/test";

    /**
     * Data Access Object for Xray API
     */
    private final HTTPJsonDao dao = XrayHTTPDaoFactory.getXrayCloudHTTPDao();

    /**
     * Find Tests by ID
     *
     * @param testId Test Set ID
     * @return Collection of test entities
     */
    public XrayTestDto findByTestId(String testId) {
        XrayTestDtoCollection testDtoCollection = dao.get(
                String.format(BY_TEST_ID_PATH + "?keys=" + testId, testId),
                XrayTestDtoCollection.class
        );
        return testDtoCollection.size() > 0 ? testDtoCollection.get(0) : null;
    }

    /**
     * Find Tests by ID
     *
     * @param testSetId Test Set ID
     * @return Collection of test entities
     */
    public XrayTestDtoCollection findByTestSetId(String testSetId) {
        return dao.get(String.format(BY_TEST_SET_ID_PATH, testSetId), XrayTestDtoCollection.class);
    }
}
