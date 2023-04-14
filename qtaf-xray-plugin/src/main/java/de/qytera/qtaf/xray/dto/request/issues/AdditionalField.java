package de.qytera.qtaf.xray.dto.request.issues;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum containing additional fields to provide to Jira issue searches.
 */
@Getter
@AllArgsConstructor
public enum AdditionalField {

    KEY("key"),
    SUMMARY("summary"),
    ID("id");

    public final String text;
}
