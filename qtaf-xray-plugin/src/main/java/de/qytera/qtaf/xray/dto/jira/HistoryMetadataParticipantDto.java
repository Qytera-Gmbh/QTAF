package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a identifiable participant in the metadata history. This might be a remote system, an event that caused
 * the change to occur, or a user on a remote system.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HistoryMetadataParticipantDto {

    private String id;
    private String displayName;
    private String displayNameKey;
    private String type;
    private String avatarUrl;
    private String url;

}
