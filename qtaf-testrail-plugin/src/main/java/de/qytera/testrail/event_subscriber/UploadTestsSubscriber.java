package de.qytera.testrail.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.security.aes.AES;
import de.qytera.testrail.annotations.TestRail;
import de.qytera.testrail.config.TestRailConfigHelper;
import de.qytera.testrail.utils.APIClient;
import de.qytera.testrail.utils.TestRailManager;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.InvalidArgumentException;
import rx.Subscription;

import javax.swing.text.html.Option;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This subscriber binds to the test finished event and uploads the tests to the TestRail API
 */
@Data
public class UploadTestsSubscriber implements IEventSubscriber {
    /**
     * QTAF Event Subscription
     */
    Subscription testFinishedSubscription;

    /**
     * Configuration
     */
    ConfigMap config = QtafFactory.getConfiguration();

    /**
     * testRail API client
     */
    APIClient client = null;

    @Override
    public void initialize() {
        this.testFinishedSubscription = QtafEvents.logsPersisted.subscribe(this::onFinishedTesting);
    }

    /**
     * Create a Client for the TestRail API
     * @return  TestRail API client
     */
    public APIClient setUpClient() {
        try {
            // Get configuration values
            String url = config.getString(TestRailConfigHelper.TESTRAIL_URL);
            String clientId = config.getString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID);
            String clientSecret = config.getString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET);
            String key = config.getString(TestRailConfigHelper.SECURITY_KEY);

            // Check configuration values
            if (clientId == null || clientId.equals("")) {
                throw new InvalidArgumentException(String.format("The parameter '%s' is not set", TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID));
            }

            if (clientSecret == null || clientSecret.equals("")) {
                throw new InvalidArgumentException(String.format("The parameter '%s' is not set", TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET));
            }

            if (key == null || key.equals("")) {
                throw new InvalidArgumentException(String.format("The parameter '%s' is not set", TestRailConfigHelper.SECURITY_KEY));
            }

            // Initialize client
            client = new APIClient(url);
            client.setM_user(AES.decrypt(clientId, key));
            client.setM_password(AES.decrypt(clientSecret, key));
            return client;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for finished testing event
     * @param testingContext    Test context event payload
     */
    public void onFinishedTesting(String testingContext) {
        // Check if plugin is enabled
        if (!config.getBoolean(TestRailConfigHelper.TESTRAIL_ENABLED)) {
            return;
        }

        client = setUpClient();
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();

        for (TestFeatureLogCollection testFeatureLogCollection : logCollection.getTestFeatureLogCollections()) {

            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();

            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {
                TestRail testRailIdAnnotation = (TestRail) entry.getValue().get(0).getAnnotation(TestRail.class);
                if (entry.getValue().get(0).getStatus() == null) {
                    return;
                }
                if (testRailIdAnnotation != null) {
                    if (entry.getValue().get(0).getStatus().equals(TestScenarioLogCollection.Status.FAILURE)) {
                        handleScenarioFailure(entry, testRailIdAnnotation);
                    } else if (entry.getValue().get(0).getStatus().equals(TestScenarioLogCollection.Status.SUCCESS)) {
                        handleScenarioSuccess(testRailIdAnnotation);
                    }
                }
            }
        }
    }

    /**
     * Handle scenario success
     * @param testRailIdAnnotation  testrail annotation of scenario
     */
    public void handleScenarioSuccess(TestRail testRailIdAnnotation) {
        Arrays.stream(testRailIdAnnotation.caseId()).forEach(caseId -> {
            try {
                TestRailManager.addResultForTestCase(client, caseId, testRailIdAnnotation.runId(), 1, "");
                QtafFactory.getLogger().info("Results are uploaded to testRail");
                JSONObject att = TestRailManager.getAttachmentForTestCase(client, caseId);

                if (att != null) {
                    JSONArray attachments = (JSONArray) att.get("attachments");
                    List<JSONObject> attachmentItems = IntStream.range(0, attachments.size())
                            .mapToObj(index -> (JSONObject) attachments.get(index))
                            .toList();
                    attachmentItems.forEach(x -> {
                        try {
                            TestRailManager.deleteAttachmentForTestCase(client, x.get("id").toString());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (Exception e) {
                QtafFactory.getLogger().error(e);
            }
        });
    }

    /**
     * Handle scenario failure
     * @param entry                 step logs
     * @param testRailIdAnnotation  testrail annotation of scenario
     */
    public void handleScenarioFailure(Map.Entry<String, List<TestScenarioLogCollection>> entry, TestRail testRailIdAnnotation) {
        Supplier<Stream<LogMessage>> logMessagesStream = () -> entry.getValue().get(0).getLogMessages().stream().filter(x -> x instanceof StepInformationLogMessage);
        boolean isErrorMessageFound = logMessagesStream.get().filter(d -> ((StepInformationLogMessage) d).getStatus().equals(StepInformationLogMessage.Status.ERROR)).map(LogMessage::getMessage).findFirst().isPresent();

        String errorMessage;

        if (!isErrorMessageFound) {
            long count = logMessagesStream.get().map(LogMessage::getMessage).count();
            Optional<String> errorMessageOption = logMessagesStream.get().map(LogMessage::getMessage).skip(count - 1).findFirst();
            assert errorMessageOption.isPresent() : "expected that there are log messages";
            errorMessage = errorMessageOption.get();
        } else {
            Optional<String> errorMessageOption = logMessagesStream.get().filter(d -> ((StepInformationLogMessage) d).getStatus().equals(StepInformationLogMessage.Status.ERROR)).map(LogMessage::getMessage).findFirst();
            assert errorMessageOption.isPresent() : "expected that there are log messages";
            errorMessage = errorMessageOption.get();
        }

        for (String caseId : testRailIdAnnotation.caseId()) {
            try {
                TestRailManager.addResultForTestCase(client, caseId, testRailIdAnnotation.runId(), 5, "Failure found in: " + errorMessage);
                TestRailManager.addAttachmentForTestCase(client, caseId, QtafFactory.getTestSuiteLogCollection().getLogDirectory() + "/Report.html");
                TestRailManager.addAttachmentForTestCase(client, caseId, entry.getValue().get(0).getScreenshotAfter());
                QtafFactory.getLogger().info("Results are uploaded to testRail");
            } catch (Exception e) {
                QtafFactory.getLogger().error(e);
            }
        }
    }
}

