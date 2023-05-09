package de.qytera.qtaf.xray.builder.jira;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.xray.builder.RequestBodyBuilder;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.ProjectServerDto;
import de.qytera.qtaf.xray.dto.jira.UserServerDto;
import de.qytera.qtaf.xray.repository.jira.JiraProjectRepository;

import java.net.URISyntaxException;

/**
 * A class for building Jira server request bodies regarding issue assignment.
 */
public class AssignIssueBuilderServer implements RequestBodyBuilder<UserServerDto> {

    @Override
    public UserServerDto build() {
        UserServerDto dto = new UserServerDto();
        String assignee = XrayConfigHelper.getResultsUploadAssignee();
        if (assignee == null) {
            try {
                ProjectServerDto projectDto = JiraProjectRepository.getInstance().getProject(XrayConfigHelper.getProjectKey());
                dto.setName(projectDto.getLead().getName());
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
            dto.setName(assignee);
        }
        return dto;
    }
}
