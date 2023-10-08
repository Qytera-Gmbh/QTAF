package de.qytera.testrail.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.security.aes.AES;
import de.qytera.qtaf.testrail.annotations.TestRail;
import de.qytera.qtaf.testrail.config.TestRailConfigHelper;
import de.qytera.qtaf.testrail.entity.Attachment;
import de.qytera.qtaf.testrail.entity.Attachments;
import de.qytera.qtaf.testrail.entity.Link;
import de.qytera.qtaf.testrail.event_subscriber.UploadTestsSubscriber;
import de.qytera.qtaf.testrail.utils.APIClient;
import de.qytera.qtaf.testrail.utils.TestRailManager;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.List;


/**
 * Testrail upload subscriber tests
 */
public class UploadTestsSubscriberTest {

    @Test(description = "Test initialization")
    public void testInitialization() {
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        Assert.assertFalse(QtafEvents.logsPersisted.hasObservers());
        subscriber.initialize();
        Assert.assertTrue(QtafEvents.logsPersisted.hasObservers());

    }

    @TestRail(caseId = "01", runId = "01")
    public void testDummyRunIdAnnotated() {
    }

    @TestRail(caseId = "01", runId = "")
    public void testDummyRunIdEmptyAnnotation() {
    }

    @Test(description = "Test getRunId(): no runId given")
    public void testGetRunIdNoRunIdGiven() {
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        Assert.assertThrows(NullPointerException.class, () -> subscriber.getRunId(null));
    }

    @Test(description = "Test getRunId(): empty runId given")
    public void testGetRunIdEmptyRunIdGiven() throws ClassNotFoundException, NoSuchMethodException {
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        Class<?> dummyClass = Class.forName("de.qytera.testrail.event_subscriber.UploadTestsSubscriberTest");

        TestRail runIdEmptyAnnotation = dummyClass.getMethod("testDummyRunIdEmptyAnnotation").getAnnotation(TestRail.class);
        Assert.assertThrows(IllegalArgumentException.class, () -> subscriber.getRunId(runIdEmptyAnnotation));
    }

    @Test(description = "Test getRunId(): correct runId given")
    public void testGetRunIdCorrectRunIdGiven() throws ClassNotFoundException, NoSuchMethodException {
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        Class<?> dummyClass = Class.forName("de.qytera.testrail.event_subscriber.UploadTestsSubscriberTest");

        TestRail runIdAnnotatedAnnotation = dummyClass.getMethod("testDummyRunIdAnnotated").getAnnotation(TestRail.class);
        Assert.assertEquals(subscriber.getRunId(runIdAnnotatedAnnotation), "01");
    }

    @Test(description = "Test getRunId(): correct runId given but overwritten by config")
    public void testGetRunIdCorrectRunIdGivenButOverwritenByConfig() throws ClassNotFoundException, NoSuchMethodException {
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        Class<?> dummyClass = Class.forName("de.qytera.testrail.event_subscriber.UploadTestsSubscriberTest");

        ConfigMap config = ConfigurationFactory.getInstance();
        String predefinedRunId = config.getString("testrail.runId");
        config.setString("testrail.runId", "212");

        TestRail runIdAnnotatedAnnotation = dummyClass.getMethod("testDummyRunIdAnnotated").getAnnotation(TestRail.class);
        Assert.assertEquals(subscriber.getRunId(runIdAnnotatedAnnotation), "212");

        // Reset runId
        config.setString("testrail.runId", predefinedRunId);
    }

