package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This resource represents application roles. Use it to get details of an application role or all application roles.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/applicationrole-get">Get application role (Jira server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationRoleServerDto extends ApplicationRoleDto {
    /*
     * Empty because Jira server does not add any additional or distinct fields to the abstract base class.
     * Nonetheless, keeping the abstract super class feels like the right thing to do, as it keeps the cloud/server
     * naming scheme consistent.
     */
}
