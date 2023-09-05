package de.qytera.qtaf.xray.dto.jira;


import com.google.gson.JsonObject;
import lombok.Data;

/**
 * Represents a simple link.
 */
@Data
public class SimpleLinkDto {
    /**
     * The unique id for the link.
     */
    private String id;
    /**
     * The style to apply to the link.
     */
    private String styleClass;
    /**
     * The icon class of the link.
     */
    private String iconClass;
    /**
     * The label for the link.
     */
    private String label;
    /**
     * The title (tooltip) for the link.
     */
    private String title;
    /**
     * The URL that the link points to.
     */
    private String href;
    /**
     * The weight for the link.
     */
    private Integer weight;
    /**
     * Parameters associated with this link.
     */
    private JsonObject params;

}