    @Test(description = "Test the setup function")
    public void testClientSetup() throws GeneralSecurityException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        subscriber.setUpClient();
        Assert.assertEquals(subscriber.getClient().getUrl(), "http://www.inet.com/index.php?/api/v2/");
        Assert.assertEquals(subscriber.getClient().getUser(), "Jane");
        Assert.assertEquals(subscriber.getClient().getPassword(), "mypass");
    }


    @DataProvider
    public Object[][] provideBadClientSetupData() throws GeneralSecurityException {
        return new Object[][]{
                new Object[]{
                        IllegalArgumentException.class,
                        "TestRail base URL is null, please set the value in your configuration file",
                        null,
                        AES.encrypt("Jane", "my-key"),
                        AES.encrypt("mypass", "my-key"),
                        "my-key"
                },
                new Object[]{
                        MissingConfigurationValueException.class,
                        "failed to find non-null value in configuration .+qtaf.json for key: 'testrail.authentication.clientId'",
                        "http://www.inet.com",
                        null,
                        AES.encrypt("mypass", "my-key"),
                        "my-key"
                },
                new Object[]{
                        MissingConfigurationValueException.class,
                        "failed to find non-null value in configuration .+qtaf.json for key: 'testrail.authentication.clientSecret'",
                        "http://www.inet.com",
                        AES.encrypt("Jane", "my-key"),
                        null,
                        "my-key"
                },
                new Object[]{
                        MissingConfigurationValueException.class,
                        "failed to find non-null value in configuration .+qtaf.json for key: 'security.key'",
                        "http://www.inet.com",
                        AES.encrypt("Jane", "my-key"),
                        AES.encrypt("mypass", "my-key"),
                        null
                },
                new Object[]{
                        BadPaddingException.class,
                        "Tag mismatch!? Make sure you're using the correct key",
                        "http://www.inet.com",
                        AES.encrypt("Jane", "my-key"),
                        AES.encrypt("mypass", "my-key"),
                        "wrong-key"
                }
        };
    }

    @Test(
            description = "test bad client setup", dataProvider = "provideBadClientSetupData"
    )
    public void testClientSetupBadParameters(
            Class<? extends Exception> exceptionClass,
            String expectedMessage,
            String url,
            String clientId,
            String clientSecret,
            String key
    ) {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, url);
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, clientId);
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, clientSecret);
        config.setString(TestRailConfigHelper.SECURITY_KEY, key);
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        try {
            subscriber.setUpClient();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().matches(expectedMessage), """
                                        
                    Expected exception message pattern: %s
                                       but got message: %s""".formatted(expectedMessage, e.getMessage()));
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
    }

    @Test(description = "Test the onFinishedTesting event handler")
    public void testOnFinishedTestingActivated() throws GeneralSecurityException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        // Clear all indices
        IndexHelper.clearAllIndices();

        // Create log collection mock
        TestSuiteLogCollection logCollectionMock = Mockito.mock(TestSuiteLogCollection.class);

        try (MockedStatic<TestSuiteLogCollection> suiteLogCollectionMock = Mockito.mockStatic(TestSuiteLogCollection.class)) {
            TestFeatureLogCollection feature1 = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("f1", "feature1");
            feature1.addScenarioLogCollection(
                    TestScenarioLogCollection.createTestScenarioLogCollection("f1", "s1", "i1", "scenario1")
            );
            logCollectionMock.addTestClassLogCollection(feature1);
            suiteLogCollectionMock.when(TestSuiteLogCollection::getInstance).thenReturn(logCollectionMock);

            UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
            subscriber.setUpClient();
            subscriber.onFinishedTesting("");
            Mockito.verify(logCollectionMock, Mockito.times(1)).getTestFeatureLogCollections();
        }

        // Clear all indices
        IndexHelper.clearAllIndices();
        logCollectionMock.clearCollection();
    }

    @Test(description = "Test the onFinishedTesting event handler with pending scenario")
    public void testOnFinishedTestingActivatedWithPendingScenario() throws GeneralSecurityException, NoSuchMethodException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        // Build demo scenario
        DemoScenario demoScenario = new DemoScenario();
        Method method = demoScenario.getClass().getMethod("demoTestMethod");
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);

        // Clear all indices
        IndexHelper.clearAllIndices();

        // Build log collection
        TestFeatureLogCollection feature1 = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("f1", "feature1");
        TestScenarioLogCollection scenario1 = TestScenarioLogCollection.createTestScenarioLogCollection("f1", "s1", "i1", "scenario1");
        scenario1.setAnnotations(new Annotation[]{testRailAnnotation});
        feature1.addScenarioLogCollection(scenario1);
        TestSuiteLogCollection suite = TestSuiteLogCollection.getInstance();
        suite.addTestClassLogCollection(feature1);

        // Test Subscriber
        UploadTestsSubscriber subscriber = Mockito.mock(UploadTestsSubscriber.class);
        subscriber.setUpClient();
        subscriber.onFinishedTesting("");

        // We set no status for the scenario log, so both handlers shouldn't be called
        Mockito.verify(subscriber, Mockito.times(0)).handleScenarioSuccess(Mockito.any());
        Mockito.verify(subscriber, Mockito.times(0)).handleScenarioFailure(Mockito.any(), Mockito.any());

        // Clear all indices
        IndexHelper.clearAllIndices();
        suite.clearCollection();
    }

    @Test(description = "Test the onFinishedTesting event handler with successful scenario")
    public void testOnFinishedTestingActivatedWithSuccessfulScenario() throws GeneralSecurityException, NoSuchMethodException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        // Build demo scenario
        DemoScenario demoScenario = new DemoScenario();
        Method method = demoScenario.getClass().getMethod("demoTestMethod");
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);

        // Clear all indices
        IndexHelper.clearAllIndices();

        // Build log collection
        TestFeatureLogCollection feature1 = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("f2", "feature2");
        TestScenarioLogCollection scenario1 = TestScenarioLogCollection.createTestScenarioLogCollection("f2", "s2", "i1", "scenario2");
        scenario1.setAnnotations(new Annotation[]{testRailAnnotation});
        scenario1.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        feature1.addScenarioLogCollection(scenario1);
        TestSuiteLogCollection suite = TestSuiteLogCollection.getInstance();
        suite.addTestClassLogCollection(feature1);

        // Test Subscriber
        UploadTestsSubscriber subscriber = Mockito.mock(UploadTestsSubscriber.class);
        Mockito.doCallRealMethod().when(subscriber).setUpClient();
        Mockito.doCallRealMethod().when(subscriber).onFinishedTesting(Mockito.any());
        subscriber.setUpClient();
        subscriber.onFinishedTesting("");

        // We set no status for the scenario log, so both handlers shouldn't be called
        Mockito.verify(subscriber, Mockito.times(1)).handleScenarioSuccess(Mockito.any());
        Mockito.verify(subscriber, Mockito.times(0)).handleScenarioFailure(Mockito.any(), Mockito.any());

        // Clear all indices
        IndexHelper.clearAllIndices();
        suite.clearCollection();
    }

    @Test(description = "Test the onFinishedTesting event handler with failed scenario")
    public void testOnFinishedTestingActivatedWithFailedScenario() throws GeneralSecurityException, NoSuchMethodException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        // Build demo scenario
        DemoScenario demoScenario = new DemoScenario();
        Method method = demoScenario.getClass().getMethod("demoTestMethod");
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);

        // Clear all indices
        IndexHelper.clearAllIndices();

        // Build log collection
        TestFeatureLogCollection feature1 = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists("f1", "feature1");
        TestScenarioLogCollection scenario1 = TestScenarioLogCollection.createTestScenarioLogCollection("f1", "s1", "i1", "scenario1");
        scenario1.setAnnotations(new Annotation[]{testRailAnnotation});
        scenario1.setStatus(TestScenarioLogCollection.Status.FAILURE);
        feature1.addScenarioLogCollection(scenario1);
        TestSuiteLogCollection suite = TestSuiteLogCollection.getInstance();
        suite.clearCollection();
        suite.addTestClassLogCollection(feature1);

        // Test Subscriber
        UploadTestsSubscriber subscriber = Mockito.mock(UploadTestsSubscriber.class);
        Mockito.doCallRealMethod().when(subscriber).setUpClient();
        Mockito.doCallRealMethod().when(subscriber).onFinishedTesting(Mockito.any());
        subscriber.setUpClient();
        subscriber.onFinishedTesting("");

        // We set no status for the scenario log, so both handlers shouldn't be called
        Mockito.verify(subscriber, Mockito.times(0)).handleScenarioSuccess(Mockito.any());
        Mockito.verify(subscriber, Mockito.times(1)).handleScenarioFailure(Mockito.any(), Mockito.any());

        // Clear all indices
        IndexHelper.clearAllIndices();
        suite.clearCollection();
    }

    @Test(description = "Test the onFinishedTesting event handler")
    public void testOnFinishedTestingDeactivated() throws GeneralSecurityException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, false);

        TestSuiteLogCollection logCollection = Mockito.mock(TestSuiteLogCollection.class);

        try (MockedStatic<TestSuiteLogCollection> suiteLogCollectionMock = Mockito.mockStatic(TestSuiteLogCollection.class)) {
            suiteLogCollectionMock.when(TestSuiteLogCollection::getInstance).thenReturn(logCollection);
            UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
            subscriber.setUpClient();
            subscriber.onFinishedTesting("");
            Mockito.verify(logCollection, Mockito.times(0)).getTestFeatureLogCollections();
        }
    }

    @Test
    public void testHandleScenarioSuccess() throws NoSuchMethodException, GeneralSecurityException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        DemoScenario demoScenario = new DemoScenario();
        Method method = demoScenario.getClass().getMethod("demoTestMethod");
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        subscriber.setUpClient();


        // See https://support.testrail.com/hc/en-us/articles/7077196481428-Attachments#getattachmentsforcase
        Attachments attachments = new Attachments();
        attachments.setOffset(0);
        attachments.setLimit(250);
        attachments.setSize(4);
        attachments.setLink(new Link(null, null));
        attachments.setAttachments(List.of(
                new Attachment(
                        "dll",
                        "other",
                        "3",
                        614308,
                        "case",
                        "msdia80.dll",
                        904704,
                        2,
                        1631722975,
                        "63c82867-526d-43be-b1a5-9ddfcf581cf5",
                        1,
                        "msdia80.dll",
                        0,
                        false,
                        "2ec27be4-812f-4806-9a5d-d39130d1691a"
                )
        ));

        try (MockedStatic<TestRailManager> manager = Mockito.mockStatic(TestRailManager.class)) {
            // Mock TestRailManager methods
            manager
                    .when(() -> TestRailManager.addAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString()))
                    .thenAnswer(invocation -> null);

            manager
                    .when(() -> TestRailManager.addResultForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                    .thenAnswer(invocation -> null);
            manager
                    .when(() -> TestRailManager.deleteAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()))
                    .thenAnswer(invocation -> null);
            manager
                    .when(() -> TestRailManager.getAttachmentsForTestCase(Mockito.any(APIClient.class), Mockito.anyString()))
                    .thenReturn(attachments);

            // Call subscriber method
            subscriber.handleScenarioSuccess(testRailAnnotation);

            // Check how many times manager methods were called
            manager.verify(
                    () -> TestRailManager.addAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString()),
                    Mockito.times(0)
            );

            manager.verify(
                    () -> TestRailManager.addResultForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()),
                    Mockito.times(1)
            );

            manager.verify(
                    () -> TestRailManager.deleteAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()),
                    Mockito.times(1)
            );

            manager.verify(
                    () -> TestRailManager.getAttachmentsForTestCase(Mockito.any(APIClient.class), Mockito.anyString()),
                    Mockito.times(1)
            );
        }
    }

    @Test
    public void testHandleScenarioFailure() throws NoSuchMethodException, GeneralSecurityException, MissingConfigurationValueException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        DemoScenario demoScenario = new DemoScenario();
        Method method = demoScenario.getClass().getMethod("demoTestMethod");
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        subscriber.setUpClient();

        // See https://support.testrail.com/hc/en-us/articles/7077196481428-Attachments#getattachmentsforcase
        Attachments attachments = new Attachments();
        attachments.setOffset(0);
        attachments.setLimit(250);
        attachments.setSize(4);
        attachments.setLink(new Link(null, null));
        attachments.setAttachments(List.of(
                new Attachment(
                        "dll",
                        "other",
                        "3",
                        614308,
                        "case",
                        "msdia80.dll",
                        904704,
                        2,
                        1631722975,
                        "63c82867-526d-43be-b1a5-9ddfcf581cf5",
                        1,
                        "msdia80.dll",
                        0,
                        false,
                        "2ec27be4-812f-4806-9a5d-d39130d1691a"
                )
        ));

        try (MockedStatic<TestRailManager> manager = Mockito.mockStatic(TestRailManager.class)) {
            // Mock TestRailManager methods
            manager
                    .when(() -> TestRailManager.addAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString()))
                    .thenAnswer(invocation -> null);

            manager
                    .when(() -> TestRailManager.addResultForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                    .thenAnswer(invocation -> null);
            manager
                    .when(() -> TestRailManager.deleteAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()))
                    .thenAnswer(invocation -> null);
            manager
                    .when(() -> TestRailManager.getAttachmentsForTestCase(Mockito.any(APIClient.class), Mockito.anyString()))
                    .thenReturn(attachments);

            // Create a scenario log collection with a single log message
            TestScenarioLogCollection scenario1 = TestScenarioLogCollection.createTestScenarioLogCollection(
                    "f1",
                    "s1",
                    "i1",
                    "scenario1"
            );
            StepInformationLogMessage step = new StepInformationLogMessage("foo.bar", "message");
            step.setStatus(StepInformationLogMessage.Status.ERROR);
            step.setScreenshotAfter("after.png");
            scenario1.addLogMessage(step);

            // Call subscriber method
            subscriber.handleScenarioFailure(scenario1, testRailAnnotation);

            // Check how many times manager methods were called
            manager.verify(
                    () -> TestRailManager.addAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString()),
                    Mockito.times(2)
            );

            manager.verify(
                    () -> TestRailManager.addResultForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()),
                    Mockito.times(1)
            );

            manager.verify(
                    () -> TestRailManager.deleteAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()),
                    Mockito.times(0)
            );

            manager.verify(
                    () -> TestRailManager.getAttachmentsForTestCase(Mockito.any(APIClient.class), Mockito.anyString()),
                    Mockito.times(0)
            );

            // Clear all indices
            IndexHelper.clearAllIndices();
        }
    }

    static class CaseIds {
        public static final String CASE1 = "c1";
    }

    static class RunIds {
        public static final String RUN1 = "1";
    }

    static class DemoScenario {
        @TestRail(caseId = {CaseIds.CASE1}, runId = RunIds.RUN1)
        public void demoTestMethod() {

        }
    }
}