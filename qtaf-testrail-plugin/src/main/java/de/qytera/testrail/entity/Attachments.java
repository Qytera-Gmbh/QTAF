package de.qytera.testrail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachments {
    private List<Attachment> attachments;
    private int offset;
    private int size;
    private Link _link;
    private int limit;
}
