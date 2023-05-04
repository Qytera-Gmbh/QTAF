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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.InvalidArgumentException;
import rx.Subscription;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This subscriber binds to the test finished event and uploads the tests to the TestRail API
 */
public class UploadTestsSubscriber implements IEventSubscriber {
    /**
     * QTAF Event Subscription
     */
    private Subscription testFinishedSubscription;

    @Override
    public void initialize() {
        if (testFinishedSubscription != null) {
            this.testFinishedSubscription = QtafEvents.logsPersisted.subscribe(this::onFinishedTesting);
        }
    }

    /**
     * Configuration
     */
    private static ConfigMap CONFIG = QtafFactory.getConfiguration();

    /**
     * testRail API client
     */
    APIClient client = null;

    /**
     * Create a Client for the TestRail API
     * @return  TestRail API client
     */
    private APIClient setUpClient() {

        try {
            // Get configuration values
            String url = CONFIG.getString(TestRailConfigHelper.TESTRAIL_URL);
            String clientId = CONFIG.getString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID);
            String clientSecret = CONFIG.getString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET);
            String key = CONFIG.getString(TestRailConfigHelper.SECURITY_KEY);

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
            client.setUser(AES.decrypt(clientId, key));
            client.setPassword(AES.decrypt(clientSecret, key));
            return client;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for finished testing event
     * @param testingContext    Test context event payload
     */
    private void onFinishedTesting(String testingContext) {
        // Check if plugin is enabled
        if (!CONFIG.getBoolean(TestRailConfigHelper.TESTRAIL_ENABLED)) {
            return;
        }

        client = setUpClient();
        var instance = TestSuiteLogCollection.getInstance();

        for (TestFeatureLogCollection testFeatureLogCollection : instance.getTestFeatureLogCollections()) {

            Map<String, List<TestScenarioLogCollection>> groupedScenarioLogs = testFeatureLogCollection.getScenariosGroupedByAbstractScenarioId();

            for (Map.Entry<String, List<TestScenarioLogCollection>> entry : groupedScenarioLogs.entrySet()) {
                TestRail testRailIdAnnotation = (TestRail) entry.getValue().get(0).getAnnotation(TestRail.class);
                if (entry.getValue().get(0).getStatus() == null) {
                    return;
                }
                if (testRailIdAnnotation != null) {
                    if (entry.getValue().get(0).getStatus().equals(TestScenarioLogCollection.Status.FAILURE)) {

                        Supplier<Stream<LogMessage>> logMessagesStream = () -> entry.getValue().get(0).getLogMessages().stream().filter(x -> x instanceof StepInformationLogMessage);
                        var isErrorMessageFound = logMessagesStream.get().filter(d -> ((StepInformationLogMessage) d).getStatus().equals(StepInformationLogMessage.Status.ERROR)).map(n -> n.getMessage()).findFirst().isPresent();

                        String errorMessage = null;

                        if (!isErrorMessageFound) {
                            var count = logMessagesStream.get().map(n -> n.getMessage()).count();
                            errorMessage = logMessagesStream.get().map(n -> n.getMessage()).skip(count - 1).findFirst().get();
                        } else {
                            errorMessage = logMessagesStream.get().filter(d -> ((StepInformationLogMessage) d).getStatus().equals(StepInformationLogMessage.Status.ERROR)).map(n -> n.getMessage()).findFirst().get();
                        }

                        for (String caseId : testRailIdAnnotation.caseId()) {
                            try {
                                TestRailManager.addResultForTestCase(client, caseId, testRailIdAnnotation.runId().runId, 5, "Failure found in: " + errorMessage);
                                TestRailManager.addAttachementForTestCase(client, caseId, QtafFactory.getTestSuiteLogCollection().getLogDirectory() + "/Report.html");
                                TestRailManager.addAttachementForTestCase(client, caseId, entry.getValue().get(0).getScreenshotAfter());
                                System.out.println("Results are uploaded to testRail");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    } else if (entry.getValue().get(0).getStatus().equals(TestScenarioLogCollection.Status.SUCCESS)) {
                        Arrays.stream(testRailIdAnnotation.caseId()).forEach(caseId -> {
                            try {
                                TestRailManager.addResultForTestCase(client, caseId, testRailIdAnnotation.runId().runId, 1, "");
                                System.out.println("Results are uploaded to testRail");
                                JSONObject att = TestRailManager.getAttachementForTestCase(client, caseId);

                                if (att != null) {
                                    JSONArray attachements = (JSONArray) att.get("attachments");
                                    List<JSONObject> attachementItems = IntStream.range(0, attachements.size())
                                            .mapToObj(index -> (JSONObject) attachements.get(index))
                                            .toList();
                                    attachementItems.stream().forEach(x -> {
                                        try {
                                            TestRailManager.deleteAttachementForTestCase(client, x.get("id").toString());
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        });
                    }
                }
            }
        }
    }
}

