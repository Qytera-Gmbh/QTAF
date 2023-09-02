package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Describes the format of the data that is returned in the JSON representation of a field. This encapsulates a subset
 * of JSON Schema.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class JsonTypeDto {

    private String type;
    private String items;
    private String system;
    private String custom;
    private int customId;

}
