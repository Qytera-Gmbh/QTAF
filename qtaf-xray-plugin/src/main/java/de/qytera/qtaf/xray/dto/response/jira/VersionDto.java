package de.qytera.qtaf.xray.dto.response.jira;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The response when retrieving project versions.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-project-versions/#api-rest-api-3-project-projectidorkey-versions-get">Get project versions (Jira Cloud)</a>
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/project-getProjectVersions">Get project versions (Jira Server)</a>
 */
@Data
public class VersionDto {

    private String expand;
    private String self;
    private String id;
    private String description;
    private String name;
    private Boolean archived;
    private Boolean released;
    private String userStartDate;
    private String userReleaseData;
    private String project;
    private Integer projectId;
    private String moveUnfixedIssuesTo;
    private List<SimpleLinkDto> operations = new ArrayList<>();
    private List<RemoteEntityLinkDto> remotelinks = new ArrayList<>();

    @Data
    public static class SimpleLinkDto {

        private String id;
        private String styleClass;
        private String iconClass;
        private String label;
        private String title;
        private String href;
        private Integer weight;
        private JsonObject params;

    }

    @Data
    public static class RemoteEntityLinkDto {

        private String self;
        private String name;
        private JsonElement link;

    }

}
