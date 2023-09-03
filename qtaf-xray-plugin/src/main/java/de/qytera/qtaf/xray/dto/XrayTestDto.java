package de.qytera.qtaf.xray.dto;

/**
 * Class that models JSON response from Xray API.
 */
public class XrayTestDto {
    private String key;
    private int id;
    private int rank;
    private String self;
    private String reporter;
    private String assignee;
    private String[] precondition;
    private String type;
    private String status;
    private String definition;

    /**
     * Get key.
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Set key.
     *
     * @param key Key
     * @return this
     */
    public XrayTestDto setKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * Get id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id Id
     * @return this
     */
    public XrayTestDto setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Get rank.
     *
     * @return rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Set rank.
     *
     * @param rank Rank
     * @return this
     */
    public XrayTestDto setRank(int rank) {
        this.rank = rank;
        return this;
    }

    /**
     * Get self.
     *
     * @return self
     */
    public String getSelf() {
        return self;
    }

    /**
     * Set self.
     *
     * @param self Self
     * @return this
     */
    public XrayTestDto setSelf(String self) {
        this.self = self;
        return this;
    }

    /**
     * Get reporter.
     *
     * @return reporter
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * Set reporter.
     *
     * @param reporter Reporter
     * @return this
     */
    public XrayTestDto setReporter(String reporter) {
        this.reporter = reporter;
        return this;
    }

    /**
     * Get assignee.
     *
     * @return assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Set assignee.
     *
     * @param assignee Assignee
     * @return this
     */
    public XrayTestDto setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    /**
     * Get precondition.
     *
     * @return precondition
     */
    public String[] getPrecondition() {
        return precondition;
    }

    /**
     * Set precondition.
     *
     * @param precondition Precondition
     * @return this
     */
    public XrayTestDto setPrecondition(String[] precondition) {
        this.precondition = precondition;
        return this;
    }

    /**
     * Get type.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set type.
     *
     * @param type Type
     * @return this
     */
    public XrayTestDto setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get status.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status.
     *
     * @param status Status
     * @return this
     */
    public XrayTestDto setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get definition.
     *
     * @return definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Set definition.
     *
     * @param definition Definition
     * @return this
     */
    public XrayTestDto setDefinition(String definition) {
        this.definition = definition;
        return this;
    }
}
