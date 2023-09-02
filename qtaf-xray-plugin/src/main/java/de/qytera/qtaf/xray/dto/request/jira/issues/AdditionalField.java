package de.qytera.qtaf.xray.dto.request.jira.issues;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum containing additional fields to provide to Jira issue searches.
 */
@Getter
@AllArgsConstructor
public enum AdditionalField {

    // These can be acquired using https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-fields/.

    /**
     * Aggregate progress field.
     */
    AGGREGATE_PROGRESS("aggregateprogress"),
    /**
     * Aggregate time estimate field.
     */
    AGGREGATE_TIME_ESTIMATE("aggregatetimeestimate"),
    /**
     * Aggregate original time estimate field.
     */
    AGGREGATE_TIME_ORIGINAL_ESTIMATE("aggregatetimeoriginalestimate"),
    /**
     * Aggregate time spent field.
     */
    AGGREGATE_TIME_SPENT("aggregatetimespent"),
    /**
     * Assignee field.
     */
    ASSIGNEE("assignee"),
    /**
     * Attachment field.
     */
    ATTACHMENT("attachment"),
    /**
     * Comment field.
     */
    COMMENT("comment"),
    /**
     * Components field.
     */
    COMPONENTS("components"),
    /**
     * Creation date field.
     */
    CREATED("created"),
    /**
     * Creator field.
     */
    CREATOR("creator"),
    /**
     * Description field.
     */
    DESCRIPTION("description"),
    /**
     * Due date field.
     */
    DUE_DATE("duedate"),
    /**
     * Environment field.
     */
    ENVIRONMENT("environment"),
    /**
     * Fix versions field.
     */
    FIX_VERSIONS("fixVersions"),
    /**
     * Issue id field.
     */
    ID("id"),
    /**
     * Issue key field.
     */
    ISSUE_KEY("issuekey"),
    /**
     * Issue links field.
     */
    ISSUE_LINKS("issuelinks"),
    /**
     * Issue restriction field.
     */
    ISSUE_RESTRICTION("issuerestriction"),
    /**
     * Issue type field.
     */
    ISSUE_TYPE("issuetype"),
    /**
     * Issue labels field.
     */
    LABELS("labels"),
    /**
     * Last viewed field.
     */
    LAST_VIEWED("lastViewed"),
    /**
     * Parent issue field.
     */
    PARENT("parent"),
    /**
     * Priority field.
     */
    PRIORITY("priority"),
    /**
     * Progress field.
     */
    PROGRESS("progress"),
    /**
     * Project field.
     */
    PROJECT("project"),
    /**
     * Reporter field.
     */
    REPORTER("reporter"),
    /**
     * Resolution field.
     */
    RESOLUTION("resolution"),
    /**
     * Resolution date field.
     */
    RESOLUTION_DATE("resolutiondate"),
    /**
     * Security field.
     */
    SECURITY("security"),
    /**
     * Status field.
     */
    STATUS("status"),
    /**
     * Status category field.
     */
    STATUS_CATEGORY("statusCategory"),
    /**
     * Status category change date field.
     */
    STATUS_CATEGORY_CHANGE_DATE("statuscategorychangedate"),
    /**
     * Subtasks field.
     */
    SUBTASKS("subtasks"),
    /**
     * Summary field.
     */
    SUMMARY("summary"),
    /**
     * Thumbnail field.
     */
    THUMBNAIL("thumbnail"),
    /**
     * Time estimate field.
     */
    TIME_ESTIMATE("timeestimate"),
    /**
     * Original time estimate field.
     */
    TIME_ORIGINAL_ESTIMATE("timeoriginalestimate"),
    /**
     * Time spent field.
     */
    TIME_SPENT("timespent"),
    /**
     * Time tracking field.
     */
    TIME_TRACKING("timetracking"),
    /**
     * Updated field.
     */
    UPDATED("updated"),
    /**
     * Versions field.
     */
    VERSIONS("versions"),
    /**
     * Votes field.
     */
    VOTES("votes"),
    /**
     * Watches field.
     */
    WATCHES("watches"),
    /**
     * Work log field.
     */
    WORK_LOG("worklog"),
    /**
     * Work ratio field.
     */
    WORK_RATIO("workratio");

    /**
     * The text the API uses for identifying and handling fields.
     */
    public final String text;
}
