package de.qytera.qtaf.apitesting.assertions;

/**
 * This interface bundles the different types of implemented assertion interfaces (body, status-code, time) so that only one import is necessary.
 */
public interface ApiAssertions extends BodyAssertions, StatusCodeAssertions, TimeAssertions {}
