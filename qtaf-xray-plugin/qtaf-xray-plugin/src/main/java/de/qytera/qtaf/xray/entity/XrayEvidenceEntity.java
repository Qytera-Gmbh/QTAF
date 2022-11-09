package de.qytera.qtaf.xray.entity;

/**
 * Xray Evidence Entity
 */
public class XrayEvidenceEntity {
    /**
     * Base64 encoded content of file
     */
    private String data;

    /**
     * Filename
     */
    private String filename;

    /**
     * MIME type of file
     */
    private String contentType;

    /**
     * Get data
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * Set data
     *
     * @param data Data
     * @return this
     */
    public XrayEvidenceEntity setData(String data) {
        this.data = data;
        return this;
    }

    /**
     * Get filename
     *
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set filename
     *
     * @param filename Filename
     * @return this
     */
    public XrayEvidenceEntity setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Get contentType
     *
     * @return contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Set contentType
     *
     * @param contentType ContentType
     * @return this
     */
    public XrayEvidenceEntity setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
