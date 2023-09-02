package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Details about a Jira cloud project component.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentCloudDto extends ComponentDto<UserCloudDto> {
    /**
     * The account ID of the component's lead user. The accountId uniquely identifies the user across all Atlassian
     * products. For example, 5b10ac8d82e05b22cc7d4ef5.
     */
    private String leadAccountId;

}
