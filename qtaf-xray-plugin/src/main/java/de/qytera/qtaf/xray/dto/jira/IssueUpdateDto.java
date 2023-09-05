package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Issue update/create request.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class IssueUpdateDto {

    private TransitionDto transition;
    private final Map<String, JsonElement> fields = new HashMap<>();
    private final Map<String, List<Map<String, JsonElement>>> update = new HashMap<>();
    private HistoryMetadataDto historyMetadata;
    private final List<EntityPropertyDto> properties = new ArrayList<>();

}
