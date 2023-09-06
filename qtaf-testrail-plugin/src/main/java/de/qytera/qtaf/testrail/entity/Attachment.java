package de.qytera.qtaf.testrail.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A TestRail attachment.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    private String filetype;
    private String icon;
    @SerializedName("entity_id")
    private String entityId;
    @SerializedName("client_id")
    private int clientId;
    @SerializedName("entity_type")
    private String entityType;
    private String filename;
    private int size;
    @SerializedName("project_id")
    private int projectId;
    @SerializedName("created_on")
    private int createdOn;
    @SerializedName("data_id")
    private String dataId;
    @SerializedName("user_id")
    private int userId;
    private String name;
    @SerializedName("legacy_id")
    private int legacyId;
    @SerializedName("is_image")
    private boolean isImage;
    private String id;
}