package de.qytera.qtaf.xray.dto.jira;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the metadata of a field.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-doTransition">Jira REST API - POST transition issue</a>
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class FieldMetaDto {

    private final boolean required;
    private JsonTypeDto schema;
    private String name;
    private String fieldId;
    private String autoCompleteUrl;
    private boolean hasDefaultValue;
    private final List<String> operations = new ArrayList<>();
    private JsonArray allowedValues;
    private JsonElement defaultValue;

}
