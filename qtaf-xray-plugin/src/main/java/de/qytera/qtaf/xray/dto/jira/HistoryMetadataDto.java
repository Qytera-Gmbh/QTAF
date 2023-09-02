package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the complete set of metadata for a history change group.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HistoryMetadataDto {

    private String type;
    private String description;
    private String descriptionKey;
    private String activityDescription;
    private String activityDescriptionKey;
    private String emailDescription;
    private String emailDescriptionKey;
    private HistoryMetadataParticipantDto actor;
    private HistoryMetadataParticipantDto generator;
    private HistoryMetadataParticipantDto cause;
    private final Map<String, String> extraData = new HashMap<>();

}
