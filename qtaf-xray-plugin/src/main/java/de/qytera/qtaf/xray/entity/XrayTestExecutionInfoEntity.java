package de.qytera.qtaf.xray.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Test Execution Info Entity
 */
public class XrayTestExecutionInfoEntity {
    /**
     * The project key
     */
    private String projectKey = null;

    /**
     * Test execution summary
     */
    private String summary = null;

    /**
     * Test execution description
     */
    private String description = null;

    /**
     * Test execution version
     */
    private String version = null;

    /**
     * Test execution revision
     */
    private String revision = null;

    /**
     * The user that is responsible for this execution
     */
    private String user = null;

    /**
     * Start of the test execution
     */
    private String startDate = null;

    /**
     * End of the test execution
     */
    private String finishDate = null;

    /**
     * Test plan key the execution belongs to
     */
    private String testPlanKey = null;

    /**
     * Test execution environments (i.e. Windows, MacOS, etc.)
     */
    private List<String> testEnvironments = new ArrayList<>();

    /**
     * Get projectKey
     *
     * @return projectKey
     */
    public String getProjectKey() {
        return projectKey;
    }

    /**
     * Set projectKey
     *
     * @param projectKey ProjectKey
     * @return this
     */
    public XrayTestExecutionInfoEntity setProjectKey(String projectKey) {
        this.projectKey = projectKey;
        return this;
    }

    /**
     * Get summary
     *
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Set summary
     *
     * @param summary Summary
     * @return this
     */
    public XrayTestExecutionInfoEntity setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description Description
     * @return this
     */
    public XrayTestExecutionInfoEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get version
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version
     *
     * @param version Version
     * @return this
     */
    public XrayTestExecutionInfoEntity setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Get user
     *
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user User
     * @return this
     */
    public XrayTestExecutionInfoEntity setUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * Get revision
     *
     * @return revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * Set revision
     *
     * @param revision Revision
     * @return this
     */
    public XrayTestExecutionInfoEntity setRevision(String revision) {
        this.revision = revision;
        return this;
    }

    /**
     * Get startDate
     *
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set startDate
     *
     * @param startDate StartDate
     * @return this
     */
    public XrayTestExecutionInfoEntity setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Get finishDate
     *
     * @return finishDate
     */
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * Set finishDate
     *
     * @param finishDate FinishDate
     * @return this
     */
    public XrayTestExecutionInfoEntity setFinishDate(String finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    /**
     * Get testPlanKey
     *
     * @return testPlanKey
     */
    public String getTestPlanKey() {
        return testPlanKey;
    }

    /**
     * Set testPlanKey
     *
     * @param testPlanKey TestPlanKey
     * @return this
     */
    public XrayTestExecutionInfoEntity setTestPlanKey(String testPlanKey) {
        this.testPlanKey = testPlanKey;
        return this;
    }

    /**
     * Get testEnvironments
     *
     * @return testEnvironments
     */
    public List<String> getTestEnvironments() {
        return testEnvironments;
    }

    /**
     * Add test environment
     *
     * @param environment Test environment
     * @return this
     */
    public XrayTestExecutionInfoEntity addTestEnvironment(String environment) {
        this.testEnvironments.add(environment);
        return this;
    }

    /**
     * Set testEnvironments
     *
     * @param testEnvironments TestEnvironments
     * @return this
     */
    public XrayTestExecutionInfoEntity setTestEnvironments(List<String> testEnvironments) {
        this.testEnvironments = testEnvironments;
        return this;
    }
}
