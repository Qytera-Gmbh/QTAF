package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple transition bean with additional fields to be set on transition.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class TransitionDto {

    private String id;
    private String name;
    private int opsbarSequence;
    private StatusDto to;
    private final Map<String, FieldMetaDto> fields = new HashMap<>();

}
