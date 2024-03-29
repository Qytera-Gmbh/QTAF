package de.qytera.qtaf.testrail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A TestRail link.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Integer next;
    private Integer prev;
}
