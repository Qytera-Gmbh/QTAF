package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Jira list wrapper.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-issueidorkey-assignee-put">E.g. Assign (Jira cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-assign">E.g. Assign (Jira server)</a>
 */
@Data
public class SimpleListWrapperDto<T> {
    private int size;
    @SerializedName("max-results")
    private int maxResults;
    private List<T> items = new ArrayList<>();
}
