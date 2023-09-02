package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response when retrieving Jira server issue type details.
 *
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/9.7.0/#api/2/issuetype-getIssueType">Get issue type (Jira server)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueTypeServerDto extends IssueTypeDto {
    /*
     * Empty because Jira server does not add any additional or distinct fields to the abstract base class.
     * Nonetheless, keeping the abstract super class feels like the right thing to do, as it keeps the cloud/server
     * naming scheme consistent.
     */
}
