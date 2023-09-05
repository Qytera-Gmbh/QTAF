package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Details about a Jira server project component.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentServerDto extends ComponentDto<UserCloudDto> {
    /**
     * The username of the component's lead user.
     */
    private String leadUserName;
    /**
     * Whether the component has been archived.
     */
    private Boolean archived;
    /**
     * Whether the component has been deleted.
     */
    private Boolean deleted;
}
