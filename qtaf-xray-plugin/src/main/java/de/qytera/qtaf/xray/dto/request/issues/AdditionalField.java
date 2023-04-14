package de.qytera.qtaf.xray.dto.request.issues;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum containing additional fields to provide to Jira issue searches.
 */
@Getter
@AllArgsConstructor
public enum AdditionalField {

    // These can be acquired using https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-fields/.

    AGGREGATE_PROGRESS("aggregateprogress"),
    AGGREGATE_TIME_ESTIMATE("aggregatetimeestimate"),
    AGGREGATE_TIME_ORIGINAL_ESTIMATE("aggregatetimeoriginalestimate"),
    AGGREGATE_TIME_SPENT("aggregatetimespent"),
    ASSIGNEE("assignee"),
    ATTACHMENT("attachment"),
    COMMENT("comment"),
    COMPONENTS("components"),
    CREATED("created"),
    CREATOR("creator"),
    DESCRIPTION("description"),
    DUE_DATE("duedate"),
    ENVIRONMENT("environment"),
    FIX_VERSIONS("fixVersions"),
    ID("id"),
    ISSUE_KEY("issuekey"),
    ISSUE_LINKS("issuelinks"),
    ISSUE_RESTRICTION("issuerestriction"),
    ISSUE_TYPE("issuetype"),
    LABELS("labels"),
    LAST_VIEWED("lastViewed"),
    PARENT("parent"),
    PRIORITY("priority"),
    PROGRESS("progress"),
    PROJECT("project"),
    REPORTER("reporter"),
    RESOLUTION("resolution"),
    RESOLUTION_DATE("resolutiondate"),
    SECURITY("security"),
    STATUS("status"),
    STATUS_CATEGORY("statusCategory"),
    STATUS_CATEGORY_CHANGE_DATE("statuscategorychangedate"),
    SUBTASKS("subtasks"),
    SUMMARY("summary"),
    THUMBNAIL("thumbnail"),
    TIME_ESTIMATE("timeestimate"),
    TIME_ORIGINAL_ESTIMATE("timeoriginalestimate"),
    TIME_SPENT("timespent"),
    TIME_TRACKING("timetracking"),
    UPDATED("updated"),
    VERSIONS("versions"),
    VOTES("votes"),
    WATCHES("watches"),
    WORK_LOG("worklog"),
    WORK_RATIO("workratio");

    public final String text;
}
