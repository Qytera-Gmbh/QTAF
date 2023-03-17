package de.qytera.qtaf.xray.entity;

/**
 * Xray custom field entity
 */
public class XrayCustomFieldEntity {
    /**
     * Custom field id
     */
    private String id;

    /**
     * Custom field value
     */
    private String value;

    /**
     * Get id
     *
     * @return id
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
    public XrayCustomFieldEntity setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set value
     *
     * @param value Value
     * @return this
     */
    public XrayCustomFieldEntity setValue(String value) {
        this.value = value;
        return this;
    }
}
