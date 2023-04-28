package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import de.qytera.qtaf.xray.events.XrayEvents;
import de.qytera.qtaf.xray.repository.jira.JiraIssueRepository;
import rx.Subscription;

import java.net.URISyntaxException;

/**
 * Event subscriber that listens for successfully imported test executions and transitions the created issues if
 * necessary.
 */
public class TransitionImportTestExecutionIssueSubscriber implements IEventSubscriber {

    private Subscription subscription;

    @Override
    public void initialize() {
        if (subscription == null) {
            subscription = XrayEvents.responseDtoAvailable.subscribe(TransitionImportTestExecutionIssueSubscriber::onResultsImport);
        }
    }

    private static void onResultsImport(ImportExecutionResultsResponseDto response) {
        TestScenarioLogCollection.Status status = getTestSuiteStatus(QtafFactory.getTestSuiteLogCollection());
        String jiraStatus;
        if (status == TestScenarioLogCollection.Status.SUCCESS) {
            jiraStatus = XrayConfigHelper.getResultsUploadCustomStatusTestExecutionIssuePassed();
        } else {
            jiraStatus = XrayConfigHelper.getResultsUploadCustomStatusTestExecutionIssueFailed();
        }
        if (jiraStatus == null) {
            return;
        }
        try {
            if (!JiraIssueRepository.getInstance().transitionIssue(response.getKey(), jiraStatus)) {
                QtafFactory.getLogger().warn(
                        String.format(
                                "Test execution issue %s was not transitioned to %s. Please verify your configuration.",
                                response.getKey(),
                                jiraStatus
                        )
                );
            }
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            QtafFactory.getLogger().error(exception);
        }
    }

    private static TestScenarioLogCollection.Status getTestSuiteStatus(TestSuiteLogCollection suiteLogs) {
        for (TestFeatureLogCollection featureLogs : suiteLogs.getTestFeatureLogCollections()) {
            for (TestScenarioLogCollection scenarioLogs : featureLogs.getScenarioLogCollection()) {
                if (scenarioLogs.getStatus() != TestScenarioLogCollection.Status.SUCCESS) {
                    return TestScenarioLogCollection.Status.FAILURE;
                }
            }
        }
        return TestScenarioLogCollection.Status.SUCCESS;
    }
}
