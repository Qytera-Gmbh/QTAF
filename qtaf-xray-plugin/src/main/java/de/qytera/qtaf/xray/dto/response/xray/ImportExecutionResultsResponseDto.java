package de.qytera.qtaf.xray.dto.response.xray;

/**
 * Xray import response DTO. This class represents the JSON structure that Xray API returns when importing tests.
 */
public interface ImportExecutionResultsResponseDto {

    /**
     * Returns the ID of the created test execution issue.
     *
     * @return the ID
     */
    String getId();

    /**
     * Returns the key of the created test execution issue.
     *
     * @return the ID
     */
    String getKey();

    /**
     * Returns the self link of the created test execution issue.
     *
     * @return the ID
     */
    String getSelf();

}
