package de.qytera.qtaf.xray.dto.jira;


import com.google.gson.JsonElement;
import lombok.Data;

/**
 * Represents a link to a remote entity.
 */
@Data
public class RemoteEntityLinkDto {
    /**
     * A reference pointing to this remote link.
     */
    private String self;
    /**
     * The remote link name.
     */
    private String name;
    /**
     * The remote link.
     */
    private JsonElement link;
}
