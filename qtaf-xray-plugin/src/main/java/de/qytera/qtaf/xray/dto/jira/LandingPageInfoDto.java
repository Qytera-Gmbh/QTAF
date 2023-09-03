package de.qytera.qtaf.xray.dto.jira;


import com.google.gson.JsonObject;
import lombok.Data;

/**
 * A project's landing page info.
 */
@Data
public class LandingPageInfoDto {
    /**
     * Landing page attributes.
     */
    private JsonObject attributes;
    /**
     * Landing page board ID.
     */
    private Integer boardId;
    /**
     * Landing page board name.
     */
    private String boardName;
    /**
     * Landing page project key.
     */
    private String projectKey;
    /**
     * Landing page project type.
     */
    private String projectType;
    /**
     * Landing page queue category.
     */
    private String queueCategory;
    /**
     * Landing page queue ID.
     */
    private Integer queueId;
    /**
     * Whether the project is a simple board.
     */
    private Boolean simpleBoard;
    /**
     * Whether the project has been simplified.
     */
    private Boolean simplified;
    /**
     * Landing page URL.
     */
    private String url;
}
