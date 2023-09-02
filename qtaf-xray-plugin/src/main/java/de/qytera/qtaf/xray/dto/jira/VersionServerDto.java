package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * The response when retrieving Jira server project versions.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/project-getProjectVersions">Get project versions (Jira Server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VersionServerDto extends VersionDto {

    private List<RemoteEntityLinkDto> remotelinks = new ArrayList<>();

    @Data
    public static class RemoteEntityLinkDto {
        private String self;
        private String name;
        private JsonElement link;
    }

}
