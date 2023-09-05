package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.jira.UserCloudDto;
import de.qytera.qtaf.xray.dto.jira.UserDto;
import de.qytera.qtaf.xray.dto.jira.UserServerDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import de.qytera.qtaf.xray.events.XrayEvents;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import rx.Subscription;

import java.net.URISyntaxException;

/**
 * Event subscriber that listens for successfully imported test executions and assigns the issues to certain Jira users.
 */
public class AssignTestExecutionIssueSubscriber implements IEventSubscriber {
    private Subscription subscription;

    @Override
    public void initialize() {
        if (subscription == null) {
            subscription = XrayEvents.responseDtoAvailable.subscribe(AssignTestExecutionIssueSubscriber::onResultsImport);
        }
    }

    private static void onResultsImport(
            ImportExecutionResultsResponseDto response
    ) {
        String assignee = XrayConfigHelper.getResultsUploadAssignee();
        if (assignee == null) {
            return;
        }
        UserDto<?, ?> user;
        if (XrayConfigHelper.isXrayCloudService()) {
            UserCloudDto dto = new UserCloudDto();
            dto.setAccountId(assignee);
            user = dto;
        } else {
            UserServerDto dto = new UserServerDto();
            dto.setName(assignee);
            user = dto;
        }
        try {
            QtafFactory.getLogger().info(
                    String.format(
                            "[QTAF Xray Plugin] Assigning issue '%s' to '%s'...",
                            response.getKey(),
                            user
                    )
            );
            if (!JiraIssueRepository.getInstance().assign(response.getKey(), user)) {
                QtafFactory.getLogger().warn(
                        String.format(
                                "[QTAF Xray Plugin] Failed to assign issue '%s' to '%s'. Please verify your configuration.",
                                response.getKey(),
                                user
                        )
                );
            }
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            QtafFactory.getLogger().error(exception);
        }
    }

}
