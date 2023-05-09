package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The DTO for Jira server when working with users.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/user-getUser">Assign (Jira Server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserServerDto extends UserDto<ApplicationRoleServerDto, GroupServerDto> {
    /**
     * The username of the user.
     */
    private String name;
}
