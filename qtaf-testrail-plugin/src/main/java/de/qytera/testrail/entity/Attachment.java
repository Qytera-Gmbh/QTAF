package de.qytera.testrail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    private String filetype;
    private String icon;
    private String entity_id;
    private int client_id;
    private String entity_type;
    private String filename;
    private int size;
    private int project_id;
    private int created_on;
    private String data_id;
    private int user_id;
    private String name;
    private int legacy_id;
    private boolean is_image;
    private String id;
}