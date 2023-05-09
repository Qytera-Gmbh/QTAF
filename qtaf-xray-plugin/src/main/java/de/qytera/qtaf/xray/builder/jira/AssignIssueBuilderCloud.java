package de.qytera.qtaf.xray.builder.jira;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.xray.builder.RequestBodyBuilder;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.ProjectCloudDto;
import de.qytera.qtaf.xray.dto.jira.UserCloudDto;
import de.qytera.qtaf.xray.repository.jira.JiraProjectRepository;

import java.net.URISyntaxException;

/**
 * A class for building Jira cloud request bodies regarding issue assignment.
 */
public class AssignIssueBuilderCloud implements RequestBodyBuilder<UserCloudDto> {

    @Override
    public UserCloudDto build() {
        UserCloudDto dto = new UserCloudDto();
        String assignee = XrayConfigHelper.getResultsUploadAssignee();
        if (assignee == null) {
            try {
                ProjectCloudDto projectDto = JiraProjectRepository.getInstance().getProject(XrayConfigHelper.getProjectKey());
                dto.setAccountId(projectDto.getLead().getAccountId());
            } catch (URISyntaxException | MissingConfigurationValueException exception) {
                QtafFactory.getLogger().warn(
                        String.format(
                                "[QTAF Xray Plugin] Failed to retrieve project lead for project '%s', the issue won't be assigned: %s",
                                XrayConfigHelper.getProjectKey(),
                                exception
                        )
                );
            }
        } else {
            dto.setAccountId(assignee);
        }
        return dto;
    }
}
