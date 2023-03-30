package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.xray.config.XrayConfigHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Xray test entity
 */
public class XrayTestEntity {
    /**
     * testKey
     */
    private String testKey;

    /**
     * start
     */
    private Date start;

    /**
     * finish
     */
    private Date finish;

    /**
     * comment
     */
    private String comment;

    /**
     * User who executed this test
     */
    private String executedBy;

    /**
     * Assignee for this test
     */
    private String assignee;

    /**
     * status
     */
    private String status;

    /**
     * step logs
     */
    private List<XrayManualTestStepResultEntity> steps = new ArrayList<>();

    /**
     * Examples
     */
    private List<String> examples = new ArrayList<>();

    /**
     * Test Execution Iteration Results
     */
    private List<XrayTestIterationResultEntity> iterations = new ArrayList<>();

    /**
     * Test Execution Defects
     */
    private List<XrayDefectEntity> defects = new ArrayList<>();

    /**
     * Scenario evidences
     */
    private List<XrayEvidenceItemEntity> evidence = new ArrayList<>();

    /**
     * Custom fields
     */
    private List<XrayCustomFieldEntity> customFields = new ArrayList<>();

    /**
     * Get testKey
     *
     * @return testKey TestKey
     */
    public String getTestKey() {
        return testKey;
    }

    /**
     * Set testKey
     *
     * @param testKey TestKey
     * @return this
     */
    public XrayTestEntity setTestKey(String testKey) {
        this.testKey = testKey;
        return this;
    }

    /**
     * Get start
     *
     * @return Start date
     */
    public Date getStart() {
        return start;
    }

    /**
     * Set start
     *
     * @param start Start
     * @return this
     */
    public XrayTestEntity setStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Get finish
     *
     * @return finish Finish
     */
    public Date getFinish() {
        return finish;
    }

    /**
     * Set finish
     *
     * @param finish Finish
     * @return this
     */
    public XrayTestEntity setFinish(Date finish) {
        this.finish = finish;
        return this;
    }

    /**
     * Get comment
     *
     * @return comment Comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     *
     * @param comment Comment
     * @return this
     */
    public XrayTestEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Get status
     *
     * @return status Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get executedBy
     *
     * @return executedBy
     */
    public String getExecutedBy() {
        return executedBy;
    }

    /**
     * Set executedBy
     *
     * @param executedBy ExecutedBy
     * @return this
     */
    public XrayTestEntity setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    /**
     * Get assignee
     *
     * @return assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Set assignee
     *
     * @param assignee Assignee
     * @return this
     */
    public XrayTestEntity setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    /**
     * Get examples
     *
     * @return examples
     */
    public List<String> getExamples() {
        return examples;
    }

    /**
     * Add an example
     * @param example   Example
     * @return  this
     */
    public XrayTestEntity addExample(String example) {
        examples.add(example);
        return this;
    }

    /**
     * Set examples
     *
     * @param examples Examples
     * @return this
     */
    public XrayTestEntity setExamples(List<String> examples) {
        this.examples = examples;
        return this;
    }

    /**
     * Set status
     *
     * @param status Status
     * @return this
     */
    public XrayTestEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get steps
     *
     * @return steps
     */
    public List<XrayManualTestStepResultEntity> getSteps() {
        return steps;
    }

    /**
     * Add Test Step
     *
     * @param step  step
     * @return              this
     */
    public XrayTestEntity addStep(XrayManualTestStepResultEntity step) {
        this.steps.add(step);
        return this;
    }

    /**
     * Set steps
     * @param steps List of steps
     * @return  this
     */
    public XrayTestEntity setSteps(List<XrayManualTestStepResultEntity> steps) {
        this.steps = steps;
        return this;
    }

    /**
     * Get iterations
     *
     * @return iterations
     */
    public List<XrayTestIterationResultEntity> getIterations() {
        return iterations;
    }

    /**
     * Add an iteration result
     * @param iteration   Iteration result
     * @return  this
     */
    public XrayTestEntity addIteration(XrayTestIterationResultEntity iteration) {
        iterations.add(iteration);
        return this;
    }

    /**
     * Set iterations
     *
     * @param iterations Iterations
     * @return this
     */
    public XrayTestEntity setIterations(List<XrayTestIterationResultEntity> iterations) {
        this.iterations = iterations;
        return this;
    }

    /**
     * Get defects
     *
     * @return defects
     */
    public List<XrayDefectEntity> getDefects() {
        return defects;
    }

    /**
     * Add a defect
     * @param defect   Defect entity
     * @return  this
     */
    public XrayTestEntity addExample(XrayDefectEntity defect) {
        defects.add(defect);
        return this;
    }

    /**
     * Set defects
     *
     * @param defects Defects
     * @return this
     */
    public XrayTestEntity setDefects(List<XrayDefectEntity> defects) {
        this.defects = defects;
        return this;
    }

    /**
     * Get evidence
     *
     * @return evidence
     */
    public List<XrayEvidenceItemEntity> getEvidence() {
        return evidence;
    }

    /**
     * Add Evidence Entity
     *
     * @param xrayEvidenceEntity  Evidence entity
     * @return              this
     */
    public XrayTestEntity addEvidence(XrayEvidenceItemEntity xrayEvidenceEntity) {
        this.evidence.add(xrayEvidenceEntity);
        return this;
    }

    /**
     * Set evidence
     *
     * @param evidence Evidence
     * @return this
     */
    public XrayTestEntity setEvidence(List<XrayEvidenceItemEntity> evidence) {
        this.evidence = evidence;
        return this;
    }

    /**
     * Get customFields
     *
     * @return customFields
     */
    public List<XrayCustomFieldEntity> getCustomFields() {
        return customFields;
    }

    /**
     * Add a custom field
     * @param customField   Example
     * @return  this
     */
    public XrayTestEntity addCustomField(XrayCustomFieldEntity customField) {
        customFields.add(customField);
        return this;
    }

    /**
     * Set customFields
     *
     * @param customFields CustomFields
     * @return this
     */
    public XrayTestEntity setCustomFields(List<XrayCustomFieldEntity> customFields) {
        this.customFields = customFields;
        return this;
    }

    /**
     * Test status entity.
     */
    public static class Status {
        /**
         * The status of a test considered passed.
         *
         * @return the status
         */
        public static Status passed() {
            return new Status(getStatusPassed());
        }

        /**
         * The status of a test considered failed.
         *
         * @return the status
         */
        public static Status failed() {
            return new Status(getStatusFailed());
        }

        /**
         * The status value, e.g. {@code "PASSED"} or {@code "FAIL"}. Depends on the configured Xray instance.
         */
        public final String text;

        /**
         * Construct a new Status with the given value.
         *
         * @param text the status value, such as {@code "PASS"} or {@code "FAILURE"}.
         */
        private Status(String text) {
            this.text = text;
        }


        private static String getStatusPassed() {
            String status = XrayConfigHelper.getStatusPassed();
            if (status != null) {
                return status;
            }
            if (XrayConfigHelper.isXrayCloudService()) {
                return "PASSED";
            }
            return "PASS";
        }

        private static String getStatusFailed() {
            String status = XrayConfigHelper.getStatusFailed();
            if (status != null) {
                return status;
            }
            if (XrayConfigHelper.isXrayCloudService()) {
                return "FAILED";
            }
            return "FAIL";
        }

    }
}
