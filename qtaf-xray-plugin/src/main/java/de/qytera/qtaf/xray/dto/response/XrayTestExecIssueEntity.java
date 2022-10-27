package de.qytera.qtaf.xray.dto.response;

/**
 * Xray import response DTO. This class represents the JSON structure that Xray API returns when importing tests.
 */
public class XrayTestExecIssueEntity {
    /**
     * Xray ID
     */
    private String id;

    /**
     * Execution key
     */
    private String key;

    /**
     * Link to self
     */
    private String self;

    /**
     * Get id
     *
     * @return id Id
     */
    public String getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id Id
     * @return this
     */
    public XrayTestExecIssueEntity setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get key
     *
     * @return key Key
     */
    public String getKey() {
        return key;
    }

    /**
     * Set key
     *
     * @param key Key
     * @return this
     */
    public XrayTestExecIssueEntity setKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * Get self
     *
     * @return self Self
     */
    public String getSelf() {
        return self;
    }

    /**
     * Set self
     *
     * @param self Self
     * @return this
     */
    public XrayTestExecIssueEntity setSelf(String self) {
        this.self = self;
        return this;
    }
}
