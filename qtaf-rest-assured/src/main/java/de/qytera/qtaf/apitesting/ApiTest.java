package de.qytera.qtaf.apitesting;

import de.qytera.qtaf.apitesting.assertions.ApiAssertions;
import de.qytera.qtaf.apitesting.requestspecifications.ApiRequestSpecifications;
import de.qytera.qtaf.apitesting.requesttypes.ApiRequestTypes;

/**
 * This interface collects all the necessary interfaces that are required for QTAF API tests.
 * The purpose of this interface is that the user only has to implement this ApiTest interface,
 * instead of several other interfaces, if he wants to perform API tests with QTAF.
 */
public interface ApiTest extends ApiRequestSpecifications, ApiRequestTypes, ApiAssertions {
}
