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
     * Sets the ID of the created test execution issue.
     *
     * @param id the id
     */
    void setId(String id);

    /**
     * Returns the key of the created test execution issue.
     *
     * @return the ID
     */
    String getKey();

    /**
     * Sets the key of the created test execution issue.
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
     * Sets the self link of the created test execution issue.
     *
     * @param self the link
     */
    void setSelf(String self);

}
