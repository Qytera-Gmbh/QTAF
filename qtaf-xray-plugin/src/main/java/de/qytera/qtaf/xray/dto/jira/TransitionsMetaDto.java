package de.qytera.qtaf.xray.dto.jira;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean for the top level of a create meta issue request.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-getTransitions">Jira REST API - GET transitions</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class TransitionsMetaDto {

    private String expand;
    private List<TransitionDto> transitions = new ArrayList<>();

}
