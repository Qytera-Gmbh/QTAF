package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.xray.dto.XrayTestDto;
import de.qytera.qtaf.xray.dto.XrayTestDtoCollection;

/**
 * An interface defining methods for interacting with Xray's API in an opaque manner.
 */
public interface IXrayTestRepository {
    /**
     * Retrieve an Xray test issue by its ID.
     *
     * @param testId the issue's ID
     * @return the Xray test issue
     */
    XrayTestDto findByTestId(String testId);

    /**
     * Retrieve all Xray test issues contained in a test set.
     *
     * @param testSetId the test set's ID
     * @return the Xray test issues
     */
    XrayTestDtoCollection findByTestSetId(String testSetId);
}
