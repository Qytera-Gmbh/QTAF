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
     * Set the ID of the created test execution issue.
     *
     * @param id the ID
     */
    void setId(String id);

    /**
     * Returns the key of the created test execution issue.
     *
     * @return the ID
     */
    String getKey();

    /**
     * Set the key of the created test execution issue.
     *
     * @param key the key
     */
    void setKey(String key);

    /**
     * Returns the self link of the created test execution issue.
     *
     * @return the ID
     */
    String getSelf();

    /**
     * Set the self link of the created test execution issue.
     *
     * @param self the self link
     */
    void setSelf(String self);

}
