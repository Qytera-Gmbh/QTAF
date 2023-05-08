package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The request for Jira Server when working with users.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.8.0/#api/2/issue-assign">Assign (Jira Server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserServerDto extends UserDto<ApplicationRoleServerDto, GroupServerDto> {
    /*
     * Empty because Jira server does not add any additional or distinct fields to the abstract base class.
     * Nonetheless, keeping the abstract super class feels like the right thing to do, as it keeps the cloud/server
     * naming scheme consistent.
     */
}
