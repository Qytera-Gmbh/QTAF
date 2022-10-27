package de.qytera.qtaf.xray.dto.response;

/**
 * Response object that is returns form Xray Server when uploading execution
 */
public class XrayCloudImportResponseDto extends XrayImportResponseDto {
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
    public XrayCloudImportResponseDto setId(String id) {
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
    public XrayCloudImportResponseDto setKey(String key) {
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
    public XrayCloudImportResponseDto setSelf(String self) {
        this.self = self;
        return this;
    }
}
