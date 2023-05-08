package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO describing Jira server project details.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/project-getProject">Get project (Jira Server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectServerDto extends ProjectDto<UserServerDto, IssueTypeServerDto, VersionServerDto> {
    /**
     * The list of project keys.
     */
    private List<String> projectKeys = new ArrayList<>();

}
