package de.qytera.qtaf.xray.entity;

/**
 * This entity class models a parameter that was passed to a test scenario, i.e. by a data provider
 */
public class XrayTestIterationParameterEntity {
    /**
     * Parameter name
     */
    private String name;

    /**
     * Parameter value
     */
    private String value;

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name Name
     * @return this
     */
    public XrayTestIterationParameterEntity setName(String name) {
        this.name = name;
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
    public XrayTestIterationParameterEntity setValue(String value) {
        this.value = value;
        return this;
    }
}
