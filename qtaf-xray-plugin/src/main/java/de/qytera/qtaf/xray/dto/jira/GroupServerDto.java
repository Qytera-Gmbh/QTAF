package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A group a Jira user can belong to.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/user-getUser">Get user (Jira server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupServerDto extends GroupDto {
    /*
     * Empty because Jira server does not add any additional or distinct fields to the abstract base class.
     * Nonetheless, keeping the abstract super class feels like the right thing to do, as it keeps the cloud/server
     * naming scheme consistent.
     */
}
