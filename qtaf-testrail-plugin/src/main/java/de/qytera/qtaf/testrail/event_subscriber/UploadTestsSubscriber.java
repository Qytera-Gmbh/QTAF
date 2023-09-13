package de.qytera.qtaf.testrail.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.security.aes.AES;
import de.qytera.qtaf.testrail.annotations.TestRail;
import de.qytera.qtaf.testrail.config.TestRailConfigHelper;
import de.qytera.qtaf.testrail.entity.Attachment;
import de.qytera.qtaf.testrail.entity.Attachments;
import de.qytera.qtaf.testrail.utils.APIClient;
import de.qytera.qtaf.testrail.utils.TestRailManager;
import lombok.Data;
import rx.Subscription;

import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * This subscriber binds to the test finished event and uploads the tests to the TestRail API.
 */
@Data
public class UploadTestsSubscriber implements IEventSubscriber {
    /**
     * QTAF Event Subscription.
     */
    private Subscription testFinishedSubscription;

    /**
     * Configuration.
     */
    private static final ConfigMap CONFIG = QtafFactory.getConfiguration();

    /**
     * testRail API client.
     */
    private APIClient client = null;

    @Override
    public void initialize() {
        if (this.testFinishedSubscription == null) {
            this.testFinishedSubscription = QtafEvents.logsPersisted.subscribe(this::onFinishedTesting);
        }
    }


    /**
     * Set up the client for the TestRail API.
     *
     * @return the client
     * @throws GeneralSecurityException           if encrypted values cannot be decrypted
     * @throws MissingConfigurationValueException if the configuration is invalid
     */
    public APIClient setUpClient() throws GeneralSecurityException, MissingConfigurationValueException {
        if (client == null) {
            // Load values from configuration file
            String url = CONFIG.getString(TestRailConfigHelper.TESTRAIL_URL);
            String clientId = CONFIG.getString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID);
            String clientSecret = CONFIG.getString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET);
            String key = CONFIG.getString(TestRailConfigHelper.SECURITY_KEY);

            client = new APIClient(url);

            // Check if id and secret exist
            if (clientId == null || clientId.isBlank()) {
                throw new MissingConfigurationValueException(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, CONFIG);
            }
            if (clientSecret == null || clientSecret.isBlank()) {
                throw new MissingConfigurationValueException(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, CONFIG);
            }

            if (key == null || key.isBlank()) { // credentials are not encrypted
                client.setUser(clientId);
                client.setPassword(clientSecret);
            } else { // credentials are encrypted
                client.setUser(AES.decrypt(clientId, key));
                client.setPassword(AES.decrypt(clientSecret, key));
            }
        }

        return client;
    }

    /**
     * Event handler for finished testing event.
     *
     * @param testingContext Test context event payload
     */
    public void onFinishedTesting(String testingContext) {
        if (Boolean.FALSE.equals(CONFIG.getBoolean(TestRailConfigHelper.TESTRAIL_ENABLED))) {
            return;
        }
        try {
            client = setUpClient();
        } catch (GeneralSecurityException | MissingConfigurationValueException exception) {
            QtafFactory.getLogger().error("[QTAF Testrail Plugin] Failed to set up API client", exception);
            return;
        }
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();
        for (TestFeatureLogCollection testFeatureLogCollection : logCollection.getTestFeatureLogCollections()) {
            testFeatureLogCollection.getScenarioLogCollection().forEach(this::handleScenario);
        }
    }

    private void handleScenario(TestScenarioLogCollection scenarioLog) {
        if (scenarioLog.getStatus() == null) {
            return;
        }
        TestRail testRailIdAnnotation = scenarioLog.getAnnotation(TestRail.class);
        if (testRailIdAnnotation != null) {
            if (scenarioLog.getStatus().equals(TestScenarioLogCollection.Status.FAILURE)) {
                handleScenarioFailure(scenarioLog, testRailIdAnnotation);
            } else if (scenarioLog.getStatus().equals(TestScenarioLogCollection.Status.SUCCESS)) {
                handleScenarioSuccess(testRailIdAnnotation);
            }
        }
    }

    /**
     * Handle scenario success.
     *
     * @param testRailIdAnnotation testrail annotation of scenario
     */
    public void handleScenarioSuccess(TestRail testRailIdAnnotation) {
        Arrays.stream(testRailIdAnnotation.caseId()).forEach(caseId -> {
            try {
                TestRailManager.addResultForTestCase(client, caseId, getRunId(testRailIdAnnotation), 1, "");
                QtafFactory.getLogger().info("Results are uploaded to testRail");
                Attachments attachments = TestRailManager.getAttachmentsForTestCase(client, caseId);
                if (attachments != null) {
                    for (Attachment attachment : attachments.getAttachments()) {
                        TestRailManager.deleteAttachmentForTestCase(client, attachment.getId());
                    }
                }
            } catch (Exception e) {
                QtafFactory.getLogger().error(e);
            }
        });
    }

    /**
     * Handle scenario failure.
     *
     * @param scenarioLog          step logs
     * @param testRailIdAnnotation testrail annotation of scenario
     */
    public void handleScenarioFailure(TestScenarioLogCollection scenarioLog, TestRail testRailIdAnnotation) {
        String errorMessage = scenarioLog.getLogMessages(StepInformationLogMessage.class).stream()
                .filter(d -> d.getStatus().equals(StepInformationLogMessage.Status.ERROR))
                .map(LogMessage::getMessage)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("expected at least one failed step"));
        for (String caseId : testRailIdAnnotation.caseId()) {
            try {
                TestRailManager.addResultForTestCase(client, caseId, getRunId(testRailIdAnnotation), 5, "Failure found in: " + errorMessage);
                TestRailManager.addAttachmentForTestCase(client, caseId, QtafFactory.getTestSuiteLogCollection().getLogDirectory() + "/Report.html");
                TestRailManager.addAttachmentForTestCase(client, caseId, scenarioLog.getScreenshotAfter());
                QtafFactory.getLogger().info("Results are uploaded to testRail");
            } catch (Exception e) {
                QtafFactory.getLogger().error(e);
            }
        }
    }

    /**
     * Returns the runId. The runId set in the configuration file is preferred to the runId set in the annotation.
     *
     * @param testRailIdAnnotation the annotation Object
     *
     * @return the runId
     */
    public String getRunId(TestRail testRailIdAnnotation) {

        ConfigMap config = ConfigurationFactory.getInstance();
        String runId;

        if (null != config.getString("testrail.runId")){
            runId = config.getString("testrail.runId");
            return runId;
        }

        if (null == testRailIdAnnotation){
            throw new NullPointerException("The passed testRailId annotation is null");
        }

        if (testRailIdAnnotation.runId().isEmpty()){
            throw new IllegalArgumentException( "No runId could be assigned to the test case. " +
                    "The runId must be set either via the configuration file (testrail.runId) " +
                    "or via the corresponding annotation.(@TestRail)");
        }
        runId = testRailIdAnnotation.runId();
        return runId;
    }
}

