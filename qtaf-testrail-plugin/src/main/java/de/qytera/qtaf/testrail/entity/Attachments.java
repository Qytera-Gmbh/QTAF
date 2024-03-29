package de.qytera.qtaf.testrail.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Multiple TestRail attachments.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachments {
    private List<Attachment> attachments;
    private int offset;
    private int size;
    @SerializedName("_link")
    private Link link;
    private int limit;
}
